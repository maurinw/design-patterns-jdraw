/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.List;

/**
 * The Figure interface defines the contract for all figures used in the JDraw
 * graphic editor. Each figure type, such as rectangles, circles, or lines, must
 * implement this interface to integrate with the drawing framework.
 * 
 * A figure is responsible for its own rendering, movement, boundary management,
 * and interaction with mouse events. It also supports the use of handles for
 * manipulation and allows listeners to observe changes in its state.
 * 
 * Implementing classes should notify registered listeners about changes in
 * their properties, such as position, size, or visual appearance.
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface Figure extends Serializable {

    /**
     * Renders the figure onto the provided graphics context. This method is called
     * whenever the figure needs to be drawn, such as during painting or when the
     * view is refreshed.
     * 
     * @param g the Graphics object used to draw the figure
     * @see java.awt.Graphics
     */
    void draw(Graphics g);

    /**
     * Moves the figure by the specified distances along the x and y axes. The
     * figure's position is adjusted according to the provided delta values, and all
     * registered listeners are notified of the change.
     * 
     * @param dx the distance to move in the x direction, in pixels
     * @param dy the distance to move in the y direction, in pixels
     * @see #addFigureListener
     */
    void move(int dx, int dy);

    /**
     * Determines whether the specified coordinates are within the bounds of the
     * figure. This method is used to identify which figure is under the mouse
     * cursor during interactions, such as clicks or selections.
     * 
     * @param x the x-coordinate of the point to test
     * @param y the y-coordinate of the point to test
     * @return <code>true</code> if the point is within the figure's bounds,
     *         <code>false</code> otherwise
     */
    boolean contains(int x, int y);

    /**
     * Adjusts the size and position of the figure based on the provided origin and
     * corner points. This method is used to resize the figure, and it should notify
     * all registered listeners of any changes to the figure's state.
     * 
     * @param origin the new top-left corner of the figure
     * @param corner the new bottom-right corner of the figure
     * @see #addFigureListener
     */
    void setBounds(Point origin, Point corner);

    /**
     * Returns the bounding rectangle of the figure. The bounds represent the
     * smallest rectangle that completely contains the figure, and is used for
     * collision detection, rendering, and selection.
     * 
     * @return the bounds of the figure as a Rectangle
     */
    Rectangle getBounds();

    /**
     * Returns a list of handles associated with the figure. Handles allow users to
     * manipulate the figure, such as resizing or rotating. If the figure does not
     * support handles, this method may return <code>null</code>.
     * 
     * @return a list of figure handles, or <code>null</code> if handles are not
     *         supported
     * @see FigureHandle
     */
    List<? extends FigureHandle> getHandles();

    /**
     * Adds a listener to this figure, allowing it to be notified of changes to the
     * figure's state, such as movement, resizing, or other modifications. If the
     * listener is already registered or is <code>null</code>, this method takes no
     * action.
     * 
     * @param listener the FigureListener to add
     * @see FigureListener
     */
    void addFigureListener(FigureListener listener);

    /**
     * Removes a listener from this figure, so it will no longer receive
     * notifications about changes to the figure's state. If the listener was not
     * previously registered or is <code>null</code>, this method takes no action.
     * 
     * @param listener the FigureListener to remove
     * @see FigureListener
     */
    void removeFigureListener(FigureListener listener);

    /**
     * Creates and returns a deep copy of this figure. The cloned figure is
     * independent of the original, with its own state and listeners.
     * 
     * @return a clone of this figure
     */
    Figure clone();
}
