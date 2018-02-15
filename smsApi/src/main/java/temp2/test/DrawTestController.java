package temp2.test;

import temp2.controller.IDrawController;
import temp2.controller.impl.DrawControllerImpl;

/**
 * @author chopra
 * 15/02/18
 */
public class DrawTestController {
    public static void main(String []args){
        IDrawController controller = new DrawControllerImpl();
        controller.fill("500 500");
        controller.fill("setFillColor #CADA56");
        controller.fill("setStrokeColor #CADA56");
        controller.fill("circle");
        controller.fill("coordinate 50 50");
        //controller.fill("coordinate 50 90");
        controller.fill("setFillColor #DDAA56");
        controller.fill("setStrokeColor #DDDA56");
        controller.fill("coordinate 90 100");
        controller.fill("coordinate 90 140");
        controller.fill("setStrokeColor #FFB700");
        controller.fill("rectangle");
        controller.fill("coordinate 90 90");

        controller.fill("circle");

        controller.fill("coordinate 390 190");
        controller.fill("coordinate 90 190");
        controller.draw();
    }
}
