import java.util.ArrayList;
import java.util.Collections;

// manage a collection of 52 cards
public class Deck {
    private ArrayList<Card> cards;

    public Deck() {    // construct a collection of 52 cards
        cards = new ArrayList<>(52);
        String[] suits = {"H","D","C","S"};
        String[] ranks = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
        for (String suit : suits) {
            for (String rank : ranks) {
                Card oneCard = new Card(rank, suit);
                cards.add(oneCard);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card deal() {    // return the top card of the deck and removes it
        return cards.remove(0);
    }

    // TEST
    @Override
    public String toString() {    // use Deck.toString() to print deck
        String result = "";
        for (Card oneCard : cards) {
            result += oneCard.toString() + " ";
        }
        return result;
    }
}
