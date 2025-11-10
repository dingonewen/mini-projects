import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellChecker {
    private Scanner inputReader; // DO NOT MODIFY, Use this field everytime you need to read user input
    private Scanner dicFileReader;
    private Scanner chkFileReader;
    private PrintWriter writer;

    public SpellChecker() {
        inputReader = new Scanner(System.in);
        dicFileReader = null;
        chkFileReader = null;
    }

    public void start() {
        String dicFileName = "";
        String chkFileName = "";
        String chkFileNameUpdate = "";
        System.out.printf(Util.DICTIONARY_PROMPT);
        while (dicFileReader == null) {
            try {
                dicFileName = inputReader.nextLine();
                File dicFile = new File(dicFileName);
                dicFileReader = new Scanner(dicFile);
                System.out.printf(Util.DICTIONARY_SUCCESS_NOTIFICATION, dicFileName);
            } catch (FileNotFoundException e) {
                System.out.printf(Util.FILE_OPENING_ERROR);
                System.out.printf(Util.DICTIONARY_PROMPT);
            }
        }
        System.out.printf(Util.FILENAME_PROMPT);
        while (chkFileReader == null) {
            try {
                chkFileName = inputReader.nextLine();
                File checkFile = new File(chkFileName);
                chkFileReader = new Scanner(checkFile);   // open the chkFile here
                chkFileNameUpdate = chkFileName.replace(".txt", "_chk.txt");
                System.out.printf(Util.FILE_SUCCESS_NOTIFICATION, chkFileName, chkFileNameUpdate);
            } catch (FileNotFoundException e) {
                System.out.printf(Util.FILE_OPENING_ERROR);
                System.out.printf(Util.FILENAME_PROMPT);
            }
        }

        WordRecommender wr = new WordRecommender(dicFileName);
        try {
            // combine FileOutputStream and PrintWriter so no need to try/catch twice
            writer = new PrintWriter(chkFileNameUpdate);   // create writer once here so it won't overwrite
            while (chkFileReader.hasNext()) {
                String word = chkFileReader.next();
                if (!wr.getCandidateSet().contains(word)) {
                    int tolerance = 2;     // @para set in the project requirement
                    int topN = 4;
                    double commonPercent = 0.5;
                    System.out.printf(Util.MISSPELL_NOTIFICATION, word);
                    System.out.printf(Util.FOLLOWING_SUGGESTIONS);
                    ArrayList<String> sugArray = wr.getWordSuggestions(word, tolerance, commonPercent, topN);
                    if (!sugArray.isEmpty()) {
                        for (int i = 0; i < sugArray.size(); i++) {
                            System.out.printf(Util.SUGGESTION_ENTRY, i + 1, sugArray.get(i));
                        }
                        System.out.printf(Util.THREE_OPTION_PROMPT);
                        boolean choice1 = false;
                        while (!choice1) {
                            char promptc = inputReader.next().charAt(0);
                            if (promptc == 'r') {
                                System.out.printf(Util.AUTOMATIC_REPLACEMENT_PROMPT);
                                int prompti = -1;
                                while (prompti < 1 || prompti > sugArray.size()) {
                                    if (inputReader.hasNextInt()) {
                                        prompti = inputReader.nextInt();
                                        inputReader.nextLine();   // clear the space after nextInt()
                                        if (prompti < 1 || prompti > sugArray.size()) {
                                            System.out.printf(Util.INVALID_RESPONSE);
                                            System.out.printf(Util.AUTOMATIC_REPLACEMENT_PROMPT);
                                        }
                                    } else {
                                        System.out.printf(Util.INVALID_RESPONSE);
                                        System.out.printf(Util.AUTOMATIC_REPLACEMENT_PROMPT);
                                        inputReader.nextLine();  // clear the space after nextInt()
                                    }
                                }
                                writer.print(sugArray.get(prompti - 1) + " ");
                                choice1 = true;
                            } else if (promptc == 'a') {
                                writer.print(word + " ");
                                choice1 = true;
                            } else if (promptc == 't') {
                                System.out.printf(Util.MANUAL_REPLACEMENT_PROMPT);
                                word = inputReader.next();
                                writer.print(word + " ");
                                choice1 = true;  // input after 't' can be any typed input
                            } else {
                                System.out.printf(Util.INVALID_RESPONSE);
                            }
                        }
                    } else {
                        System.out.printf(Util.NO_SUGGESTIONS);
                        System.out.printf(Util.TWO_OPTION_PROMPT);
                        boolean choice2 = false;
                        while (!choice2) {
                            char promptc = inputReader.next().charAt(0);
                            if (promptc == 'a') {
                                writer.print(word + " ");
                                choice2 = true;
                            } else if (promptc == 't') {
                                System.out.printf(Util.MANUAL_REPLACEMENT_PROMPT);
                                word = inputReader.next();
                                writer.print(word + " ");
                                choice2 = true;   // input after 't' can be any typed input
                            } else {
                                System.out.printf(Util.INVALID_RESPONSE);
                            }
                        }
                    }
                } else {
                    writer.print(word + " ");
                }
            }
            // close inside try to avoid the condition when writing cannot be completed
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.printf(Util.FILE_OPENING_ERROR);  // PrintWriter
        }
        inputReader.close();  // DO NOT MODIFY - must be the last line of this method!
        dicFileReader.close();
        chkFileReader.close();
    }
}