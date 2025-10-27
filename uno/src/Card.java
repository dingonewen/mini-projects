public class Card {
    private int num;
    private String color;

    public Card(String color, int num) {
        this.num = num;
        this.color = color;
    }
    public boolean equals(Card c) {
        return this.num == c.num || this.color.equals(c.color);
    }

    public String toString() {
        return color + " " + num;
    }

}
