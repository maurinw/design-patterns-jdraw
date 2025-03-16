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
 * An abstract base class for drawing tools in the JDraw application.
 * This class provides a common implementation for tools that create figures
 * in a drawing context. 
 *
 * @see jdraw.framework.DrawTool
 */
public abstract class AbstractDrawTool implements DrawTool {

    /**
     * The path to the image resources used for tool icons.
     */
    private static final String IMAGES = "/images/";

    /**
     * The drawing context in which this tool operates.
     */
    private final DrawContext context;

    /**
     * The name of the tool.
     */
    private final String name;

    /**
     * The filename of the icon representing this tool.
     */
    private final String iconName;

    /**
     * Temporary variable that holds the figure currently being created. It is used during
     * the process between the mouse press and release.
     */
    private Figure newFigure = null;

    /**
     * Temporary variable that holds the anchor point where the mouse was initially pressed.
     * This point defines one corner of the figure being created.
     */
    private Point anchor = null;

    /**
     * Constructs a new tool with the specified drawing context, name, and icon.
     *
     * @param context  the drawing context in which this tool operates
     * @param name     the name of the tool (figure)
     * @param iconName the filename of the icon for the tool (figure)
     */
    protected AbstractDrawTool(DrawContext context, String name, String iconName) {
        this.context = context;
        this.name = name;
        this.iconName = iconName;
    }

    /**
     * Deactivates the drawing tool by clearing the status bar message.
     * This method is called when the tool is no longer active.
     *
     * @see jdraw.framework.DrawTool#deactivate()
     */
    @Override
    public void deactivate() {
        this.context.showStatusText("");
    }

    /**
     * Activates the drawing tool and updates the status bar to indicate that the tool is active.
     * This method is called when the tool becomes active.
     *
     * @see jdraw.framework.DrawTool#activate()
     */
    @Override
    public void activate() {
        this.context.showStatusText(name + " Mode");
    }

    /**
     * Returns the cursor to be used when this tool is active.
     * The default cursor is a crosshair, which indicates drawing mode.
     *
     * @return a crosshair cursor for drawing mode
     */
    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    }

    /**
     * Returns the icon associated with this tool.
     * The icon is loaded from the resource path defined by {@code IMAGES} and the specified icon name.
     *
     * @return the tool's icon for display in the user interface
     */
    @Override
    public Icon getIcon() {
        return new ImageIcon(getClass().getResource(IMAGES + iconName));
    }

    /**
     * Returns the name of this tool.
     * The name is used for identification purposes in the user interface.
     *
     * @return the name of the tool (e.g. "Rectangle")
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Handles the mouse drag event by updating the bounds of the figure being created.
     * As the mouse is dragged, the figure is resized based on the current mouse coordinates.
     * The status bar is updated to display the current width and height of the figure.
     *
     * @param x the current x-coordinate of the mouse
     * @param y the current y-coordinate of the mouse
     * @param e the mouse event containing additional details (e.g. modifier keys)
     * @see jdraw.framework.DrawTool#mouseDrag(int, int, MouseEvent)
     */
    @Override
    public void mouseDrag(int x, int y, MouseEvent e) {
        newFigure.setBounds(anchor, new Point(x, y));
        java.awt.Rectangle r = newFigure.getBounds();
        this.context.showStatusText("w: " + r.width + ", h: " + r.height);
    }

    /**
     * Finalizes the creation of the figure when the mouse button is released.
     * This method resets the temporary variables and updates the status bar to indicate that
     * the tool is still in its drawing mode.
     *
     * @param x the x-coordinate of the mouse release
     * @param y the y-coordinate of the mouse release
     * @param e the mouse event containing additional details (e.g. modifier keys)
     * @see jdraw.framework.DrawTool#mouseUp(int, int, MouseEvent)
     */
    @Override
    public void mouseUp(int x, int y, MouseEvent e) {
        newFigure = null;
        anchor = null;
        this.context.showStatusText(name + " Mode");
    }

    /**
     * Initiates the creation of a new figure when the mouse is pressed.
     * This method sets the anchor point to the location of the mouse press, creates a new figure,
     * and adds it to the drawing model.
     *
     * @param x the x-coordinate of the mouse press
     * @param y the y-coordinate of the mouse press
     * @param e the mouse event containing additional details (e.g. modifier keys)
     * @throws IllegalStateException if a figure is already being created
     * @see jdraw.framework.DrawTool#mouseDown(int, int, MouseEvent)
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
     * Creates a new figure at the specified position.
     * Subclasses must implement this method to create a specific type of figure.
     *
     * @param x the x-coordinate at which the figure is to be created
     * @param y the y-coordinate at which the figure is to be created
     * @return a new {@code Figure} instance at the specified location
     */
    protected abstract Figure createFigureAt(int x, int y);
}
