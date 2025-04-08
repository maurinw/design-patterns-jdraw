package jdraw.figures;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jdraw.figures.handles.EState;
import jdraw.figures.handles.Handle;
import jdraw.figures.handles.NEState;
import jdraw.figures.handles.NState;
import jdraw.figures.handles.NWState;
import jdraw.figures.handles.SEState;
import jdraw.figures.handles.SState;
import jdraw.figures.handles.SWState;
import jdraw.figures.handles.SwappableFigure;
import jdraw.figures.handles.WState;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

/**
 * Base class for figures.
 */
public abstract class AbstractFigure implements SwappableFigure {

    private final Handle sw = new Handle(new SWState(this));
    private final Handle se = new Handle(new SEState(this));
    private final Handle nw = new Handle(new NWState(this));
    private final Handle ne = new Handle(new NEState(this));
    private final Handle n = new Handle(new NState(this));
    private final Handle e = new Handle(new EState(this));
    private final Handle s = new Handle(new SState(this));
    private final Handle w = new Handle(new WState(this));
    private final List<FigureHandle> handles = List.of(sw, se, nw, ne, n, e, s, w);

    /** List of listeners. */
    private final List<FigureListener> observers = new CopyOnWriteArrayList<>();

    /** Adds a listener. */
    @Override
    public void addFigureListener(FigureListener listener) {
        if (listener != null && !observers.contains(listener)) {
            observers.add(listener);
        }
    }

    /** Removes a listener. */
    @Override
    public void removeFigureListener(FigureListener listener) {
        observers.remove(listener);
    }

    /** Notifies all listeners of a change. */
    protected void notifyObservers() {
        FigureEvent figureEvent = new FigureEvent(this);
        for (FigureListener figureListener : observers) {
            figureListener.figureChanged(figureEvent);
        }
    }

    /** Clones the figure (not implemented). */
    @Override
    public Figure clone() {
        return null;
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
