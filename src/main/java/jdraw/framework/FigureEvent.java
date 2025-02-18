/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

/**
 * The FigureEvent class represents a change which occured in a figure within
 * the JDraw application. Such changes can include modifications to the figure's
 * position, size, or other attributes. This event is used to notify listeners
 * that a figure has been updated, allowing the application to respond
 * appropriately, such as by re-rendering the figure or updating the user
 * interface.
 * 
 * This class encapsulates the source figure that triggered the event, providing
 * a reference to the figure that has changed.
 * 
 * @see Figure
 * @see FigureListener
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public class FigureEvent {

    /**
     * The figure that triggered the event. This figure has undergone a change,
     * prompting the creation of the event for listener notification.
     */
    private final Figure source;

    /**
     * Constructs a new FigureEvent with the specified figure as the source of the
     * event.
     * 
     * @param source the figure that has changed, prompting this event
     */
    public FigureEvent(Figure source) {
        this.source = source;
    }

    /**
     * Returns the figure that triggered this event, providing context for listeners
     * about which figure has changed.
     * 
     * @return the figure that has changed
     */
    public Figure getFigure() {
        return source;
    }
}
