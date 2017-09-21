package lt.metasite.service;


import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WordsService {

    public Map<String, Integer> findWords(List<File> files) {
        Map<String, Integer> foundWords = new HashMap<>();
        for (File file : files) {
            try (Stream<String> stream = Files.lines(Paths.get(file.getPath()))) {
                stream.forEach(s -> {
                    StringBuilder word = new StringBuilder();
                    char[] chars = s.toCharArray();
                    for (char ch : chars) {
                        if (Character.isLetter(ch)) {
                            word.append((char) ch);
                        } else {
                            addToFoundWords(word, foundWords);
                            word.setLength(0);
                        }
                    }
                    if (word.length() != 0) {
                        addToFoundWords(word, foundWords);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return foundWords;
    }

    private void addToFoundWords(StringBuilder stringBuilder, Map<String, Integer> foundWords) {
        String word = stringBuilder.toString().toLowerCase();
        if (!word.isEmpty()) {
            if (foundWords.containsKey(word)) {
                foundWords.put(word, foundWords.get(word) + 1);
            } else {
                foundWords.put(word, 1);
            }
        }
    }
}
