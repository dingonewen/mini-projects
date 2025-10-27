import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private boolean isComputer;
    private ArrayList<Card> hand;
    private static Scanner inputReader = new Scanner(System.in);

    public Player(boolean isComputer) {
        this.isComputer = isComputer;
        this.hand = new ArrayList<>();
    }

    public void drawCard(Card newCard) {
        // Player should take the top card from the draw pile
        hand.add(newCard);
    }

    public boolean handEmpty() {
        return hand.isEmpty();
    }
    public Card playCard(Card topCard) {
        if (isComputer) {
            computerTurn(topCard);
        } else {
            humanTurn(topCard);
        }
        return null;
    }

    private boolean validCards(Card topCard) {
        for (int i = 0; i < hand.size(); i++) {
            if (topCard.equals(hand.get(i))) {
                return true;
            }
        }
        return false;
    }

    private void printHand() {    // prints the current cards in hand
        System.out.print("Your hand: [");
        int i;                   // declare i outside the loop so it can be used outside the loop too
        for (i = 0; i < hand.size(); i++) {
            System.out.printf("%s", hand.get(i));
        }
        System.out.println("]");
    }

    private Card computerTurn(Card topCard) {
        // play the top card that works, make it a dumb computer player at this moment
        for (int i = 0; i < hand.size(); i++) {
            if (topCard.equals(hand.get(i))) {
                return hand.remove(i);
            }
        }
        return null;
    }

    private Card humanTurn(Card topCard) {
        // different cuz needs user input, and know what cards are in hand
        printHand();
        // tell the player if it has any valid cards to play
        if (!validCards(topCard)) {
            System.out.println("You have no valid cards to play.");
            return null;
        }
        // if no valid cards to play, play should draw a car
        System.out.printf("Select a card to play by index (0 through %d): ", hand.size() - 1);
        // select a card to play if applicable, otherwise pass turn, need a scanner, reading index input and access to the discard pile
        int index = inputReader.nextInt();
        while (index < 0 || index >= hand.size()||!topCard.equals(hand.get(index))) {
            System.out.printf("Invalid card. Top card: %s%n", topCard);
            printHand();
            System.out.print("Please select a valid card to play: ");
            index = inputReader.nextInt();
        }
        return hand.remove(index);
    }
}
