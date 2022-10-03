import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Gamer play = new Gamer("Walrus");
        play.addFirstMoves();
        //double timeAtSend = play.recMove.elapsedTime();
        while (true) {
            if (play.isGameWon() > 0){
                break;
            }
            //Wait for file to write
//            while(play.recMove != null && play.recMove.elapsedTime() - timeAtSend < 200) {
//            }
            int[] move = play.updateBoard();
            int bestSpot = -1;
            int depth = 1;
            int maxDepth = play.countRemainingMoves(play.getGameBoard()[move[1]]);
            Strategy strategy = new Strategy(play.getGameBoard(), play.getBoardWinState());

            System.out.println("win state " + play.isBoardWon(play.getGameBoard()[move[1]]));
            //If the board at the opponent's spot is won
            if (play.isBoardWon(play.getGameBoard()[move[1]]) > 0) {
                // Pick another board
                play.updateBoardWinState();
                Node rootNodeBoard = new Node(2,0,0,play.getBoardWinState());

                        //Run the for loop to do iterative minimax alpha-beta pruning
                        int[] bestBoard =  {-1,-1}; //hValue - Spot
                        for (int i = 0; i < 9; i ++){
                            if(play.getBoardWinState()[i] > 0){
                                int evalBoard = strategy.evaluate(1,play.getGameBoard()[i]);
                                if(evalBoard > bestBoard[0]){
                                    bestBoard[0] = evalBoard;
                                    bestBoard[1] = i;
                                }
                            }
                        }


                        strategy.currentBoard = bestBoard[1];
                        //strategy.currentBoard = strategy.minimax2(rootNodeBoard, false, -1000000, 1000000, depth)[0];
                        System.out.println("Time to choose another board, at " + strategy.currentBoard);
                        while (play.recMove.elapsedTime() < 7 && depth != maxDepth) { // 7 is tentative
                            Node rootNode = new Node(2, strategy.currentBoard, 0, play.getGameBoard()[strategy.currentBoard]);
                            bestSpot = strategy.minimax2(rootNode, false, -1000000, 1000000, depth)[0];
                            depth++;
                        }

                    System.out.println("Board choose is at " + strategy.currentBoard);
                System.out.println("---------------Our board to move in: " + strategy.currentBoard);
                play.ourMove(strategy.currentBoard, bestSpot);
            } else {
                //Run the for loop to do iterative minimax alpha-beta pruning
                strategy.currentBoard = move[1];
                while (play.recMove.elapsedTime() < 7 && depth != maxDepth) { // 7 is tentative
                    Node rootNode = new Node(2, move[1], 0, play.getGameBoard()[move[1]]);
                    bestSpot = strategy.minimax2(rootNode, false, -1000000, 1000000, depth)[0];
                    depth++;
                }
                System.out.println("---------------Our board to move in: " + move[1]);
                play.ourMove(move[1], bestSpot);
            }
            while(play.playerfile.exists()){}
        }
    }
}
