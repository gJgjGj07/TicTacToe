import java.util.Scanner;

public class TicTacToe {
    private static char[][] board = {
        {' ',' ',' '},
        {' ',' ',' '},
        {' ',' ',' '}
    };

    private static char currentPlayer = 'X';
    private static boolean gameOver = false;

    public static void main(String[] args) {
        printBoard();
        playerMove();
        gameOver = checkWin();
        if (!gameOver) {
            switchPlayer();
        }
    };
    
    private static void printBoard() {
        System.out.println("  0   1   2");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2)
                    System.out.print(" | ");
            }
            System.out.println();
            if (i < 2)
                System.out.println(" -----------");
        }
    }

    private static void playerMove() {
        Scanner scanner = new Scanner(System.in);
        int row, col;

        while (true) {
            System.out.println("Player " + currentPlayer + ", please enter row and col:");
            row = scanner.nextInt();
            col = scanner.nextInt();
            //check avaivble then update
            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
                board[row][col] = currentPlayer;
                break;
            } else {
                System.out.println("Can't do it.");
            }
        }

    }
    
    private static void switchPlayer() {

    }

    private static boolean checkWin() {
        
        
    }
}
