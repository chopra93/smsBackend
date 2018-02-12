package temp;

/**
 * Created by Chopra on 12/02/18.
 */
public class Shape {
    private String fillColour;
    private String strokeColour;
    private String shapeType;

    public Shape() {
    }

    public Shape(String fillColour, String strokeColour, String shapeType) {
        this.fillColour = fillColour;
        this.strokeColour = strokeColour;
        this.shapeType = shapeType;
    }

    public String getFillColour() {
        return fillColour;
    }

    public void setFillColour(String fillColour) {
        this.fillColour = fillColour;
    }

    public String getStrokeColour() {
        return strokeColour;
    }

    public void setStrokeColour(String strokeColour) {
        this.strokeColour = strokeColour;
    }

    public String getShapeType() {
        return shapeType;
    }

    public void setShapeType(String shapeType) {
        this.shapeType = shapeType;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "fillColour='" + fillColour + '\'' +
                ", strokeColour='" + strokeColour + '\'' +
                ", shapeType='" + shapeType + '\'' +
                '}';
    }
}
