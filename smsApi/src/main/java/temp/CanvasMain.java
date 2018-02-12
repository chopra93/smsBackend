package temp;

import sun.security.provider.SHA;

import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * Created by Chopra on 12/02/18.
 */
public class CanvasMain {
    private Canvas canvas;


    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    int distanceBetweenTwoPoint(Point p1, Point p2){
        Integer x = abs(p1.getX() - p2.getX());
        Integer y = abs(p1.getY() - p2.getY());

        Double sqrt = Math.sqrt(x * x + y * y);
        return sqrt.intValue();
    }

    private void fetchInput(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter width and height");
        String str = sc.nextLine();
        String[] arr = str.split("\\s+");

        // setting width and height
        // 500 500
        canvas.setWidth(Integer.parseInt(arr[0]));
        canvas.setHeight(Integer.parseInt(arr[1]));

//        System.out.println("Enter fill color ");
//        str = sc.nextLine();
//        arr = str.split("\\s+");

        // setting fill colour and stroke colour
        // setFillColor #CADA56
//        canvas.getDefaultShape().setFillColour(arr[1]);
//
//        System.out.println("Enter stroke color ");
//        str = sc.nextLine();
//        arr = str.split("\\s+");

        // setting stroke colour
        // setStrokeColor #CADA56
//        canvas.getDefaultShape().setStrokeColour(arr[1]);

        System.out.println("Enter string ");
        str = sc.nextLine();

        String earlier = "";
        while (!str.equalsIgnoreCase("-1")){
            arr = str.split("\\s+");
            int len = arr.length;

            if (len == 1){
                if (arr[0].equalsIgnoreCase("Circle")){

                    Circle circle = new Circle();

                    System.out.println("Enter point of circle ");
                    str = sc.nextLine();
                    arr = str.split("\\s+");

                    Point p1 = new Point();
                    p1.setX(Integer.parseInt(arr[1]));
                    p1.setY(Integer.parseInt(arr[2]));

                    System.out.println("Enter point on circle circum ");
                    str = sc.nextLine();
                    arr = str.split("\\s+");

                    Point p2 = new Point();
                    p2.setX(Integer.parseInt(arr[1]));
                    p2.setY(Integer.parseInt(arr[2]));

                    circle.setCenter(p1);
                    circle.setCircle(p2);

                    canvas.setShape(circle);

                    processShape(canvas,"Circle");

                    earlier = "Circle";

                }
                else
                if(arr[0].equalsIgnoreCase("Rectangle")){

                    Rectangle rectangle = new Rectangle();

                    System.out.println("Enter top left point ");

                    str = sc.nextLine();
                    arr = str.split("\\s+");

                    Point p1 = new Point();
                    p1.setX(Integer.parseInt(arr[1]));
                    p1.setY(Integer.parseInt(arr[2]));


                    System.out.println("Enter bottom right point ");

                    str = sc.nextLine();
                    arr = str.split("\\s+");

                    Point p2 = new Point();
                    p2.setX(Integer.parseInt(arr[1]));
                    p2.setY(Integer.parseInt(arr[2]));

                    rectangle.setTopLeft(p1);
                    rectangle.setBottomRight(p2);

                    canvas.setShape(rectangle);

                    processShape(canvas,"Rectangle");


                    earlier = "Rectangle";
                }
                else
                if(arr[0].equalsIgnoreCase("lineSegment")){
                    LineSegment lineSegment = new LineSegment();

                    System.out.println("Enter starting point ");

                    str = sc.nextLine();
                    arr = str.split("\\s+");

                    Point p1 = new Point();
                    p1.setX(Integer.parseInt(arr[1]));
                    p1.setY(Integer.parseInt(arr[2]));


                    System.out.println("Enter end point ");

                    str = sc.nextLine();
                    arr = str.split("\\s+");

                    Point p2 = new Point();
                    p2.setX(Integer.parseInt(arr[1]));
                    p2.setY(Integer.parseInt(arr[2]));

                    lineSegment.setStart(p1);
                    lineSegment.setEnd(p2);

                    canvas.setShape(lineSegment);

                    processShape(canvas,"Linesegment");

                    earlier = "LineSegment";
                }
            }
            else
            if(len == 3){
                if (earlier.equalsIgnoreCase("Circle")){
                    Circle circle = new Circle();

                    arr = str.split("\\s+");

                    Point p1 = new Point();
                    p1.setX(Integer.parseInt(arr[1]));
                    p1.setY(Integer.parseInt(arr[2]));

                    System.out.println("Enter point on circle circum ");
                    str = sc.nextLine();
                    arr = str.split("\\s+");

                    Point p2 = new Point();
                    p2.setX(Integer.parseInt(arr[1]));
                    p2.setY(Integer.parseInt(arr[2]));

                    circle.setCenter(p1);
                    circle.setCircle(p2);

                    canvas.setShape(circle);

                    processShape(canvas,"Circle");

                }
                else
                if (earlier.equalsIgnoreCase("REctangle")){
                    Rectangle rectangle = new Rectangle();

                    arr = str.split("\\s+");

                    Point p1 = new Point();
                    p1.setX(Integer.parseInt(arr[1]));
                    p1.setY(Integer.parseInt(arr[2]));


                    System.out.println("Enter bottom right point ");

                    str = sc.nextLine();
                    arr = str.split("\\s+");

                    Point p2 = new Point();
                    p2.setX(Integer.parseInt(arr[1]));
                    p2.setY(Integer.parseInt(arr[2]));

                    rectangle.setTopLeft(p1);
                    rectangle.setBottomRight(p2);

                    canvas.setShape(rectangle);

                    processShape(canvas,"Rectangle");
                }
                else
                if (earlier.equalsIgnoreCase("linesegment")){

                    LineSegment lineSegment = new LineSegment();

                    arr = str.split("\\s+");

                    Point p1 = new Point();
                    p1.setX(Integer.parseInt(arr[1]));
                    p1.setY(Integer.parseInt(arr[2]));


                    System.out.println("Enter end point ");

                    str = sc.nextLine();
                    arr = str.split("\\s+");

                    Point p2 = new Point();
                    p2.setX(Integer.parseInt(arr[1]));
                    p2.setY(Integer.parseInt(arr[2]));

                    lineSegment.setStart(p1);
                    lineSegment.setEnd(p2);

                    canvas.setShape(lineSegment);

                    processShape(canvas,"Linesegment");
                }
            }
            else {

                arr = str.split("\\s+");
                if (arr[0].equalsIgnoreCase("setFillColor")){
                    canvas.getDefaultShape().setFillColour(arr[1]);
                }
                else
                if (arr[0].equalsIgnoreCase("setStrokeColor")){
                    canvas.getDefaultShape().setStrokeColour(arr[1]);
                }
            }
            System.out.println("Enter string ");
            str = sc.nextLine();

        }
    }


    void processShape(Canvas canvas, String shapeString){
        if (shapeString.equalsIgnoreCase("Circle")){
            Circle c = (Circle) canvas.getShape();
            int r = distanceBetweenTwoPoint(((Circle) canvas.getShape()).getCenter(),((Circle) canvas.getShape()).getCircle());
            System.out.println("<circle cx=\"" + c.getCenter().getX() + "\" cy=\"" + c.getCenter().getY() +
                    "\" r=\"" + r +  "\" stroke=\"" + canvas.getDefaultShape().getStrokeColour() + "\" fill=\"" + canvas.getDefaultShape().getFillColour() + "\" />");
        }
        else
        if (shapeString.equalsIgnoreCase("Rectangle")){
            Rectangle r = (Rectangle) canvas.getShape();
            Integer width = r.getBottomRight().getX() - r.getTopLeft().getX();
            Integer height = r.getBottomRight().getY() - r.getTopLeft().getY();
            System.out.println("<rect x=\"" + r.getTopLeft().getX() + "\" y=\"" + r.getTopLeft().getY() +
                    "\" width=\"" + width + "\" height=\"" + height +"\" stroke=\"" + canvas.getDefaultShape().getStrokeColour() + "\" fill=\"" + canvas.getDefaultShape().getFillColour() + "\" />");
        }
        else
        if (shapeString.equalsIgnoreCase("Linesegment")){
            LineSegment l = (LineSegment) canvas.getShape();
            System.out.println("<line x1=\"" + l.getStart().getX() + "\" y1=\"" + l.getStart().getY() +
                    " x2=\"" + l.getEnd().getX() + "\" y2=\"" + l.getEnd().getY()
                    + "\" stroke=\"" + canvas.getDefaultShape().getStrokeColour() + "\" fill=\"" + canvas.getDefaultShape().getFillColour() + "\" />");
        }
    }


    public static void main(String[] args) {
        CanvasMain canvasMain = new CanvasMain();

        Canvas canvas = new Canvas();
        canvas.setDefaultShape(new Shape());

        canvasMain.setCanvas(canvas);

        canvasMain.fetchInput();

    }
}
