package temp2.dto;

/**
 * @author chopra
 * 14/02/18
 */
public class Shape {
    private String fillColor;
    private String strokeColor;

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }

    @Override
    public String toString() {
        return " stroke = \""+ getStrokeColor() +"\" fill = \""+getFillColor()+"\" ";
    }
}
