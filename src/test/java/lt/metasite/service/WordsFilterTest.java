package lt.metasite.service;

import lt.metasite.data.WordFilter;
import lt.metasite.threads.WordsCountFileWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;


public class WordsFilterTest {

    private WordsService wordsService;
    private List<File> fileList;

    @Before
    public void setUp() {
        wordsService = new WordsService();
        fileList = new ArrayList<>();
    }

    @Test
    public void shouldFilterWordsByInterval() throws Exception {
        WordFilter wordFilter = new WordFilter('A', 'D');
        String path = "src/test/java/lt/metasite/files/";
        String fileName = "src/main/webapp/display/words-interval-" + wordFilter.getFrom() + "-" + wordFilter.getTill() + ".txt";
        fileList.add(new File(path + "filter-words.txt"));

        Map<String, Integer> foundWords = wordsService.findWords(fileList);
        WordsCountFileWriter wordsCountFileWriter = new WordsCountFileWriter(fileName, foundWords, wordFilter);
        wordsCountFileWriter.run();
        Map<String, Integer> filteredWords = wordsCountFileWriter.getFilteredWords();
        assertEquals(8, foundWords.size());
        assertEquals(2, filteredWords.size());
    }

    @Test
    public void shouldWriteFilteredWordsToFile() throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        String path = "src/test/java/lt/metasite/files/";
        fileList.add(new File(path + "filter-words.txt"));
        Map<String, Integer> foundWords = wordsService.findWords(fileList);

        WordFilter wordFilter = new WordFilter('A', 'G');
        String fileName = path + "words-interval-" + wordFilter.getFrom() + "-" + wordFilter.getTill() + ".txt";
        File file = new File(fileName);

        WordsCountFileWriter wordsCountFileWriter = new WordsCountFileWriter(fileName, foundWords, wordFilter);
        wordsCountFileWriter.run();
        Map<String, Integer> filteredWords = wordsCountFileWriter.getFilteredWords();

        assertEquals(8, foundWords.size());
        assertEquals(3, filteredWords.size());


        String name = "src/main/webapp/display/words-interval-" + wordFilter.getFrom() + "-" + wordFilter.getTill() + ".txt";
        wordsCountFileWriter = new WordsCountFileWriter(name, foundWords, wordFilter);
        executorService.submit(wordsCountFileWriter);
        fileList.remove(0);
        fileList.add(file);

        foundWords = wordsService.findWords(fileList);
        assertEquals(3, foundWords.size());
    }
}