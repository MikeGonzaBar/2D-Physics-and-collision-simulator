package ProyectoPOO.Engine;

import java.util.List;

import ProyectoPOO.Shapes2D.*;

/**
 * Coordinates the fixed-step physics loop, rendering, and Swing window.
 */
public class SimEngine implements Runnable {
    private static final int COLLISION_SOLVER_STEPS = 3;
    private static final double MAX_FRAME_TIME = 0.25;

    private Thread gameThread;
    private Window window;
    private Renderer renderer;
    private Collisions collisions;
    private Forces forces;

    private volatile boolean isRunning = false;
    private boolean render = false;
    public static final double REFRESH_RATE = 1.0 / 60.0;
    private int width = 720, height = 640;
    private float scale = 1.0f;
    private String title = "Motor de fisicas";
    private final List<Shape2d> shapes;


    public SimEngine(List<Shape2d> shapes) {
        this.shapes = shapes;
    }

    public SimEngine(List<Shape2d> shapes, String title) {
        this.shapes = shapes;
        this.setTitle(title);
    }

    public SimEngine(List<Shape2d> shapes, String title, int width, int height) {
        this.shapes = shapes;
        this.setTitle(title);
        this.setHeight(height);
        this.setWidth(width);
    }

    public SimEngine(List<Shape2d> shapes, int width, int height) {
        this.shapes = shapes;
        this.setHeight(height);
        this.setWidth(width);
    }

    public SimEngine(List<Shape2d> shapes, int width, int height, float scale) {
        this.shapes = shapes;
        this.setHeight(height);
        this.setWidth(width);
        this.setScale(scale);
    }

    public SimEngine(List<Shape2d> shapes, String title, int width, int height, float scale) {
        this.shapes = shapes;
        this.setHeight(height);
        this.setTitle(title);
        this.setWidth(width);
        this.setScale(scale);
    }

    public void start() {
        if (isRunning) {
            return;
        }
        window = new Window(this);
        renderer = new Renderer(this);
        collisions = new Collisions(shapes, window);
        forces = new Forces(shapes, 6000);
        gameThread = new Thread(this, "physics-simulation-loop");
        gameThread.start();
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;

        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;
        double unprocessedTime = 0;
        double frameTime = 0;
        int frames = 0;
        int frameRate = 0;


        while (isRunning) {
            render = false;
            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;
            if (passedTime > MAX_FRAME_TIME) {
                passedTime = MAX_FRAME_TIME;
            }
            unprocessedTime += passedTime;
            frameTime += passedTime;

            while (unprocessedTime >= REFRESH_RATE) {
                unprocessedTime -= REFRESH_RATE;
                render = true;
                forces.applyGravity();
                forces.sumForces(REFRESH_RATE);
                synchronized (shapes) {
                    for (Shape2d shape : shapes) {
                        ShapeMove.moveShape(shape, REFRESH_RATE);
                    }
                }
                for (int i = 0; i < COLLISION_SOLVER_STEPS; i++) {
                    collisions.checkWallCollisions();
                    collisions.checkCollisionsObjects();
                }
                collisions.checkWallCollisions();
                if (frameTime >= 1.0) {
                    frameTime = 0;
                    frameRate = frames;
                    frames = 0;
                }
            }
            if (render) {
                renderer.paintWindow(frameRate);
                window.update();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    stop();
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Window getWindow() {
        return window;
    }

    public List<Shape2d> getShapes() {
        return shapes;
    }
}
