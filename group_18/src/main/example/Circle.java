package example;

public class Circle extends Shape {

    private int radius;

    public Circle(int radius) {
        this.radius = radius;
    }

    @Override
    public double calculateSurface() {

        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {

        return 2 * Math.PI * radius;
    }
}
