package TicTacToe;
public class Game {
    private char[][] board = {
            { ' ', ' ', ' ' },
            { ' ', ' ', ' ' },
            { ' ', ' ', ' ' }
    };
    private int currentPlayer = 1; // Player 1 starts
    private boolean gameOver = false;
    private String winnerMessage = "It's a draw!";

    public synchronized boolean makeMove(int playerId, String move) {
        if (gameOver || playerId != currentPlayer)
            return false;

        String[] parts = move.split(" ");
        if (parts.length != 2)
            return false;

        try {
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != ' ')
                return false;

            board[row][col] = (playerId == 1) ? 'X' : 'O';
            if (checkWin()) {
                gameOver = true;
                winnerMessage = "Player " + playerId + " wins!";
            } else if (isBoardFull()) {
                gameOver = true;
            } else {
                currentPlayer = 3 - currentPlayer; // Switch between 1 and 2
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public synchronized String getBoardString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  0   1   2\n");
        for (int i = 0; i < 3; i++) {
            sb.append(i).append(" ");
            for (int j = 0; j < 3; j++) {
                sb.append(board[i][j]);
                if (j < 2)
                    sb.append(" | ");
            }
            sb.append("\n");
            if (i < 2)
                sb.append(" -----------\n");
        }
        return sb.toString();
    }

    public synchronized boolean isGameOver() {
        return gameOver;
    }

    public synchronized String getWinnerMessage() {
        return winnerMessage;
    }

    public synchronized int getCurrentPlayer() {
        return currentPlayer;
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return true;
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return true;
        }
        return (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
                (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ')
                    return false;
            }
        }
        return true;
    }
}
