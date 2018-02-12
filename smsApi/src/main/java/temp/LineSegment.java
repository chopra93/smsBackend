package temp;

/**
 * Created by Chopra on 12/02/18.
 */
public class LineSegment extends Shape
{
    private Point start;
    private Point end;

    public LineSegment() {
    }

    public LineSegment(String fillColour, String strokeColour, String shapeType) {
        super(fillColour, strokeColour, shapeType);
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "LineSegment{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
