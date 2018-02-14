package temp2.dto;

/**
 * @author chopra
 * 14/02/18
 */
public class LineSegment extends Shape {
    private int coordinateX1;
    private int coordinateX2;
    private int coordinateY1;
    private int coordinateY2;

    public int getCoordinateX1() {
        return coordinateX1;
    }

    public void setCoordinateX1(int coordinateX1) {
        this.coordinateX1 = coordinateX1;
    }

    public int getCoordinateX2() {
        return coordinateX2;
    }

    public void setCoordinateX2(int coordinateX2) {
        this.coordinateX2 = coordinateX2;
    }

    public int getCoordinateY1() {
        return coordinateY1;
    }

    public void setCoordinateY1(int coordinateY1) {
        this.coordinateY1 = coordinateY1;
    }

    public int getCoordinateY2() {
        return coordinateY2;
    }

    public void setCoordinateY2(int coordinateY2) {
        this.coordinateY2 = coordinateY2;
    }

    @Override
    public String toString() {
        String props = super.toString();
        return "<Line cx1=\"" + coordinateX1 + "\" cx2=\"" + coordinateX2 + "\" cy1=\"" + coordinateY1 + "\" cy2=\"" + coordinateY2 + "\""+props+"/>\n";
    }
}
