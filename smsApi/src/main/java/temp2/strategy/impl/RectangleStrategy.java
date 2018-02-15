package temp2.strategy.impl;

import temp2.dto.Rectangle;
import temp2.dto.Shape;
import temp2.strategy.IDrawStrategy;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chopra
 * 15/02/18
 */
public class RectangleStrategy implements IDrawStrategy {
    private Rectangle rectangle;
    private boolean first = true;

    @Override
    public Shape fill(String input, String fillColor, String strokeColor, AtomicBoolean isComplete) {
        validate(input);
        if (first) {
            rectangle.setFillColor(fillColor);
            rectangle.setStrokeColor(strokeColor);
            rectangle.setCoordinateX(Integer.parseInt(input.split(" ")[1]));
            rectangle.setCoordinateY(Integer.parseInt(input.split(" ")[2]));
            first = !first;
            isComplete.set(false);
            return null;
        } else {
            first = !first;
            rectangle.setHeight(Integer.parseInt(input.split(" ")[2])-rectangle.getCoordinateY());
            rectangle.setWidth((Integer.parseInt(input.split(" ")[1]))-rectangle.getCoordinateX());
            isComplete.set(true);
            return rectangle;
        }
    }

    private boolean validate(String input) {
        if(input == null){
            return false;
        }
        if (rectangle == null) {
            rectangle = new Rectangle();
        }
        return true;
    }
}
