package jdraw.figures;

import jdraw.framework.DrawContext;
import jdraw.framework.Figure;

/**
 * A drawing tool for creating line figures in the JDraw application.
 * This tool extends {@link AbstractDrawTool} and implements the creation of line figures.
 * When the user interacts with the drawing canvas, an oval is created and added to the drawing model.
 *
 * @see AbstractDrawTool
 */
public final class LineTool extends AbstractDrawTool {

    /**
     * Constructs a new LineTool with the specified drawing context.
     *
     * @param context the drawing context in which this tool operates
     */
    public LineTool(DrawContext context) {
        super(context, "Line", "line.png");
    }

    /**
     * Creates a new {@link Line} figure at the specified position.
     *
     * @param x the x-coordinate at which the line is to be created
     * @param y the y-coordinate at which the line is to be created
     * @return a new Line
     */
    @Override
    protected Figure createFigureAt(int x, int y) {
        return new Line(x, y);
    }
}
