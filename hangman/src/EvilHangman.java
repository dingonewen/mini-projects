import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

public class EvilHangman {
    private ArrayList<String> wordList;
    private HashSet<Character> previousGuesses;
    private TreeSet<Character> incorrectGuesses; // behaves like a hash set, but orders the entries!
    private EvilSolution evilSolution;
    private Scanner inputReader;

    public EvilHangman() {
        this("engDictionary.txt");
    }

    public EvilHangman(String filename) {
        try {
            wordList = dictionaryToList(filename);
        } catch (IOException e) {
            System.out.printf(
                    "Couldn't read from the file %s. Verify that you have it in the right place and try running again.",
                    filename);
            e.printStackTrace();
            System.exit(0); // stop the program--no point in trying if you don't have a dictionary
        }

        previousGuesses = new HashSet<>();
        incorrectGuesses = new TreeSet<>();          // use TreeSet to print out incorrectGuesses in a sorted list
        HashSet<Integer> lengths = new HashSet<>();    // choose word by a specific length rather than choose word randomly
        for (String word : wordList) {
            lengths.add(word.length());
        }

        ArrayList<Integer> lengthList = new ArrayList<>(lengths);
        int randomLength = lengthList.get(new Random().nextInt(lengthList.size()));     // randomize out a specific length

        ArrayList<String> wordsOfLength = new ArrayList<>();    // filter in all words at this length
        for (String word : wordList) {
            if (word.length() == randomLength) {
                wordsOfLength.add(word);
            }
        }
        evilSolution = new EvilSolution(wordsOfLength);
        inputReader = new Scanner(System.in);
    }

    private static ArrayList<String> dictionaryToList(String filename) throws IOException {
        try (FileInputStream fileInput = new FileInputStream(filename);         // try-with-resources
             Scanner fileReader = new Scanner(fileInput)) {
            ArrayList<String> wordList = new ArrayList<>();
            while (fileReader.hasNext()) {
                wordList.add(fileReader.next());
            }
            return wordList;
        }
    }

    public void start() {
        while (!evilSolution.isSolved()) {
            char guess = promptForGuess();
            recordGuess(guess);
        }
        printVictory();
    }

    private char promptForGuess() {
        while (true) {
            System.out.println("Guess a letter.\n");
            evilSolution.printProgress();
            System.out.println("Incorrect guesses:\n" + incorrectGuesses.toString());
            String input = inputReader.next();
            if (input.length() != 1) {
                System.out.println("Please enter a single character.");
            } else if (previousGuesses.contains(input.charAt(0))) {
                System.out.println("You've already guessed that.");
            } else if (!Character.isAlphabetic(input.charAt(0))) {
                System.out.println("Please enter an alphabetic character.");
            } else {
                return input.charAt(0);
            }
        }
    }

    private void recordGuess(char guess) {
        previousGuesses.add(guess);
        boolean guessCorrect = evilSolution.addGuess(guess);
        if (!guessCorrect) {
            incorrectGuesses.add(guess);
        }
    }

    private void printVictory() {
        System.out.printf("Congrats! The word was %s%n", evilSolution.getTarget());
    }

}




