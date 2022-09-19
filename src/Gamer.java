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
    public boolean updateBoard() {
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


    public int[] getMove() {
        //need to check null
        try {
            while (!movefile.exists()) {
            }

            Scanner move = new Scanner(movefile);
            if (move.hasNextLine()) {
                String[] arr = move.nextLine().split(" ");
                if (arr[0].equals(ourName)) {
                    //We are the player 1
                    return new int[]{1, Integer.parseInt(arr[1]), Integer.parseInt(arr[2])};
                } else {
                    return new int[]{2, Integer.parseInt(arr[1]), Integer.parseInt(arr[2])};
                }
            }
            return null;
        } catch(FileNotFoundException e) {
            System.out.println("File " + movefile + "not found");
            return null;
        }
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
     * TODO also print what boards have been won and who won them
     */
    public String displayBoard() {
        //Printing the original 9x9 board with annotation 1 as our move and 2 as our player's move
        updateBoard(); // Update the board before printing
        String border = "---------------------\n";
        String row0 = String.format("%s %s %s | %s %s %s | %s %s %s\n",gameBoard[0][0],gameBoard[0][1],gameBoard[0][2],gameBoard[1][0],gameBoard[1][1],gameBoard[1][2],gameBoard[2][0],gameBoard[2][1],gameBoard[2][2]);
        String row1 = String.format("%s %s %s | %s %s %s | %s %s %s\n",gameBoard[0][3],gameBoard[0][4],gameBoard[0][5],gameBoard[1][3],gameBoard[1][4],gameBoard[1][5],gameBoard[2][3],gameBoard[2][4],gameBoard[2][5]);
        String row2 = String.format("%s %s %s | %s %s %s | %s %s %s\n",gameBoard[0][6],gameBoard[0][7],gameBoard[0][8],gameBoard[1][6],gameBoard[1][7],gameBoard[1][8],gameBoard[2][6],gameBoard[2][7],gameBoard[2][8]);
        String row3 = String.format("%s %s %s | %s %s %s | %s %s %s\n",gameBoard[3][0],gameBoard[3][1],gameBoard[3][2],gameBoard[4][0],gameBoard[4][1],gameBoard[4][2],gameBoard[5][0],gameBoard[5][1],gameBoard[5][2]);
        String row4 = String.format("%s %s %s | %s %s %s | %s %s %s\n",gameBoard[3][3],gameBoard[3][4],gameBoard[3][5],gameBoard[4][3],gameBoard[4][4],gameBoard[4][5],gameBoard[5][3],gameBoard[5][4],gameBoard[5][5]);
        String row5 = String.format("%s %s %s | %s %s %s | %s %s %s\n",gameBoard[3][6],gameBoard[3][7],gameBoard[3][8],gameBoard[4][6],gameBoard[4][7],gameBoard[4][8],gameBoard[5][6],gameBoard[5][7],gameBoard[5][8]);
        String row6 = String.format("%s %s %s | %s %s %s | %s %s %s\n",gameBoard[6][0],gameBoard[6][1],gameBoard[6][2],gameBoard[7][0],gameBoard[7][1],gameBoard[7][2],gameBoard[8][0],gameBoard[8][1],gameBoard[8][2]);
        String row7 = String.format("%s %s %s | %s %s %s | %s %s %s\n",gameBoard[6][3],gameBoard[6][4],gameBoard[6][5],gameBoard[7][3],gameBoard[7][4],gameBoard[7][5],gameBoard[8][3],gameBoard[8][4],gameBoard[8][5]);
        String row8 = String.format("%s %s %s | %s %s %s | %s %s %s\n",gameBoard[6][6],gameBoard[6][7],gameBoard[6][8],gameBoard[7][6],gameBoard[7][7],gameBoard[7][8],gameBoard[8][6],gameBoard[8][7],gameBoard[8][8]);

//        for(int i = 0; i < gameBoard.length; i++) {
//            board.append("[");
//
//            for(int j = 0; j < gameBoard[0].length; j++) {
//                board.append(gameBoard[i][j]);
//                if(j != 8) { board.append(","); }
//            }
//            board.append("]\n");
//        }
        return row0 + row1 + row2 + border +
                row3 + row4 + row5 + border +
                row6 + row7 + row8;
    }

    public void ourMove() throws FileNotFoundException{
        //Put the enemy move on our updated board
        updateBoard();

        //Put our move on our updated board (if legal)
        sendMove(ourName, nextBoard, nextSpot);
        updateBoard();
    }

//    private int[] rowToBoard(int row, int col) {
//        // put into new array then print
//        int board = -1;
//        int spot = -1;
//        if(row >= 0 && row <= 2) {
//            board =
//        }
//    }

}
