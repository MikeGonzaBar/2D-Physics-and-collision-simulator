package ProyectoPOO.Engine;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;

public class Window {

    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics2D g2d;
    private Graphics g;

    public Window(SimEngine GE){
        image = new BufferedImage(GE.getWidth(),GE.getHeight(),BufferedImage.TYPE_INT_RGB);
        canvas = new Canvas();
        Dimension dim = new Dimension((int) (GE.getWidth()*GE.getScale()),(int) (GE.getHeight()*GE.getScale()));
        canvas.setPreferredSize(dim);
        canvas.setMaximumSize(dim);
        canvas.setMinimumSize(dim);

        frame = new JFrame(GE.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();
    }

    public void update(){
        g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        bs.show();
    }

    public BufferedImage getImage() {
        return image;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Graphics2D getG2d() {
        return g2d;
    }

    public int getWidth(){
        return canvas.getWidth();
    }

    public int getHeight(){
        return canvas.getHeight();
    }
}