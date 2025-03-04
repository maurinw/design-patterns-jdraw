/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

/**
 * The Rect class represents a rectangle figure in JDraw. This class utilizes
 * the java.awt.Rectangle class to handle the geometric properties and drawing
 * of the rectangle, thereby reusing existing functionality for managing
 * rectangular shapes.
 * 
 * This implementation of the Figure interface provides basic functionality for
 * rendering, moving, and manipulating rectangle figures within the drawing
 * application. It includes methods for drawing the rectangle, adjusting its
 * bounds, and determining if a point lies within the rectangle.
 * 
 * Note: The current implementation does not yet notify listeners of changes,
 * nor does it provide actual handles or cloning functionality. These areas are
 * marked for future development.
 * 
 * @author Christoph Denzler &amp; Dominik Gruntz
 */
public class Rect implements Figure {
    private static final long serialVersionUID = 9120181044386552132L;

    /**
     * Use the java.awt.Rectangle in order to save/reuse code.
     */
    private final Rectangle rectangle;

    /**
     * List for FigureListeners
     */
    private List<FigureListener> listeners;

    /**
     * Constructs a new rectangle with the specified position and dimensions.
     * 
     * @param x the x-coordinate of the upper-left corner of the rectangle
     * @param y the y-coordinate of the upper-left corner of the rectangle
     * @param w the width of the rectangle
     * @param h the height of the rectangle
     */
    public Rect(int x, int y, int w, int h) {
        rectangle = new Rectangle(x, y, w, h);
        listeners = new ArrayList<FigureListener>();
    }

    /**
     * Renders the rectangle onto the specified graphics context. The rectangle is
     * filled with white color and outlined with a black border.
     * 
     * @param g the graphics context to use for drawing
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g.setColor(Color.BLACK);
        g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    /**
     * Sets the bounds of the rectangle by specifying two diagonal points. The
     * rectangle's position and size are adjusted accordingly.
     * 
     * @param origin the first corner point of the rectangle
     * @param corner the opposite corner point of the rectangle
     */
    @Override
    public void setBounds(Point origin, Point corner) {
        rectangle.setFrameFromDiagonal(origin, corner);
        notifyListeners();
    }

    /**
     * Moves the rectangle by the specified distances along the x and y axes.
     * 
     * @param dx the distance to move along the x-axis
     * @param dy the distance to move along the y-axis
     */
    @Override
    public void move(int dx, int dy) {
        rectangle.setLocation(rectangle.x + dx, rectangle.y + dy);
        notifyListeners();

    }

    private void notifyListeners() {
        for(FigureListener listener : listeners) {
            listener.figureChanged(new FigureEvent(this));
        }
    }

    /**
     * Checks whether the specified point is inside the bounds of the rectangle.
     * 
     * @param x the x-coordinate of the point to check
     * @param y the y-coordinate of the point to check
     * @return true if the point is within the rectangle; false otherwise
     */
    @Override
    public boolean contains(int x, int y) {
        return rectangle.contains(x, y);
    }

    /**
     * Returns the bounding rectangle of this figure.
     * 
     * @return a rectangle representing the bounds of this figure
     */
    @Override
    public Rectangle getBounds() {
        return rectangle.getBounds();
    }

    /**
     * Returns a list of handles associated with this rectangle. Handles are used
     * for manipulating the figure in the drawing area. Currently, this method
     * returns null and is intended to be implemented in an upcoming assignment.
     * 
     * @return a list of handles for this figure, or null if not yet implemented
     * @see jdraw.framework.Figure#getHandles()
     */
    @Override
    public List<FigureHandle> getHandles() {
        return null;
    }

    /**
     * Adds a listener to this figure to observe changes such as movement or
     * resizing. Currently not implemented.
     * 
     * @param listener the FigureListener to add
     */
    @Override
    public void addFigureListener(FigureListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from this figure. Currently not implemented.
     * 
     * @param listener the FigureListener to remove
     */
    @Override
    public void removeFigureListener(FigureListener listener) {
        listeners.remove(listener);
    }

    /**
     * Creates and returns a copy of this rectangle figure. Currently returns null
     * and is intended to be implemented in a future assignment.
     * 
     * @return a clone of this figure, or null if not yet implemented
     */
    @Override
    public Figure clone() {
        return null;
    }

}
