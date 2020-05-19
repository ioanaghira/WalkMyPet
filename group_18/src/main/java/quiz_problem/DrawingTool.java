package quiz_problem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DrawingTool {

    private Set<Shape> shapes = new HashSet<>();
    private List<Shape> listShapes = new ArrayList<>();

    // When adding to a Set, equals will be verified on the objects that you want to introduce
    // You can check this using: debug
    // Both validations have been added (with messages), any one of them is a good option
    public void addInSet(Shape newShape) {

        if (!shapes.add(newShape)) {
            System.out.println("A shape of this type already exists (verified in add) for Set");
        }
    }

    // When adding to a List, equals will NOT be verified on the objects that you want to introduce
    // Only when calling "contains" will the equals method be checked
    public void addInList(Shape newShape) {

        if (listShapes.contains(newShape)) {
            System.out.println("A shape of this type already exists (verified in add) for List");
        } else {
            listShapes.add(newShape);
        }
    }

    public double calculateSurfaceTotalSum() {

        double sum = 0;

        for (Shape shape : shapes) {

            sum += shape.calculateSurface();
        }

        return sum;
    }

    public double calculatePerimeterTotalSum() {

        double sum = 0;

        for (Shape shape : shapes) {

            sum += shape.calculatePerimeter();
        }

        return sum;
    }

    public Set<Shape> getShapes() {

        return shapes;
    }
}
