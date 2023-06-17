import java.awt.*;
import java.io.IOException;

public class Field {
    final int WIDTH;
    final int HEIGHT;
    final char BORDER_SYMBOL = '#';
    final char EMPTY_SYMBOL = ' ';
    final char SNAKE_HEAD_SYMBOL = '@';
    final char SNAKE_BODY_SYMBOL = 'X';
    final char FOOD_SYMBOL = '*';
    final Snake snake;
    private Point food;
    private char[][] points;
    public Field(int width, int height) {
        WIDTH = width + 2;
        HEIGHT = height + 2;
        points = new char[HEIGHT][WIDTH];
        clearField();
        snake = new Snake(getSnakeHead());
        placeFood();
    }

    public boolean checkCell(int x, int y) {
        if(points[y][x] == BORDER_SYMBOL) return false;

        Point[] snakeBody = snake.getBody();
        for(Point point : snakeBody) {
            if(point.x == x && point.y == y) return false;
        }

        return true;
    }
    public Point getFood() {
        return food;
    }
    public void showField() {
        clearField();
        Point[] snakeBody = snake.getBody();

        int length = snakeBody.length;
        for(int i = 1; i < length; i++) {
            points[snakeBody[i].y][snakeBody[i].x] = SNAKE_BODY_SYMBOL;
        }

        points[snakeBody[0].y][snakeBody[0].x] = SNAKE_HEAD_SYMBOL;
        if(food != null) points[food.y][food.x] = FOOD_SYMBOL;

        cls();
        drawField();
    }

    private void drawField() {
        for(int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j < WIDTH; j++) {
                System.out.print(points[i][j]);
            }
            System.out.println();
        }
    }

    private void clearField() {
        for(int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j < WIDTH; j++) {
                if(i == 0 || i == HEIGHT - 1 || j == 0 || j == WIDTH - 1) {
                    points[i][j] = BORDER_SYMBOL;
                } else {
                    points[i][j] = EMPTY_SYMBOL;
                }
            }
        }
    }

    private Point getSnakeHead() {
        int x = (int)(1 + Math.random() * (WIDTH - 2));
        int y = (int)(1 + Math.random() * (HEIGHT - 2));
        return new Point(x, y);
    }

    private static void cls(){
        try {

            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c",
                        "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }

    public boolean placeFood() {
        boolean isFreeSpace = checkFreeSpace();
        if(isFreeSpace == false) return false;

        while(true) {
            int x = (int) (1 + Math.random() * (WIDTH - 2));
            int y = (int) (1 + Math.random() * (HEIGHT - 2));
            if(points[y][x] == EMPTY_SYMBOL) {
                food = new Point(x, y);
                break;
            }
        }
        return true;
    }

    private boolean checkFreeSpace() {
        for (char[] row : points) {
            for(char cell : row) {
                if(cell == EMPTY_SYMBOL) {
                    return true;
                }
            }
        }
        return false;
    }
}
