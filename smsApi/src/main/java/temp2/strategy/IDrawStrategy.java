package temp2.strategy;

import temp2.dto.Shape;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chopra
 * 14/02/18
 */
public interface IDrawStrategy {
    Shape fill(String input, String fillColor, String strokeColor, AtomicBoolean isComplete);
}
