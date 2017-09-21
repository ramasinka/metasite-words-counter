package lt.metasite.data;


import java.util.Map;

public class WordFilter {

    private char from;
    private char till;
    private Map<String, Integer> foundWords;

    public WordFilter(char from, char till) {
        this.from = from;
        this.till = till;
    }

    public WordFilter(Map<String, Integer> foundWords) {
        this.foundWords = foundWords;
    }

    public char getFrom() {
        return from;
    }

    public char getTill() {
        return till;
    }

    public Map<String, Integer> getFoundWords() {
        return foundWords;
    }

    public void setFoundWords(Map<String, Integer> foundWords) {
        this.foundWords = foundWords;
    }
}
