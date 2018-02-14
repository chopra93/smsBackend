package temp2.dto;

/**
 * @author chopra
 * 14/02/18
 */
public class Rectangle extends Shape {
    private int coordinateX;
    private int coordinateY;
    private int width;
    private int height;

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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        String props = super.toString();
        return "<rect x = \"" + coordinateX + "\" y = \"" + coordinateY + "\" width = \"" + width + "\" height = \"" + height + "\""+props+"/>\n";
    }
}
