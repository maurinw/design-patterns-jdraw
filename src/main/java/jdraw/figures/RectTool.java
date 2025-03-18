package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;

/**
 * Tool for drawing rectangles.
 */
public final class RectTool extends AbstractDrawTool {

    /**
     * Constructs the rectangle tool.
     *
     * @param context drawing context
     */
    public RectTool(DrawContext context) {
        super(context, "Rectangle", "rectangle.png");
    }

    /**
     * Creates a new rectangle.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return new rectangle
     */
    @Override
    protected Figure createFigureAt(int x, int y) {
        return new Rect(x, y, 0, 0);
    }
}
