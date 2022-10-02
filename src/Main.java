import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Gamer play = new Gamer("Walrus");
        play.addFirstMoves();
        double timeAtSend = play.recMove.elapsedTime();
        while (true) {
            //Wait for file to write
            while(play.recMove.elapsedTime() - timeAtSend < 100) {
            }
            int[] move = play.updateBoard();
            int bestSpot = -1;
            int depth = 6;
            int maxDepth = play.countRemainingMoves(play.getGameBoard()[move[1]]);
            Strategy strategy = new Strategy(play.getGameBoard(), play.getBoardWinState());

            //If the board at the opponent's spot is won
            if (play.getBoardWinState()[move[1]] > 0) {
                // Pick another board
                int i = 0;
                while (i < 9) {
                    if (play.isBoardWon(play.getGameBoard()[move[i]]) == 0) {
                        //Run the for loop to do iterative minimax alpha-beta pruning
                        strategy.currentBoard = i;
                        //while (play.recMove.elapsedTime() < 7 && depth != maxDepth) { // 7 is tentative
                            Node rootNode = new Node(2, i, 0, play.getGameBoard()[i]);
                            bestSpot = strategy.minimax2(rootNode, true, -1000000, 1000000, depth)[0];
                            depth++;
                        //}
                        break;
                    }
                    i++;
                }
                System.out.println("---------------Our board to move in: " + move[i]);
                play.ourMove(move[i], bestSpot);
            } else {
                //Run the for loop to do iterative minimax alpha-beta pruning
                strategy.currentBoard = move[1];
                //while (play.recMove.elapsedTime() < 7 && depth != maxDepth) { // 7 is tentative
                    Node rootNode = new Node(2, move[1], 0, play.getGameBoard()[move[1]]);
                    bestSpot = strategy.minimax2(rootNode, true, -1000000, 1000000, depth)[0];
                    depth++;
                //}
                System.out.println("---------------Our board to move in: " + move[1]);
                play.ourMove(move[1], bestSpot);
            }
        }
    }
}
