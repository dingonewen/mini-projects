import java.util.Scanner;

public class Board {
    private final Scanner input = new Scanner(System.in);
    private Card[][] gameBoard;
    private int discardedRemaining;
    private boolean[] discardUsed;

    public Board() {
        gameBoard = new Card[4][5];
        discardedRemaining = 4;
        discardUsed = new boolean[4];
    }

    public void displayBoard() {  //Prints the current state of the gameBoard
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

    public int getValidInput() {     //Handles user input, including error checking for invalid or taken spots
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

    public void placeCard(Card card, int position) {
        int[] coords = positionToRowCol(position);
        gameBoard[coords[0]][coords[1]] = card;
    }

    public int[] positionToRowCol(int position) {       //mapping integer to gameBoard position
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

    public boolean isFull() {
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

    public Card getCardAt(int row, int col) {  // Score will need it
        return gameBoard[row][col];
    }
}
