package temp;

/**
 * Created by Chopra on 12/02/18.
 */
public class Rectangle extends Shape {
    private Point topLeft;
    private Point bottomRight;

    public Rectangle() {
    }

    public Rectangle(String fillColour, String strokeColour, String shapeType) {
        super(fillColour, strokeColour, shapeType);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "topLeft=" + topLeft +
                ", bottomRight=" + bottomRight +
                '}';
    }
}
