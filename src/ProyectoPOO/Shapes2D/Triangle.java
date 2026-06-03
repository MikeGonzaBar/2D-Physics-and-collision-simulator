package ProyectoPOO.Shapes2D;

/**
 * Isosceles triangle represented by its center, base width, and height.
 */
public class Triangle extends Polygon {
    private double height;
    private double width;
    
    public Triangle(double x, double y, double width, double height) {
        super(
            new double[] {x - width / 2, x + width / 2, x},
            new double[] {y + height / 2, y + height / 2, y - height / 2}
        );
        this.height = requirePositive("height", height);
        this.width = requirePositive("width", width);
        center.setX(x);
        center.setY(y);
        updateVertices();
    }

    public double getHeight() {
        return height;
    }

    /**
     * @deprecated Use {@link #getHeight()}.
     */
    @Deprecated
    public double getHeigth() {
        return getHeight();
    }

    public double getWidth() {
        return width;
    }

    @Override
    public void setCenter(double x, double y) {
        this.center.setX(x);
        this.center.setY(y);
        updateVertices();
    }

    @Override
    public void updateVertices() {
        vertices[0].setX(center.getX() - width / 2);
        vertices[0].setY(center.getY() + height / 2);
        vertices[1].setX(center.getX() + width / 2);
        vertices[1].setY(center.getY() + height / 2);
        vertices[2].setX(center.getX());
        vertices[2].setY(center.getY() - height / 2);
    }

    @Override
    public double getPerimeter() {
        double perimeter = width;
        perimeter += 2.0 * Math.sqrt((width / 2.0) * (width / 2.0) + height * height);
        return perimeter;
    }

    @Override
    public double getArea() {
        return width * height / 2.0;
    }
}
