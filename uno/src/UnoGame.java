public class UnoGame {
    private Player[] players;
    private Deck drawPile;
    private Deck discardPile;

    /**
     * Constructs an UnoGame with the given number of Players.
     * There will always be only one human Player.
     *
     * @param numPlayers Number of players
     */
    public UnoGame(int numPlayers) {
        players = new Player[numPlayers];
        players[0] = new Player(false);
        for (int i = 1; i < numPlayers; i++) {
            players[i] = new Player(true);
        }
        drawPile = new Deck(20);
        discardPile = new Deck();

        drawPile.shuffle();
        dealCards();
    }

    /**
     * Plays a single game of Uno.
     */
    public void play() {
        boolean done = false;
        int index = (int) (Math.random() * players.length);
        Player current;
        discardPile.addCard(drawPile.removeTopCard());

        while (!done) {
            System.out.printf("Top card: %s%n", discardPile.getTopCard());

            current = players[index];
            Card topCard = discardPile.getTopCard();

            Card played = current.playCard(topCard);
            if (played == null) {
                Card c = drawPile.removeTopCard();
                current.drawCard(c);
                System.out.printf("Player %d drew a card%n", index);
                played = current.playCard(topCard);
            }

            if (played != null) {
                discardPile.addCard(played);
                System.out.printf("Player %d played %s%n", index, played);
            }

            if (current.handEmpty()) {
                done = true;
            } else {
                if (drawPile.isEmpty()) {
                    drawPile.reshuffle(discardPile);
                }
                index = (index + 1) % players.length;
                System.out.println();
            }
        }
        System.out.printf("Game over! The winner is player %d%n", index);
    }

    /**
     * Deals initial starting hand to each Player.
     */
    private void dealCards() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < players.length; j++) {
                players[j].drawCard(drawPile.removeTopCard());
            }
        }
    }
}
