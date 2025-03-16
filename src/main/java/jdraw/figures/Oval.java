package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D.Float;

/**
 * The Oval class represents an oval (ellipse) figure in JDraw.
 *
 * This class uses a java.awt.geom.Ellipse2D.Float object to manage the
 * geometric properties of the oval. The oval is drawn with a white fill and
 * a black outline.
 *
 * The draw method updates the oval's frame based on its current bounds before
 * drawing it. The contains method checks whether a given point is inside the oval.
 */
public class Oval extends AbstractRectangularFigure {

    /**
     * The ellipse used for geometric calculations.
     */
    private final java.awt.geom.Ellipse2D.Float ellipse;

    /**
     * Constructs a new oval with the specified position and dimensions.
     *
     * @param x the x-coordinate of the upper-left corner of the oval
     * @param y the y-coordinate of the upper-left corner of the oval
     * @param w the width of the oval
     * @param h the height of the oval
     */
    protected Oval(int x, int y, int w, int h) {
        super(x, y);
        ellipse = new Float(x, y, 0, 0);
    }

    /**
     * Renders the oval on the given graphics context.
     *
     * The oval's frame is updated based on its current bounds. It is filled with white
     * and outlined with black.
     *
     * @param g the graphics context used for drawing
     */
    @Override
    public void draw(Graphics g) {
        Rectangle bounds = getBounds();
        ellipse.setFrame(bounds.x, bounds.y, bounds.width, bounds.height);

        g.setColor(Color.WHITE);
        g.fillOval(bounds.x, bounds.y, bounds.width, bounds.height);
        g.setColor(Color.BLACK);
        g.drawOval((int) ellipse.x, (int) ellipse.y, (int) ellipse.width, (int) ellipse.height);
    }

    /**
     * Checks if the specified point is within the bounds of the oval.
     *
     * @param x the x-coordinate of the point to test
     * @param y the y-coordinate of the point to test
     * @return true if the point is inside the oval, false otherwise
     */
    @Override
    public boolean contains(int x, int y) {
        return ellipse.contains(x, y);
    }
}
