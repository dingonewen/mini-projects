import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public Deck(int cardsPerColor) {       // if cardsPerColor >= 10, construct 4 colors for num 0-9, 40 in total;
        cards = new ArrayList<>();         // if cardsPerColor < 10, construct 4 colors of this num of cards
        for (int i = 0; i < cardsPerColor; i++) {
            Card red = new Card("red", i % 10);
            Card yellow = new Card("yellow", 1 % 10);
            Card blue = new Card("blue", i % 10);
            Card green = new Card("green", i % 10);
            cards.add(red);
            cards.add(yellow);
            cards.add(blue);
            cards.add(green);
        }
    }

    public void shuffle() {        // randomized the order of the cards
        Collections.shuffle(cards);
    }

    public Card getTopCard() {
        return cards.get(0);
    }

    public void addCard(Card newCard) {
        cards.add(0, newCard);
    }

    public Card removeTopCard() {
        return cards.remove(0);
    }

    /**
     * isEmpty() method encapsulates the internal ArrayList, allowing the implementation
     * to be changed in the future without affecting external code.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void reshuffle(Deck otherCards) {
        Card topCard = otherCards.removeTopCard();
        while (!otherCards.isEmpty()) {
            cards.add(otherCards.removeTopCard());
        }
        shuffle();
        otherCards.addCard(topCard);
    }
}
