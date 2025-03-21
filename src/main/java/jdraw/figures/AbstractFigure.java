package jdraw.figures;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

/**
 * Base class for figures.
 */
public abstract class AbstractFigure implements Figure {

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

    /** Returns the figure handles (not implemented). */
    @Override
    public List<? extends FigureHandle> getHandles() {
        return null;
    }

}
