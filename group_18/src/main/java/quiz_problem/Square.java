package quiz_problem;

public class Square extends Shape {

    private int l;

    public Square(int l) {

        this.l = l;
    }

    @Override
    public double calculateSurface() {

        return l * l;
    }

    @Override
    public double calculatePerimeter() {

        return 2 * (l + l);
    }

    @Override
    public boolean equals(Object obj) {

        Shape shape = (Shape) obj;
        final boolean isSameClass = this.getClass() == shape.getClass();

        if (isSameClass) {
            System.out.println("A shape of this type already exists (verified in equals)");
        }
        
        return isSameClass;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
