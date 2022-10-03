import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Gamer {
    public final File movefile = new File("ref\\move_file");
    public final File playerfile = new File("ref\\Walrus.go");
    private final File first_four_moves = new File("ref\\first_four_moves");

    private String ourName;
    private int[][] gameBoard;
    private int[] boardWinState; //1x9 matrix that indicates the win state of the big board
    public Stopwatch recMove;
    private int[] finalFirstMove;

    public Gamer(String ourName) {
        this.ourName = ourName;
        this.gameBoard = new int[9][9];//Board-Spot?
        this.boardWinState = new int[9];
        this.finalFirstMove = new int[2];
    }

    public int[][] getGameBoard(){
        return  gameBoard;
    }

    public  int[] getBoardWinState(){
        return boardWinState;
    }

    /**
     * Retrieve the value of ourName
     * @return ourName
     */
    public String getOurName() {
        return ourName;
    }

    /**
     * Updates the board based on the most recent move received
     * @return true if board successfully updated
     */
    public int[] updateBoard() {
        int[] move = getMove();
        if(move == null) {
            return finalFirstMove;
        }
        int player = move[0];
        int board = move[1];
        int spot = move[2];

        //Update the move on our board
        gameBoard[board][spot] = player;
        displayBoard();
        return new int[]{move[1], move[2]};
    }

    /**
     * Generates an int for a random move
     * @return a random integer between 0 and 8
     */
    public int randMove() {
        // random int from 0-8
        return (int) (Math.random() * 9);
    }

    /**
     * Determines the legality of a given move based on the opponent's move
     * @param board the board our move will take place on
     * @param spot the exact spot on that board
     * @return true if the move can be made on the game board
     */
    public boolean legalMove(int board, int spot) {
        //First check if the board not won already
        if (isBoardWon(gameBoard[board]) == 0) {
            if (getMove() == null && board == finalFirstMove[1]) {
                return true;
            } else if(gameBoard[board][spot] == 0 && board == getMove()[2]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a given board has been won
     * @param board a 1x9 array representing a board
     * @return an int representing the player that won this board, or 0 if the board is not won
     */
    public int isBoardWon(int[] board) {
        //Check all row won
        // {0,1,2}, {3,4,5}, {6,7,8}
        for(int i = 0; i < 8; i+=3) {
            if(board[i] == board[i+1] && board[i] == board[i+2]){
                return board[i];
            }
        }
        //Check all column won
        // {0,3,6}, {1,4,7}, {3,5,8}
        for(int i = 0; i < 3; i++) {
            if(board[i] == board[i+3] && board[i+3] == board[i+6]){
                return board[i];
            }
        }

        //Check diagonal won
        // {0,4,8}, {2,4,6}
        if(board[4] == board[0] && board[4] == board[8]){
            return board[4];
        } else if (board[4] == board[2] && board[4] == board[6]){
            return board[4];
        }

        return 0;
    }

    /**
     * Determines the win state of each board, then determines whether a player has won the entire game
     * @return the player number that won the game, or 0 if no player has won
     */
    private int isGameWon(){
        //Time complexity
        for(int i = 0; i < 9; i++){
            boardWinState[i] = isBoardWon(gameBoard[i]);
        }

        return isBoardWon(boardWinState);
    }

    public void addFirstMoves(){
        try  {
            while (!first_four_moves.exists()){
            }
            Scanner move = new Scanner(first_four_moves);
            String[] moves = new String[4];
            for (int i = 0; i < moves.length; i++){
                String[] arr = move.nextLine().split(" ");
                gameBoard[Integer.parseInt(arr[1])][Integer.parseInt(arr[2])] = arr[0].equals(ourName) ? 1 : 2;
                if (i == 3){
                    finalFirstMove[0] = Integer.parseInt(arr[1]);
                    finalFirstMove[1] = Integer.parseInt(arr[2]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + first_four_moves + "not found");
        }
    }
    /**
     * Retrieves the opponent's move from movefile
     * @return an array of format {player_number, board, spot}
     */
    public int[] getMove() {
        //need to check null
        try {
            while (!movefile.exists() || !playerfile.exists()) {
            }
            recMove = new Stopwatch(); // 10 sec timer starts

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
     * @param board the board our move will take place on
     * @param spot the exact spot on that board
     * @return a string representing the move, or a message detailing any errors
     */
    public String sendMove(String playerName, int board, int spot) {
        String aMove = String.format("%s %s %s", playerName, board, spot);
        while(!legalMove(board,spot)){
            board = randMove();
            spot = randMove();
//            System.out.println("No possible move, retrying");
//            System.out.println("New Board: " + board + " New Spot: " + spot);
        }
        if (legalMove(board, spot)) {
            aMove = String.format("%s %s %s", playerName, board, spot);
            try {
                PrintWriter writeMove = new PrintWriter(movefile);
                writeMove.println(aMove);
                writeMove.close();

            } catch (FileNotFoundException e) {
                return "Move not sent";
            }
            if (playerName.equals(ourName)) {
                gameBoard[board][spot] = 1;
            } else {
                gameBoard[board][spot] = 2;
            }
        } else {

            System.out.println("No possible move");
        }
        return aMove;
    }

    /**
     * Prints the current state of gameBoard
     * TODO also print what boards have been won and who won them
     */
    public void displayBoard() {
        //Printing the original 9x9 board with annotation 1 as our move and 2 as our player's move
        //updateBoard(); // Update the board before printing

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

        System.out.println(row0 + row1 + row2 + border +
                row3 + row4 + row5 + border +
                row6 + row7 + row8);
    }

    /**
     * Completes the program's move
     * @param board the board the move takes place on
     * @param spot the exact spot on that board
     */
    public void ourMove(int board, int spot) {
        if (isGameWon() == 0){
            //Put our move on our updated board (if legal)
            sendMove(ourName, board, spot);
            //Update our move on the board
//            updateBoard();
        }
    }

    public int countRemainingMoves(int[] board) {
        int count = 0;
        for(int i = 0; i < 9; i++) {
            if(board[i] == 0) {
                count++;
            }
        }
        return count;
    }

}
