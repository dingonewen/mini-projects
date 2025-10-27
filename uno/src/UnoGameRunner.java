import java.util.Scanner;

public class UnoGameRunner {
    public static void main(String[] args) {
        Scanner inputReader = new Scanner(System.in);
        System.out.print("Please enter the number of players (2 - 4): ");
        int numPlayers = inputReader.nextInt();
        while (numPlayers < 2 || numPlayers > 4) {
            System.out.print("Invalid number of players. Please enter a number between 2 and 4: ");
            numPlayers = inputReader.nextInt();
        }
        UnoGame game = new UnoGame(numPlayers);
        game.play();
    }
}
