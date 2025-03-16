package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;

/**
 * A drawing tool for creating rectangle figures in the JDraw application.
 * This tool extends {@link AbstractDrawTool} and implements the creation of rectangle figures.
 * When the user clicks and drags on the canvas, a rectangle is created and added to the drawing model.
 *
 * @see AbstractDrawTool
 */
public final class RectTool extends AbstractDrawTool {

    /**
     * Constructs a new RectTool with the specified drawing context.
     *
     * @param context the drawing context in which this tool operates
     */
    public RectTool(DrawContext context) {
        super(context, "Rectangle", "rectangle.png");
    }

    /**
     * Creates a new {@link Rect} figure at the specified position.
     *
     * @param x the x-coordinate at which the rectangle is to be created
     * @param y the y-coordinate at which the rectangle is to be created
     * @return a new Rect with initial width and height of zero
     */
    @Override
    protected Figure createFigureAt(int x, int y) {
        return new Rect(x, y, 0, 0);
    }
}
