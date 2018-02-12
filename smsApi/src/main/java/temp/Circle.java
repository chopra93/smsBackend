package temp;

/**
 * Created by Chopra on 12/02/18.
 */
public class Circle extends Shape {

    private Point center;
    private Point circle;

    public Circle() {
    }

    public Circle(String fillColour, String strokeColour, String shapeType) {
        super(fillColour,strokeColour,shapeType);
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Point getCircle() {
        return circle;
    }

    public void setCircle(Point circle) {
        this.circle = circle;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "center=" + center +
                ", circle=" + circle +
                '}';
    }
}
