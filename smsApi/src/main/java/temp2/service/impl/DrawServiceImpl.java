package temp2.service.impl;

import org.apache.commons.lang3.StringUtils;
import temp2.dto.Canvas;
import temp2.dto.Shape;
import temp2.service.IDrawService;
import temp2.strategy.IDrawStrategy;
import temp2.strategy.impl.CircleStrategy;
import temp2.strategy.impl.LineStrategy;
import temp2.strategy.impl.RectangleStrategy;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chopra
 * 15/02/18
 */
public class DrawServiceImpl implements IDrawService {

    private String currentFillColor;
    private String currentStrokeColor;
    private IDrawStrategy strategy;
    private Canvas canvas;

    public DrawServiceImpl() {
        currentFillColor = "#FFFFFF";
        currentStrokeColor = "#00000";
    }

    @Override
    public String draw() {
        if (canvas == null)
            return "Canvas not created.";
        return canvas.toString();
    }

    @Override
    public void fill(String input) {
        try {
            if (!validateShapeRequest(input))
                return;
            processRequest(input);
        } catch (Throwable err) {
            System.out.println("Invalid Input.");
            //System.out.println("Error : " + Arrays.asList(err.getStackTrace()));
        }
    }

    private void processRequest(String input) throws Exception {
        if (canvas == null) {
            createCanvas(input);
            return;
        }
        processStrategy(input);
    }

    private void processStrategy(String input) throws Exception {
        if (input.equals("circle"))
            strategy = new CircleStrategy();
        else if (input.equals("rectangle"))
            strategy = new RectangleStrategy();
        else if (input.equals("line")) {
            strategy = new LineStrategy();
        } else if (input.startsWith("setFillColor"))
            currentFillColor = input.split(" ")[1];
        else if (input.startsWith("setStrokeColor"))
            currentStrokeColor = input.split(" ")[1];
        else {
            if (strategy == null) {
                throw new Exception("Invalid Input.");
            }
            AtomicBoolean isComplete = new AtomicBoolean();
            Shape sh = strategy.fill(input, currentFillColor, currentStrokeColor, isComplete);
            if (isComplete.get() == true) {
                canvas.addShape(sh);
            }
        }
    }

    private void createCanvas(String input) {

        int x = Integer.parseInt(input.split(" ")[0]);
        int y = Integer.parseInt(input.split(" ")[1]);
        if (x <= 0)
            return;
        if (y <= 0)
            return;
        canvas = new Canvas();
        canvas.setWidth(Integer.parseInt(input.split(" ")[0]));
        canvas.setHeight(Integer.parseInt(input.split(" ")[1]));
    }

    private boolean validateShapeRequest(String input) {
        if (StringUtils.isEmpty(input))
            return false;
        return true;
    }
}
