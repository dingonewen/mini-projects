import java.util.ArrayList;

public class Score {
    private Board board;

    public Score() {
        board = new Board();
    }

    public int calculateTotalScore(Board board) {  // Loops through all 4 rows and 5 columns, calls calculateH and Score for each, and sums the results
        int sumScore = 0;
        for (int row = 0; row < 4; row++) {
            ArrayList<Card> rowHand = new ArrayList<>();  // create a new rowHand in every row
            for (int col = 0; col < 5; col++) {
                if (board.getCardAt(row, col) != null) {
                    rowHand.add(board.getCardAt(row, col));
                }
            }
            sumScore += calculateHandScore(rowHand);
        }
        for (int col = 0; col < 5; col++) {
            ArrayList<Card> colHand = new ArrayList<>();  // create a new colHand in every col
            for (int row = 0; row < 4; row++) {
                if (board.getCardAt(row, col) != null) {
                    colHand.add(board.getCardAt(row, col));
                }
            }
            sumScore += calculateHandScore(colHand);
        }
        return sumScore;
    }

    public int calculateHandScore(ArrayList<Card> hand) {  //The core scoring logic for a single hand
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
