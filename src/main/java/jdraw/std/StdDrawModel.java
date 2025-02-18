/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.std;

import java.util.stream.Stream;

import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawModelListener;
import jdraw.framework.Figure;

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
 * @author TODO add your name here
 */
public class StdDrawModel implements DrawModel {

    @Override
    public void addFigure(Figure f) {
        // TODO: Implement the logic to add a figure to the model and notify listeners
        System.out.println("StdDrawModel.addFigure has to be implemented");
    }

    @Override
    public Stream<Figure> getFigures() {
        // TODO: Implement the logic to return all figures in the model
        System.out.println("StdDrawModel.getFigures has to be implemented");
        return Stream.empty(); // Placeholder to ensure the application starts, replace with actual
                               // implementation
    }

    @Override
    public void removeFigure(Figure f) {
        // TODO: Implement the logic to remove a figure from the model and notify
        // listeners
        System.out.println("StdDrawModel.removeFigure has to be implemented");
    }

    @Override
    public void addModelChangeListener(DrawModelListener listener) {
        // TODO: Implement the logic to add a listener to the model.
        System.out.println("StdDrawModel.addModelChangeListener has to be implemented");
    }

    @Override
    public void removeModelChangeListener(DrawModelListener listener) {
        // TODO: Implement the logic to remove a listener from the model.
        System.out.println("StdDrawModel.removeModelChangeListener has to be implemented");
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
        // TODO: Implement the logic for setting the figure's index in the model.
        System.out.println("StdDrawModel.setFigureIndex has to be implemented");
    }

    @Override
    public void removeAllFigures() {
        // TODO: Implement the logic to remove all figures from the model.
        System.out.println("StdDrawModel.removeAllFigures has to be implemented");
    }
}
