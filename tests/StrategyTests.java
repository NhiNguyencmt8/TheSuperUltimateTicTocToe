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
        System.out.println("3 p1 " + strat.evaluate(1, board3));
        System.out.println("3 p2 " + strat.evaluate(2, board3));

        // 0 0 0
        // 0 2 0
        // 0 0 0
        int[] board3_1 = {0,0,0,0,2,0,0,0,0};
        System.out.println("3_1 p1 " + strat.evaluate(1, board3_1));
        System.out.println("3_1 p2 " + strat.evaluate(2, board3_1));

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
        Assert.assertEquals(strat.evaluate(1, board6), -strat.evaluate(2, board6));

        // 1 2 0
        // 0 0 0
        // 2 1 0
        int[] board7 = {1,2,0,0,0,0,2,1,0};
        Assert.assertEquals(0, strat.evaluate(1, board7));
        Assert.assertEquals(0, strat.evaluate(2, board7));

        // 1 2 0
        // 0 0 0
        // 0 2 0
        int[] board8 = {1,2,0,0,0,0,0,2,0};
        System.out.println("8 p1" + strat.evaluate(1, board8));
        System.out.println("8 p2" + strat.evaluate(2, board8));
    }

    @Test
    public void testMinimaxNull(){
        Strategy strat = new Strategy(new int[9][9], new int[9]);
        //Assert.assertNull(strat.minimax(null, true, 0, 0, 0));
    }

    @Test
    public void testMinimaxBlankBoardTerminal(){
        Strategy strat = new Strategy(new int[9][9], new int[9]);
        strat.currentBoard = 0;
        //System.out.println(strat.getChildren(strat.createNode(1,0)));
        //System.out.println(strat.getChildren(strat.createNode(1,0)).isEmpty());
        //Assert.assertEquals(0, strat.getSpot(strat.minimax(strat.createNode(1,0), true, 0, 0, 0)));
        //Assert.assertEquals(0, strat.getBoard(strat.minimax(strat.createNode(0,0), true, 0, 0, 0)));
    }

    @Test
    public void testMinimaxTerminal(){
        int[][] gameBoard = new int[9][9];
        gameBoard[0][2] = 1;
        gameBoard[0][7] = 1;
        gameBoard[0][3] = 2;
        gameBoard[4][5] = 2;

        int[] ourBoard = gameBoard[0];
        Strategy strat = new Strategy(gameBoard,new int[9]);
        strat.currentBoard = 0;
        //Assert.assertEquals(4,strat.getSpot(strat.minimax(strat.createNode(0,4),true,0,0,0, strat.modifyBoard(ourBoard,4,1))));
        //Assert.assertEquals(65,strat.getHValue(strat.minimax(strat.createNode(0,4),true,0,0,0, strat.modifyBoard(ourBoard,4,1))));
    }

    @Test
    public void testMax(){
        int[][] gameBoard = new int[9][9];
        gameBoard[0][0] = 1;
        gameBoard[0][7] = 2;
        gameBoard[0][1] = 1;

        //board 0
        // 1 0 0
        // 1 0 2
        // 0 0 0

        //board 0
        // 1 0 0
        // 0 0 0
        // 0 2 0

        int[] ourBoard = gameBoard[0];


        Strategy strat = new Strategy(gameBoard, new int[9]);


        Node n = new Node(2,0,7, ourBoard);
        n.addChild(n, 2,1);
        n.addChild(n,4,1);

        n.addChild(n.children.get(0),1,2);
        n.addChild(n.children.get(0),2,2);
        n.addChild(n.children.get(1),1,2);
        n.addChild(n.children.get(1),2,2);

        n.addChild(n.children.get(1).children.get(0),8,1);
        n.addChild(n.children.get(0).children.get(0),6,1);

        strat.currentBoard = 0;

        System.out.println(strat.minimax(n,false,0,0,2).spot);


        //strat.printChildren(strat.setChildren(strat.createNode(0,7), boards,spots));
        //System.out.println(strat.numChildren(strat.setChildren(strat.createNode(0,7), boards,spots)));


        //Assert.assertEquals(0,strat.getBoard(strat.minimax(strat.setChildren(strat.createNode(0,7), boards,spots),true,0,0,0,strat.modifyBoard(ourBoard,7,1))));
        //Assert.assertEquals(3,strat.getSpot(strat.minimax(strat.setChildren(strat.createNode(0,7), boards,spots),true,0,0,0,strat.modifyBoard(ourBoard,7,1))));
        //Assert.assertEquals(-65,strat.getHValue(strat.minimax(strat.setChildren(strat.createNode(0,7), boards,spots),true,0,0,0,strat.modifyBoard(ourBoard,7,1))));

    }

    @Test
    public void testFindBestMove(){
        int[][] gameBoard = new int[9][9];
        gameBoard[0][0] = 1;
        gameBoard[0][3] = 1;
        gameBoard[0][4] = 2;

        //board 0
        // 1 0 0
        // 1 2 0
        // 0 0 0

        int[] ourBoard = gameBoard[0];

        Strategy strat = new Strategy(gameBoard, new int[9]);
        int[] boards = {0,0,0};
        int[] spots = {2,5,3};

        strat.currentBoard = 0;


        //strat.printChildren(strat.setChildren(strat.createNode(0,6), boards,spots));
        //System.out.println(strat.numChildren(strat.setChildren(strat.createNode(0,6), boards,spots)));

        //Assert.assertEquals(0,strat.getBoard(strat.minimax(strat.setChildren(strat.createNode(0,7), boards,spots),true,0,0,0,strat.modifyBoard(ourBoard,7,1))));
        //Assert.assertEquals(6,strat.getSpot(strat.findBestMove(ourBoard,1)));
        //Assert.assertEquals(-65,strat.getHValue(strat.minimax(strat.setChildren(strat.createNode(0,7), boards,spots),true,0,0,0,strat.modifyBoard(ourBoard,7,1))));

    }

//    @Test
//    public void testFindBestMove(){
//        int[][] gameBoard = new int[9][9];
//        gameBoard[0][0] = 1;
//        gameBoard[0][3] = 1;
//        gameBoard[0][4] = 2;
//
//        //board 0
//        // 1 0 0
//        // 1 2 0
//        // 0 0 0
//
//        int[] ourBoard = gameBoard[0];
//
//        Strategy strat = new Strategy(gameBoard, new int[9]);
//        int[] boards = {0,0,0};
//        int[] spots = {2,5,3};
//
//        strat.currentBoard = 0;
//
//
//        //Assert.assertEquals(0,strat.getBoard(strat.minimax(strat.setChildren(strat.createNode(0,7), boards,spots),true,0,0,0,strat.modifyBoard(ourBoard,7,1))));
//        Assert.assertEquals(6,strat.getSpot(strat.findBestMove(ourBoard,1)));
//        //Assert.assertEquals(-65,strat.getHValue(strat.minimax(strat.setChildren(strat.createNode(0,7), boards,spots),true,0,0,0,strat.modifyBoard(ourBoard,7,1))));
//
//    }

}
