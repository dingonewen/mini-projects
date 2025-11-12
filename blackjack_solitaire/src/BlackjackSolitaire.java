import java.util.ArrayList;
import java.util.Scanner;

//final implementation, rules, flow of the game
public class BlackjackSolitaire {
    private Deck deck;
    private Card[][] gameBoard;
    private int discardedRemaining;
    private Scanner input = new Scanner(System.in);
    private boolean[] discardUsed = new boolean[4];  // check if the discard slots were taken


    public BlackjackSolitaire() {
        deck = new Deck();
        gameBoard = new Card[4][5];
        discardedRemaining = 4;
    }
    public void play() {
        System.out.println("Welcome to the game Blackjack Solitaire. The game is on!");
        while (!boardIsFull()) {
            deck.shuffle();
            displayBoard();
            Card card = deck.deal();
            deck.shuffle();
            System.out.println("Drawing a card...the card dealt is: " + card);
            System.out.println("Choose one position on the game board to either place the card (1 - 16) or discard the card (17 - 20):");
            int selectedPosition = getValidInput();
            if (selectedPosition != -1) {   // fix the bug that when card is discard, the new card dealt is not displayed
                placeCard(card, selectedPosition);
            }
        }
        int totalScore = calculateTotalScore();
        System.out.println("Game over! Calculating final score...");
        System.out.println("※※※ Your final score is: " + totalScore + "! ※※※");
        System.out.println("Thank you for your time. If you'd like to play again, please re-run the program.");
    }

    private void displayBoard() {  //Prints the current state of the gameBoard
        System.out.print("==== Game Board Current Layout ====");
        for (int row = 0; row < 2; row++) {
            System.out.println();   // switch line whenever switch row
            for (int col = 0; col < 5; col++) {
                if (gameBoard[row][col] == null) {
                    int positionNum = 5 * row + col + 1;
                    System.out.print("[" + Integer.toString(positionNum) + "]" + "    ");
                } else {
                    System.out.print(gameBoard[row][col].toString() + "     ");
                }
            }
        }
        for (int row = 2; row < 4; row++) {
            System.out.println();
            for (int col = 0; col < 5; col++) {
                if (col == 0 || col == 4) {
                    System.out.print("      ");
                } else if (gameBoard[row][col] == null) {
                    int positionNum = 10 + 3 * (row - 2) + col;
                    System.out.print("[" + Integer.toString(positionNum) + "]" + "    ");
                } else {
                    System.out.print(gameBoard[row][col].toString() + "     ");
                }
            }
        }
        System.out.println();
        System.out.println("===================================");
    }

    private int getValidInput() {     //Handles user input, including error checking for invalid or taken spots
        while (true) {     // loop until return the chosen position or discardedRemaining
            if (input.hasNextInt()) {
                int position = input.nextInt();
                if (position <= 0 || position > 20) {
                    input.nextLine();
                    System.out.println("Invalid integer input. Prompt the position again (1 - 20):");
                } else if (17 <= position && position <= 20) {
                    if (discardedRemaining == 0) {
                        input.nextLine();
                        System.out.println("Discard slots are full. Prompt another position on the game board.");
                        displayBoard();
                    } else {
                        input.nextLine();
                        if (discardUsed[position - 17]) {
                            System.out.println("This discard slot was taken. Prompt another position.");
                            displayBoard();
                        } else {
                            discardedRemaining--;
                            discardUsed[position - 17] = true;
                            System.out.println("Card was discarded. You have " + discardedRemaining + " discards remaining.");
                            System.out.println("Drawing another card...");
                            return -1;    // fixing the bug that it leaves the getValidInput w/o display the new card dealt
                        }
                    }
                } else if (1 <= position && position <= 16 ) {
                    int[] coords = positionToRowCol(position);
                    if (gameBoard[coords[0]][coords[1]] != null) {
                        input.nextLine();
                        System.out.println("This position was taken. Prompt another position:");
                        displayBoard();
                    } else {
                        input.nextLine();
                        System.out.println("Card was successfully placed. Game board updated.");
                        return position;
                    }
                }
            } else {
                input.nextLine();
                System.out.println("Invalid input. Prompt the position again (1 - 20):");
                displayBoard();
            }
        }
    }

    private void placeCard(Card card, int position) {
        int[] coords = positionToRowCol(position);
        gameBoard[coords[0]][coords[1]] = card;
    }

    private int[] positionToRowCol(int position) {       //mapping integer to gameBoard position
        if (position >= 1 && position <= 5) {
            return new int[]{0, position - 1};
        } else if (position >= 6 && position <= 10) {
            return new int[]{1, position - 6};
        } else if (position >= 11 && position <= 13) {
            return new int[]{2, position - 10};
        } else if (position >= 14 && position <= 16) {
            return new int[]{3, position - 13};
        } else {
            return null;
        }
    }

    private boolean boardIsFull() {
        int count = 0;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 5; col++) {
                if (gameBoard[row][col] == null) {
                    count++;
                }
            }
        }
        for (int row = 2; row < 4; row++) {
            for (int col = 1; col < 4; col++) {
                if (gameBoard[row][col] == null) {
                    count++;
                }
            }
        }
        return count == 0;
    }

    private int calculateTotalScore() {  // Loops through all 4 rows and 5 columns, calls calculateH and Score for each, and sums the results
        int sumScore = 0;
        for (int row = 0; row < 4; row++) {
            ArrayList<Card> rowHand = new ArrayList<>();  // create a new rowHand in every row
            for (int col = 0; col < 5; col++) {
                if (gameBoard[row][col] != null) {
                    rowHand.add(gameBoard[row][col]);
                }
            }
            sumScore += calculateHandScore(rowHand);
        }
        for (int col = 0; col < 5; col++) {
            ArrayList<Card> colHand = new ArrayList<>();  // create a new colHand in every col
            for (int row = 0; row < 4; row++) {
                if (gameBoard[row][col] != null) {
                    colHand.add(gameBoard[row][col]);
                }
            }
            sumScore += calculateHandScore(colHand);
        }
        return sumScore;
    }

    private int calculateHandScore(ArrayList<Card> hand) {  //The core scoring logic for a single hand
        int sum = 0;
        int aceCount = 0;
        int score = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (!hand.get(i).rankString().equals("A")) {
                sum += hand.get(i).getValue();
            } else {
                aceCount++;
            }
        }
        sum += aceCount * 11;
        while (sum > 21 && aceCount > 0) {
            sum -= 10;
            aceCount--;
        }
        if (sum == 21) {
            if (hand.size() == 2) {
                score = 10;
            } else {
                score = 7;
            }
        } else if (sum == 20) {
            score = 5;
        } else if (sum == 19) {
            score = 4;
        } else if (sum == 18) {
            score = 3;
        } else if (sum == 17) {
            score = 2;
        } else if (sum <= 16) {
            score = 1;
        } else {
            score = 0;
        }
        return score;
    }
}
