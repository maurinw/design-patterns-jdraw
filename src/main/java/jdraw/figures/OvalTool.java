package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;

/**
 * Tool for drawing ovals.
 */
public final class OvalTool extends AbstractDrawTool {

    /**
     * Constructs the oval tool.
     *
     * @param context the drawing context
     */
    public OvalTool(DrawContext context) {
        super(context, "Oval", "oval.png");
    }

    /**
     * Creates a new oval figure.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return a new oval
     */
    @Override
    protected Figure createFigureAt(int x, int y) {
        return new Oval(x, y, 0, 0);
    }
}
