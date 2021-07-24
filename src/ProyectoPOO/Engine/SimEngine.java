package ProyectoPOO.Engine;

import java.util.ArrayList;
import ProyectoPOO.Shapes2D.*;

public class SimEngine implements Runnable{

    private Thread gameThread;
    private Window window;
    private Renderer renderer;
    private Collisions collisions;
    private Forces forces;

    private boolean isRunning = false;
    private boolean render = false;
    public static final double REFRESH_RATE = 1.0/60.0;
    private int width = 720, height = 640;
    private float scale =1.0f;
    private String title = "Motor de físicas";
    private ArrayList<Shape2d> shapes;


    public SimEngine(ArrayList<Shape2d> shapes){
        this.shapes = shapes;
    }

    public SimEngine(ArrayList<Shape2d> shapes, String title){
        this.shapes = shapes;
        this.setTitle(title);
    }

    public SimEngine(ArrayList<Shape2d> shapes, String title, int width, int height){
        this.shapes = shapes;
        this.setTitle(title);
        this.setHeight(height);
        this.setWidth(width);
    }

    public SimEngine(ArrayList<Shape2d> shapes, int width, int height){
        this.shapes = shapes;
        this.setHeight(height);
        this.setWidth(width);
    }

    public SimEngine(ArrayList<Shape2d> shapes, int width, int height, float scale){
        this.shapes = shapes;
        this.setHeight(height);
        this.setWidth(width);
        this.setScale(scale);
    }

    public SimEngine(ArrayList<Shape2d> shapes, String title, int width, int height, float scale){
        this.shapes = shapes;
        this.setHeight(height);
        this.setTitle(title);
        this.setWidth(width);
        this.setScale(scale);
    }

    public void start(){
        window = new Window(this);
        renderer = new Renderer(this);
        gameThread = new Thread(this);
        collisions = new Collisions(shapes, window);
        forces = new Forces(shapes, 6000);
        gameThread.run();
    }

    @Override
    public void run(){
        isRunning = true;

        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;
        double unprocessedTime = 0;
        double frameTime = 0;
        int frames = 0;
        int frameRate = 0;


        while(isRunning){
            render = false;
            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;
            unprocessedTime += passedTime;
            frameTime += passedTime;

            while(unprocessedTime >= REFRESH_RATE){
                unprocessedTime -= REFRESH_RATE;
                render = true;
                forces.applyGravityCircles();
                forces.sumForces(.014);
                for(int i = 0; i < shapes.size(); i++){
                    Shape2d o = shapes.get(i);
                    ShapeMove.moveShape(o, .014);
                }
                collisions.checkWallCollisions();
                collisions.checkCollisionsObjects();
                if(frameTime >= 1.0){
                    frameTime = 0;
                    frameRate = frames;
                    frames = 0;
                }
            }
            if(render){
                window.update();
                renderer.paintWindow(frameRate);
                frames++;
            }else{
                try{
                    Thread.sleep(1);
                }catch(InterruptedException ex){
                    ex.printStackTrace();
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

    public ArrayList<Shape2d> getShapes(){
        return shapes;
    }
}