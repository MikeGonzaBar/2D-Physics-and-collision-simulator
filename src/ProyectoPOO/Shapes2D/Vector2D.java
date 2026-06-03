package ProyectoPOO.Shapes2D;

/**
 * Mutable two-dimensional vector used for positions, velocities, and
 * accelerations inside the simulator.
 */
public class Vector2D {

    private double x;
    private double y;
    private double initialAngle;

    public Vector2D() {
        x = 0.0;
        y = 0.0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double angle() {
        return Math.atan2(y, x);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")"; 
    }

    /**
     * Rotates this vector into a temporary coordinate space.
     *
     * <p>Call {@link #restoreCoordinates()} to return to the original space.
     */
    public void rotateCoordinates(double tiltAngle) {
        this.initialAngle += tiltAngle;
        double angle = angle();
        double mag = magnitude();
        angle -= tiltAngle;
        x = mag * Math.cos(angle);
        y = mag * Math.sin(angle);
    }

    public void restoreCoordinates() {
        double angle = angle();
        double mag = magnitude();
        angle += initialAngle;
        x = mag * Math.cos(angle);
        y = mag * Math.sin(angle);
        initialAngle = 0.0;
    }
    
    @Override
    public Vector2D clone() {
        return new Vector2D(x, y);
    }
}
