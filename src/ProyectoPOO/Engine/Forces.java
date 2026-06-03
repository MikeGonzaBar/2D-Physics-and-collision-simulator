package ProyectoPOO.Engine;

import java.util.List;

import ProyectoPOO.Shapes2D.*;

/**
 * Applies frame forces, such as gravity, to the active simulation shapes.
 */
public class Forces {
    private static final double LINEAR_DAMPING = 0.2;

    private final List<Shape2d> shapes;
    public double gravity;

    public Forces(List<Shape2d> shapes, double gravity) {
        this.shapes = shapes;
        this.gravity = gravity;
    }

    public void applyGravity() {
        synchronized (shapes) {
            for (Shape2d shape : shapes) {
                if (shape.isAffectedByGravity()) {
                    shape.addAcceleration(new Vector2D(0, gravity));
                }
            }
        }
    }

    /**
     * @deprecated Use {@link #applyGravity()}.
     */
    @Deprecated
    public void applyGravityCircles() {
        applyGravity();
    }

    public void sumForces(double passedTime) {
        synchronized (shapes) {
            for (Shape2d shape : shapes) {
                if (shape.isStatic()) {
                    shape.sumAccelerations();
                    continue;
                }

                Vector2D acceleration = shape.sumAccelerations();
                shape.setVelocity(
                    (shape.getVx() + acceleration.getX() * passedTime)
                        * (1.0 - passedTime * LINEAR_DAMPING),
                    (shape.getVy() + acceleration.getY() * passedTime)
                        * (1.0 - passedTime * LINEAR_DAMPING)
                );
            }
        }
    }
}
