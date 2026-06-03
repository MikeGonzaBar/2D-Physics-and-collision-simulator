package ProyectoPOO.Shapes2D;

/**
 * Axis-aligned rectangle represented by its center, width, and height.
 */
public class Rectangle extends Polygon {
    private double height;
    private double width;
    
    public Rectangle(double x, double y, double width, double height) {
        super(
            new double[] {x - width / 2, x + width / 2, x + width / 2, x - width / 2},
            new double[] {y + height / 2, y + height / 2, y - height / 2, y - height / 2}
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
    public void updateVertices() {
        vertices[0].setX(center.getX() - width / 2);
        vertices[0].setY(center.getY() + height / 2);
        vertices[1].setX(center.getX() + width / 2);
        vertices[1].setY(center.getY() + height / 2);
        vertices[2].setX(center.getX() + width / 2);
        vertices[2].setY(center.getY() - height / 2);
        vertices[3].setX(center.getX() - width / 2);
        vertices[3].setY(center.getY() - height / 2);
    }

    @Override
    public void setCenter(double x, double y) {
        this.center.setX(x);
        this.center.setY(y);
        updateVertices();
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public double getArea() {
        return width * height;
    }
}
