package TicTacToe;
import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                var out = new PrintWriter(socket.getOutputStream(), true)) {
            System.out.println("Connected to the server!");

            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            var consoleInput = new BufferedReader(new InputStreamReader(System.in));
            String clientMessage;
            while ((clientMessage = consoleInput.readLine()) != null) {
                out.println(clientMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
