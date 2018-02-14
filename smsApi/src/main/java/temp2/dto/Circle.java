package temp2.dto;

/**
 * @author chopra
 * 14/02/18
 */
public class Circle extends Shape{
    private int coordinateX;
    private int coordinateY;
    private int radius;

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        String props = super.toString();
        return "<circle cx = \"" + coordinateX + "\" cy = \"" + coordinateY + "\" r = \"" + radius + "\""+props+"/>\n";
    }
}
