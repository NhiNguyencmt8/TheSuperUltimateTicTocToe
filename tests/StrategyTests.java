import org.junit.Assert;
import org.junit.Test;

public class StrategyTests {

    @Test
    public void testEvaluate() {
        Strategy strat = new Strategy(null,null);
        // 0 1 2
        // 1 1 2
        // 1 0 0
        int[] board = {0,1,2,1,1,2,1,0,0};
        System.out.println(strat.evaluate(1, board));
        System.out.println(strat.evaluate(2, board));

        // 0 0 2
        // 1 1 2
        // 1 0 0
        int[] board2 = {0,0,2,1,1,2,1,0,0};
        System.out.println(strat.evaluate(1, board2));
        System.out.println(strat.evaluate(2, board2));

        // 0 0 0
        // 0 1 0
        // 0 0 0
        int[] board3 = {0,0,0,0,1,0,0,0,0};
        System.out.println(strat.evaluate(1, board3));
        System.out.println(strat.evaluate(2, board3));

        // 0 0 0
        // 1 0 0
        // 0 0 0
        int[] board4 = {0,0,0,1,0,0,0,0,0};
        System.out.println(strat.evaluate(1, board4));
        System.out.println(strat.evaluate(2, board4));

        // 0 0 0
        // 0 0 0
        // 0 0 0
        int[] board5 = {0,0,0,0,0,0,0,0,0};
        Assert.assertEquals(0, strat.evaluate(1, board5));
        Assert.assertEquals(0, strat.evaluate(2, board5));

        // 1 1 0
        // 1 0 2
        // 0 2 2
        int[] board6 = {1,1,0,1,0,2,0,2,2};
        Assert.assertEquals(strat.evaluate(1, board6), strat.evaluate(2, board6));
    }

    @Test
    public void testMinimaxNull(){
        Strategy strat = new Strategy(new int[9][9], new int[9]);
        Assert.assertNull(strat.minimax(null, true, 0, 0, 0));
    }

    @Test
    public void testMinimaxBlankBoardTerminal(){
        Strategy strat = new Strategy(new int[9][9], new int[9]);
        strat.currentBoard = 0;
        Assert.assertEquals(0, strat.getSpot(strat.minimax(strat.createNode(1,0), true, 0, 0, 0)));
        Assert.assertEquals(0, strat.getBoard(strat.minimax(strat.createNode(0,0), true, 0, 0, 0)));
    }

    @Test
    public void testMinimaxTerminal(){
        int[][] gameBoard = new int[9][9];
        gameBoard[0][2] = 1;
        gameBoard[0][7] = 1;
        gameBoard[0][3] = 2;
        gameBoard[4][5] = 2;

        Strategy strat = new Strategy(gameBoard,new int[9]);
        strat.currentBoard = 0;
        Assert.assertEquals(4,strat.getSpot(strat.minimax(strat.createNode(0,4),true,0,0,0)));
        Assert.assertEquals(0,strat.getBoard(strat.minimax(strat.createNode(0,4),true,0,0,0)));
    }

}
