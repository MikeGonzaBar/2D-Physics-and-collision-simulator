package ProyectoPOO;

import java.util.ArrayList;
import java.util.List;

import ProyectoPOO.Engine.Collisions;
import ProyectoPOO.Engine.Forces;
import ProyectoPOO.Engine.ShapeMove;
import ProyectoPOO.Shapes2D.Circle;
import ProyectoPOO.Shapes2D.Rectangle;
import ProyectoPOO.Shapes2D.Shape2d;
import ProyectoPOO.Shapes2D.Triangle;
import ProyectoPOO.Shapes2D.Vector2D;

public final class PhysicsSmokeTests {

    private PhysicsSmokeTests() {
    }

    public static void main(String[] args) {
        testCircleConstructorSetsCenter();
        testRectangleGeometry();
        testGravityAppliesToAllShapes();
        testShapeMovement();
        testCircleCollisionResponse();
        testCircleRectangleCollisionSeparatesEmbeddedCircle();
        testRectangleCornerWallCollisionDoesNotFallThroughFloor();
        testRectangleRectangleCollisionSeparatesStackedSquares();
        testRectangleRectangleCollisionSeparatesSideImpact();
        testStaticBodyIgnoresForcesAndMovement();
        testGravityCanBeDisabledForDynamicBody();
        testCircleBouncesOffStaticRectangleWithoutMovingPlatform();
        testExactTouchingRectanglesDoNotJitter();
        testSameCenterCirclesSeparateWithoutNaN();
        testInvalidShapeDimensionsFailFast();
        testMassChangesCollisionCorrectionShare();
        System.out.println("PhysicsSmokeTests passed");
    }

    private static void testCircleConstructorSetsCenter() {
        Circle circle = new Circle(320, 40, 20);

        assertClose(320, circle.getX(), "circle x");
        assertClose(40, circle.getY(), "circle y");
        assertClose(20, circle.getRadius(), "circle radius");
        assertClose(0, circle.getVx(), "circle vx");
        assertClose(0, circle.getVy(), "circle vy");
    }

    private static void testRectangleGeometry() {
        Rectangle rectangle = new Rectangle(10, 20, 30, 40);

        assertClose(1200, rectangle.getArea(), "rectangle area");
        assertClose(140, rectangle.getPerimeter(), "rectangle perimeter");
        assertClose(40, rectangle.getHeight(), "rectangle height");
    }

    private static void testGravityAppliesToAllShapes() {
        List<Shape2d> shapes = new ArrayList<>();
        Circle circle = new Circle(10, 20, 5);
        Rectangle rectangle = new Rectangle(100, 120, 30, 40);
        shapes.add(circle);
        shapes.add(rectangle);

        Forces forces = new Forces(shapes, 10);
        forces.applyGravity();
        forces.sumForces(0.1);

        assertClose(0.98, circle.getVy(), "circle gravity velocity");
        assertClose(0.98, rectangle.getVy(), "rectangle gravity velocity");
    }

    private static void testShapeMovement() {
        Circle circle = new Circle(10, 20, 5);
        circle.setVelocity(new Vector2D(6, -3));

        ShapeMove.moveShape(circle, 0.5);

        assertClose(13, circle.getX(), "moved x");
        assertClose(18.5, circle.getY(), "moved y");
    }

    private static void testCircleCollisionResponse() {
        Circle c1 = new Circle(0, 0, 10);
        Circle c2 = new Circle(19, 0, 10);
        c1.setVelocity(10, 0);
        c2.setVelocity(-4, 0);

        Collisions.attendCircleCollision(c1, c2, 19);

        assertTrue(c1.getVx() < 0, "first circle should rebound left");
        assertTrue(c2.getVx() > 0, "second circle should rebound right");

        double dx = c1.getX() - c2.getX();
        double dy = c1.getY() - c2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        assertClose(20, distance, "separated circle distance");
    }

    private static void testCircleRectangleCollisionSeparatesEmbeddedCircle() {
        Circle circle = new Circle(100, 95, 20);
        Rectangle rectangle = new Rectangle(100, 100, 80, 40);
        circle.setVelocity(0, 50);

        boolean collided = Collisions.resolveCircleRectangleCollision(circle, rectangle);
        double rectangleTop = rectangle.getY() - rectangle.getHeight() / 2.0;

        assertTrue(collided, "embedded circle should collide with rectangle");
        assertClose(rectangleTop, circle.getY() + circle.getRadius(), "circle bottom should touch rectangle top");
        assertTrue(
            circle.getVy() - rectangle.getVy() < 0,
            "circle and rectangle should separate after collision"
        );
    }

    private static void testRectangleCornerWallCollisionDoesNotFallThroughFloor() {
        Rectangle rectangle = new Rectangle(20, 200, 40, 40);
        rectangle.setVelocity(-10, 50);

        for (int i = 0; i < 4; i++) {
            Collisions.resolveRectangleWallCollision(rectangle, 300, 220);
        }

        assertClose(20, rectangle.getX(), "rectangle should stay inside left wall");
        assertClose(200, rectangle.getY(), "rectangle should stay on the floor");
        assertTrue(rectangle.getVx() >= 0, "rectangle should not keep moving into left wall");
        assertTrue(rectangle.getVy() <= 0, "rectangle should not keep moving into floor");
    }

    private static void testRectangleRectangleCollisionSeparatesStackedSquares() {
        Rectangle bottom = new Rectangle(100, 100, 40, 40);
        Rectangle top = new Rectangle(100, 65, 40, 40);
        top.setVelocity(0, 40);

        boolean collided = Collisions.resolveRectangleRectangleCollision(bottom, top);

        assertTrue(collided, "stacked rectangles should collide");
        assertClose(
            bottom.getY() - bottom.getHeight() / 2.0,
            top.getY() + top.getHeight() / 2.0,
            "top rectangle bottom should touch lower rectangle top"
        );
        assertClose(100, top.getX(), "stacked collision should not move rectangle sideways");
        assertTrue(
            top.getVy() - bottom.getVy() <= 0,
            "stacked rectangles should be moving apart after collision"
        );
    }

    private static void testRectangleRectangleCollisionSeparatesSideImpact() {
        Rectangle left = new Rectangle(100, 100, 40, 40);
        Rectangle right = new Rectangle(135, 100, 40, 40);
        left.setVelocity(40, 0);

        boolean collided = Collisions.resolveRectangleRectangleCollision(left, right);

        assertTrue(collided, "side rectangles should collide");
        assertClose(
            left.getX() + left.getWidth() / 2.0,
            right.getX() - right.getWidth() / 2.0,
            "rectangle edges should touch after side collision"
        );
        assertClose(100, left.getY(), "side collision should not move rectangle vertically");
    }

    private static void testStaticBodyIgnoresForcesAndMovement() {
        List<Shape2d> shapes = new ArrayList<>();
        Rectangle platform = new Rectangle(100, 200, 80, 20);
        platform.setStatic(true);
        platform.setVelocity(100, 100);
        shapes.add(platform);

        Forces forces = new Forces(shapes, 1000);
        forces.applyGravity();
        forces.sumForces(1.0);
        ShapeMove.moveShape(platform, 1.0);

        assertClose(100, platform.getX(), "static body x");
        assertClose(200, platform.getY(), "static body y");
        assertClose(0, platform.getVx(), "static body vx");
        assertClose(0, platform.getVy(), "static body vy");
    }

    private static void testGravityCanBeDisabledForDynamicBody() {
        List<Shape2d> shapes = new ArrayList<>();
        Circle circle = new Circle(20, 30, 10);
        circle.setAffectedByGravity(false);
        shapes.add(circle);

        Forces forces = new Forces(shapes, 1000);
        forces.applyGravity();
        forces.sumForces(1.0);

        assertClose(0, circle.getVx(), "gravity-disabled vx");
        assertClose(0, circle.getVy(), "gravity-disabled vy");
    }

    private static void testCircleBouncesOffStaticRectangleWithoutMovingPlatform() {
        Circle circle = new Circle(100, 95, 20);
        Rectangle platform = new Rectangle(100, 100, 80, 40);
        platform.setStatic(true);
        circle.setVelocity(0, 50);

        boolean collided = Collisions.resolveCircleRectangleCollision(circle, platform);

        assertTrue(collided, "circle should collide with static rectangle");
        assertClose(100, platform.getX(), "static platform x");
        assertClose(100, platform.getY(), "static platform y");
        assertClose(80, circle.getY() + circle.getRadius(), "circle should be outside platform");
        assertTrue(circle.getVy() < 0, "circle should rebound from static platform");
    }

    private static void testExactTouchingRectanglesDoNotJitter() {
        Rectangle left = new Rectangle(100, 100, 40, 40);
        Rectangle right = new Rectangle(140, 100, 40, 40);

        boolean collided = Collisions.resolveRectangleRectangleCollision(left, right);

        assertTrue(!collided, "exact-touch rectangles should not be treated as overlapping");
        assertClose(100, left.getX(), "exact-touch left x");
        assertClose(140, right.getX(), "exact-touch right x");
    }

    private static void testSameCenterCirclesSeparateWithoutNaN() {
        Circle c1 = new Circle(100, 100, 10);
        Circle c2 = new Circle(100, 100, 10);

        Collisions.attendCircleCollision(c1, c2, 0);

        assertFinite(c1.getX(), "same-center c1 x");
        assertFinite(c2.getX(), "same-center c2 x");
        assertClose(20, Math.abs(c1.getX() - c2.getX()), "same-center circles should separate");
    }

    private static void testInvalidShapeDimensionsFailFast() {
        expectThrows(() -> new Circle(0, 0, 0), "zero radius circle");
        expectThrows(() -> new Rectangle(0, 0, -1, 10), "negative rectangle width");
        expectThrows(() -> new Triangle(0, 0, 10, 0), "zero triangle height");

        Circle circle = new Circle(0, 0, 1);
        expectThrows(() -> circle.setMass(0), "zero mass");
    }

    private static void testMassChangesCollisionCorrectionShare() {
        Circle light = new Circle(0, 0, 10);
        Circle heavy = new Circle(15, 0, 10);
        light.setMass(1);
        heavy.setMass(9);

        Collisions.attendCircleCollision(light, heavy, 15);

        assertTrue(Math.abs(light.getX()) > Math.abs(heavy.getX() - 15), "lighter body should move farther");
        assertClose(20, heavy.getX() - light.getX(), "mass-corrected circles should separate");
    }

    private static void assertClose(double expected, double actual, String label) {
        if (Math.abs(expected - actual) > 0.000001) {
            throw new AssertionError(label + ": expected " + expected + " but got " + actual);
        }
    }

    private static void assertTrue(boolean condition, String label) {
        if (!condition) {
            throw new AssertionError(label);
        }
    }

    private static void assertFinite(double value, String label) {
        if (!Double.isFinite(value)) {
            throw new AssertionError(label + ": expected a finite value but got " + value);
        }
    }

    private static void expectThrows(Runnable runnable, String label) {
        try {
            runnable.run();
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError(label + ": expected IllegalArgumentException");
    }
}
