package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

/**
 * Represents a line figure.
 */
public class Line extends AbstractFigure {

    private final Line2D line;

    /**
     * Constructs a line at the given position.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Line(int x, int y) {
        line = new Line2D.Double(x, y, x, y);
    }

    /** Moves the line. */
    @Override
    public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            line.setLine(
                line.getX1() + dx,
                line.getY1() + dy,
                line.getX2() + dx,
                line.getY2() + dy
            );
            notifyObservers();
        }
    }

    /** Sets the line bounds. */
    @Override
    public void setBounds(Point origin, Point corner) {
        if (line.getX1() != origin.x || line.getY1() != origin.y || line.getX2() != corner.x || line.getY2() != corner.y) {
            line.setLine(origin, corner);
        }
        notifyObservers();
    }

    /** Returns the line bounds. */
    @Override
    public Rectangle getBounds() {
        return line.getBounds();
    }

    /** Checks if the point is on the line. */
    @Override
    public boolean contains(int x, int y) {
        return line.ptLineDistSq(x, y) < 16;
    }

    /** Draws the line. */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine((int)line.getX1(), (int)line.getY1(), (int)line.getX2(), (int)line.getY2());
    }
}
