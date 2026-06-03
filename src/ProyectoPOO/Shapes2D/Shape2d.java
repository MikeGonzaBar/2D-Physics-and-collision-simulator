package ProyectoPOO.Shapes2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Base type for every simulated two-dimensional shape.
 *
 * <p>Shapes keep their own position, velocity, and one-frame acceleration queue.
 * The simulation engine consumes the acceleration queue on each fixed physics
 * step, then clears it.
 */
public abstract class Shape2d {
    protected Vector2D center;
    protected Vector2D velocity;
    private final List<Vector2D> accelerations;
    private boolean staticBody;
    private boolean affectedByGravity;
    private double mass;

    public Shape2d() {
        this.center = new Vector2D();
        this.velocity = new Vector2D();
        this.accelerations = new ArrayList<>();
        this.affectedByGravity = true;
        this.mass = 1.0;
    }

    public abstract double getPerimeter();
    public abstract double getArea();
    public abstract void setCenter(double x, double y); 

    public double getX() {
        return center.getX();
    }

    public double getY() {
        return center.getY();
    }

    public double getVx() {
        return velocity.getX();
    }

    public double getVy() {
        return velocity.getY();
    }

    public Vector2D getCenter() {
        return center;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(double vx, double vy) {
        if (staticBody) {
            this.velocity.setX(0);
            this.velocity.setY(0);
            return;
        }
        this.velocity.setX(vx);
        this.velocity.setY(vy);
    }

    public void setVelocity(Vector2D velocity) {
        this.setVelocity(velocity.getX(), velocity.getY());
    }

    public void addAcceleration(Vector2D acceleration) {
        if (staticBody) {
            return;
        }
        accelerations.add(acceleration);
    }

    public Vector2D sumAccelerations() {
        Vector2D acceleration = new Vector2D(0, 0);
        for (Vector2D accel : accelerations) {
            acceleration.setX(accel.getX() + acceleration.getX());
            acceleration.setY(accel.getY() + acceleration.getY());
        }
        accelerations.clear();
        return acceleration;
    }

    public boolean isStatic() {
        return staticBody;
    }

    public void setStatic(boolean staticBody) {
        this.staticBody = staticBody;
        if (staticBody) {
            setVelocity(0, 0);
            accelerations.clear();
            affectedByGravity = false;
        }
    }

    public boolean isAffectedByGravity() {
        return affectedByGravity;
    }

    public void setAffectedByGravity(boolean affectedByGravity) {
        this.affectedByGravity = affectedByGravity;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = requirePositive("mass", mass);
    }

    public double getInverseMass() {
        if (staticBody) {
            return 0.0;
        }
        return 1.0 / mass;
    }

    protected static double requirePositive(String name, double value) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be positive");
        }
        return value;
    }
}
