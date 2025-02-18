/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

/**
 * The FigureListener interface defines the contract for listeners that are
 * interested in receiving notifications about changes to figures in the JDraw
 * application. Implementing this interface allows objects to react to
 * modifications in figures, such as changes in size, position, or other
 * properties, by handling the associated events.
 * 
 * This interface supports the observer pattern, enabling the decoupling of
 * figure objects from the components that respond to their changes, such as
 * views or other parts of the application that need to stay updated with the
 * figure's state.
 * 
 * @see FigureEvent
 * @see Figure
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
@FunctionalInterface
public interface FigureListener {

    /**
     * Called when a figure has changed. This method is triggered in response to
     * events that indicate modifications to a figure's properties, such as its
     * position, size, or appearance. Implementers should define the actions to take
     * when such changes occur.
     * 
     * @param e the event describing the change in the figure
     */
    void figureChanged(FigureEvent e);
}
