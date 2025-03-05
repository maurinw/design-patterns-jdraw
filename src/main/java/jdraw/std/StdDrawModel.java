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
  * interface for the JDraw application.
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
         if (!figures.contains(f)) {
             figures.add(f);
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
     // TODO: Initialize with the appropriate implementation for undo/redo functionality.
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
         if (!figures.contains(f)) {
             throw new IllegalArgumentException("Figure not in model");
         }

         if (index < 0 || index >= figures.size()) {
             throw new IndexOutOfBoundsException("Index out of bounds");
         }
         int currentIndex = figures.indexOf(f);

         if (currentIndex == index) {
             notifyListeners(f, Type.DRAWING_CHANGED);
             return;
         }

         figures.remove(currentIndex);
         figures.add(index, f);
         notifyListeners(f, Type.DRAWING_CHANGED);
     }
 
     @Override
     public void removeAllFigures() {
         for (Figure f : figures) {
             f.removeFigureListener(this);
         }
         figures.clear();
         notifyListeners(null, Type.DRAWING_CLEARED);
     }
 
     @Override
     public void figureChanged(FigureEvent e) {
         notifyListeners(e.getFigure(), DrawModelEvent.Type.FIGURE_CHANGED);
     }
 }
 