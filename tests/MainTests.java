import org.junit.Assert;
import org.junit.Test;

public class MainTests {
    @Test
    public void testPutOneMoveOnBoard(){
        Gamer play = new Gamer("Walrus");
        Strategy strategy = new Strategy(play.getGameBoard(),play.getBoardWinState());
        int player = 1;
        int currentBoard = -1;

        int[] previousMove = play.getMove();
        if(previousMove[0] != player){
            //We are able to move
            //first find out which board to move
            //If the board at the opponent's move spot is win or not
            if(play.getBoardWinState()[previousMove[1]] == 1){
                //
                //strategy.findBestMove(play.getBoardWinState(),player);
            }

        }
        int   m1 =0,m2 = 0;
        int[][] theboard = play.getGameBoard();


        play.ourMove(m1, m2);
    }
}
