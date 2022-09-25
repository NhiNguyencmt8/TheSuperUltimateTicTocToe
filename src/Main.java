import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Gamer play = new Gamer("Walrus");
        //System.out.println(play.legalMove(4,6));
        int[] opponentMove = play.getMove();
        System.out.println(opponentMove[1] + " " + opponentMove[2]);
        int m1 = play.randMove();
        int m2 = play.randMove();
        play.ourMove(m1, m2);

    }
}