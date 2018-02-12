package temp1;

/**
 * Created by Chopra on 12/02/18.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2018 PAYTM (P) Limited . All Rights Reserved. PAYTM PROPRIETARY/CONFIDENTIAL. Use is
 * subject to license terms.
 *
 * @author aloksharma
 * @version 1.0, 21/1/18
 */
public class Canvas {
    int height;
    int width;
    List<Shape> shapes;
    Shape defaultShape;

    Canvas(Shape s) {
        this.defaultShape = s;
        this.shapes = new ArrayList<>();
    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public Shape getDefaultShape() {
        return defaultShape;
    }

    public void setDefaultShape(Shape defaultShape) {
        this.defaultShape = defaultShape;
    }

    static List<String> getInput() {
        List<String> input = new ArrayList<>();
        input.add("s");
        input.add("setFillColor #CADA56");
        input.add("setStrokeColor #CADA56");

        input.add("circle");
        input.add("coordinate 50 50");
        input.add("coordinate 50 90");

        input.add("setFillColor #AAB700");
        input.add("setStrokeColor #AAB700");

        //input.add("circle");
        input.add("coordinate 90 100");
        input.add("coordinate 90 140");

        input.add("setStrokeColor #FFB700");

        input.add("rectangle");
        input.add("coordinate 90 90");
        input.add("coordinate 390 190");
        return input;
    }



    private static void processinput(List<String> input, Canvas canvas) {
        for(int i = 0; i < input.size(); i++) {
            String [] line = input.get(i).split("\\s+");
            if(i == 0) {
                canvas.setHeight(Integer.parseInt(line[0]));
                canvas.setWidth(Integer.parseInt(line[1]));
            }

            else if(line[0].equalsIgnoreCase("setFillColor")) {
                canvas.getDefaultShape().setFillColor(line[1]);
            }

            else if(line[0].equalsIgnoreCase("setStrokeColor")) {
                canvas.getDefaultShape().setStrokeColor(line[1]);
            }

            else if(line[0].equalsIgnoreCase("circle")) {
                canvas.getDefaultShape().setShapeType("circle");
            }
            else if(line[0].equalsIgnoreCase("rectangle")) {
                canvas.getDefaultShape().setShapeType("rectangle");
            }
            else if(line[0].equalsIgnoreCase("line")) {
                canvas.getDefaultShape().setShapeType("line");
            }

            else if(line[0].equalsIgnoreCase("coordinate")) {
                if(canvas.getDefaultShape().getShapeType().equalsIgnoreCase("circle")) {
                    Circle c = new Circle();
                    Point p = new Point();
                    p.setX(Integer.parseInt(line[1]));
                    p.setY(Integer.parseInt(line[2]));
                    c.setCentre(p);
                    i++;
                    line = input.get(i).split("\\s+");

                    p = new Point();
                    p.setX(Integer.parseInt(line[1]));
                    p.setY(Integer.parseInt(line[2]));
                    c.setCirle(p);

                    c.setShapeType("circle");
                    c.setFillColor(canvas.getDefaultShape().getFillColor());
                    c.setStrokeColor(canvas.getDefaultShape().getStrokeColor());
                    canvas.getShapes().add(c);

                }

                if(canvas.getDefaultShape().getShapeType().equalsIgnoreCase("rectangle")) {

                    Rectangle r = new Rectangle();
                    Point p = new Point();
                    p.setX(Integer.parseInt(line[1]));
                    p.setY(Integer.parseInt(line[2]));
                    r.setTopLeft(p);
                    i++;
                    line = input.get(i).split("\\s+");

                    p = new Point();
                    p.setX(Integer.parseInt(line[1]));
                    p.setY(Integer.parseInt(line[2]));
                    r.setBottomRight(p);


                    r.setFillColor(canvas.getDefaultShape().getFillColor());
                    r.setStrokeColor(canvas.getDefaultShape().getStrokeColor());

                    r.setShapeType("rectangle");

                    canvas.getShapes().add(r);

                }

                if(canvas.getDefaultShape().getShapeType().equalsIgnoreCase("line")) {
                    LineSegment l = new LineSegment();
                    Point p = new Point();
                    p.setX(Integer.parseInt(line[1]));
                    p.setY(Integer.parseInt(line[2]));
                    l.setStart(p);
                    i++;
                    line = input.get(i).split("\\s+");

                    p = new Point();
                    p.setX(Integer.parseInt(line[1]));
                    p.setY(Integer.parseInt(line[2]));
                    l.setEnd(p);
                    l.setShapeType("line");

                    l.setFillColor(canvas.getDefaultShape().getFillColor());
                    l.setStrokeColor(canvas.getDefaultShape().getStrokeColor());
                    canvas.getShapes().add(l);

                }
            }
        }
    }



    private static void getOutput(Canvas canvas) {
        System.out.println("<svg width=\"" + canvas.getWidth() + "\" height=\"" + canvas.getHeight() +"\"");
        for(Shape shape : canvas.getShapes()) {
            if(shape. getShapeType().equalsIgnoreCase("circle")) {
                Circle c = (Circle) shape;
                Integer r = dist(c.getCentre(), c.getCirle());
                System.out.println("<circle cx=\"" + c.getCentre().getX() + "\" cy=\"" + c.getCentre().getY() +
                        "\" r=\"" + r +  "\" stroke=\"" + c.getStrokeColor() + "\" fill=\"" + c.getFillColor() + "\" />");
            }

            if(shape.getShapeType().equalsIgnoreCase("rectangle")) {
                Rectangle r = (Rectangle) shape;
                Integer width = r.getBottomRight().getX() - r.getTopLeft().getX();
                Integer height = r.getBottomRight().getY() - r.getTopLeft().getY();
                System.out.println("<rect x=\"" + r.getTopLeft().getX() + "\" y=\"" + r.getTopLeft().getY() +
                        "\" width=\"" + width + "\" height=\"" + height +"\" stroke=\"" + r.getStrokeColor() + "\" fill=\"" + r.getFillColor() + "\" />");
            }

            if(shape.getShapeType().equalsIgnoreCase("line")) {
                LineSegment l = (LineSegment) shape;
                System.out.println("<line x1=\"" + l.getStart().getX() + "\" y1=\"" + l.getStart().getY() +
                        " x2=\"" + l.getEnd().getX() + "\" y2=\"" + l.getEnd().getY()
                        + "\" stroke=\"" + l.getStrokeColor() + "\" fill=\"" + l.getFillColor() + "\" />");
            }
        }
        System.out.println("</svg>");
    }

    private static int dist(Point centre, Point cirle) {
        Integer x = centre.getX() - cirle.getX();
        Integer y = centre.getY() - cirle.getY();
        Double sqrt = Math.sqrt(x * x + y * y);
        return sqrt.intValue();
    }

    public static void main(String[] args) {
        Shape shape = new Shape();
        Canvas canvas = new Canvas(shape);
        List<String> input = getInput();
        processinput(input, canvas);
        getOutput(canvas);
    }
}


class Point {

    int x;
    int  y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class Shape{
    String fillColor;
    String strokeColor;
    String shapeType;

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

    public String getShapeType() {
        return shapeType;
    }

    public void setShapeType(String shapeType) {
        this.shapeType = shapeType;
    }
}

class LineSegment extends Shape {

    Point start;
    Point end;

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
}

class Rectangle extends Shape {

    Point topLeft;
    Point bottomRight;

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }
}

class Circle extends Shape {

    Point centre;
    Point cirle;

    public Point getCentre() {
        return centre;
    }

    public void setCentre(Point centre) {
        this.centre = centre;
    }

    public Point getCirle() {
        return cirle;
    }

    public void setCirle(Point cirle) {
        this.cirle = cirle;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "fillColor='" + fillColor + '\'' +
                ", strokeColor='" + strokeColor + '\'' +
                ", shapeType='" + shapeType + '\'' +
                ", centre=" + centre +
                ", cirle=" + cirle +
                '}';
    }
}