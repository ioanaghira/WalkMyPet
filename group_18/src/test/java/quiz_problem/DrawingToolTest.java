package quiz_problem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DrawingToolTest {

    private DrawingTool drawingTool;

    @BeforeEach
    void setup() {
        drawingTool = new DrawingTool();
    }

    @Test
    void should_AddNewSquareInDrawingTool_isValid() {

        // GIVEN
        Square square = new Square(5);

        // WHEN
        drawingTool.addInSet(square);

        // THEN
        Set<Shape> shapeSet = drawingTool.getShapes();

        assertTrue(shapeSet.contains(square));
        assertEquals(1, shapeSet.size());
    }

    @Test
    void triesTo_AddTwoNewSquaresInDrawingTool_FirstValid() {

        // GIVEN
        Square square = new Square(5);
        Square square2 = new Square(5);

        // WHEN
        drawingTool.addInSet(square);
        drawingTool.addInSet(square2);

        // THEN
        Set<Shape> shapeSet = drawingTool.getShapes();

        assertTrue(shapeSet.contains(square));
        assertEquals(1, shapeSet.size());
    }

    @Test
    void calculateSurfaceTotalSum() {
    }

    @Test
    void calculatePerimeterTotalSum() {
    }
}