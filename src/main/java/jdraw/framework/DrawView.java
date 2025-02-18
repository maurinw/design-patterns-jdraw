/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * The DrawView interface defines the contract for the view component in the
 * JDraw application, responsible for displaying the graphical model and
 * forwarding user interactions to the associated controller. It manages the
 * rendering of figures, handles user input, and maintains the current selection
 * state within the drawing area.
 * 
 * This interface plays a crucial role in the Model-View-Controller (MVC)
 * architecture of JDraw, linking the drawing model with the drawing tools and
 * user interface. It provides methods for setting the context, managing grids,
 * selecting figures, and handling graphical rendering.
 *
 * @see DrawModel
 * @see DrawContext
 * @see DrawTool
 * @see DrawGrid
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface DrawView {

    /** Default width of the view in pixels. */
    int DEFAULT_WIDTH = 300;
    /** Default height of the view in pixels. */
    int DEFAULT_HEIGHT = 200;

    /**
     * Returns the model associated with this view, which contains all figures
     * displayed within the drawing area.
     * 
     * @return the model presented by this view
     */
    DrawModel getModel();

    /**
     * Sets the context for this view, linking it to the environment that manages
     * interactions and the application's state.
     * 
     * @param context the context to be used by this view
     */
    void setDrawContext(DrawContext context);

    /**
     * Returns the context currently associated with this view.
     * 
     * @return the context associated with this view
     */
    DrawContext getContext();

    /**
     * Sets the current grid for this view. If the provided grid is
     * <code>null</code>, any existing grid is removed, allowing freeform drawing.
     * 
     * @param g the grid to be used, or <code>null</code> to remove the grid
     */
    void setGrid(DrawGrid g);

    /**
     * Returns the current grid used by this view for constraining coordinates.
     * 
     * @return the current grid, or <code>null</code> if no grid is set
     */
    DrawGrid getGrid();

    /**
     * Returns the handle at the specified mouse position, if any. This method uses
     * the constrained mouse coordinates (x, y) to determine which handle, if any,
     * is under the cursor.
     * 
     * @param x the constrained x-coordinate of the mouse
     * @param y the constrained y-coordinate of the mouse
     * @param e the original mouse event containing additional context
     * @return the handle at the mouse position, or <code>null</code> if no handle
     *         is present
     */
    FigureHandle getHandle(int x, int y, MouseEvent e);

    /**
     * Returns a list of figures currently selected in the view. The figures are
     * ordered by the sequence in which they were selected by the user.
     * Modifications to this list do not affect the selection state of the figures.
     * 
     * To change the selection, use the methods {@link #addToSelection(Figure)},
     * {@link #removeFromSelection(Figure)}, and {@link #clearSelection()}.
     * 
     * @return a list of currently selected figures, or an empty list if none are
     *         selected
     */
    List<Figure> getSelection();

    /**
     * Clears the current selection and removes all selection markers from the view.
     */
    void clearSelection();

    /**
     * Adds a figure to the current selection. The figure is appended to the end of
     * the ordered selection list.
     * 
     * @param f the figure to add to the selection
     */
    void addToSelection(Figure f);

    /**
     * Removes the given figure from the current selection. If the figure is not
     * currently selected, no action is taken.
     * 
     * @param f the figure to remove from the selection
     */
    void removeFromSelection(Figure f);

    /**
     * Sets a selection rubber band (visual selection rectangle) to be displayed by
     * the view. If the <code>rect</code> parameter is <code>null</code>, any
     * existing rubber band is removed.
     * 
     * @param rect the rectangle representing the selection rubber band, or
     *             <code>null</code> to remove it
     */
    void setSelectionRubberBand(Rectangle rect);

    /**
     * Draws the figures in the drawing model using the provided graphics context.
     * This method iterates over all figures in the model and invokes their
     * respective paint methods to render them.
     * 
     * @param g the graphics context to use for painting the figures
     */
    void paint(Graphics g);

    /**
     * Repaints the entire view, prompting a redraw of all figures and other visual
     * components.
     */
    void repaint();

    /**
     * Sets the cursor for the drawing view, updating the cursor displayed to the
     * user to reflect the current interaction mode or tool.
     * 
     * @param c the cursor to be set
     */
    void setCursor(Cursor c);

    /**
     * Handles cleanup and resource deallocation when the view is closed, such as
     * removing registered listeners and freeing associated resources.
     */
    void close();

    /**
     * Requests focus for this view, directing user input and keyboard events to
     * this component.
     */
    void requestFocus();
}
