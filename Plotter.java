import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Point;
import java.awt.Rectangle;
import matcalc.*;
import java.awt.event.MouseWheelEvent;

public class Plotter extends JPanel {

    private static BasicStroke AXIS_STROKE = new BasicStroke(3);
    private float width = 20f, height = 20f;

    /**
     * calculates the inverval for labels on axis
     * @param axis a character indicating whether to calculate the interval for x or y axis
     * @return the distance between two labels in world space
     */
    private float getInterval(char axis) {
        float hsize;
        if(axis == 'x') {
            hsize = this.width * 0.5f;
        }else {
            hsize = this.height * 0.5f;
        }

        float digits = (float) Math.floor(Math.log10(hsize) + 1.0);
        float interval = 0.1f * (float) Math.pow(10, digits - 1);
        return interval;
    }

    private Point transform(float x, float y) {
        // create orthographic matrix
        float m11 = 2.0f / this.width;
        float m21 = 0.0f;

        float m12 = 0.0f;
        float m22 = 2.0f / this.height;

        //System.out.printf("orthographic matrix:\n%f, %f\n%f, %f\n", m11, m21, m12, m22);

        // mulitply coordinate with orthographi matrix
        float xWorldPos = m11 * x + m21 * y;
        float yWorldPos = m12 * x + m22 * y;

        // transform to screen coordinates
        Rectangle bounds = this.getBounds();
        float rx = (xWorldPos + 1) * 0.5f;
        float ry = (yWorldPos + 1) * 0.5f;

        return new Point((int) (rx * bounds.width), bounds.height - (int) (ry * bounds.height));
    }

    private void drawGraph(Graphics2D g2d, Function f, float dx) {

        float hwidth = this.width * 0.5f;
        
        Point prev = transform(-hwidth, f.evaluate(-hwidth));
        for(float x = -hwidth + dx; x < hwidth; x += dx) {
            float y = f.evaluate(x);
            Point p = this.transform(x, y);
            g2d.drawLine(prev.x, prev.y, p.x, p.y);
            prev = p;            
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(AXIS_STROKE);

        g2d.setColor(Color.DARK_GRAY);
        g2d.fill(this.getBounds());

        float dx = this.getInterval('x');
        float dy = this.getInterval('y');

        // draw axis
        Point left = transform(-this.width * 0.5f, 0.0f);
        Point right = transform(this.width * 0.5f, 0.0f);

        Point top = transform(0.0f, this.height * 0.5f);
        Point bottom = transform(0.0f, -this.height * 0.5f);

        g2d.setColor(Color.GRAY);
        g2d.drawLine(left.x, left.y, right.x, right.y);
        g2d.drawLine(top.x, top.y, bottom.x, bottom.y);

        for(float x = 0.0f; x < this.width * 0.5f; x += dx) {
            Point screenPos1 = transform(x, 0.0f);
            Point screenPos2 = transform(-x, 0.0f);
            final int labelSize = 10;
            g2d.drawLine(screenPos1.x, screenPos1.y + labelSize, screenPos1.x, screenPos1.y - labelSize);
            g2d.drawLine(screenPos2.x, screenPos2.y + labelSize, screenPos2.x, screenPos2.y - labelSize);
        }

        for(float y = 0.0f; y < this.height * 0.5f; y += dy) {
            Point screenPos1 = transform(0.0f, y);
            Point screenPos2 = transform(0.0f, -y);
            final int labelSize = 10;
            g2d.drawLine(screenPos1.x + labelSize, screenPos1.y, screenPos1.x - labelSize, screenPos1.y);
            g2d.drawLine(screenPos2.x + labelSize, screenPos2.y, screenPos2.x - labelSize, screenPos2.y);
        }

        
        // parse function
        try {
            String expression = GleichungPanel.func;
            int firstBracket = expression.indexOf("(", 0);
            int secondBracket = expression.indexOf(")", 0);
            int eqIndex = expression.indexOf("=", 0);

            String var = expression.substring(firstBracket + 1, secondBracket);
            String func = expression.substring(0, firstBracket);
            String ex = expression.substring(eqIndex + 1);

            Function f = new Function(func, var);
            f.setExpression(Expression.create(ex));
            
            // render function
            g2d.setColor(Color.RED);
            this.drawGraph(g2d, f, dx * 0.1f);
        } catch (Exception e) {
            return;
        }
    }
    

    public void zoom(int dir) {
        float changeZoom = 1.0f + ((float) dir * 0.1f);
        this.width *= changeZoom;
        this.height *= changeZoom;
        this.repaint();
    }

    public Plotter() {
        this.addMouseWheelListener((MouseWheelEvent e) -> {
            this.zoom(e.getWheelRotation());
        });
    }
}
