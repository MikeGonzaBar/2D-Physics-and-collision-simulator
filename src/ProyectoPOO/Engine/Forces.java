package ProyectoPOO.Engine;

import java.util.ArrayList;

import ProyectoPOO.Shapes2D.*;

public class Forces {
    private ArrayList<Shape2d> shapes;
    public double gravity;

    public Forces(ArrayList<Shape2d> shapes, double gravity){
        this.shapes = shapes;
        this.gravity = gravity;
    }

    public void applyGravityCircles(){
        for(int i = 0; i < shapes.size(); i++){
            Shape2d o = shapes.get(i);
            if(o instanceof Circle){
                Circle c1 = (Circle) o;
                c1.addAcceleration(new Vector2D(0,gravity));
            }
        }
    }

    public void sumForces(double passedTime){
        for(int i = 0; i < shapes.size(); i++){
            Shape2d o = shapes.get(i);
            if(o instanceof Circle){
                Circle c1 = (Circle) o;
                Vector2D acceleration = c1.sumAccelerations();
                c1.getVelocity().setX(c1.getVx()+acceleration.getX()*passedTime);
                c1.getVelocity().setY(c1.getVy()+acceleration.getY()*passedTime);
                c1.setVelocity(c1.getVx()*(1.0 - (passedTime * 0.2)),c1.getVy()*(1.0 - (passedTime * 0.2)));
            }
        }
    }
}