package ProyectoPOO.Engine;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;

/**
 * Swing/AWT window and buffer strategy used by the simulator.
 */
public class Window {

    private final JFrame frame;
    private final BufferedImage image;
    private final Canvas canvas;
    private final BufferStrategy bs;

    public Window(SimEngine GE) {
        image = new BufferedImage(GE.getWidth(), GE.getHeight(), BufferedImage.TYPE_INT_RGB);
        canvas = new Canvas();
        Dimension dim = new Dimension((int) (GE.getWidth() * GE.getScale()), (int) (GE.getHeight() * GE.getScale()));
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
    }

    public void update() {
        do {
            do {
                Graphics g = bs.getDrawGraphics();
                try {
                    g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
                } finally {
                    g.dispose();
                }
            } while (bs.contentsRestored());

            bs.show();
            Toolkit.getDefaultToolkit().sync();
        } while (bs.contentsLost());
    }

    public BufferedImage getImage() {
        return image;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public int getWidth() {
        return canvas.getWidth();
    }

    public int getHeight() {
        return canvas.getHeight();
    }
}
