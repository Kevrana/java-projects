import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {

        final int BOARD_WIDTH = 600, BOARD_HEIGHT = 600;

        JFrame frame = new JFrame("Snake");

        frame.setVisible(true);
        frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(BOARD_WIDTH, BOARD_HEIGHT);
        frame.add(snakeGame);
        frame.pack();

        snakeGame.requestFocus();
    }
}
