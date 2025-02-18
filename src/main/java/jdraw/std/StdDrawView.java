/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.std;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawModelEvent;
import jdraw.framework.DrawModelListener;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;
import jdraw.framework.DrawGrid;

/**
 * The StdDrawView class is the standard implementation of the DrawView
 * interface for the JDraw application. It serves as the primary component for
 * visualizing the drawing model, handling user interactions, and managing
 * figure selections within the editor. This class integrates the
 * model-view-controller (MVC) architecture by acting as the view component,
 * rendering figures, responding to mouse and keyboard inputs, and maintaining a
 * selection of figures.
 * 
 * StdDrawView supports functionalities such as grid alignment, figure
 * selection, resizing, and manipulation through mouse events. It also handles
 * the interaction with drawing tools, applying actions like moving or deleting
 * figures in response to user input.
 * 
 * This class is designed to be used as part of the graphical user interface
 * within a JDraw application, encapsulating the core behaviors needed for
 * interactive drawing.
 * 
 * @see DrawView
 * @see DrawModel
 * @see Figure
 * @see FigureHandle
 * @see DrawContext
 * @see DrawGrid
 * 
 * @author Dominik Gruntz
 * @version 2.0
 */
@SuppressWarnings("serial")
public final class StdDrawView extends JComponent implements DrawView {
    /** Space in pixels around the minimal bounding box of all figures. */
    private static final int BOUNDING_BOX_PADDING = 10;

    /**
     * The drawing model associated with this view, containing all figures to be
     * visualized.
     */
    private final DrawModel model;
    /**
     * The drawing context, which provides access to tools and manages user
     * interactions.
     */
    private DrawContext context;
    /** The grid used for aligning figures within the view, if applicable. */
    private DrawGrid grid;
    /** The list of currently selected figures in the view. */
    private final List<Figure> selection = new LinkedList<>();
    /**
     * The handles associated with the currently selected figures, used for
     * manipulation.
     */
    private final List<FigureHandle> handles = new LinkedList<>();
    /**
     * Listener for model changes, ensuring the view updates in response to
     * modifications.
     */
    private final DrawModelListener ml;

    /**
     * Indicates whether a mouse drag interaction is active. When dragging is
     * active, keyboard-based moving or deleting of figures is disabled.
     */
    private boolean dragging = false;

    /**
     * Creates a new StdDrawView with the specified drawing model. The view will
     * visualize the figures in the model and respond to user interactions according
     * to the configured drawing context and grid settings.
     * 
     * @param model the model that this view will visualize
     */
    public StdDrawView(DrawModel model) {

        this.model = model;

        ml = e -> {
            Dimension size = getPreferredSize();
            setPreferredSize(size);
            revalidate();

            if (e.getType() == DrawModelEvent.Type.FIGURE_REMOVED) {
                removeFromSelection(e.getFigure());
            }
            if (e.getType() == DrawModelEvent.Type.DRAWING_CLEARED) {
                clearSelection();
            }

// If a figure was added or removed, we could restrict the area to be repainted, but the
// problem is that this area is not known as long as the size of the handles is not known.
//            if (e.getType() == DrawModelEvent.Type.FIGURE_ADDED
//             || e.getType() == DrawModelEvent.Type.FIGURE_REMOVED) {
//                // here the bounds of all the handles has to be added to
//                // e.getFigure().getBounds()
//                repaint(e.getFigure().getBounds());
//            } else {
//                repaint();
//            }
            repaint();
        };

        // Registers the model change listener to update the view when the model changes
        this.model.addModelChangeListener(ml);

        InputEventHandler ieh = new InputEventHandler();
        addMouseListener(ieh);
        addMouseMotionListener(ieh);
        addKeyListener(ieh);
    }

    /**
     * Closes the view, removing its listener from the drawing model to prevent
     * further updates. This method should be called when the view is no longer
     * needed to free resources and to avoid memory leaks.
     */
    @Override
    public void close() {
        model.removeModelChangeListener(ml);
    }

    @Override
    public DrawModel getModel() {
        return model;
    }

    // Grid Management
    // ===============
    @Override
    public void setGrid(DrawGrid grid) {
        if (this.grid != null) {
            this.grid.deactivate();
        }
        this.grid = grid;
        if (this.grid != null) {
            this.grid.activate();
        }
    }

    @Override
    public DrawGrid getGrid() {
        return grid;
    }

    /**
     * Constrains the given point according to the current grid's rules. This method
     * adjusts the point's coordinates to align with the grid, if a grid is active.
     * It also manages grid-related state changes based on the interaction mode.
     * 
     * @param p    the point to constrain to the grid
     * @param mode indicates the interaction mode: 1 for mousePressed, 0 for
     *             mouseDragged, and 2 for mouseReleased. This mode controls whether
     *             grid-specific methods like mouseDown and mouseUp are called.
     * @return a point adjusted to align with the grid, or the original point if no
     *         grid is set
     */
    private Point constrainPoint(Point p, int mode) {
        if (grid != null) {
            if (mode == 1) {
                grid.mouseDown();
            }
            p = grid.constrainPoint(p);
            if (mode == 2) {
                grid.mouseUp();
            }
        }
        return p;
    }

    /**
     * Paints the figures and handles within this view, using the provided graphics
     * context.
     * 
     * @param g the graphics context used for painting the figures and handles
     */
    @Override
    public void paintComponent(Graphics g) {
        // g.setColor(getBackground());
        // g.fillRect(0, 0, getWidth(), getHeight());
        model.getFigures().forEachOrdered(f -> f.draw(g));
        handles.forEach(fh -> fh.draw(g));

        if (selectionRectangle != null) {
            g.setColor(Color.BLACK);
            g.drawRect(selectionRectangle.x, selectionRectangle.y, selectionRectangle.width, selectionRectangle.height);
        }
    }

    // Selection Management
    // ====================
    @Override
    public List<Figure> getSelection() {
        return new LinkedList<>(selection);
    }

    @Override
    public void clearSelection() {
        selection.clear();
        handles.clear();
        repaint();
    }

    @Override
    public void addToSelection(Figure f) {
        context.setDefaultTool();
        if (!selection.contains(f)) {
            selection.add(f);
            List<? extends FigureHandle> hList = f.getHandles();
            if (hList != null) {
                handles.addAll(hList);
            }
        }
    }

    @Override
    public void removeFromSelection(Figure f) {
        if (selection.remove(f)) {
            handles.removeIf(h -> h.getOwner() == f);
        }
    }

    /** The current selection rectangle used for rubber band selection. */
    private Rectangle selectionRectangle;

    /**
     * Sets the selection rubber band rectangle, which visually indicates a
     * selection area during drag-select operations.
     * 
     * @param selRectangle the new selection rectangle
     */
    @Override
    public void setSelectionRubberBand(Rectangle selRectangle) {
        this.selectionRectangle = selRectangle;
    }

    // View Size Management
    // ====================
    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getPreferredSize() {
        Rectangle r = new Rectangle();
        model.getFigures().forEachOrdered(f -> r.add(f.getBounds()));

        Dimension size = new Dimension();
        size.height = r.height + r.y + BOUNDING_BOX_PADDING;
        size.width = r.width + r.x + BOUNDING_BOX_PADDING;
        return size;
    }

    @Override
    public FigureHandle getHandle(int x, int y, MouseEvent e) {
        for (FigureHandle fh : handles) {
            if (fh.contains(x, y)) {
                return fh;
            }
        }
        return null;
    }

    @Override
    public void setDrawContext(DrawContext context) {
        this.context = context;
    }

    @Override
    public DrawContext getContext() {
        return context;
    }

    /**
     * Handles all mouse and keyboard events for the StdDrawView, managing
     * interactions such as figure selection, movement, and keyboard shortcuts for
     * figure manipulation.
     * 
     * @author Christoph Denzler
     */
    private class InputEventHandler implements MouseListener, MouseMotionListener, KeyListener {
        // KeyListener
        // ===========

        // Checkstyle will complain about a too high cyclomatic complexity.
        // Switch statements inherently boost this metric but still present quite
        // readable code.
        // Unfortunately the cyclometric complexity check cannot be suppressed from
        // code.
        @Override
        public void keyPressed(KeyEvent e) {
            // Disable figure deletion and moving while a drag operation is active
            if (dragging) {
                return;
            }

            int code = e.getKeyCode();
            if (code == KeyEvent.VK_DELETE || code == KeyEvent.VK_BACK_SPACE) {
                model.getDrawCommandHandler().beginScript();
                for (Figure f : getSelection()) {
                    model.getDrawCommandHandler().addCommand(new RemoveFigureCommand(model, f));
                    model.removeFigure(f);
                    // as a consequence, the figure is also removed from the selection
                }
                model.getDrawCommandHandler().endScript();
                repaint();
            }

            // Handle arrow key movements for selected figures
            int dx = 0;
            int dy = 0;
            switch (code) {
            case KeyEvent.VK_LEFT:
                dx = (grid != null) ? -grid.getStepX(false) : -1;
                break;
            case KeyEvent.VK_RIGHT:
                dx = (grid != null) ? grid.getStepX(true) : +1;
                break;
            case KeyEvent.VK_UP:
                dy = (grid != null) ? -grid.getStepY(false) : -1;
                break;
            case KeyEvent.VK_DOWN:
                dy = (grid != null) ? grid.getStepY(true) : +1;
                break;
            default:
            }

            // Move the selected figures if arrow keys were pressed
            if (dx != 0 || dy != 0) {
                model.getDrawCommandHandler().beginScript();
                for (Figure figure : selection) {
                    figure.move(dx, dy);
                    model.getDrawCommandHandler().addCommand(new MoveCommand(figure, dx, dy));
                }
                model.getDrawCommandHandler().endScript();
            }
        }

        @Override
        public void keyReleased(KeyEvent keyevent) {
            // No action needed on key release
        }

        @Override
        public void keyTyped(KeyEvent keyevent) {
            // No action needed on key typed
        }

        // MouseListener
        // =============
        @Override
        public void mousePressed(MouseEvent e) {
            requestFocus();
            Point p = constrainPoint(new Point(e.getX(), e.getY()), 1);
            if (dragging) {
                // mouse was pressed during dragging, e.g. another mouse button.
                context.getTool().mouseDrag(p.x, p.y, e);
            } else if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
                model.getDrawCommandHandler().beginScript();
                context.getTool().mouseDown(p.x, p.y, e);
                dragging = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (dragging) {
                Point p = constrainPoint(new Point(e.getX(), e.getY()), 2);
                if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) == 0) {
                    dragging = false;
                    context.getTool().mouseUp(p.x, p.y, e);
                    model.getDrawCommandHandler().endScript();
                } else {
                    context.getTool().mouseDrag(p.x, p.y, e);
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // No action needed on mouse click
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // No action needed on mouse enter
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // No action needed on mouse exit
        }

        // MouseMotionListener
        // ===================
        @Override
        public void mouseDragged(MouseEvent e) {
            if (dragging) {
                Point p = constrainPoint(new Point(e.getX(), e.getY()), 0);
                context.getTool().mouseDrag(p.x, p.y, e);
                setCursor(context.getTool().getCursor());
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            for (FigureHandle h : handles) {
                if (h.contains(x, y)) {
                    StdDrawView.super.setCursor(h.getCursor());
                    return;
                }
            }
            setCursor(context.getTool().getCursor());
        }
    }
}
