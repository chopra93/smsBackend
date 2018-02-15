package temp2.strategy.impl;

import temp2.dto.Circle;
import temp2.dto.Shape;
import temp2.strategy.IDrawStrategy;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chopra
 * 14/02/18
 */
public class CircleStrategy implements IDrawStrategy {

    private Circle circle;
    private boolean first = true;

    @Override
    public Shape fill(String input, String fillColor, String strokeColor, AtomicBoolean isComplete) {
        validate(input);
        if (first){
            circle = new Circle();
            circle.setFillColor(fillColor);
            circle.setStrokeColor(strokeColor);
            circle.setCoordinateX(Integer.parseInt(input.split(" ")[1]));
            circle.setCoordinateY(Integer.parseInt(input.split(" ")[2]));
            first = !first;
            isComplete.set(false);
            return null;
        }
        else {
            first = !first;
            circle.setRadius(getRadius(input));
            isComplete.set(true);
            return circle;
        }
    }

    private int getRadius(String input){
        int x = circle.getCoordinateX()-Integer.parseInt(input.split(" ")[1]);
        int y = circle.getCoordinateY()-Integer.parseInt(input.split(" ")[2]);
        int rad = (int) Math.sqrt(x*x+y*y);
        return rad;
    }

    private boolean validate(String input){
        if (input == null){
            return false;
        }
        if (circle == null){
            circle = new Circle();
        }
        return true;
    }
}
