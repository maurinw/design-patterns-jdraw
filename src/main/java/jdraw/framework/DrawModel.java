/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

import java.util.stream.Stream;

/**
 * The DrawModel interface defines the contract for managing the collection of
 * figures in a drawing. It represents the data model in the JDraw application,
 * storing all figures within a graphic and providing methods for manipulating
 * these figures. Every draw view is associated with a draw model.
 * 
 * The draw model supports operations for adding, removing, and organizing
 * figures, as well as notifying listeners of any changes. It maintains figures
 * in insertion order, which the view interprets as "back-to-front" when
 * rendering.
 * 
 * @see DrawView
 * @see Figure
 * @see DrawModelListener
 * @see DrawCommandHandler
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface DrawModel {

    /**
     * Adds a new figure to the draw model.
     * 
     * @param f the figure to be added to the draw model.
     */
    void addFigure(Figure f);

    /**
     * Removes the specified figure from the draw model. If the figure is not
     * present in the model, no action is taken.
     * 
     * @param f the figure to be removed from the draw model.
     */
    void removeFigure(Figure f);

    /**
     * Removes all figures from the draw model. This method is typically used when
     * loading a new drawing from a file, allowing the model to clear existing
     * figures before loading new ones.
     */
    void removeAllFigures();

    /**
     * Returns a sequential {@code Stream} of figures contained in this draw model.
     * The stream provides a way to iterate over all figures in the model in their
     * insertion order, which is interpreted by the view as "back-to-front."
     * 
     * @return a sequential {@code Stream} over the figures in this draw model.
     */
    Stream<? extends Figure> getFigures();

    /**
     * Adds a model listener to this draw model. The listener will be notified of
     * any changes to the model, such as when figures are added, removed, or
     * reordered. The order in which the model events will be delivered to multiple
     * listeners is not specified.
     * 
     * Duplicate listeners are not added, and if the listener is null, no action is
     * performed, i.e. no exception is thrown and the set of listeners is not
     * changed.
     * 
     * @param listener the draw model listener to be added.
     * @see DrawModelListener
     */
    void addModelChangeListener(DrawModelListener listener);

    /**
     * Removes the specified model listener from this draw model. After removal, the
     * listener will no longer receive notifications about changes to the model. If
     * the listener was not previously added or is null, no action is performed.
     * 
     * @param listener the draw model listener to be removed.
     * @see DrawModelListener
     */
    void removeModelChangeListener(DrawModelListener listener);

    /**
     * Returns the draw command handler associated with this model. The command
     * handler is responsible for managing undo and redo operations on figures
     * within the model.
     * 
     * @return the draw command handler used by this model.
     * @see DrawCommandHandler
     */
    DrawCommandHandler getDrawCommandHandler();

    /**
     * Sets the index of the specified figure within the model. Changing a figure's
     * index affects its position relative to other figures. The order of the other
     * figures in the model remains unchanged. If the figure is moved to a new
     * index, a <code>DRAWING_CHANGED</code> event is sent to all registered model
     * listeners.
     * 
     * @param f     the figure whose index is to be set.
     * @param index the new position index for the figure. Other figures are
     *              adjusted accordingly.
     * 
     * @throws IllegalArgumentException  if the figure is not contained in the
     *                                   model.
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   (<code>index &lt; 0 || index &ge; size()</code>),
     *                                   where size() is the number of figures in
     *                                   the model.
     */
    void setFigureIndex(Figure f, int index) throws IllegalArgumentException, IndexOutOfBoundsException;
}
