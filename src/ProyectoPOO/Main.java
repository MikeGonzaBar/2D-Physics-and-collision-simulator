package ProyectoPOO;

import java.util.ArrayList;
import java.util.List;

import ProyectoPOO.Engine.SimEngine;
import ProyectoPOO.Shapes2D.*;

public class Main {

    public static void main(final String[] args) {
        final List<Shape2d> shapes = new ArrayList<>();

        shapes.add(platform(115, 620, 210, 40));
        shapes.add(platform(370, 580, 140, 35));
        shapes.add(platform(610, 625, 170, 30));

        shapes.add(rectangle(120, 100, 58, 58, 220, 40));
        shapes.add(rectangle(500, 120, 64, 64, -180, 20));
        shapes.add(circle(320, 60, 24, 150, 10));
        shapes.add(circle(650, 90, 28, -210, 30));

        final SimEngine engine = new SimEngine(shapes, "2D Physics Simulator");
        engine.start();
    }

    private static Rectangle platform(double x, double y, double width, double height) {
        Rectangle platform = new Rectangle(x, y, width, height);
        platform.setStatic(true);
        return platform;
    }

    private static Rectangle rectangle(double x, double y, double width, double height, double vx, double vy) {
        Rectangle rectangle = new Rectangle(x, y, width, height);
        rectangle.setVelocity(new Vector2D(vx, vy));
        return rectangle;
    }

    private static Circle circle(double x, double y, double radius, double vx, double vy) {
        Circle circle = new Circle(x, y, radius);
        circle.setVelocity(new Vector2D(vx, vy));
        return circle;
    }
}
