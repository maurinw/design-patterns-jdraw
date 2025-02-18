/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;

/**
 * The RectTool class provides the functionality for drawing rectangles in
 * JDraw. It implements the DrawTool interface, allowing the user to create
 * rectangles by clicking and dragging the mouse. This tool manages the creation
 * process, from the initial mouse press to resizing during dragging and
 * finalizing on release.
 *
 * This class uses the context and view from the drawing framework to interact
 * with the drawing model, update the status bar, and provide feedback to the
 * user through the cursor and icon updates.
 * 
 * @see jdraw.framework.Figure
 * @see jdraw.framework.DrawTool
 * @see jdraw.framework.DrawView
 *
 * @author Christoph Denzler &amp; Dominik Gruntz
 */
public class RectTool implements DrawTool {

    /**
     * The path to the image resources used for tool icons.
     */
    private static final String IMAGES = "/images/";

    /**
     * The drawing context in which this tool operates, providing access to the
     * model, view, and other necessary components.
     */
    private final DrawContext context;

    /**
     * The view associated with the current drawing context, used to interact with
     * the visible drawing area and model. This variable can be used as a shortcut,
     * i.e. instead of calling context.getView().
     */
    private final DrawView view;

    /**
     * Temporary variable to hold the reference to the new rectangle during the
     * creation process (from mouse press to release).
     */
    private Rect newRect = null;

    /**
     * Temporary variable to hold the anchor point where the mouse was initially
     * pressed, marking one corner of the rectangle being created.
     */
    private Point anchor = null;

    /**
     * Constructs a new RectTool for the specified drawing context.
     * 
     * @param context the drawing context in which this tool will be used.
     */
    public RectTool(DrawContext context) {
        this.context = context;
        this.view = context.getView();
    }

    /**
     * Deactivates the rectangle drawing mode by resetting the status bar message
     * and any other state as needed.
     * 
     * @see jdraw.framework.DrawTool#deactivate()
     */
    @Override
    public void deactivate() {
        this.context.showStatusText("");
    }

    /**
     * Activates the rectangle drawing mode, updating the status bar to indicate the
     * active tool and setting up any necessary state or configuration.
     * 
     * @see jdraw.framework.DrawTool#activate()
     */
    @Override
    public void activate() {
        this.context.showStatusText("Rectangle Mode");
    }

    /**
     * Initializes the creation of a new rectangle by setting the anchor point where
     * the mouse was pressed. A new rectangle object is created and added to the
     * drawing model.
     * 
     * @param x the x-coordinate of the mouse press
     * @param y the y-coordinate of the mouse press
     * @param e the mouse event containing additional information such as modifier
     *          keys
     * 
     * @see jdraw.framework.DrawTool#mouseDown(int, int, MouseEvent)
     */
    @Override
    public void mouseDown(int x, int y, MouseEvent e) {
        if (newRect != null) {
            throw new IllegalStateException();
        }
        anchor = new Point(x, y);
        newRect = new Rect(x, y, 0, 0);
        view.getModel().addFigure(newRect);
    }

    /**
     * Adjusts the size of the new rectangle as the mouse is dragged, updating the
     * dimensions based on the current mouse position relative to the anchor point.
     * The status bar is updated to reflect the current size.
     * 
     * @param x the current x-coordinate of the mouse
     * @param y the current y-coordinate of the mouse
     * @param e the mouse event containing additional information such as modifier
     *          keys
     * 
     * @see jdraw.framework.DrawTool#mouseDrag(int, int, MouseEvent)
     */
    @Override
    public void mouseDrag(int x, int y, MouseEvent e) {
        newRect.setBounds(anchor, new Point(x, y));
        java.awt.Rectangle r = newRect.getBounds();
        this.context.showStatusText("w: " + r.width + ", h: " + r.height);
    }

    /**
     * Finalizes the creation of the rectangle when the mouse button is released.
     * Resets temporary variables and updates the status bar to indicate the mode.
     * 
     * @param x the x-coordinate of the mouse release
     * @param y the y-coordinate of the mouse release
     * @param e the mouse event containing additional information such as modifier
     *          keys
     * 
     * @see jdraw.framework.DrawTool#mouseUp(int, int, MouseEvent)
     */
    @Override
    public void mouseUp(int x, int y, MouseEvent e) {
        newRect = null;
        anchor = null;
        this.context.showStatusText("Rectangle Mode");
    }

    /**
     * Returns the cursor used when this tool is active, which is a crosshair cursor
     * to indicate drawing mode.
     * 
     * @return the cursor for rectangle drawing mode
     */
    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

    /**
     * Returns the icon associated with this tool, displayed in the UI to represent
     * the rectangle drawing mode.
     * 
     * @return the icon for this tool
     */
    @Override
    public Icon getIcon() {
        return new ImageIcon(getClass().getResource(IMAGES + "rectangle.png"));
    }

    /**
     * Returns the name of this tool, used for identification in the UI.
     * 
     * @return the name of the tool, "Rectangle"
     */
    @Override
    public String getName() {
        return "Rectangle";
    }

}
