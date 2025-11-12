//everything about a single card
public class Card {
    private String rank;  // A, 2-10, J, Q, K
    private String suit; // Hearts, Diamonds, Clubs, Spaces

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getValue() {
        if (rank.equals("J") || rank.equals("Q") || rank.equals("K")) {
            return 10;
        }
        if (rank.equals("A")) {
            return 11; // Ace = 11 for now, conditions will rewrite when calculating scores
        }
        return Integer.parseInt(rank);   // return int 2-10 if rank = "2"-"10"
    }

    @Override
    public String toString() {
        return rank + suit;
    }

    public String rankString() {
        return rank;
    }

}
