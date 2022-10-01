import java.util.ArrayList;
import java.util.List;

    public class Node implements Comparable<Node>{
        public int player;
        public int hValue;
        public int spot;
        public int cBoard;
        public int[] boardConfig;
        public List<Node> children;
        public Node rootNode;
        public boolean isRoot = true;
        public Node(int player, int board, int spot, int[] boardConfig) {
            this.player = player;
            this.cBoard = board;
            this.spot = spot;
            children = new ArrayList<Node>();
            this.boardConfig = boardConfig;
            this.rootNode = null;

        }


        public Node getRootNode(int player){
            if(rootNode != null && player == 1 ){
                if(rootNode.rootNode != null ){
                    return rootNode.getRootNode(player);
                }
            } else if (rootNode != null && player == 2){
                if(rootNode.rootNode.rootNode != null ){
                    return rootNode.getRootNode(player);
                }
            }


                return this;
        }

        // what do we want to compare for a node
        @Override
        public int compareTo(Node o) {
            return 0;
        }

        public void addChild(Node n, int spot, int player){
            int[] newConfig = new int[9];
            for (int i = 0; i < n.boardConfig.length; i++){
                newConfig[i] = n.boardConfig[i];
            }
            newConfig[spot] = player;
            Node c1 = new Node(player, n.cBoard, spot, newConfig);
            n.children.add(c1);
            c1.rootNode = n;
        }

        public int[] modifyBoard(int[] pBoard, int i, int player){
            int[] newBoard = pBoard;
            newBoard[i] = player;
            return newBoard;
        }


    }
