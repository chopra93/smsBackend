package temp2.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chopra
 * 14/02/18
 */
public class Canvas {
    private int width;
    private int height;
    List<Shape> shapes;

    public Canvas() {
        shapes = new ArrayList<>();
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

    public void addShape(Shape shape){
        shapes.add(shape);
    }

    @Override
    public String toString() {
        String ret = "<svg width = \"" + width + "\" height = \"" + height + "\">\n";
        for (Shape sh : shapes) {
            ret += sh.toString();
        }
        ret += "</svg>";
        return ret;
    }
}
