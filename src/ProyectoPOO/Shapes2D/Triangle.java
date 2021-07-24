package ProyectoPOO.Shapes2D;

public class Triangle extends Polygon{
    private double height;
    private double width;
    
    public Triangle(double x, double y, double width, double height){
        super(new double[] {x-width/2,x+width/2,x}, new double[] {y+height/2,y+height/2,y-height/2});
        this.height = height;
        this.width = width;
        center.setX(x);
        center.setY(y);
    }

    public double getHeigth(){
        return height;
    }

    public double getWidth(){
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
        vertices[0].setX(center.getX()-width/2); // Bottom left
        vertices[0].setY(center.getY()+height/2);
        vertices[1].setX(center.getX()+width/2); // Bottom right
        vertices[1].setY(center.getY()+height/2);
        vertices[2].setX(center.getX()); // Top
        vertices[2].setY(center.getY()-height/2);
    }

    public double getPerimeter() {
        double perimeter = width;
        perimeter += 2.0*(Math.sqrt((width/2.0)*(width/2.0) + height*height));
        return perimeter;
    }

    public double getArea() {
        return width*height/2.0;
    }
}