package TicTacToe;
import java.io.*;
import java.net.*;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 327;
    private static Game game = new Game();

    public static void main(String[] args) {
        System.out.println("Server is starting...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            var pool = Executors.newFixedThreadPool(2); // Support 2 players
            System.out.println("Waiting for players to connect...");
            int playerId = 1;
            while (playerId <= 2) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Player " + playerId + " connected.");
                pool.execute(new PlayerHandler(clientSocket, playerId));
                playerId++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class PlayerHandler implements Runnable {
        private Socket socket;
        private int playerId;

        PlayerHandler(Socket socket, int playerId) {
            this.socket = socket;
            this.playerId = playerId;
        }

        @Override
        public void run() {
            try (var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    var out = new PrintWriter(socket.getOutputStream(), true)) {
                out.println("Welcome Player " + playerId + "!");

                while (true) {
                    synchronized (game) {
                        if (game.isGameOver()) {
                            out.println("Game over! " + game.getWinnerMessage());
                            break;
                        }

                        if (game.getCurrentPlayer() != playerId) {
                            out.println("Wait for your turn...");
                            try {
                                game.wait(); // Wait for the other player to make a move
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            out.println(game.getBoardString());
                            out.println("Your turn! Enter your move (row and column, e.g., 1 1):");
                            String move = in.readLine();
                            if (game.makeMove(playerId, move)) {
                                game.notifyAll(); // Notify the other player that the move is made
                            } else {
                                out.println("Invalid move. Try again.");
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
