package ProyectoPOO;

import java.util.ArrayList;

import ProyectoPOO.Shapes2D.*;


public class GenerateCircles extends Thread {

    private ArrayList<Shape2d> shapes;

    public GenerateCircles(ArrayList<Shape2d> shapes){
        this.shapes = shapes;
    }

    @Override
    public void run() {
        while(true){
            if(shapes.size() == 25) return;
            Circle c1 = new Circle(320, 0, 20);
            c1.setVelocity(new Vector2D(Math.random()*700,Math.random()*700));
            shapes.add(c1);
            try{
                Thread.sleep(250);
            }catch(InterruptedException e){
            }
        }
        
    }
    
}