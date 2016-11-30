package CS420Project3;

//heavily based on the tic tac toe code she provided
public class Board {
    private int[][] gameBoard;
    private final int SIZE = 8;
    private final int MAXDEPTH = 5;
    
    //initialize the starting board
    public Board() {
        gameBoard = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                gameBoard[i][j] = 0;
            }
        }
        printBoard();
    }
    
    public Board(int[][] b) {
        gameBoard = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                gameBoard[i][j] = b[i][j];
            }
        }
    }
    
    //places the player move on the board if legal
    public boolean getPlayerMove(int i, int j) {
        if (checkLegalMove(i, j - 1) == 0) {
            gameBoard[i][j - 1] = 2;
            printBoard();
            return true;
        }
        else if (checkLegalMove(i, j - 1) == 1) {
            System.out.println("Move already taken!");
            return false;
        }
        else {
            System.out.println("Invalid move!");
            return false;
        }
    }
    
    //checks if given move is legal
    public int checkLegalMove(int i, int j) {
        if (i < SIZE && i >= 0 && j < SIZE && j >= 0) {
            if (gameBoard[i][j] == 0) {
                return 0;
            }
            else {
                return 1;
            }
        }
        else 
            return -1;
    }
    
    public int evaluate() {
        return 0;
    }
    
    //determine what move does the computer make
    public void makeMove() {
        int best = -20000;
        int depth = MAXDEPTH;
        int score, mi = 0, mj = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (gameBoard[i][j] == 0) {
                    gameBoard[i][j] = 1; //make move on board
                    score = min(depth - 1);
                    if (score > best) {
                        mi = i;
                        mj = j;
                        best = score;
                    }
                    gameBoard[i][j] = 0; //undo move
                }
            }
        }
        gameBoard[mi][mj] = 1;
        printBoard();
    }
    
    int min(int depth) {
        int best = 20000;
        int score;
        if (check4winner() != 0) {
            return (check4winner());
        }
        if (depth == 0) {
            return (evaluate());
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (gameBoard[i][j] == 0) {
                    gameBoard[i][j] = 2; // make move on board
                    score = max(depth - 1);
                    if (score < best) {
                        best = score;
                    }
                    gameBoard[i][j] = 0; // undo move
                }
            }
        }
        return (best);
    }

    int max(int depth) {
        int best = -20000;
        int score;
        if (check4winner() != 0) {
            return (check4winner());
        }
        if (depth == 0) {
            return (evaluate());
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (gameBoard[i][j] == 0) {
                    gameBoard[i][j] = 1; // make move on board
                    score = min(depth - 1);
                    if (score > best) {
                        best = score;
                    }
                    gameBoard[i][j] = 0; // undo move
                }
            }
        }
        return (best);
    }

    //check if there is four in a line
    public int check4winner() {
        return 0;
    }
    
    //prints the current board status
    public void printBoard() {
        String str = "ABCDEFGH";
        char[] c = str.toCharArray();
        System.out.print("\n ");
        for (int i = 1; i <= 8; i++) {
            System.out.print(" " + i);
        }
        System.out.print("\n");
        for (int j = 0; j < SIZE; j++) {
            System.out.print(c[j]);
            for (int k = 0; k < SIZE; k++) {
                switch (gameBoard[j][k]) {
                    case 0:
                        System.out.print(" -");
                        break;
                    case 1:
                        System.out.print(" X");
                        break;
                    default:
                        System.out.print(" O");
                        break;
                }
            }
            System.out.print("\n");
        }
        System.out.println();
    }

}
