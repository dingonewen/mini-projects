import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class WordRecommender {
    private String dictionaryFile;
    private HashSet<String> candidateSet;

    public WordRecommender(String dictionaryFile) {
        this.dictionaryFile = dictionaryFile;    // receive the dictionary file name from SpellChecker
        candidateSet = new HashSet<>();
        loadDictionaryFile();  // load dictionary in constructor so it doesn't have to be loaded every time we call getWordSuggestions() - that would be super inefficient
    }

    public HashSet<String> getCandidateSet() {
        return candidateSet;
    }

    public void loadDictionaryFile() {
        try {
            Scanner fileReader = new Scanner(new File(dictionaryFile));
            while (fileReader.hasNext()) {
                candidateSet.add(fileReader.next());  // assuming dictionary words are all lowercase, separated with spaces
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not find the dictionary file!");
        }
    }

    // Percentage in Common: # of characters in aSet AND in bSet / # of characters in aSet OR in bSet
    public double calculatePerInCom(String a, String b) {
        HashSet<Character> aSet = new HashSet<>();
        HashSet<Character> bSet = new HashSet<>();
        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            aSet.add(c);
        }
        for (int i = 0; i < b.length(); i++) {
            char c = b.charAt(i);
            bSet.add(c);
        }
        HashSet<Character> totalSet = new HashSet<>(aSet);  // can't be initialized at the top, otherwise they are all empty
        HashSet<Character> commonSet = new HashSet<>(aSet);
        totalSet.addAll(bSet);   // keep the union
        commonSet.retainAll(bSet);  // keep the intersection
        double percentInCom = (double) commonSet.size() / totalSet.size();
        return percentInCom;
    }

    public double getSimilarity(String word1, String word2) {
        double leftSim = 0;
        double rightSim = 0;
        for (int i = 0; i < Math.min(word1.length(), word2.length()); i++) {
            if (word1.charAt(i) == word2.charAt(i)) {
                leftSim++;
            }
        }
        for (int i = 0; i < Math.min(word1.length(), word2.length()); i++) {
            if (word1.charAt(word1.length() - i - 1) == word2.charAt(word2.length() - i - 1)) {
                rightSim++;
            }
        }
        return (leftSim + rightSim) / 2;
    }

    // premise: call getWordSuggestions with: tolerance = 2, commonPercent = 0.5, topN = 4
    public ArrayList<String> getWordSuggestions(String word, int tolerance, double commonPercent, int topN) {
        ArrayList<String> candidateArray = new ArrayList<>();
        // we've loaded the dictionaryFile in constructor - loadDictionaryFile()
        for (String dicWord : candidateSet) {
            if ((Math.abs(word.length() - dicWord.length()) <= tolerance && calculatePerInCom(word, dicWord) >= commonPercent)) {
                candidateArray.add(dicWord);
            }
        }
        HashMap<String, Double> simMap = new HashMap<>();  //
        for (String dicWord : candidateArray) {
            simMap.put(dicWord, getSimilarity(word, dicWord));
        }
        // algo: start from index 0, iterate to find the max in the rest indexes, swap the max to current index, index++ and repeat
        for (int i = 0; i < candidateArray.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < candidateArray.size(); j++) {
                if (simMap.get(candidateArray.get(j)) > simMap.get(candidateArray.get(maxIndex))) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {   // swap in the inner loop, remember to swap with temp
                String temp = candidateArray.get(i);
                candidateArray.set(i, candidateArray.get(maxIndex));
                candidateArray.set(maxIndex, temp);
            }
        }
        // subList the candidateArray after the HashMap to avoid duplicates in the dictionary(?) that make the candidateArray shorter than topN after subList
        if (candidateArray.size() >= topN) {
            return new ArrayList<>(candidateArray.subList(0, topN)); // 0 included, topN excluded
        } else {
            return candidateArray;
        }
    }
}