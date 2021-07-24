package ProyectoPOO.Engine;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ProyectoPOO.Shapes2D.*;

public class Renderer {

    private BufferedImage image;
    private Graphics2D g2d;
    private Window window;
    private ArrayList<Shape2d> shapes;
    

    public Renderer(SimEngine GE){
        image = GE.getWindow().getImage();
        window = GE.getWindow();
        shapes = GE.getShapes();
    }

    // Se ejecuta en cada frame
    public void paintWindow(int fps){

        g2d = image.createGraphics();
        g2d.setColor(new Color(0, 0, 0));
        g2d.fillRect(0, 0, window.getCanvas().getWidth(), window.getCanvas().getHeight());
        g2d.setColor(Color.GREEN);
        g2d.drawString("FPS: " + fps, 5, 15);
        for(int i = 0; i < shapes.size(); i++){
            Shape2d shape = shapes.get(i);
            if(shape instanceof Triangle){
                Triangle t1 = (Triangle) shape;
                g2d.setColor(Color.GREEN);
                g2d.fillPolygon(t1.getXCoords(), t1.getYCoords(), 3);
            }else if(shape instanceof Circle){
                Circle c1 = (Circle) shape;
                g2d.setColor(Color.RED);
                g2d.fillOval((int) (c1.getX()-c1.getRadius()), (int) (c1.getY()-c1.getRadius()), (int) c1.getRadius() * 2, (int) c1.getRadius() * 2);
            }else if(shape instanceof Rectangle){
                Rectangle r1 = (Rectangle) shape;
                g2d.setColor(Color.WHITE);
                g2d.fillPolygon(r1.getXCoords(), r1.getYCoords(), 4);
            }
        }
    }
}