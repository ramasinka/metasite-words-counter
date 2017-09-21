package lt.metasite.service;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class WordsFinderTest {

    private List<File> fileList;
    private WordsService wordsService;

    @Before
    public void setUp() {
        wordsService = new WordsService();
        fileList = new ArrayList<>();
    }

    @Test
    public void shouldFindSameWords() {
        fileList.add(new File("src/test/java/lt/metasite/files/same-words.txt"));

        Map<String, Integer> foundWords = wordsService.findWords(fileList);
        assertEquals(3, foundWords.size());
    }


    @Test
    public void shouldFindSameWordsInTwoFiles() {
        fileList.add(new File("src/test/java/lt/metasite/files/two_files/first-words.txt"));
        fileList.add(new File("src/test/java/lt/metasite/files/two_files/second-words.txt"));

        Map<String, Integer> foundWords = wordsService.findWords(fileList);
        assertEquals(6, foundWords.size());
        assertEquals(Integer.valueOf(1), foundWords.get("how"));
        assertEquals(Integer.valueOf(5), foundWords.get("goodmorning"));
        assertEquals(Integer.valueOf(4), foundWords.get("hello"));
        assertEquals(Integer.valueOf(2), foundWords.get("bye"));
        assertEquals(Integer.valueOf(2), foundWords.get("are"));
        assertEquals(Integer.valueOf(1), foundWords.get("you"));
    }

    @Test
    public void shouldFindSameWordsWithOtherSymbols() {
        fileList.add(new File("src/test/java/lt/metasite/files/words-other-symbols.txt"));
        Map<String, Integer> foundWords = wordsService.findWords(fileList);

        assertEquals(11, foundWords.size());
        assertEquals(Integer.valueOf(2), foundWords.get("hello"));
        assertEquals(Integer.valueOf(2), foundWords.get("how"));
    }
}
