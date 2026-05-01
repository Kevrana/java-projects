import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// A simple Tic Tac Toe game in Java
public class TicTacToe {

    // dimensions of the game board, including space for the text display on the
    // top, and a restart button and scoreboard on bottom.
    private static final int BOARD_WIDTH = 600;
    private static final int BOARD_HEIGHT = 750;

    // player strings
    private static final String PLAYER_X = "X";
    private static final String PLAYER_O = "O";
    String currentPlayer = PLAYER_X;
    boolean gameOver = false;
    int turnCount = 0;
    int playerXScore = 0;
    int playerOScore = 0;

    JFrame frame = new JFrame("Tic Tac Toe"); // Frame for the game window
    JLabel textLabel = new JLabel(); // Label for displaying the game status (e.g., whose turn it is, game // over,
                                     // tie, etc.)
    JLabel scoreLabel = new JLabel(); // Label for displaying the scoreboard
    JPanel textPanel = new JPanel(); // Panel for displaying the game status area
    JPanel gamePanel = new JPanel(); // Panel for displaying the game board
    JPanel restartPanel = new JPanel(); // Panel for displaying the restart button
    JPanel scorePanel = new JPanel(); // Panel for displaying the scoreboard
    JPanel bottomPanel = new JPanel(); // Panel for displaying the restart button and scoreboard
    JButton restartButton = new JButton("Restart");
    JButton[][] board = new JButton[3][3]; // array for the tiles on the game board

    TicTacToe() {
        // Set the properties of the frame
        frame.setSize(BOARD_WIDTH, BOARD_HEIGHT); // Set the size of the game window
        frame.setLocationRelativeTo(null); // Center the game window on the screen
        frame.setResizable(false); // Make the game window non-resizable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the game window when the user clicks the close
                                                              // button
        frame.setLayout(new BorderLayout());

        // Set the properties of the text label
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Kevin's Tic Tac Toe!");
        textLabel.setOpaque(true);

        // add the text label to the text panel and the text panel to the frame
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // Set the properties of the game panel
        gamePanel.setLayout(new GridLayout(3, 3));
        gamePanel.setBackground(Color.darkGray);

        // add the game panel to the frame
        frame.add(gamePanel);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton tile = new JButton();
                board[row][col] = tile;
                gamePanel.add(tile);

                // set the properties of each tile
                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                // add an action listener to each tile
                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) {
                            return;
                        }
                        // Handle tile click
                        JButton clickedTile = (JButton) e.getSource();
                        if (clickedTile.getText().equals("")) {
                            clickedTile.setText(currentPlayer);
                            turnCount++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer == PLAYER_X ? PLAYER_O : PLAYER_X;
                                textLabel.setText(currentPlayer + "'s turn");
                            }
                        }
                    }
                });
            }
        }

        // Set the properties of the score label
        scoreLabel.setBackground(Color.darkGray);
        scoreLabel.setForeground(Color.white);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setText("Player X: " + playerXScore + " | Player O: " + playerOScore);
        scoreLabel.setOpaque(true);

        // Add the score label to the score panel and the score panel to the bottom
        // panel
        scorePanel.setLayout(new BorderLayout());
        scorePanel.add(scoreLabel);
        bottomPanel.add(scorePanel);

        // Set the properties of the restart button
        restartButton.setBackground(Color.green);
        restartButton.setForeground(Color.black);
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setHorizontalAlignment(JLabel.CENTER);
        restartButton.setText("Restart");
        restartButton.setFocusable(false);

        // add the restart button to the restart panel and the restart panel to the
        // bottom panel
        restartPanel.setLayout(new BorderLayout());
        restartPanel.add(restartButton);
        bottomPanel.add(restartPanel);

        // Add the bottom panel to the frame
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Add an action listener to the restart button
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Reset the game
                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        board[row][col].setFont(new Font("Arial", Font.BOLD, 120));
                        board[row][col].setText("");
                        board[row][col].setBackground(Color.darkGray);
                        board[row][col].setForeground(Color.white);
                    }
                }
                currentPlayer = PLAYER_X;
                turnCount = 0;
                gameOver = false;
                textLabel.setText("Kevin's Tic Tac Toe!");
                textLabel.setForeground(Color.white);
            }
        });

        frame.setVisible(true); // Make the game window visible
    }

    private void checkWinner() {
        // Check rows
        for (int row = 0; row < 3; row++) {
            // Check if the row is empty
            if (board[row][0].getText() == "")
                continue;

            // Check if the row has a winner where all three tiles have the same value
            if (board[row][0].getText() == board[row][1].getText()
                    && board[row][1].getText() == board[row][2].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[row][i]);
                }
                gameOver = true;
                return;
            }
        }
        // Check columns
        for (int col = 0; col < 3; col++) {
            // Check if the column is empty
            if (board[0][col].getText() == "")
                continue;

            // Check if the column has a winner where all three tiles have the same value
            if (board[0][col].getText() == board[1][col].getText()
                    && board[1][col].getText() == board[2][col].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][col]);
                }
                gameOver = true;
                textLabel.setText("Game Over! " + currentPlayer + " wins!");
                return;
            }
        }
        // Check diagonals
        if (board[0][0].getText() == board[1][1].getText() && board[1][1].getText() == board[2][2].getText()
                && board[0][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            textLabel.setText("Game Over! " + currentPlayer + " wins!");
            return;
        }
        if (board[0][2].getText() == board[1][1].getText() && board[1][1].getText() == board[2][0].getText()
                && board[0][2].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][2 - i]);
            }
            gameOver = true;
            textLabel.setText("Game Over! " + currentPlayer + " wins!");
            return;
        }

        // Check for a tie
        if (turnCount == 9) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    setTie(board[i][j]);
                }
            }
            gameOver = true;
        }
    }

    private void setWinner(JButton tile) {
        tile.setBackground(Color.green);
        tile.setForeground(Color.gray);
        tile.setFont(new Font("Arial", Font.BOLD, 200));
        textLabel.setForeground(Color.red);
        textLabel.setText("Game Over! " + currentPlayer + " wins!");
    }

    private void setTie(JButton tile) {
        tile.setBackground(Color.yellow);
        tile.setForeground(Color.black);
        textLabel.setForeground(Color.red);
        textLabel.setText("Game Over! It's a tie!");
    }

    private void updateScore(String winner) {
        if (winner == PLAYER_X) {
            playerXScore++;
        } else if (winner == PLAYER_O) {
            playerOScore++;
        }
        scoreLabel.setText("X: " + playerXScore + " O: " + playerOScore);
    }
}
