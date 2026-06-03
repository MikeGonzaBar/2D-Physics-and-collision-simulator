package ProyectoPOO.Engine;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import ProyectoPOO.Shapes2D.*;

/**
 * Draws the current simulation state into the window back buffer.
 */
public class Renderer {

    private final BufferedImage image;
    private final List<Shape2d> shapes;
    

    public Renderer(SimEngine GE) {
        image = GE.getWindow().getImage();
        shapes = GE.getShapes();
    }

    public void paintWindow(int fps) {
        Graphics2D g2d = image.createGraphics();
        try {
            g2d.setColor(new Color(0, 0, 0));
            g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
            g2d.setColor(Color.GREEN);
            g2d.drawString("FPS: " + fps, 5, 15);

            synchronized (shapes) {
                for (Shape2d shape : shapes) {
                    if (shape instanceof Triangle) {
                        Triangle t1 = (Triangle) shape;
                        g2d.setColor(Color.GREEN);
                        g2d.fillPolygon(t1.getXCoords(), t1.getYCoords(), 3);
                    } else if (shape instanceof Circle) {
                        Circle c1 = (Circle) shape;
                        int diameter = (int) Math.round(c1.getRadius() * 2);
                        g2d.setColor(Color.RED);
                        g2d.fillOval(
                            (int) Math.round(c1.getX() - c1.getRadius()),
                            (int) Math.round(c1.getY() - c1.getRadius()),
                            diameter,
                            diameter
                        );
                    } else if (shape instanceof Rectangle) {
                        Rectangle r1 = (Rectangle) shape;
                        g2d.setColor(Color.WHITE);
                        g2d.fillPolygon(r1.getXCoords(), r1.getYCoords(), 4);
                    }
                }
            }
        } finally {
            g2d.dispose();
        }
    }
}
