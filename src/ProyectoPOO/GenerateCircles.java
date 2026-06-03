package ProyectoPOO;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import ProyectoPOO.Shapes2D.*;

/**
 * Adds circles to a running simulation at a steady interval.
 */
public class GenerateCircles extends Thread {

    private static final int MAX_SHAPES = 25;
    private static final int SPAWN_DELAY_MILLIS = 250;

    private final List<Shape2d> shapes;

    public GenerateCircles(List<Shape2d> shapes) {
        this.shapes = shapes;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            synchronized (shapes) {
                if (shapes.size() >= MAX_SHAPES) {
                    return;
                }

                Circle c1 = new Circle(320, 20, 20);
                c1.setVelocity(new Vector2D(randomVelocity(), randomVelocity()));
                shapes.add(c1);
            }

            try {
                Thread.sleep(SPAWN_DELAY_MILLIS);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

    private double randomVelocity() {
        return ThreadLocalRandom.current().nextDouble(-700, 700);
    }
}
