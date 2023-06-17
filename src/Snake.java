import java.awt.*;
import java.util.function.Function;

public class Snake {
    private Point[] body;

    public Snake(Point head) {
        body = new Point[] { new Point(head.x, head.y) };
    }

    public Point[] getBody() {
        return body;
    }

    public Point getHead() {
        return body.length > 0 ? body[0] : null;
    }


    public void growUp() {
        int length = body.length;

        if(length == 0) {
            return;
        }

        Point[] newBody = new Point[length + 1];

        for(int i = 0; i < length; i++) {
            newBody[i] = body[i];
        }

        newBody[length] = body[length - 1];
        body = newBody;
    }

    public void moveLeft() {
        move((p) -> new Point(p.x - 1, p.y));
    }

    public void moveRight() {
        move((p) -> new Point(p.x + 1, p.y));
    }

    public void moveUp() {
        move((p) -> new Point(p.x, p.y - 1));
    }


    public void moveDown() {
        move((p) -> new Point(p.x, p.y + 1));
    }

    private void move(Function<Point, Point> func) {
        int length = body.length;

        if(length == 0) {
            return;
        }

        Point head = new Point(body[0].x, body[0].y);

        for(int i = length - 1; i > 0; i--) {
            body[i] = body[i - 1];
        }
        body[0] = func.apply(head);
    }
}
