package CS420Project3;

public class Board {
    private int[][] gameBoard;
    private static final int SIZE = 8;
    private static final int MAXDEPTH = 5;
    private long time = 30;
    private long startTime;
    
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

    Board(long time) {
        this.time = time * 1000 - 500;
        gameBoard = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                gameBoard[i][j] = 0;
            }
        }
        printBoard();
    }
    
    //places the player move on the board if legal
    public boolean getPlayerMove(int i, int j) {
        switch (checkLegalMove(i, j - 1)) {
            case 0:
                gameBoard[i][j - 1] = 2;
                printBoard();
                return true;
            case 1:
                System.out.println("Move already taken!");
                return false;
            default:
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
        int score, mi = 4, mj = 4;
        startTimer();
        for (int i = 3; i !=2; i = (i+1)%SIZE) {
            for (int j = 3; j != 2; j = (j+1)%SIZE) {
                if (gameBoard[i][j] == 0) {
                    gameBoard[i][j] = 1; //make move on board
                    score = min(depth - 1, i, j);
                    if (score > best) {
                        mi = i;
                        mj = j;
                        best = score;
                    }
                    gameBoard[i][j] = 0; //undo move
                }
            }
            if (gameBoard[i][2] == 0) {
                    gameBoard[i][2] = 1; //make move on board
                    score = min(depth - 1, i, 2);
                    if (score > best) {
                        mi = i;
                        mj = 2;
                        best = score;
                    }
                    gameBoard[i][2] = 0; //undo move
                }
        }
        for (int j = 3; j != 2; j = (j + 1) % SIZE) {
            if (gameBoard[2][j] == 0) {
                gameBoard[2][j] = 1; //make move on board
                score = min(depth - 1, 2, j);
                if (score > best) {
                    mi = 2;
                    mj = j;
                    best = score;
                }
                gameBoard[2][j] = 0; //undo move
            }
        }
        
        gameBoard[mi][mj] = 1;
        printBoard();
        System.out.println((System.currentTimeMillis() - startTime)/1000 + "\n");
        gameOver(mi,mj,1);
    }
    
    int min(int depth, int x, int y) {
        int best = 20000;
        int beta = -20000;
        int score;
        
        if((System.currentTimeMillis()- startTime > time )){
            return -1;
        }
        
        if (check4Winner(x, y, 1) != 0) {
            return (check4Winner(x, y, 1));
        }
        if (depth == 0) {
            return (evaluate());
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (gameBoard[i][j] == 0) {
                    gameBoard[i][j] = 2; // make move on board
                    score = max(depth - 1, i, j);
                    if (score < best) {
                        best = score;
                    }
                    gameBoard[i][j] = 0; // undo move
                    if (score >= beta){
                        beta = score;
                    }else{
                        return best;
                    }

                }
            }
        }
        return (best);
    }

    int max(int depth, int x, int y) {
        int best = -20000;
        int beta = 20000;
        int score;
        if (check4Winner(x, y, 2) != 0) {
            return check4Winner(x,y,2);
        }
        if (depth == 0) {
            return (evaluate());
        }
        
        if((System.currentTimeMillis()- startTime > time )){
            return 1;
        }
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (gameBoard[i][j] == 0) {
                    gameBoard[i][j] = 1; // make move on board
                    score = min(depth - 1, i , j);
                    if (score > best) {
                        best = score;
                    }
                    gameBoard[i][j] = 0; // undo move 
                    
                    if (score <= beta) {
                        beta = score;
                    } else {
                        return score;
                    }
                    
                }
            }
        }
        return (best);
    }
    
    private void startTimer(){
        startTime = System.currentTimeMillis();
    }
    

    //check if there is four in a line
    public int check4Winner(int row, int col, int color) {
        if (color == 1) { //computer
            if (row == 0 && col == 0) {
                if (checkRight(++row, col, color) >= 4) {
                    return 5000; 
                }
                if (checkUp(row, ++col, color) >= 4) {
                    return 5000; 
                }
            }
            if (row == 0 && col == SIZE - 1) {
                if (checkRight(++row, col, color) >= 4) {
                    return 5000;
                }
                if (checkDown(row, --col, color) >= 4) {
                    return 5000; 
                }
            }
            if (row == SIZE - 1 && col == SIZE - 1) {
                if (checkLeft(--row, col, color) >= 4) {
                    return 5000;
                }
                if (checkDown(row, --col, color) >= 4) {
                    return 5000; 
                }
            }
            if (row == SIZE - 1 && col == 0) {
                if (checkLeft(--row, col, color) >= 4) {
                    return 5000;
                }
                if (checkUp(row, ++col, color) >= 4) {
                    return 5000;
                }
            }
            if (row == 0) {
                if (checkRight(++row, col, color) >= 4) {
                    return 5000; 
                }
                if (checkUp(row, ++col, color) + checkDown(row, --col, color) >= 4) {
                    return 5000; 
                }
            }
            if (col == SIZE - 1) {
                if (checkLeft(--row, col, color) + checkRight(++row, col, color) >= 4) {
                    return 5000; 
                }
                if (checkDown(row, --col, color) >= 4) {
                    return 5000; 
                }
            }
            if (row == SIZE - 1) {
                if (checkLeft(--row, col, color) >= 4) {
                    return 5000; 
                }
                if (checkUp(row, ++col, color) + checkDown(row, --col, color) >= 4) {
                    return 5000;
                }
            } 
            if (col == 0) {
                if (checkLeft(--row, col, color) + checkRight(++row, col, color) >= 4) {
                    return 5000; 
                }
                if (checkUp(row, ++col, color) >= 4) {
                    return 5000; 
                }
            }
            if (checkLeft(--row, col, color) + checkRight(++row, col, color) >= 4) {
                return 5000; 
            }
            if (checkUp(row, ++col, color) + checkDown(row, --col, color) >= 4) {
                return 5000; 
            }
        }
        if (color == 2) { //player
            if (row == 0 && col == 0) {
                if (checkRight(++row, col, color) >= 4) {
                    return -5000; 
                }
                if (checkUp(row, ++col, color) >= 4) {
                    return -5000; 
                }
            }
            if (row == 0 && col == SIZE - 1) {
                if (checkRight(++row, col, color) >= 4) {
                    return -5000;
                }
                if (checkDown(row, --col, color) >= 4) {
                    return -5000; 
                }
            }
            if (row == SIZE - 1 && col == SIZE - 1) {
                if (checkLeft(--row, col, color) >= 4) {
                    return -5000;
                }
                if (checkDown(row, --col, color) >= 4) {
                    return -5000; 
                }
            }
            if (row == SIZE - 1 && col == 0) {
                if (checkLeft(--row, col, color) >= 4) {
                    return -5000;
                }
                if (checkUp(row, ++col, color) >= 4) {
                    return -5000;
                }
            }
            if (row == 0) {
                if (checkRight(++row, col, color) >= 4) {
                    return -5000; 
                }
                if (checkUp(row, ++col, color) + checkDown(row, --col, color) >= 4) {
                    return -5000; 
                }
            }
            if (col == SIZE - 1) {
                if (checkLeft(--row, col, color) + checkRight(++row, col, color) >= 4) {
                    return -5000; 
                }
                if (checkDown(row, --col, color) >= 4) {
                    return -5000; 
                }
            }
            if (row == SIZE - 1) {
                if (checkLeft(--row, col, color) >= 4) {
                    return -5000; 
                }
                if (checkUp(row, ++col, color) + checkDown(row, --col, color) >= 4) {
                    return -5000;
                }
            } 
            if (col == 0) {
                if (checkLeft(--row, col, color) + checkRight(++row, col, color) >= 4) {
                    return -5000; 
                }
                if (checkUp(row, ++col, color) >= 4) {
                    return -5000; 
                }
            }
            if (checkLeft(--row, col, color) + checkRight(++row, col, color) >= 4) {
                return -5000; 
            }
            if (checkUp(row, ++col, color) + checkDown(row, --col, color) >= 4) {
                return -5000; 
            }
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (gameBoard[i][j] == 0)
                    return 0; 
            }
        }
        return -1; //draw 
    }
    
    public int checkUp(int x, int y, int color) {
        if (color == gameBoard[x][y] && y == SIZE - 1)
            return 1;
        if (color == gameBoard[x][y])
            return 1 + checkUp(x, ++y, color);
        return 0;
    }
    
    public int checkDown(int x, int y, int color) {
        if (color == gameBoard[x][y] && y == 0)
            return 1;
        if (color == gameBoard[x][y])
            return 1 + checkDown(x, --y, color);
        return 0;
    }
    
    public int checkLeft(int x, int y, int color) {
        if (color == gameBoard[x][y] && x == 0)
            return 1;
        if (color == gameBoard[x][y])
            return 1 + checkLeft(--x, y, color);
        return 0;
    }
    
    public int checkRight(int x, int y, int color) {
        if (color == gameBoard[x][y] && x == SIZE - 1)
            return 1;
        if (color == gameBoard[x][y])
            return 1 + checkRight(++x, y, color);
        return 0;
    }
    
    //determines the result of the game
    public void gameOver(int i, int j, int color) {
        if (check4Winner(i,j, color) == 5000){
            System.out.println("Computer wins!"); 
            System.exit(0);
        }
        if (check4Winner(i,j, color) == -5000){
            System.out.println("You win!");
            System.exit(0);
        }
        if (check4Winner(i,j, color) == 1){
            System.out.println("Draw!");
            System.exit(0);
        }
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
