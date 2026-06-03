package ProyectoPOO.Engine;

import java.util.List;

import ProyectoPOO.Shapes2D.*;

/**
 * Detects and resolves simple wall and object collisions for the active shapes.
 */
public class Collisions {
    private static final double EPSILON = 0.000001;

    private final List<Shape2d> shapes;
    private final Window window;
    public static double BOUNCE = .9;

    public Collisions(List<Shape2d> shapes, Window window) {
        this.shapes = shapes;
        this.window = window;
    }

    public void checkWallCollisions() {
        synchronized (shapes) {
            for (Shape2d o1 : shapes) {
                if (o1 instanceof Circle) {
                    resolveCircleWallCollision((Circle) o1, window.getWidth(), window.getHeight());
                } else if (o1 instanceof Rectangle) {
                    resolveRectangleWallCollision((Rectangle) o1, window.getWidth(), window.getHeight());
                }
            }
        }
    }

    public static void resolveCircleWallCollision(Circle circle, double width, double height) {
        if (circle.getX() - circle.getRadius() <= 0) {
            circle.setCenter(circle.getRadius(), circle.getY());
            if (circle.getVx() < 0) {
                circle.setVelocity(-circle.getVx() * BOUNCE, circle.getVy());
            }
        } else if (circle.getX() + circle.getRadius() >= width) {
            circle.setCenter(width - circle.getRadius(), circle.getY());
            if (circle.getVx() > 0) {
                circle.setVelocity(-circle.getVx() * BOUNCE, circle.getVy());
            }
        }

        if (circle.getY() + circle.getRadius() >= height) {
            circle.setCenter(circle.getX(), height - circle.getRadius());
            if (circle.getVy() > 0) {
                circle.setVelocity(circle.getVx(), -circle.getVy() * BOUNCE);
            }
        } else if (circle.getY() - circle.getRadius() <= 0) {
            circle.setCenter(circle.getX(), circle.getRadius());
            if (circle.getVy() < 0) {
                circle.setVelocity(circle.getVx(), -circle.getVy() * BOUNCE);
            }
        }
    }

    public static void resolveRectangleWallCollision(Rectangle rectangle, double width, double height) {
        if (rectangle.getX() - rectangle.getWidth() / 2 <= 0) {
            rectangle.setCenter(rectangle.getWidth() / 2, rectangle.getY());
            if (rectangle.getVx() < 0) {
                rectangle.setVelocity(-rectangle.getVx() * BOUNCE, rectangle.getVy());
            }
        } else if (rectangle.getX() + rectangle.getWidth() / 2 >= width) {
            rectangle.setCenter(width - rectangle.getWidth() / 2, rectangle.getY());
            if (rectangle.getVx() > 0) {
                rectangle.setVelocity(-rectangle.getVx() * BOUNCE, rectangle.getVy());
            }
        }

        if (rectangle.getY() + rectangle.getHeight() / 2 >= height) {
            rectangle.setCenter(rectangle.getX(), height - rectangle.getHeight() / 2);
            if (rectangle.getVy() > 0) {
                rectangle.setVelocity(rectangle.getVx(), -rectangle.getVy() * BOUNCE);
            }
        } else if (rectangle.getY() - rectangle.getHeight() / 2 <= 0) {
            rectangle.setCenter(rectangle.getX(), rectangle.getHeight() / 2);
            if (rectangle.getVy() < 0) {
                rectangle.setVelocity(rectangle.getVx(), -rectangle.getVy() * BOUNCE);
            }
        }
    }

    public void checkCollisionsObjects() {
        synchronized (shapes) {
            for (int i = 0; i < shapes.size(); i++) {
                Shape2d o1 = shapes.get(i);
                for (int j = i + 1; j < shapes.size(); j++) {
                    Shape2d o2 = shapes.get(j);

                    if (o1 instanceof Circle && o2 instanceof Circle) {
                        Circle c1 = (Circle) o1;
                        Circle c2 = (Circle) o2;
                        Vector2D subs = new Vector2D(
                            c1.getCenter().getX() - c2.getCenter().getX(),
                            c1.getCenter().getY() - c2.getCenter().getY()
                        );
                        double distance = Math.sqrt(subs.getX() * subs.getX() + subs.getY() * subs.getY());
                        if (distance <= c1.getRadius() + c2.getRadius()) {
                            attendCircleCollision(c1, c2, distance);
                        }
                    } else if ((o1 instanceof Circle && o2 instanceof Triangle)
                            || (o1 instanceof Triangle && o2 instanceof Circle)) {
                        checkCircleTriangleCollision(o1, o2);
                    } else if ((o1 instanceof Circle && o2 instanceof Rectangle)
                            || (o1 instanceof Rectangle && o2 instanceof Circle)) {
                        checkCircleRectangleCollision(o1, o2);
                    } else if (o1 instanceof Rectangle && o2 instanceof Rectangle) {
                        checkRectangleRectangleCollision((Rectangle) o1, (Rectangle) o2);
                    }
                }
            }
        }
    }

    private void checkCircleTriangleCollision(Shape2d o1, Shape2d o2) {
        Circle c1 = o1 instanceof Circle ? (Circle) o1 : (Circle) o2;
        Triangle t1 = o1 instanceof Triangle ? (Triangle) o1 : (Triangle) o2;

        for (int k = 0; k < t1.getVertices().length; k++) {
            Vector2D p1 = t1.getVertices()[k];
            Vector2D p2 = k == t1.getVertices().length - 1
                ? t1.getVertices()[0]
                : t1.getVertices()[k + 1];

            boolean p1InCircle = pointInsideCircle(p1, c1);
            boolean p2InCircle = pointInsideCircle(p2, c1);
            if (p1InCircle || p2InCircle) {
                c1.setVelocity(-c1.getVx(), -c1.getVy());
                continue;
            }

            double distX = p1.getX() - p2.getX();
            double distY = p1.getY() - p2.getY();
            double len = Math.sqrt(distX * distX + distY * distY);

            double dot = (
                (c1.getX() - p1.getX()) * (p2.getX() - p1.getX())
                    + (c1.getY() - p1.getY()) * (p2.getY() - p1.getY())
            ) / (len * len);

            double closestX = p1.getX() + dot * (p2.getX() - p1.getX());
            double closestY = p1.getY() + dot * (p2.getY() - p1.getY());

            if (!pointInLine(p1, p2, new Vector2D(closestX, closestY))) {
                continue;
            }

            distX = closestX - c1.getX();
            distY = closestY - c1.getY();
            double distance = Math.sqrt(distX * distX + distY * distY);
            if (distance <= c1.getRadius()) {
                if (p1 == t1.getVertices()[2] && p2 == t1.getVertices()[0]) {
                    c1.setCenter(c1.getX() - 5, c1.getY() - 5);
                    c1.setVelocity(-c1.getVx(), -c1.getVy());
                } else if (p1 == t1.getVertices()[1] && p2 == t1.getVertices()[2]) {
                    c1.setCenter(c1.getX() + 5, c1.getY() - 5);
                    c1.setVelocity(-c1.getVx(), -c1.getVy());
                } else {
                    c1.setCenter(c1.getX(), c1.getY() + 5);
                    c1.setVelocity(c1.getVx(), -c1.getVy());
                }
            }
        }
    }

    private void checkCircleRectangleCollision(Shape2d o1, Shape2d o2) {
        Circle c1 = o1 instanceof Circle ? (Circle) o1 : (Circle) o2;
        Rectangle r1 = o1 instanceof Rectangle ? (Rectangle) o1 : (Rectangle) o2;

        resolveCircleRectangleCollision(c1, r1);
    }

    public static boolean resolveCircleRectangleCollision(Circle circle, Rectangle rectangle) {
        double minX = rectangle.getX() - rectangle.getWidth() / 2.0;
        double maxX = rectangle.getX() + rectangle.getWidth() / 2.0;
        double minY = rectangle.getY() - rectangle.getHeight() / 2.0;
        double maxY = rectangle.getY() + rectangle.getHeight() / 2.0;

        double closestX = clamp(circle.getX(), minX, maxX);
        double closestY = clamp(circle.getY(), minY, maxY);
        double deltaX = circle.getX() - closestX;
        double deltaY = circle.getY() - closestY;
        double distanceSquared = deltaX * deltaX + deltaY * deltaY;
        double radius = circle.getRadius();

        if (distanceSquared > radius * radius) {
            return false;
        }

        double normalX;
        double normalY;
        double penetration;

        if (distanceSquared > EPSILON) {
            double distance = Math.sqrt(distanceSquared);
            normalX = deltaX / distance;
            normalY = deltaY / distance;
            penetration = radius - distance;
        } else {
            double left = circle.getX() - minX;
            double right = maxX - circle.getX();
            double top = circle.getY() - minY;
            double bottom = maxY - circle.getY();
            double nearestSide = Math.min(Math.min(left, right), Math.min(top, bottom));

            if (nearestSide == left) {
                normalX = -1;
                normalY = 0;
                penetration = radius + left;
            } else if (nearestSide == right) {
                normalX = 1;
                normalY = 0;
                penetration = radius + right;
            } else if (nearestSide == top) {
                normalX = 0;
                normalY = -1;
                penetration = radius + top;
            } else {
                normalX = 0;
                normalY = 1;
                penetration = radius + bottom;
            }
        }

        double totalInverseMass = circle.getInverseMass() + rectangle.getInverseMass();
        if (totalInverseMass <= 0) {
            return true;
        }

        double circleCorrection = penetration * circle.getInverseMass() / totalInverseMass;
        double rectangleCorrection = penetration * rectangle.getInverseMass() / totalInverseMass;

        circle.setCenter(
            circle.getX() + normalX * circleCorrection,
            circle.getY() + normalY * circleCorrection
        );
        rectangle.setCenter(
            rectangle.getX() - normalX * rectangleCorrection,
            rectangle.getY() - normalY * rectangleCorrection
        );

        double relativeVelocityX = circle.getVx() - rectangle.getVx();
        double relativeVelocityY = circle.getVy() - rectangle.getVy();
        double velocityAlongNormal = relativeVelocityX * normalX + relativeVelocityY * normalY;
        if (velocityAlongNormal < 0) {
            double impulse = -(1.0 + BOUNCE) * velocityAlongNormal / totalInverseMass;
            double impulseX = impulse * normalX;
            double impulseY = impulse * normalY;
            circle.setVelocity(
                circle.getVx() + impulseX * circle.getInverseMass(),
                circle.getVy() + impulseY * circle.getInverseMass()
            );
            rectangle.setVelocity(
                rectangle.getVx() - impulseX * rectangle.getInverseMass(),
                rectangle.getVy() - impulseY * rectangle.getInverseMass()
            );
        }

        return true;
    }

    private void checkRectangleRectangleCollision(Rectangle r1, Rectangle r2) {
        resolveRectangleRectangleCollision(r1, r2);
    }

    public static boolean resolveRectangleRectangleCollision(Rectangle r1, Rectangle r2) {
        double dx = r2.getX() - r1.getX();
        double dy = r2.getY() - r1.getY();
        double overlapX = (r1.getWidth() + r2.getWidth()) / 2.0 - Math.abs(dx);
        double overlapY = (r1.getHeight() + r2.getHeight()) / 2.0 - Math.abs(dy);

        if (overlapX <= 0 || overlapY <= 0) {
            return false;
        }

        double normalX = 0;
        double normalY = 0;
        double penetration;

        if (overlapX < overlapY) {
            normalX = dx < 0 ? -1 : 1;
            penetration = overlapX;
        } else {
            normalY = dy < 0 ? -1 : 1;
            penetration = overlapY;
        }

        double totalInverseMass = r1.getInverseMass() + r2.getInverseMass();
        if (totalInverseMass <= 0) {
            return true;
        }

        double correction1 = penetration * r1.getInverseMass() / totalInverseMass;
        double correction2 = penetration * r2.getInverseMass() / totalInverseMass;
        r1.setCenter(r1.getX() - normalX * correction1, r1.getY() - normalY * correction1);
        r2.setCenter(r2.getX() + normalX * correction2, r2.getY() + normalY * correction2);

        double relativeVelocityX = r2.getVx() - r1.getVx();
        double relativeVelocityY = r2.getVy() - r1.getVy();
        double velocityAlongNormal = relativeVelocityX * normalX + relativeVelocityY * normalY;

        if (velocityAlongNormal < 0) {
            double impulse = -(1.0 + BOUNCE) * velocityAlongNormal / totalInverseMass;
            double impulseX = impulse * normalX;
            double impulseY = impulse * normalY;

            r1.setVelocity(
                r1.getVx() - impulseX * r1.getInverseMass(),
                r1.getVy() - impulseY * r1.getInverseMass()
            );
            r2.setVelocity(
                r2.getVx() + impulseX * r2.getInverseMass(),
                r2.getVy() + impulseY * r2.getInverseMass()
            );
        }

        return true;
    }

    public static void attendCircleCollision(Circle c1, Circle c2, double distance) {
        double minDist = c1.getRadius() + c2.getRadius();
        if (distance > minDist) {
            return;
        }

        double normalX;
        double normalY;
        if (distance > EPSILON) {
            normalX = (c1.getX() - c2.getX()) / distance;
            normalY = (c1.getY() - c2.getY()) / distance;
        } else {
            normalX = 1.0;
            normalY = 0.0;
            distance = 0.0;
        }

        double totalInverseMass = c1.getInverseMass() + c2.getInverseMass();
        if (totalInverseMass <= 0) {
            return;
        }

        double overlap = minDist - distance;
        c1.setCenter(
            c1.getX() + normalX * overlap * c1.getInverseMass() / totalInverseMass,
            c1.getY() + normalY * overlap * c1.getInverseMass() / totalInverseMass
        );
        c2.setCenter(
            c2.getX() - normalX * overlap * c2.getInverseMass() / totalInverseMass,
            c2.getY() - normalY * overlap * c2.getInverseMass() / totalInverseMass
        );

        double relativeVelocityX = c1.getVx() - c2.getVx();
        double relativeVelocityY = c1.getVy() - c2.getVy();
        double velocityAlongNormal = relativeVelocityX * normalX + relativeVelocityY * normalY;
        if (velocityAlongNormal < 0) {
            double impulse = -(1.0 + BOUNCE) * velocityAlongNormal / totalInverseMass;
            double impulseX = impulse * normalX;
            double impulseY = impulse * normalY;
            c1.setVelocity(
                c1.getVx() + impulseX * c1.getInverseMass(),
                c1.getVy() + impulseY * c1.getInverseMass()
            );
            c2.setVelocity(
                c2.getVx() - impulseX * c2.getInverseMass(),
                c2.getVy() - impulseY * c2.getInverseMass()
            );
        }
    }

    /**
     * @deprecated Use {@link #attendCircleCollision(Circle, Circle, double)}.
     */
    @Deprecated
    public static void AttendCircleCollision(Circle c1, Circle c2, double distance) {
        attendCircleCollision(c1, c2, distance);
    }

    public boolean pointInsideCircle(Vector2D p1, Circle c1) {
        double distX = p1.getX() - c1.getX();
        double distY = p1.getY() - c1.getY();
        double distance = Math.sqrt(distX * distX + distY * distY);
        return distance <= c1.getRadius();
    }

    public boolean pointInLine(Vector2D p1, Vector2D p2, Vector2D px) {
        double d1x = px.getX() - p1.getX();
        double d1y = px.getY() - p1.getY();
        double d1 = Math.sqrt(d1x * d1x + d1y * d1y);

        double d2x = px.getX() - p2.getX();
        double d2y = px.getY() - p2.getY();
        double d2 = Math.sqrt(d2x * d2x + d2y * d2y);
      
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        double lineLen  = Math.sqrt(dx * dx + dy * dy);
        double buffer = 0.1;
        return d1 + d2 >= lineLen - buffer && d1 + d2 <= lineLen + buffer;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
