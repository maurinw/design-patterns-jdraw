package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D.Float;

public class Oval extends AbstractRectangularFigure {

    private final java.awt.geom.Ellipse2D.Float ellipse;

    protected Oval(int x, int y) {
        super(x, y);
        ellipse = new Float(x, y, 0, 0);
    }

    @Override
    public void draw(Graphics g) {
        Rectangle bounds = getBounds();
        ellipse.setFrame(bounds.x, bounds.y, bounds.width, bounds.height);

        g.setColor(Color.WHITE);
        g.fillOval(bounds.x, bounds.y, bounds.width, bounds.height);
        g.setColor(Color.BLACK);
        g.drawOval((int) ellipse.x, (int) ellipse.y, (int) ellipse.width, (int) ellipse.height);
    }

    @Override
    public boolean contains(int x, int y) {
        return ellipse.contains(x, y);
    }

}
