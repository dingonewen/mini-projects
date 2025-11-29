import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.HashSet;

public class EvilSolution {
    private ArrayList<String> currentWordList;
    private ArrayList<Character> partialSolution;
    private final int wordLength;
    private int missingChars;

    public EvilSolution(ArrayList<String> wordList) {
        if (wordList == null || wordList.isEmpty()) {                  // check if wordList is empty or null
            throw new IllegalArgumentException("Word list cannot be empty");
        }
        HashSet<Integer> lengths = new HashSet<>();                    // gather all lengths of the words, "Select a word of random length as initial target"
        for (String word : wordList) {
            lengths.add(word.length());
        }
        ArrayList<Integer> lengthList = new ArrayList<>(lengths);
        int randomLength = lengthList.get(new Random().nextInt(lengthList.size()));      // use a randomized length
        ArrayList<String> wordsOfLength = new ArrayList<>();             // filter to only include words of the selected length, prevent IndexOutOfBoundsException
        for (String word : wordList) {
            if (word.length() == randomLength) {
                wordsOfLength.add(word);
            }
        }
        this.currentWordList = wordsOfLength;          // use filtered list with the randomized length
        wordLength = randomLength;
        missingChars = wordLength;
        partialSolution = new ArrayList<>(missingChars);
        for (int i = 0; i < wordLength; i++) {
            partialSolution.add('_');
        }
    }

    public boolean isSolved() {
        return missingChars == 0;
    }

    public void printProgress() {
        for (char c : partialSolution) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public boolean addGuess(char guess) {
        HashMap<ArrayList<Character>, ArrayList<String>> families = patternGroup(guess);    // group currentWordList into different word families by pattern
        ArrayList<Character> updatedPattern = null;
        int maxSize = 0;
        for (ArrayList<Character> pattern : families.keySet()) {
            if (families.get(pattern).size() > maxSize) {
                maxSize = families.get(pattern).size();
                updatedPattern = pattern;
            }
        }
        boolean guessCorrect = !updatedPattern.equals(partialSolution);    // check if updatedPattern is the same as the partialSolution
        if (guessCorrect) {                                                // same means guess is incorrect, different means guess is correct
            for (int i = 0; i < wordLength; i++) {                         // if pattern changes, update partialSolution
                if (partialSolution.get(i) == '_' && updatedPattern.get(i) != '_') {
                    missingChars--;
                }
            }
        }
        partialSolution = new ArrayList<>(updatedPattern);
        currentWordList = families.get(updatedPattern);          // narrow down currentWordList to only have words that fit the current pattern
        return guessCorrect;
    }

    // Helper method 1: Group the currentWordList by different patterns into word families
    // Each word family is a list of words sharing the same pattern
    // e.g., pattern "-e--" → word family ["heal", "belt"]
    //       pattern "e---" → word family ["echo"]
    private HashMap<ArrayList<Character>, ArrayList<String>> patternGroup(char guess) {
        HashMap<ArrayList<Character>, ArrayList<String>> families = new HashMap<>();
        for (String word : currentWordList) {
            ArrayList<Character> pattern = createPattern(word, guess);    // call another helper method 2 to define pattern
            if (!families.containsKey(pattern)) {
                families.put(pattern, new ArrayList<>());       // if pattern doesn't exist, add pattern and create ArrayList for value
            }
            families.get(pattern).add(word);                    // add word as value to the HashMap, iterate all words in the currentWordList
        }
        return families;
    }

    // Helper method 2: Generate pattern for a word based on the guessed letter
    // Takes the current partialSolution and reveals positions where 'guess' appears in 'word'
    // e.g., if partialSolution is "----" and word is "heal" with guess 'e',
    //       returns "-e--"
    private ArrayList<Character> createPattern(String word, char guess) {
        ArrayList<Character> pattern = new ArrayList<>(partialSolution);        // defensive copying
        for (int i = 0; i < word.length(); i++) {                               // logic similar to addGuess() in Hangman, add words to pattern
            if (word.charAt(i) == guess) {
                pattern.set(i, guess);
            }
        }
        return pattern;
    }

    public String getTarget() {
        return currentWordList.get(0);
    }

}
