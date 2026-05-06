import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    // snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    // food
    Tile snakeFood;
    Random random;

    // logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        snakeFood = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    // Draw the game components
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // configure the components being drawn
    public void draw(Graphics g) {

        // grid
        Color gridColor = new Color(5, 5, 5); // dark gray
        g.setColor(gridColor);
        for (int i = 0; i < boardWidth / tileSize; i++) {
            // Draw vertical lines
            g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
            // Draw horizontal lines
            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        }

        // food
        g.setColor(Color.RED);
        g.fillRect(snakeFood.x * tileSize, snakeFood.y * tileSize, tileSize, tileSize);

        // snake head
        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

        // snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }

        // score
        g.setFont(new Font("Arial", Font.BOLD, 20));
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 80));
            g.setColor(Color.RED);
            // draw the game over message in the center of the screen
            g.drawString("Game Over!", boardWidth / 2 - 225,
                    boardHeight / 2);
            g.drawString("Score: " + String.valueOf(snakeBody.size()), boardWidth / 2 - 150,
                    boardHeight / 2 + 100);
        } else {
            g.setColor(Color.GREEN);
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }

    }

    // Place the food at a random location
    public void placeFood() {
        snakeFood.x = random.nextInt(boardWidth / tileSize); // 600 / 25 = 24 -> Random number from 0 to 24
        snakeFood.y = random.nextInt(boardHeight / tileSize); // 600 / 25 = 24 -> Random number from 0 to 24
    }

    // collision detection
    public boolean collision(Tile a, Tile b) {
        return a.x == b.x && a.y == b.y;
    }

    // Move the snake
    public void move() {

        // check if the snake has eaten the food
        if (collision(snakeHead, snakeFood)) {
            snakeBody.add(new Tile(snakeFood.x, snakeFood.y));
            placeFood();
        }

        // move the snake body
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // move the snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // game over conditions
        // Check if the snake has hit itself
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            // If the snake head collides with any part of its body
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        // Check if the snake has hit the wall
        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || snakeHead.y * tileSize < 0
                || snakeHead.y * tileSize > boardHeight) {
            gameOver = true;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }

    }

    // Not needed
    @Override
    public void keyTyped(KeyEvent e) {
    }

    // Not needed
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
