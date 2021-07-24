package ProyectoPOO.Shapes2D;

import java.util.ArrayList;

public abstract class Shape2d {
    protected Vector2D center;
    protected Vector2D velocity;
    private ArrayList<Vector2D> accelerations;

    public Shape2d(){
        this.center = new Vector2D();
        this.velocity = new Vector2D();
        this.accelerations = new ArrayList<>();
    }

    public abstract double getPerimeter();
    public abstract double getArea();
    public abstract void setCenter(double x, double y); 

    public double getX(){
        return center.getX();
    }

    public double getY(){
        return center.getY();
    }

    public double getVx(){
        return velocity.getX();
    }

    public double getVy(){
        return velocity.getY();
    }

    public Vector2D getCenter() {
        return center;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(double vx, double vy) {
        this.velocity.setX(vx);
        this.velocity.setY(vy);
    }

    public void setVelocity(Vector2D velocity) {
        this.setVelocity(velocity.getX(),velocity.getY());
    }

    public void addAcceleration(Vector2D acceleration){
        accelerations.add(acceleration);
    }

    public Vector2D sumAccelerations(){
        Vector2D acceleration = new Vector2D(0,0);
        for(Vector2D accel : accelerations){
            acceleration.setX(accel.getX()+acceleration.getX());
            acceleration.setY(accel.getY()+acceleration.getY());
        }
        accelerations.clear();
        return acceleration;
    }

    
}