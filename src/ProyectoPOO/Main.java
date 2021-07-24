package ProyectoPOO;

import java.util.ArrayList;

import ProyectoPOO.Engine.SimEngine;
import ProyectoPOO.Shapes2D.*;

public class Main {
    /*public static void main(String[] args){
      ArrayList<Shape2d> shapes = new ArrayList<>();                     ///
        shapes.add(new Triangle(50, 600, 80, 100));                        ///
        ((Triangle) shapes.get(0)).setVelocity(new Vector2D(0,0));         ///
        shapes.add(new Rectangle(350, 250, 80, 80));                       ///  Input de las figuras a trazar
        ((Rectangle) shapes.get(1)).setVelocity(new Vector2D(-400,0));     ///    
        Thread Gen = new GenerateCircles(shapes);                          ///
        Gen.start();                                                       ///
        SimEngine GE = new SimEngine(shapes,"Motor");                      ///  Funcionamiento del API
        GE.start();                                                  ///
    }*/

   /* public static void main(String[] args){
        ArrayList<Shape2d> shapes = new ArrayList<>();
        shapes.add(new Rectangle(45, 250, 80, 80));
        ((Rectangle) shapes.get(0)).setVelocity(new Vector2D(-400,600));
        shapes.add(new Rectangle(350, 250, 80, 80));
        ((Rectangle) shapes.get(1)).setVelocity(new Vector2D(-1500,0));
        shapes.add(new Rectangle(350, 400, 80, 80));
        ((Rectangle) shapes.get(2)).setVelocity(new Vector2D(5000,2221));

        SimEngine GE = new SimEngine(shapes,"Motor"); // Funcionamiento del API
        GE.start();
    }*/

    public static void main(final String[] args) {
        final ArrayList<Shape2d> shapes = new ArrayList<>();
        shapes.add(new Rectangle(45, 200, 80, 80));
        ((Rectangle) shapes.get(0)).setVelocity(new Vector2D(-500, 500));
        shapes.add(new Rectangle(245, 200, 80, 80));
        ((Rectangle) shapes.get(1)).setVelocity(new Vector2D(-500, 500));
        shapes.add(new Rectangle(445, 200, 80, 80));
        ((Rectangle) shapes.get(2)).setVelocity(new Vector2D(500, -500));
        shapes.add(new Rectangle(645, 200, 80, 80));
        ((Rectangle) shapes.get(3)).setVelocity(new Vector2D(500, 500));
        shapes.add(new Rectangle(805, 200, 80, 80));
        ((Rectangle) shapes.get(4)).setVelocity(new Vector2D(-500, 500));
        shapes.add(new Rectangle(1005, 200, 80, 80));
        ((Rectangle) shapes.get(5)).setVelocity(new Vector2D(500, -500));
        shapes.add(new Rectangle(205, 500, 80, 80));
        ((Rectangle) shapes.get(6)).setVelocity(new Vector2D(-500, 500));
        shapes.add(new Rectangle(500, 500, 80, 80));
        ((Rectangle) shapes.get(7)).setVelocity(new Vector2D(-500, 500));
        shapes.add(new Rectangle(800, 500, 80, 80));
        ((Rectangle) shapes.get(8)).setVelocity(new Vector2D(500, -500));

        final SimEngine GE = new SimEngine(shapes, "Motor"); // Funcionamiento del API
        GE.start();
    }
}