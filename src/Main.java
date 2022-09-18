import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Gamer play = new Gamer("Walrus");
        //System.out.println(play.legalMove(4,6));

        System.out.println(play.displayBoard());
        try {
            //play.sendMove("Walrus", play.randMove(), play.randMove());
            int[] recMove = play.getMove();
            if (recMove == null) {
                System.out.println("no move received");
            } else {
                System.out.println(Arrays.toString(recMove));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}