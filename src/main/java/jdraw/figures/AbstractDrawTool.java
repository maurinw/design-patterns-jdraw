package jdraw.figures;

import java.awt.Cursor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.Figure;

/**
 * Base class for drawing tools.
 */
public abstract class AbstractDrawTool implements DrawTool {

    /** Path to icon images. */
    private static final String IMAGES = "/images/";

    /** Drawing context. */
    private final DrawContext context;

    /** Tool name. */
    private final String name;

    /** Icon filename. */
    private final String iconName;

    /** Figure being created. */
    private Figure newFigure = null;

    /** Anchor point from mouse press. */
    private Point anchor = null;

    /**
     * Constructs a tool.
     *
     * @param context  drawing context
     * @param name     tool name
     * @param iconName icon filename
     */
    protected AbstractDrawTool(DrawContext context, String name, String iconName) {
        this.context = context;
        this.name = name;
        this.iconName = iconName;
    }

    /** Clears the status text. */
    @Override
    public void deactivate() {
        this.context.showStatusText("");
    }

    /** Sets the status text to tool mode. */
    @Override
    public void activate() {
        this.context.showStatusText(name + " Mode");
    }

    /** Returns a crosshair cursor. */
    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

    /** Returns the tool's icon. */
    @Override
    public Icon getIcon() {
        return new ImageIcon(getClass().getResource(IMAGES + iconName));
    }

    /** Returns the tool name. */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Updates the figure bounds while dragging.
     *
     * @param x current x-coordinate
     * @param y current y-coordinate
     * @param e mouse event
     */
    @Override
    public void mouseDrag(int x, int y, MouseEvent e) {
        newFigure.setBounds(anchor, new Point(x, y));
        java.awt.Rectangle r = newFigure.getBounds();
        this.context.showStatusText("w: " + r.width + ", h: " + r.height);
    }

    /**
     * Ends the figure creation on mouse release.
     *
     * @param x x-coordinate of release
     * @param y y-coordinate of release
     * @param e mouse event
     */
    @Override
    public void mouseUp(int x, int y, MouseEvent e) {
        newFigure = null;
        anchor = null;
        this.context.showStatusText(name + " Mode");
    }

    /**
     * Starts a new figure on mouse press.
     *
     * @param x x-coordinate of press
     * @param y y-coordinate of press
     * @param e mouse event
     */
    @Override
    public void mouseDown(int x, int y, MouseEvent e) {
        if (newFigure != null) {
            throw new IllegalStateException("A figure is already in creation.");
        }
        anchor = new Point(x, y);
        newFigure = createFigureAt(x, y);
        context.getView().getModel().addFigure(newFigure);
    }

    /**
     * Creates a figure at the given position.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return new figure
     */
    protected abstract Figure createFigureAt(int x, int y);
}
