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

        }
        return null;
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
