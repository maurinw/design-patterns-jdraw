package jdraw.figures;

import java.awt.Point;
import java.awt.Rectangle;


/**
 * Base class for rectangular figures.
 */
public abstract class AbstractRectangularFigure extends AbstractFigure {

    /** Rectangle bounds. */
    private final Rectangle rectangle;

    /**
     * Constructs a rectangular figure.
     *
     * @param x x-coordinate of top-left
     * @param y y-coordinate of top-left
     */
    protected AbstractRectangularFigure(int x, int y) {
        this.rectangle = new Rectangle(x, y, 0, 0);
    }

    /** Moves the figure. */
    @Override
    public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            rectangle.translate(dx, dy);
            notifyObservers();
        }
    }

    /** Sets new bounds. */
    @Override
    public void setBounds(Point origin, Point corner) {
        Rectangle original = new Rectangle(rectangle);
        rectangle.setFrameFromDiagonal(origin, corner);
        if (!original.equals(rectangle)) {
            notifyObservers();
        }
    }

    /** Returns a copy of the bounds. */
    @Override
    public Rectangle getBounds() {
        return rectangle.getBounds();
    }

    /** Checks if a point is inside the figure. */
    @Override
    public boolean contains(int x, int y) {
        return rectangle.contains(x, y);
    }
}
