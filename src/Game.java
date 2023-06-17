import java.awt.*;
import java.util.Scanner;

public class Game {
    private Field field;
    private boolean isDead = false;
    private boolean isWon = false;
    private Directions currentDirection = Directions.NONE;
    private int score = 0;
    public Game(int width, int height) {
        field = new Field(width, height);
    }

    public void start() {
        isDead = false;
        isWon = false;
        Scanner scanner = new Scanner(System.in);

        while(true) {
            field.showField();
            System.out.println("Score: " + score);

            char ch = scanner.next().charAt(0);

            if(ch == '0') {
                System.out.println("Game over");
                break;
            }

            handleMove(ch);

            if(isDead == true) {
                System.out.println("You're dead. Sorry :(");
                break;
            }

            if(isWon == true) {
                System.out.println("You win! :)");
                break;
            }

        }
        scanner.close();
    }

    public int getScore() {
        return score;
    }
    private boolean handleMove(char ch) {
        boolean res = false;
        Point head = field.snake.getHead();
        switch(ch) {
            case 'w':
                if(currentDirection != Directions.DOWN) {
                    if (!field.checkCell(head.x, head.y - 1)) {
                        isDead = true;
                    } else {
                        field.snake.moveUp();
                        currentDirection = Directions.UP;
                        res = true;
                    }
                }
                break;
            case 's':
                if(currentDirection != Directions.UP) {
                    if (!field.checkCell(head.x, head.y + 1)) {
                        isDead = true;
                    } else {
                        field.snake.moveDown();
                        currentDirection = Directions.DOWN;
                        res = true;
                    }
                }
                break;
            case 'a':
                if(currentDirection != Directions.RIGHT) {
                    if (!field.checkCell(head.x - 1, head.y)) {
                        isDead = true;
                    } else {
                        field.snake.moveLeft();
                        currentDirection = Directions.LEFT;
                        res = true;
                    }
                }
                break;
            case 'd':
                if(currentDirection != Directions.LEFT) {
                    if (!field.checkCell(head.x + 1, head.y)) {
                        isDead = true;
                    } else {
                        field.snake.moveRight();
                        currentDirection = Directions.RIGHT;
                        res = true;
                    }
                }
                break;
        }

        head = field.snake.getHead();
        Point food = field.getFood();
        if(head.x == food.x && head.y == food.y) {
            field.snake.growUp();
            score++;

            boolean isGameContinue = field.placeFood();
            if(isGameContinue == false) {
                isWon = true;
            }
        }

        return res;
    }

    enum Directions{
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NONE
    }
}
