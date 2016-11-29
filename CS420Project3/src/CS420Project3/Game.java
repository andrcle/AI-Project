package CS420Project3;

import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Would you like to go first? (y/n): ");
        String response = sc.nextLine();
        if (response.toLowerCase().equals("y")) {
            //System.out.print("\nHow long should the computer think about its moves (in secs)?: ");
            //int time = sc.nextInt();
            Board b = new Board();
            while (true) {
                while (true) {
                    System.out.print("Choose your move: ");
                    String move = sc.nextLine();
                    String[] split = move.split("");
                    if (b.getPlayerMove(charPosition(split[0]), Integer.parseInt(split[1]))) {
                        break;
                    } 
                }
                b.makeMove();
            }
        }
        else {
            Board b = new Board();
            b.makeMove();
            while (true) {
                while (true) {
                    System.out.print("Choose your move: ");
                    String move = sc.nextLine();
                    String[] split = move.split("");
                    if (b.getPlayerMove(charPosition(split[0]), Integer.parseInt(split[1]))) {
                        break;
                    } 
                }
                b.makeMove();
            }
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
