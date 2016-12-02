package CS420Project3;

import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Would you like to go first? (y/n): ");
        String response = sc.nextLine();
        if (response.toLowerCase().equals("y")) {
            System.out.print("\nHow long should the computer think about its moves (in secs)?: ");
            long time = sc.nextInt();
            Board b = new Board(time);
            gameLoop(b);
            
        }
        else {
            System.out.print("\nHow long should the computer think about its moves (in secs)?: ");
            int time = sc.nextInt();
            Board b = new Board(time);
            b.makeMove();
            gameLoop(b);

        }
    }
    
    //main game loop
    public static void gameLoop(Board b) {
        Scanner sc = new Scanner(System.in);
        while (true) {
                while (true) { //error checking
                    System.out.print("Choose your move: ");
                    String move = sc.nextLine();
                    String[] split = move.split("");
                    int x = charPosition(split[0]);
                    int y = Integer.parseInt(split[1]);
                    if (b.getPlayerMove(x,y)) {
                        b.gameOver(x, y - 1, 2);
                        break;
                    } 
                }
                b.makeMove();
                
            }
    }
    
    //returns the position based on the character
    public static int charPosition(String s) {
        String str = "ABCDEFGH";
        String[] c = str.split("");
        for (int i = 0; i < c.length; i++) {
            if (s.toUpperCase().equals(c[i]))
                return i;
        }
        return -1;
    }
    
}

