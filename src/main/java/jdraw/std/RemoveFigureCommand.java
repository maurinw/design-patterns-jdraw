/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.std;

import java.util.List;
import java.util.stream.Collectors;

import jdraw.framework.DrawCommand;
import jdraw.framework.DrawModel;
import jdraw.framework.Figure;

/**
 * The RemoveFigureCommand class implements the DrawCommand interface to provide
 * the functionality for removing a figure from the drawing model in the JDraw
 * application. This command supports undo and redo operations, allowing the
 * removal of a figure to be reversed or reapplied as needed.
 * 
 * The command captures the figure to be removed, the model from which it is
 * removed, and the figure's original index in the model to ensure that it can
 * be restored to its correct position upon undo.
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 */

public class RemoveFigureCommand implements DrawCommand {
    private static final long serialVersionUID = 9121230304586234374L;

    /** The drawing model from which the figure is removed. */
    private final DrawModel model;
    /** The figure to be removed from the model. */
    private final Figure figure;
    /** The index of the figure in the model before removal, used for undoing. */
    private final int index;

    /**
     * Constructs a RemoveFigureCommand for removing a specific figure from the
     * specified drawing model. The command records the figure and its position in
     * the model to support undo functionality.
     * 
     * @param model  the drawing model from which to remove the figure
     * @param figure the figure to be removed
     * @throws IllegalArgumentException if the figure is not currently contained in
     *                                  the model. This implies that the command
     *                                  must be created _before_ the figure is
     *                                  removed from the model.
     */
    public RemoveFigureCommand(DrawModel model, Figure figure) {
        this.model = model;
        this.figure = figure;

        List<Figure> figures = model.getFigures().collect(Collectors.toList());
        index = figures.indexOf(figure);
        if (index == -1) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Executes the removal of the figure from the model. This method will
     * effectively remove the figure if it has not already been removed.
     */
    @Override
    public void redo() {
        model.removeFigure(figure);
    }

    /**
     * Reverts the removal of the figure by adding it back to the model at its
     * original position.
     */
    @Override
    public void undo() {
        model.addFigure(figure);
        model.setFigureIndex(figure, index);
    }
}
