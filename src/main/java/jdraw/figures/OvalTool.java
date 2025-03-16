package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;

/**
 * A drawing tool for creating oval figures in the JDraw application.
 * This tool extends {@link AbstractDrawTool} and implements the creation of oval figures.
 * When the user interacts with the drawing canvas, an oval is created and added to the drawing model.
 *
 * @see AbstractDrawTool
 */
public final class OvalTool extends AbstractDrawTool {

    /**
     * Constructs a new OvalTool with the specified drawing context.
     *
     * @param context the drawing context in which this tool operates
     */
    public OvalTool(DrawContext context) {
        super(context, "Oval", "oval.png");
    }

    /**
     * Creates a new {@link Oval} figure at the specified position.
     *
     * @param x the x-coordinate at which the oval is to be created
     * @param y the y-coordinate at which the oval is to be created
     * @return a new Oval with initial width and height of zero
     */
    @Override
    protected Figure createFigureAt(int x, int y) {
        return new Oval(x, y, 0, 0);
    }
}
