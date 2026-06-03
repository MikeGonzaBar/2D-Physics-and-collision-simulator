package ProyectoPOO.Engine;

import ProyectoPOO.Shapes2D.*;

/**
 * Utility for integrating shape position from velocity.
 */
public final class ShapeMove {

    private ShapeMove() {
    }

    public static void moveShape(Shape2d shape, double passedTime) {
        if (shape.isStatic()) {
            return;
        }

        shape.setCenter(
            shape.getX() + shape.getVx() * passedTime,
            shape.getY() + shape.getVy() * passedTime
        );
    }
}
