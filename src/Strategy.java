import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.max;

public class Strategy {
    private int[][] gameBoard;
    public int currentBoard;
    private int[] boardWinState;
    final int MIN_VALUE = -2147483647;
    final int MAX_VALUE = 2147483647;
    final int INVALID_SPOT = -1;

    public Strategy(int[][] gameBoard, int[] boardWinState) {
        this.gameBoard = gameBoard;
        this.boardWinState = boardWinState;

    }

    /**
     * Determines whether a board is won in ourPlayer's favor
     * @param ourPlayer the int representing the player that the AI wants to win
     * @param board the board being checked for its win state
     * @return 1 if ourPlayer won the board, -1 if the other player won the board, 0 if no one won
     */
    public int utility(int ourPlayer, int[] board) {
        int winner = new Gamer("").isBoardWon(board);
        if(winner == ourPlayer) {
           return 1;
        } else if (winner == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Evaluates a given board
     * @param player the player number representing the AI
     * @param board the board being evaluated
     * @return a heuristic denoting the value of the board for the given player
     */
    public int evaluate(int player, int[] board) {
        int otherPlayer = 0;
        if(player == 1) { otherPlayer = 2; }
        else { otherPlayer = 1; }

        int heuristic = 0;
        //all rows
        for(int i = 0; i < 8; i+=3) {
            int ourp = 0, otherp = 0;
            //checks each spot in a row
            for(int j = i; j < i+3; j++) {
                if(board[j] == player) { ourp++; }
                else if(board[j] == otherPlayer) { otherp++; }
            }
            if(ourp > otherp) { heuristic += 5; }
            else if(otherp > ourp) { heuristic -= 5; }
        }

        //all columns
        for(int i = 0; i < 3; i++) {
            int ourp = 0; int otherp = 0;
            //checks each spot in a column
            for(int j = i; j < 8; j+=3) {
                if(board[j] == player) { ourp++; }
                else if(board[j] == otherPlayer) { otherp++; }
            }
            if(ourp > otherp) { heuristic += 5; }
            else if(otherp > ourp) { heuristic -= 5; }
        }

        //diagonal 1
        int ourp = 0; int otherp = 0;
        for(int i = 0; i <=8; i+=4) {
            if(board[i] == player) { ourp++; }
            else if(board[i] == otherPlayer) { otherp++; }
        }
        if(ourp > otherp) { heuristic += 5; }
        else if(otherp > ourp) { heuristic -= 5; }

        //diagonal 2
        ourp = 0; otherp = 0;
        for(int i = 2; i <= 6; i+=2) {
            if(board[i] == player) { ourp++; }
            else if(board[i] == otherPlayer) { otherp++; }
        }
        if(ourp > otherp) { heuristic += 5; }
        else if(otherp > ourp) { heuristic -= 5; }

        return heuristic;
    }

    //Rule-based strategy
    // If have a wining move -> just go
    // Opponent has a winning move -> stop em
    // A move that can create (1 or 2) possible winning moves <- do the same to block the opponent
    // Create a forking move that creates two win paths (double trap)
    // Prevent the opponent from creating a forking move
    //Heuristic board evaluation function
    //Compute the scores for each of the 8 possible winning (3 rows/col + 2 diagonal)

    // 1 in a row + empty boxes
    // 2 in a row + empty boxes
    // 3 in a row (WIN)

    private class Node implements Comparable<Node>{
        private int hValue;
        private int spot;
        private int cBoard;
        private List<Node> children;
        public Node(int board, int spot) {
            this.cBoard = board;
            this.spot = spot;
            children = new ArrayList<Node>();
        }

        // what do we want to compare for a node
        @Override
        public int compareTo(Node o) {
            return 0;
        }
    }

    public List<Node> getChildren (Node n){ return n.children; }

    public int numChildren(Node n){ return n.children.size(); }

    public Node createNode(int board, int spot){
        return new Node(board, spot);
    }

    public int getSpot(Node n){
        return n.spot;
    }

    public int getBoard(Node n){
        return n.cBoard;
    }

    public int getHValue(Node n){ return n.hValue; }

    public Node setChildren(Node n, int[] boards, int[] spots){
        for (int i = 0; i < boards.length; i++){
           n.children.add(createNode(boards[i],spots[i]));
        }
        return n;
    }

    public void printChildren(Node n){
        for(int i = 0; i < n.children.size(); i++){
            System.out.println("Child Number: " + i);
            System.out.println("Board: " + getBoard(n.children.get(i)) + "; Spot " + getSpot(n.children.get(i)));
        }
    }

    //Put all the legal moves (spots) in the list
    //Search for all the empty cells on the board (not the big board) and put it on the list
    private ArrayList<Node> possibleMoves(){
        ArrayList<Node> pmoves = new ArrayList<>();
        int[] cBoard = gameBoard[currentBoard];
        for (int spot = 0; spot < cBoard.length; spot++){
            if(cBoard[spot] == 0){
                Node possibleNode = new Node(currentBoard,spot); //Review this after we call the function somewhere in the code
                pmoves.add(possibleNode);
            }
        }

        return pmoves;
    }

    public int[] modifyBoard(int[] pBoard, int i, int player){
        pBoard[i] = player;
        return pBoard;
    }

    public Node minimax(Node n, boolean isMax, int alpha, int beta, int depth, int[] pBoard) {
        Node currentBest;
        if (n == null){
            return null;
        }
        if (n.children.isEmpty() && isMax){
            n.hValue = evaluate(1, pBoard);
            return n;
        }else if (n.children.isEmpty()){
            n.hValue = evaluate(2, pBoard);
            return n;
        }

        if (isMax){
            Node bestMove = new Node(currentBoard,INVALID_SPOT); //Setting dummy variable
            bestMove.hValue = MIN_VALUE;                         //Setting dummy heuristic
            for (int i = 0; i < n.children.size(); i++){         //Traversing list to deal with all children
                currentBest = minimax(n.children.get(i),false,alpha,beta,depth-1, modifyBoard(pBoard,i,1)); //Putting move on potential board and recursing
                if (currentBest.hValue > bestMove.hValue){       //Comparing newly returned heuristic value to the previous heuristic value
                    bestMove = currentBest;                      //Setting best move to current best if the heuristic is LARGER
                    System.out.println(currentBest.spot);
                    System.out.println(currentBest.hValue);
                }
                if (bestMove.hValue > alpha){                    //Alpha-beta pruning for Max case; limits program and saves time
                    return bestMove;
                }
            }
            return bestMove;                                     //Returns in case where Alpha beta pruning is not necessary
        }else{
            Node bestMove = new Node(currentBoard,INVALID_SPOT);
            bestMove.hValue = MAX_VALUE;
            for (int i = 0; i < n.children.size(); i++){
                currentBest = minimax(n.children.get(i),true,alpha,beta,depth-1, modifyBoard(pBoard,i,2));
                if (currentBest.hValue < bestMove.hValue){
                    bestMove = currentBest;
                }
                if (bestMove.hValue < beta){
                    return bestMove;
                }
            }
            return bestMove;
        }
    }


}
