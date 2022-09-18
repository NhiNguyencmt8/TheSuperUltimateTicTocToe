import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Gamer {
    private final File movefile = new File("ref\\move_file");

    private String ourName;
    private int nextBoard;
    private int nextSpot;
    private int[][] gameBoard;

    public Gamer(String ourName) {
        this.ourName = ourName;
        gameBoard = new int[9][9];//Board-Spot?
    }

    /**
     * Updates the board based on the most recent move received
     * @return true if board successfully updated
     */
    public boolean updateBoard()throws FileNotFoundException{
        int[] move = getMove();
        if(move == null) {
            return false;
        }
        int player = move[0];
        int board = move[1];
        int spot = move[2];

        if (legalMove(board, spot)){
            gameBoard[board][spot] = player;
        }
        return true;
    }
    //Generate a random move and rewrite an existed file
    public int randMove() {
        // random int from 0-8
        return (int) (Math.random() * 9);
    }

    public boolean legalMove(int board, int spot) {
        //First check if the move is empty
        boolean legal = true;

        if (gameBoard[board][spot] > 0){
            return !legal;
        }else{
            return legal;
        }
    }


    public int[] getMove() throws FileNotFoundException {
        //need to check null
        while (!movefile.exists()) {
        }

        Scanner move = new Scanner(movefile);
        if (move.hasNextLine()) {
            String[] arr = move.nextLine().split(" ");
            if(arr[0].equals(ourName)){
                //We are the player 1
                return new int[] {1, Integer.parseInt(arr[1]), Integer.parseInt(arr[2])};
            } else {
                return new int[] {2, Integer.parseInt(arr[1]), Integer.parseInt(arr[2])};
            }
        }
        return null;
    }

    /**
     * Writes a given move to movefile
     * @param playerName the name of the player that made the given move
     * @param board
     * @param spot
     * @return
     * @throws FileNotFoundException
     */
    public String sendMove(String playerName, int board, int spot) throws FileNotFoundException {
        if (legalMove(board, spot)) {
            String aMove = String.format("%s %s %s", playerName, randMove(), randMove());
            PrintWriter writeMove = new PrintWriter(movefile);
            writeMove.println(aMove);
            writeMove.close();

            return aMove;
        } else {
            return "Cannot Move";
        }
    }

    /**
     * Can be used to display the current state of gameBoard
     * @return string representation of gameBoard
     * @TODO NEED A FUNCTION TO CONVERT ROW,COL TO BOARD,SPOT
     */
    public String displayBoard() throws FileNotFoundException {
        //Printing the original 9x9 board with annotation 1 as our move and 2 as our player's move
        updateBoard(); // Update the board before printing
        StringBuilder board = new StringBuilder();
        for(int i = 0; i < gameBoard.length; i++) {
            board.append("[");

            for(int j = 0; j < gameBoard[0].length; j++) {
                board.append(gameBoard[i][j]);
                if(j != 8) { board.append(","); }
            }
            board.append("]\n");
        }

        return board.toString();
    }

    public void ourMove() throws FileNotFoundException{
        //Put the enemy move on our updated board
        updateBoard();

        //Put our move on our updated board (if legal)
        sendMove(ourName, nextBoard, nextSpot);
        updateBoard();
    }

}
