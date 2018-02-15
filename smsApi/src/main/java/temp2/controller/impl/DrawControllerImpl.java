package temp2.controller.impl;

import temp2.controller.IDrawController;
import temp2.service.IDrawService;
import temp2.service.impl.DrawServiceImpl;

/**
 * @author chopra
 * 15/02/18
 */
public class DrawControllerImpl implements IDrawController {

    private IDrawService drawService;

    public DrawControllerImpl() {
        this.drawService = new DrawServiceImpl();
    }

    @Override
    public String draw() {
        System.out.println(drawService.draw());
        return drawService.draw();
    }

    @Override
    public void fill(String input) {
        drawService.fill(input);
    }

}
