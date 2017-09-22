package lt.metasite.threads;


import lt.metasite.data.WordFilter;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WordsCountFileWriter implements Runnable {

    private String fileName;
    private Map<String, Integer> foundWords;
    private WordFilter wordFilter;
    private Map<String, Integer> filteredWords = new HashMap<>();

    public WordsCountFileWriter(String fileName, Map<String, Integer> foundWords, WordFilter wordFilter) {
        this.fileName = fileName;
        this.foundWords = foundWords;
        this.wordFilter = wordFilter;
    }

    @Override
    public void run() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new java.io.FileWriter(new File(fileName)));
            Map<String, Integer> filteredWords = filterWords();
            filteredWords
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(map -> {
                        try {
                            bufferedWriter.write(map.getKey() + " " + map.getValue());
                            bufferedWriter.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.getStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, Integer> filterWords() throws Exception {
        List<Character> chars = getChars(wordFilter.getFrom(), wordFilter.getTill());
        foundWords
                .entrySet()
                .stream()
                .forEach(map -> {
                    String word = map.getKey();
                    char first = word.charAt(0);
                    if (chars.contains(first)) {
                        filteredWords.put(word, map.getValue());
                    }
                });
        return filteredWords;
    }

    private List<Character> getChars(char at, char till) {
        Set<Character> characters = new HashSet<>();
        for (char c = at; c <= till; c++) {
            characters.add(c);
        }
        return characters.stream().map(Character::toLowerCase).collect(Collectors.toList());
    }

    public Map<String, Integer> getFilteredWords() {
        return filteredWords;
    }
}