/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.std;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

/**
 * The StdSelectionTool class implements the default selection tool for the
 * JDraw application, allowing users to select, move, and manipulate figures
 * within the drawing area. It provides functionality for single and multiple
 * figure selection, as well as direct manipulation of figures through handles.
 * 
 * This tool supports basic selection interactions, including clicking to
 * select, dragging to move figures, and using a selection rubber band to select
 * multiple figures at once. It also manages keyboard modifiers (e.g., Shift) to
 * extend or modify selections.
 * 
 * The StdSelectionTool integrates with the DrawView to render and manipulate
 * figures, and it interacts with the DrawContext to update status messages and
 * tool-specific behavior within the application.
 * 
 * @see DrawTool
 * @see DrawView
 * @see DrawContext
 * @see Figure
 * @see FigureHandle
 * 
 * @author Christoph Denzler
 */
public class StdSelectionTool implements DrawTool {

    /** The path to the image resources used by the tool. */
    private static final String IMAGES = "/images/";

    /** The drawing view on which this tool operates. */
    private DrawView view;

    /** The drawing context within which this tool is used. */
    private DrawContext context;

    /**
     * Stores the original (unconstrained) mouse position when an interaction
     * begins.
     */
    private int originalX, originalY;

    /** Temporary variables for tracking the current constrained mouse position. */
    private int tempX, tempY;

    /**
     * Indicates whether the tool is in selection mode, which occurs when a rubber
     * band selection is being drawn.
     */
    private boolean selMode = false;

    /** Initial coordinates for the start of a selection drag. */
    private int sx0, sy0;

    /** Current coordinates during a selection drag operation. */
    private int sx1, sy1;

    /** The last figure that was selected, used for tracking selection changes. */
    private Figure lastSelectedFigure;

    /** The handle currently being interacted with, if any. */
    private FigureHandle currentHandle;

    /**
     * Constructs a new StdSelectionTool for the specified view and context,
     * enabling selection and manipulation of figures within the drawing area.
     * 
     * @param view    the view on which this tool operates
     * @param context the context in which this tool operates
     */
    public StdSelectionTool(DrawView view, DrawContext context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void activate() {
        view.requestFocus();
        context.showStatusText("Selection mode");
    }

    @Override
    public void deactivate() {
        context.showStatusText("");
    }

    /**
     * Checks whether the specified coordinates are on an already selected figure.
     * This method is used to maintain or extend the current selection based on user
     * interactions.
     * 
     * @param x the x-coordinate of the point to check
     * @param y the y-coordinate of the point to check
     * @return <code>true</code> if the point is on an already selected figure;
     *         <code>false</code> otherwise
     */
    private boolean isOnSelected(int x, int y) {
        return view.getSelection().stream().filter(f -> f.contains(x, y)).findAny().isPresent();
    }

    @Override
    public Cursor getCursor() {
        if (currentHandle != null) {
            return currentHandle.getCursor();
        } else {
            return Cursor.getDefaultCursor();
        }
    }

    @Override
    public void mouseDown(int constrainedX, int constrainedY, MouseEvent e) {
        originalX = e.getX();
        originalY = e.getY();
        tempX = constrainedX;
        tempY = constrainedY;

        // 0. Check if a handle is used for manipulation
        lastSelectedFigure = null;
        currentHandle = view.getHandle(originalX, originalY, e);
        if (currentHandle != null) {
            currentHandle.startInteraction(constrainedX, constrainedY, e, view);
            return;
        }

        // 1. Check if the mouse position is on an already
        //    selected figure; in this case keep the selection
        if (!isOnSelected(originalX, originalY) || e.isShiftDown()) {

            // 2. if the mouse click is outside of an existing selection then
            //    deselect all figures - except if shift is down (modifier used to extend the selection)
            if (!e.isShiftDown()) {
                view.clearSelection();
            }

            // 3. Look for new figures (which are not already selected) and select them. Only one figure.
            List<Figure> figures = new LinkedList<>();
            view.getModel().getFigures().forEachOrdered(f -> figures.add(0, f));
            for (Figure f : figures) {
                if (f.contains(originalX, originalY) && !view.getSelection().contains(f)) {
                    view.addToSelection(f);
                    lastSelectedFigure = f;
                    break;
                }
            }

            // 4. If dragging mouse for spanning a selection, remember starting position
            //    (sx0, sy0) and initialize the current position (sx1, sy1).
            if (lastSelectedFigure == null && !e.isShiftDown()) {
                sx0 = originalX;
                sy0 = originalY;
                sx1 = originalX;
                sy1 = originalY;
                selMode = true;
            }
        }
        view.repaint();
    }

    /**
     * Adjusts a rectangle to ensure it is non-empty by flipping its dimensions if
     * necessary. According to the API, a rectangle is considered empty if its width
     * or height is zero or negative.
     * 
     * @param r a potentially empty rectangle
     * @return a non-empty rectangle with the same dimensions as the input parameter
     *         <b>Note:</b> The returned reference refers to the same rectangle as
     *         the (mutagle) input parameter.
     */
    private Rectangle makePositiveSize(Rectangle r) {
        if (r.width < 0) {
            r.width = -r.width;
            r.x -= r.width;
        }
        if (r.height < 0) {
            r.height = -r.height;
            r.y -= r.height;
        }
        return r;
    }

    /**
     * Checks whether the outer rectangle fully contains the inner one, accounting
     * for potential zero-width or zero-height conditions that affect containment
     * checks.
     * 
     * @param outer the rectangle supposed to fully contain the inner rectangle
     * @param inner the rectangle supposed to be fully surrounded by the outer
     *              rectangle
     * @return <code>true</code> if the outer rectangle fully encloses the inner;
     *         <code>false</code> otherwise
     */
    private boolean contains(Rectangle outer, Rectangle inner) {
        makePositiveSize(inner);
        if (inner.height == 0) {
            return inner.y > outer.y && inner.y <= outer.y + outer.height && inner.x >= outer.x
                    && (inner.x + inner.width) <= outer.x + outer.width;
        }
        if (inner.width == 0) {
            return inner.x > outer.x && inner.x <= outer.x + outer.width && inner.y >= outer.y
                    && (inner.y + inner.height) <= outer.y + outer.height;
        }
        return outer.contains(inner);
    }

    /**
     * Returns the smaller of two integers.
     * 
     * @param x the first integer
     * @param y the second integer
     * @return the smaller of the two values
     */
    private static int min(int x, int y) {
        return x < y ? x : y;
    }

    /**
     * Returns the absolute value of an integer.
     * 
     * @param x the integer to compute the absolute value of
     * @return the absolute value of <code>x</code>
     */
    private static int abs(int x) {
        return x < 0 ? -x : x;
    }

    @Override
    public void mouseDrag(int i, int j, java.awt.event.MouseEvent e) {
        if (currentHandle != null) {
            currentHandle.dragInteraction(i, j, e, view);
            return;
        }

        if (selMode) {
            sx1 = e.getX();
            sy1 = e.getY();
            Rectangle selRectangle = new Rectangle(min(sx0, sx1), min(sy0, sy1), abs(sx1 - sx0), abs(sy1 - sy0));

            Set<Figure> sel = view.getModel().getFigures().filter(f -> contains(selRectangle, f.getBounds()))
                    .collect(Collectors.toSet());
            view.setSelectionRubberBand(selRectangle);
            view.clearSelection();
            for (Figure f : sel) {
                view.addToSelection(f);
            }
            view.repaint();
            return;
        }

        int k = i - tempX;
        int l = j - tempY;

        for (Figure f : view.getSelection()) {
            f.move(k, l);
            view.getModel().getDrawCommandHandler().addCommand(new MoveCommand(f, k, l));
        }

        tempX = i;
        tempY = j;
        view.repaint();
    }

    @Override
    public void mouseUp(int i, int j, MouseEvent e) {
        if (currentHandle != null) {
            currentHandle.stopInteraction(i, j, e, view);
            currentHandle = null;
            return;
        }

        if (selMode) {
            // Finalize rubber band selection
            selMode = false;
            view.setSelectionRubberBand(null);
            view.repaint();
        }

        // Handle deselection of figures when clicking with Shift key
        if (e.isShiftDown() && e.getX() == originalX && e.getY() == originalY && lastSelectedFigure == null) {
            List<Figure> figures = view.getModel().getFigures().collect(Collectors.toList());
            Collections.reverse(figures);
            for (Figure f : figures) {
                if (f.contains(originalX, originalY)) {
                    if (view.getSelection().contains(f)) {
                        view.removeFromSelection(f);
                        view.repaint();
                    }
                    break;
                }
            }
        }
    }

    @Override
    public Icon getIcon() {
        return new ImageIcon(getClass().getResource(IMAGES + "selection.png"));
    }

    @Override
    public String getName() {
        return "Selection";
    }
}
