/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

/**
 * The DrawModelListener interface defines the contract for listeners that are
 * interested in receiving notifications about changes in the draw model.
 * Implementing classes will be notified whenever there are modifications to the
 * figures within the drawing model, such as additions, deletions, or updates.
 * 
 * This interface allows the implementation of observer patterns to react to
 * changes in the draw model, providing a mechanism for views or other
 * components to update accordingly in response to model state changes.
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
@FunctionalInterface
public interface DrawModelListener {

    /**
     * Called when the draw model changes. This method is triggered whenever the
     * model undergoes modifications, such as when figures are added, removed, or
     * altered. Implementations should define the specific actions to take in
     * response to these changes, such as updating the view or processing the
     * changes further.
     * 
     * @param event the event encapsulating details about the model change
     */
    void modelChanged(DrawModelEvent event);
}
