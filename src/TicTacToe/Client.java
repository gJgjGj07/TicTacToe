package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 327;
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;

    private static JFrame frame;
    private static JButton[][] buttons = new JButton[3][3];
    private static boolean myTurn = false;

    public static void main(String[] args) {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connected to the server!");

            // Set up the GUI
            setUpGUI();

            // Start a new thread to listen for server messages
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                        handleServerMessage(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setUpGUI() {
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 3));
        frame.setPreferredSize(new Dimension(480, 420));
        frame.pack();
        frame.setVisible(true);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setEnabled(false); // Disable buttons initially
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                frame.add(buttons[i][j]);
            }
        }

        frame.pack();
        frame.setVisible(true);
    }

    private static void handleServerMessage(String message) {
        if (message.startsWith("Your turn")) {
            myTurn = true;
            enableBoard(true);
        } else if (message.startsWith("Wait")) {
            myTurn = false;
            enableBoard(false);
        } else if (message.startsWith("Game over")) {
            JOptionPane.showMessageDialog(frame, message);
        } else if (message.startsWith("Board")) {
            updateBoard(message.substring(6)); // "Board: " is the prefix
        }
    }

    private static void enableBoard(boolean enable) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(enable);
            }
        }
    }

    private static void updateBoard(String boardState) {
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String cell = String.valueOf(boardState.charAt(index++));
                buttons[i][j].setText(cell.equals(" ") ? "" : cell);
            }
        }
    }

    private static class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (myTurn && buttons[row][col].getText().equals("")) {
                buttons[row][col].setText("X");
                out.println(row + " " + col);
                myTurn = false;
                enableBoard(false);
            }
        }
    }
}
