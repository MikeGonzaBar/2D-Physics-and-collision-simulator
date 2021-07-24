package ProyectoPOO.Engine;

import ProyectoPOO.Shapes2D.*;


public interface ShapeMove {
    
    public static void moveShape(Shape2d shape, double passedTime){
        shape.setCenter(shape.getX() + shape.getVx()*passedTime, shape.getY() + shape.getVy()*passedTime);
    }

}