import java.util.ArrayList;
import java.util.Arrays;


import static java.lang.Math.max;

public class Strategy {
    private int[][] gameBoard;
    public int currentBoard;

    private int[] boardWinState;
    final int MIN_VALUE = -2147483647;
    final int MAX_VALUE = 2147483647;
    final int INVALID_SPOT = -1;
    Node bestMove = new Node(1,currentBoard,INVALID_SPOT,new int[1]);

    public Strategy(int[][] gameBoard, int[] boardWinState) {
        this.gameBoard = gameBoard;
        this.boardWinState = boardWinState;
    }

//public

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
        for(int i = 0; i <= 6; i+=3) {
            int ourp = 0, otherp = 0;
            //checks each spot in a row
            for(int j = i; j < i+3; j++) {
                if(board[j] == player) { ourp++; }
                else if(board[j] == otherPlayer) { otherp++; }
            }
            heuristic = getHeuristic(heuristic, ourp, otherp);
        }

        //all columns
        for(int i = 0; i < 3; i++) {
            int ourp = 0; int otherp = 0;
            //checks each spot in a column
            for(int j = i; j <= 8; j+=3) {
                if(board[j] == player) { ourp++; }
                else if(board[j] == otherPlayer) { otherp++; }
            }
            heuristic = getHeuristic(heuristic, ourp, otherp);
        }

        //diagonal 1
        int ourp = 0; int otherp = 0;
        for(int i = 0; i <=8; i+=4) {
            if(board[i] == player) { ourp++; }
            else if(board[i] == otherPlayer) { otherp++; }
        }
        heuristic = getHeuristic(heuristic, ourp, otherp);

        //diagonal 2
        ourp = 0; otherp = 0;
        for(int i = 2; i <= 6; i+=2) {
            if(board[i] == player) { ourp++; }
            else if(board[i] == otherPlayer) { otherp++; }
        }
        heuristic = getHeuristic(heuristic, ourp, otherp);

        return heuristic;
    }

    private int getHeuristic(int heuristic, int ourp, int otherp) {
        if(ourp + otherp == 3 && ourp > 0 && otherp > 0) {
            // if the 3 spots examined are all full, do not adjust heuristic
            return heuristic;
        } else if(ourp == 3) {
            // if our player has three in a row
            return 500;
        } else if(otherp == 3) {
            // if the other player has three in a row
            return -500;
        }

        if(ourp - otherp == 2) { heuristic += 50; }
        else if(otherp - ourp == 2) { heuristic -= 50; }
        else if(ourp - otherp == 1) { heuristic += 5; }
        else if(otherp - ourp == 1) { heuristic += 300; } // theirs is 2 - us is 1 -> we block them //-=5 before

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

    //Put all the legal moves (spots) in the list
    //Search for all the empty cells on the board (not the big board) and put it on the list
    public Node addNewLevel(Node n,int player){
        for (int spot = 0; spot < 9; spot++){
            if(n.boardConfig[spot] == 0){
                n.addChild(n,spot,player);
            }
        }
        return n;
    }

    public Node doMinimax(Node n, boolean isMax, int alpha, int beta, int depth) {
        if(isMax && depth%2 != 0) {
            bestMove.hValue = MIN_VALUE;
        } else {
            bestMove.hValue = MAX_VALUE;
        }
        return minimax(n, isMax, alpha, beta, depth);
    }

    public Node minimax(Node n, boolean isMax, int alpha, int beta, int depth) {
        Node current;

        if (n == null){
            return null;
        }


            if (depth == 0){
                n.hValue = evaluate(1, n.boardConfig);
                printIndBoard(n.boardConfig);
                return n;
            }

            if (isMax){
                addNewLevel(n,1);
                    for (int i = 0; i < n.children.size(); i++){         //Traversing list to deal with all children
                        System.out.println("Child Spot: " + n.children.get(i).spot);
                        current = minimax(n.children.get(i),false,alpha,beta,depth-1); //Putting move on potential board and recursing

                        System.out.println(bestMove.hValue);
                        if (current.hValue > bestMove.hValue){       //Comparing newly returned heuristic value to the previous heuristic value
                            bestMove = current;                      //Setting best move to current best if the heuristic is LARGER
                            alpha = bestMove.hValue;
                            System.out.println();
                        }


                    }

                return bestMove.getRoot(bestMove);                                     //Returns in case where Alpha beta pruning is not necessary
            }else{


                //add children line (+1 depth)
                //check for vacant spots
                //put these spots on the tree
                addNewLevel(n,2);

                    for (int i = 0; i < n.children.size(); i++){
                        int iterativeEval = evaluate(1,n.children.get(i).boardConfig);
                        if ( iterativeEval<= -500 && n.spot != -1){
                            return bestMove;
                        }
                        current = minimax(n.children.get(i),true,alpha,beta,depth-1);

                        if (current.hValue < bestMove.hValue){
                            bestMove = current;
                            beta = bestMove.hValue;
                        }

                    }
                return bestMove.getRoot(bestMove);
                }


            }





    public void printIndBoard(int[] board) {
        System.out.printf("%d %d %d\n%d %d %d\n%d %d %d\n", board[0],board[1],board[2],board[3],board[4],board[5],board[6],board[7],board[8]);
    }

//    public Node findBestMove(int[] currentBoard, int player){
//
//        int bestVal = -100000;
//        Node bestMove = new Node(player, this.currentBoard,-1, currentBoard);
//        bestMove.spot = -1;
//
//        for (int i = 0; i < 9; i++){
//            int spot = i;
//            if (currentBoard[spot] == 0){
//
//                //Just put this as depth = 3 I guess
//                Node moveNode = minimax(bestMove,false,0,0,8);
//
//                if (moveNode.hValue > bestVal) {
//                    bestMove = moveNode;
//                    bestVal = moveNode.hValue;
//                }
//
//            }
//        }
//        return  bestMove;
//
//    }


}
