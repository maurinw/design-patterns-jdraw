package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * A rectangle figure.
 */
public class Rect extends AbstractRectangularFigure {

    private static final long serialVersionUID = 9120181044386552132L;

    /**
     * Constructs a rectangle with position and size.
     *
     * @param x x-coordinate of top-left
     * @param y y-coordinate of top-left
     * @param w width
     * @param h height
     */
    public Rect(int x, int y, int w, int h) {
        super(x, y);
        setBounds(new Point(x, y), new Point(x + w, y + h));
    }

    /**
     * Constructs a rectangle with zero size.
     *
     * @param x x-coordinate of top-left
     * @param y y-coordinate of top-left
     */
    public Rect(int x, int y) {
        this(x, y, 0, 0);
    }

    public Rect(Rect source) {
        super(source);
    }

    /** Draws the rectangle. */
    @Override
    public void draw(Graphics g) {
        Rectangle rectangle = getBounds();
        g.setColor(Color.WHITE);
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g.setColor(Color.BLACK);
        g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public Rect clone() {
        return new Rect(this);
    }

}
