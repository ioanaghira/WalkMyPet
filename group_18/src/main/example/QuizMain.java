package example;

public class QuizMain {

    public static void main(String... args) {

        // what objects do we need ? declared

        DrawingTool drawingTool = new DrawingTool();

//        drawingTool.addInSet(new Circle(3));
//        drawingTool.addInSet(new Circle(4));
//        drawingTool.addInSet(new Circle(5));

        // Add in the SET
        drawingTool.addInSet(new Square(5));
        drawingTool.addInSet(new Square(6));
        drawingTool.addInSet(new Square(7));

        // Add in the LIST
        drawingTool.addInList(new Square(8));
        drawingTool.addInList(new Square(9));
        drawingTool.addInList(new Square(10));

        System.out.println(drawingTool.calculateSurfaceTotalSum());
        System.out.println(drawingTool.calculatePerimeterTotalSum());
    }
}
