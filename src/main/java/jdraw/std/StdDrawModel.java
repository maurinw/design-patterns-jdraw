/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.std;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawModelEvent;
import jdraw.framework.DrawModelListener;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;
import jdraw.framework.DrawModelEvent.Type;

/**
 * The StdDrawModel class provides a standard implementation of the DrawModel
 * interface for the JDraw application. This class is intended as a template for
 * students to complete as part of their course assignments. It currently
 * contains placeholder methods that need to be properly implemented.
 * 
 * This class manages the collection of figures in a drawing, supports the
 * addition and removal of figures, and handles the registration of listeners to
 * respond to changes in the model. Additionally, it provides access to a
 * DrawCommandHandler for managing undo and redo operations.
 * 
 * Students are expected to implement the methods to fully comply with the
 * DrawModel interface, including maintaining the correct order of figures,
 * notifying listeners of changes, and integrating with the command handler for
 * undo/redo functionality.
 * 
 * @author Maurin Wirth
 */
public class StdDrawModel implements DrawModel, FigureListener {

    private List<DrawModelListener> listeners;
    private List<Figure> figures;

    public StdDrawModel(){
        listeners = new ArrayList<DrawModelListener>();
        figures = new ArrayList<Figure>();
    }

    private void notifyListeners(Figure f, Type t) {
        for(DrawModelListener listener : listeners) {
            listener.modelChanged(new DrawModelEvent(this, f, t));
        }
    }

    @Override
    public void addFigure(Figure f) {
        if (figures.add(f)) {
            f.addFigureListener(this);
            notifyListeners(f, Type.FIGURE_ADDED);
        }
    }

    @Override
    public void removeFigure(Figure f) {
        if (figures.remove(f)) {
            notifyListeners(f, Type.FIGURE_REMOVED);
            f.removeFigureListener(this);
        }
    }

    @Override
    public Stream<Figure> getFigures() {
        return figures.stream();
                               
    }

    @Override
    public void addModelChangeListener(DrawModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeModelChangeListener(DrawModelListener listener) {
        listeners.remove(listener);
    }

    /** The draw command handler. Initialized here with a dummy implementation. */
    // TODO: Initialize with the appropriate implementation for undo/redo
    // functionality.
    private DrawCommandHandler handler = new EmptyDrawCommandHandler();

    /**
     * Retrieve the draw command handler in use.
     * 
     * @return the draw command handler.
     */
    @Override
    public DrawCommandHandler getDrawCommandHandler() {
        return handler;
    }

    @Override
    public void setFigureIndex(Figure f, int index) {
        
    }

    @Override
    public void removeAllFigures() {
        figures.clear();
        notifyListeners(null, Type.DRAWING_CLEARED);
    }

    @Override
    public void figureChanged(FigureEvent e) {
        notifyListeners(e.getFigure(), DrawModelEvent.Type.FIGURE_CHANGED);
    }
}
