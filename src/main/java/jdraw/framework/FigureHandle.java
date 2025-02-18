/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * The FigureHandle interface defines the contract for handles used to
 * manipulate figures directly within the JDraw editor. Handles are interactive
 * components attached to figures that allow users to perform actions such as
 * resizing, moving, or rotating figures through direct manipulation with the
 * mouse.
 * 
 * Each handle is aware of its owning figure and provides methods to determine
 * its location, render itself, and track user interactions. Handles facilitate
 * intuitive graphical modifications by providing visual cues and specific
 * cursors to indicate possible operations.
 * 
 * @see Figure
 * @see DrawView
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface FigureHandle {

    /**
     * Returns the figure that owns this handle. The owner is the figure that this
     * handle is attached to and manipulates.
     * 
     * @return the figure that owns the handle
     */
    Figure getOwner();

    /**
     * Returns the current location of this handle. The location is dependent on the
     * position and dimensions of the owning figure, and it typically marks a point
     * that can be dragged or interacted with.
     * 
     * @return the current location of this handle
     */
    Point getLocation();

    /**
     * Renders the handle on the provided graphics context. This method is called
     * during the drawing process to visually represent the handle on the screen.
     * 
     * @param g the graphics context to use for drawing the handle
     */
    void draw(Graphics g);

    /**
     * Returns the cursor that should be displayed when the mouse is over this
     * handle. The cursor provides a visual indication of the type of interaction
     * that can be performed, such as resizing or moving.
     * 
     * A default implementation may return Cursor.getDefaultCursor().
     * 
     * @return the cursor to display when interacting with the handle
     */
    Cursor getCursor();

    /**
     * Determines whether a given point, specified by its coordinates, is within the
     * bounds of this handle. This is typically used to detect whether the handle
     * has been clicked or hovered over.
     * 
     * @param x the x-coordinate of the point to test
     * @param y the y-coordinate of the point to test
     * @return <code>true</code> if the point is within the handle;
     *         <code>false</code> otherwise
     */
    boolean contains(int x, int y);

    /**
     * Initiates an interaction with the handle, typically when a mouse press event
     * occurs. This method may capture the starting position of the interaction and
     * sets up any necessary state for handling subsequent drag events.
     * 
     * @param x the x position where the interaction started
     * @param y the y position where the interaction started
     * @param e the mouse event that initiated the interaction
     * @param v the view in which the interaction is performed
     */
    void startInteraction(int x, int y, MouseEvent e, DrawView v);

    /**
     * Handles the continuation of an interaction with the handle, such as during a
     * drag operation. This method updates the state of the handle and the owning
     * figure based on the current mouse position.
     * 
     * @param x the current x position of the mouse during the interaction
     * @param y the current y position of the mouse during the interaction
     * @param e the mouse event associated with the drag interaction
     * @param v the view in which the interaction is performed
     */
    void dragInteraction(int x, int y, MouseEvent e, DrawView v);

    /**
     * Finalizes the interaction with the handle, typically when the mouse is
     * released. This method completes the manipulation and updates the figure's
     * state accordingly.
     * 
     * @param x the final x position of the mouse at the end of the interaction
     * @param y the final y position of the mouse at the end of the interaction
     * @param e the mouse event that ended the interaction
     * @param v the view in which the interaction was performed
     */
    void stopInteraction(int x, int y, MouseEvent e, DrawView v);
}
