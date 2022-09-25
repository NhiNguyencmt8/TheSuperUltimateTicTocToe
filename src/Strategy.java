import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.max;

public class Strategy {
    private int[][] gameBoard;
    private int currentBoard;
    private int[] boardWinState;

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

    public int evaluate(int ourPlayer, int otherPlayer, int[] board) {
        for(int i = 0; i < 8; i+=3) {
            int ourp = 0; int otherp = 0;
            for(int j = i; j < i+3; j++) {
                if(board[j] == ourPlayer) {ourp++;}
                else if(board[j] == otherPlayer) {otherp++;}
            }
        }
        return 0;
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

    private class Node{
        private int hValue;
        private int spot;
        private List<Node> children;
        public Node(int hValue, int spot){
            this.hValue = hValue;
            this.spot = spot;
            children = new ArrayList<Node>();
        }

    }

    //Put all the legal moves in the list (??)
    //Search for all the empty cells on the board (not the big board) and put it on the list
    private HashMap possibleMoves(){
        HashMap pmoves = new HashMap();
        int[] cBoard = gameBoard[currentBoard];
        for (int spot = 0; spot < cBoard.length; spot++){
            if(cBoard[spot] == 0){
                pmoves.put(spot,0);
            }
        }

        return pmoves;
    }


    public Node minimax2(Node n, boolean isMax, int alpha, int beta, int depth) {
        Node currentBest;
        if (n.children == null){
            return evaluate();
        }
        if (isMax){
            Node bestMove = new Node(-100000,-1);
            for (Node n1 : n.children){
                currentBest = minimax2(n1,false,alpha,beta,depth-1);
                if (currentBest.hValue > bestMove.hValue){
                    bestMove = currentBest;
                }
                if (bestMove.hValue > alpha){
                    return bestMove;
                }

            }
        }else{
            Node bestMove = new Node(100000, -3);
            for (Node n1 : n.children){
                currentBest = minimax2(n1,true,alpha,beta,depth-1);
                if (currentBest.hValue < bestMove.hValue){
                    bestMove = currentBest;
                }
            }
        }
        return new Node(1,1);
    }
    //Return the ultimate spot for the move which the gamer will use
    public int minimax(int keyIndex, boolean isMax,int alpha,int beta,int depth){
        //Level 0 (we are max and we want to pick maximum of the next x combinations)
        HashMap allMoves = possibleMoves();
        //evaluate()


        //Grab all the values in the hashmap and return the highest


        if(allMoves.isEmpty()){
            return keyIndex;// return the key :")
        }
        //Check if node is the leaf -> return the value of the node
        if (isMax){ // Our turn (?)
            int maxVal = 0;

            //Recurse for left and right children
            for (int i = 0; i < 2; i++) {
                int value = minimax(keyIndex*2 + i, false, alpha, beta, depth + 1);
                maxVal = max(value,maxVal);
                alpha = max(alpha,maxVal);

                //Alpha - beta pruning
                if (alpha >= beta){
                    break;
                }

            }
            return maxVal;
        }else{

        }

    }

}
