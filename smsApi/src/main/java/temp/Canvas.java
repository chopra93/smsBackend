package temp;

/**
 * Created by Chopra on 12/02/18.
 */
public class Canvas {
    private Shape shape;
    private Shape defaultShape;
    private int height;
    private int width;


    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Shape getDefaultShape() {
        return defaultShape;
    }

    public void setDefaultShape(Shape defaultShape) {
        this.defaultShape = defaultShape;
    }

    @Override
    public String toString() {
        return "Canvas{" +
                "shape=" + shape +
                ", defaultShape=" + defaultShape +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
