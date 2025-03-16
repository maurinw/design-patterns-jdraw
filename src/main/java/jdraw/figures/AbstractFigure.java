package jdraw.figures;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

public abstract class AbstractFigure implements Figure{

    private final List<FigureListener> observers = new CopyOnWriteArrayList<>();

    @Override
    public void addFigureListener(FigureListener listener) {
        if (listener != null && !observers.contains(listener)) {
            observers.add(listener);
        }
    }

    @Override
    public void removeFigureListener(FigureListener listener) {
        observers.remove(listener);
    }

    protected void notifyObservers() {
        FigureEvent figureEvent = new FigureEvent(this);
        for (FigureListener figureListener : observers) {
            figureListener.figureChanged(figureEvent);
        }
    }

    @Override
    public Figure clone() {
        return null;
    }

    @Override
    public List<? extends FigureHandle> getHandles() {
        return null;
    }
}
