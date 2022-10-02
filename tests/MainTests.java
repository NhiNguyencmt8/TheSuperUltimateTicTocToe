import org.junit.Assert;
import org.junit.Test;

public class MainTests {
    @Test
    public void testPutOneMoveOnBoard(){
        Gamer play = new Gamer("Walrus");

        int player = 1;
        int currentBoard = -1;
        int   m1 =0,m2 = 0;
        int[][] theboard = play.getGameBoard();
        int[] opponentMove = play.getMove();


        play.updateBoard();


        //If the board at the opponent's spot is won
        if(play.isBoardWon(theboard[opponentMove[2]]) > 0){
            //Pick another board
            //We'll do it later
            for(int i = 0; i < 9; i++) {
                if(play.isBoardWon(theboard[opponentMove[2]]) == 0) {
                    //Run the for loop to do iterative minimax alpha-beta pruning
                    break;
                }
            }
        } else {
            //Run the for loop to do iterative minimax alpha-beta pruning
            int depth = 1;
            int maxDepth = play.countRemainingMoves(theboard[opponentMove[2]]);
            int bestSpot = -1;
            Strategy strategy = new Strategy(play.getGameBoard(),play.getBoardWinState());
            strategy.currentBoard = opponentMove[2];
            while(play.recMove.elapsedTime() < 7){ // 7 is tentative
                Node rootNode = new Node(2,opponentMove[2],0,theboard[opponentMove[2]]);
                bestSpot = strategy.minimax2(rootNode, true, -1000000, 1000000, depth)[0];
                //@TODO handle depth based on stopwatch value
                //Gamer -> count remaining moves ?
                if(depth == maxDepth) {
                    break;
                } else {
                    depth++;
                }
            }
        }

        play.ourMove(m1, m2);
    }
}
