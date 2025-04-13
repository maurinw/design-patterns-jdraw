package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D.Float;

/**
 * Represents an oval figure.
 */
public class Oval extends AbstractRectangularFigure {

    private java.awt.geom.Ellipse2D.Float ellipse;

    /**
     * Constructs a new oval.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param w the width
     * @param h the height
     */
    protected Oval(int x, int y, int w, int h) {
        super(x, y);
        ellipse = new Float(x, y, 0, 0);
    }

    public Oval(Oval source) {
        super(source);
        ellipse = (java.awt.geom.Ellipse2D.Float)source.ellipse.clone();
    }

    @Override
    public Oval clone() {
        return new Oval(this);
    }

    /** Draws the oval. */
    @Override
    public void draw(Graphics g) {
        Rectangle bounds = getBounds();
        ellipse.setFrame(bounds.x, bounds.y, bounds.width, bounds.height);

        g.setColor(Color.WHITE);
        g.fillOval(bounds.x, bounds.y, bounds.width, bounds.height);
        g.setColor(Color.BLACK);
        g.drawOval((int) ellipse.x, (int) ellipse.y, (int) ellipse.width, (int) ellipse.height);
    }

    /** Checks if the point is inside the oval. */
    @Override
    public boolean contains(int x, int y) {
        return ellipse.contains(x, y);
    }
}
