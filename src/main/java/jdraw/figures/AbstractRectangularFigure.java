package jdraw.figures;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import jdraw.figures.handles.Handle;
import jdraw.figures.handles.NEState;
import jdraw.figures.handles.NWState;
import jdraw.figures.handles.SEState;
import jdraw.figures.handles.SWState;
import jdraw.figures.handles.NState;
import jdraw.figures.handles.EState;
import jdraw.figures.handles.SState;
import jdraw.figures.handles.WState;
import jdraw.figures.handles.SwappableFigure;
import jdraw.framework.FigureHandle;

/**
 * Base class for rectangular figures.
 */
public abstract class AbstractRectangularFigure extends AbstractFigure implements SwappableFigure {

    /** Rectangle bounds. */
    private final Rectangle rectangle;

    private final Handle sw = new Handle(new SWState(this));
    private final Handle se = new Handle(new SEState(this));
    private final Handle nw = new Handle(new NWState(this));
    private final Handle ne = new Handle(new NEState(this));
    private final Handle n = new Handle(new NState(this));
    private final Handle e = new Handle(new EState(this));
    private final Handle s = new Handle(new SState(this));
    private final Handle w = new Handle(new WState(this));
    private final List<FigureHandle> handles = List.of(sw, se, nw, ne, n, e, s, w);

    /**
     * Constructs a rectangular figure.
     *
     * @param x x-coordinate of top-left
     * @param y y-coordinate of top-left
     */
    protected AbstractRectangularFigure(int x, int y) {
        this.rectangle = new Rectangle(x, y, 0, 0);
    }

    /** Moves the figure. */
    @Override
    public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            rectangle.translate(dx, dy);
            notifyObservers();
        }
    }

    /** Sets new bounds. */
    @Override
    public void setBounds(Point origin, Point corner) {
        Rectangle original = new Rectangle(rectangle);
        rectangle.setFrameFromDiagonal(origin, corner);
        if (!original.equals(rectangle)) {
            notifyObservers();
        }
    }

    /** Returns a copy of the bounds. */
    @Override
    public Rectangle getBounds() {
        return rectangle.getBounds();
    }

    /** Checks if a point is inside the figure. */
    @Override
    public boolean contains(int x, int y) {
        return rectangle.contains(x, y);
    }

    @Override
    public List<FigureHandle> getHandles() {
        return handles;
    }

    @Override
    public void swapVertical() {
        for (FigureHandle figureHandle : handles) {
            Handle handle = (Handle) figureHandle; // wegen setState und getState
            handle.setState(handle.getState().swapVertical());
        }
    }

    @Override
    public void swapHorizontal() {
        for (FigureHandle figureHandle : handles) {
            Handle handle = (Handle) figureHandle;
            handle.setState(handle.getState().swapHorizontal());
        }
    }
}
