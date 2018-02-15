package temp2.strategy.impl;

import temp2.dto.LineSegment;
import temp2.dto.Shape;
import temp2.strategy.IDrawStrategy;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chopra
 * 15/02/18
 */
public class LineStrategy implements IDrawStrategy{

    LineSegment line;
    boolean first = true;

    @Override
    public Shape fill(String input, String fillColor, String strokeColor, AtomicBoolean isComplete) {
        validate(input);
        if (first) {
            line = new LineSegment();
            line.setFillColor(fillColor);
            line.setStrokeColor(strokeColor);
            line.setCoordinateX1(Integer.parseInt(input.split(" ")[1]));
            line.setCoordinateY1(Integer.parseInt(input.split(" ")[2]));
            first = !first;
            isComplete.set(false);
            return null;
        } else {
            first = !first;
            line.setCoordinateX2(Integer.parseInt(input.split(" ")[1]));
            line.setCoordinateY2(Integer.parseInt(input.split(" ")[2]));
            isComplete.set(true);
            return line;
        }
    }

    private boolean validate(String input) {
        if (input == null){
            return false;
        }
        if (line == null) {
            line = new LineSegment();
        }
        return true;
    }
}
