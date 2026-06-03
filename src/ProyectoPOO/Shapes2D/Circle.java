package ProyectoPOO.Shapes2D;

/**
 * Circle shape represented by a center point and radius.
 */
public class Circle extends Shape2d {
    private double radius;

    public Circle(double x, double y) {
        this.setCenter(x, y);
        this.radius = 1;
    }
    
    public Circle(double x, double y, double radius) {
        this.setCenter(x, y);
        this.radius = requirePositive("radius", radius);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = requirePositive("radius", radius);
    }

    @Override
    public double getPerimeter() {
        return 2 * this.getRadius() * Math.PI;
    }

    @Override
    public double getArea() {
        return Math.PI * this.getRadius() * this.getRadius();
    }

    @Override
    public void setCenter(double x, double y) {
        this.center.setX(x);
        this.center.setY(y);
    }
}
