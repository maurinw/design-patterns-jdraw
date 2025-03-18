package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;

/**
 * Tool for drawing lines.
 */
public final class LineTool extends AbstractDrawTool {

    /**
     * Constructs the line tool.
     *
     * @param context the drawing context
     */
    public LineTool(DrawContext context) {
        super(context, "Line", "line.png");
    }

    /**
     * Creates a new line figure.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return a new line
     */
    @Override
    protected Figure createFigureAt(int x, int y) {
        return new Line(x, y);
    }
}
