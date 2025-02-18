/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

/**
 * The DrawModelEvent class represents an event that indicates changes in a draw
 * model. This event is triggered whenever figures are added, removed, or
 * modified within the model, such as changes to their size, position, or other
 * attributes. Additionally, it handles events where multiple figures are
 * affected, like when the entire drawing is cleared or the order of figures is
 * changed.
 * 
 * This event provides crucial information for listeners to react accordingly,
 * ensuring that the user interface remains synchronized with the state of the
 * draw model.
 * 
 * @see DrawModelListener
 * @see FigureListener
 * @see DrawModel
 * @see Figure
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public class DrawModelEvent {

    /**
     * The Type enum defines the different types of events that can occur within a
     * draw model and that can be detected and handled by listeners.
     */
    public enum Type {
        /**
         * Indicates that a figure was added to the draw model.
         */
        FIGURE_ADDED,

        /**
         * Indicates that a figure was removed from the draw model.
         */
        FIGURE_REMOVED,

        /**
         * Indicates that a figure's position, size, or other attributes were changed in
         * the draw model.
         */
        FIGURE_CHANGED,

        /**
         * Indicates that the entire drawing was cleared, meaning all figures were
         * removed from the draw model at once.
         */
        DRAWING_CLEARED,

        /**
         * Indicates that multiple changes occurred, such as when the order of figures
         * is altered. This event suggests that the entire scene needs to be redrawn,
         * but that no figures were added or removed.
         */
        DRAWING_CHANGED
    }

    /**
     * The draw model that triggered the event. This represents the source of the
     * change and allows listeners to identify which model the event pertains to.
     */
    private final DrawModel source;

    /**
     * The figure associated with the event, if applicable. For events like
     * <code>DRAWING_CLEARED</code> or <code>DRAWING_CHANGED</code>, this field will
     * be <code>null</code> since no single figure is directly involved.
     */
    private final Figure figure;

    /**
     * The actual type of the event that is being reported. See the enum declared in
     * this class for details.
     */
    private final Type type;

    /**
     * Constructs a new DrawModelEvent with the specified model, affected figure,
     * and event type.
     * 
     * @param source the draw model that triggered the event
     * @param figure the figure associated with the event, or <code>null</code> if
     *               the event affects multiple figures or none specifically
     * @param type   the type of event, indicating the nature of the change
     */
    public DrawModelEvent(DrawModel source, Figure figure, Type type) {
        this.source = source;
        this.figure = figure;
        this.type = type;
    }

    /**
     * Returns the draw model that has changed, which serves as the source of this
     * event.
     * 
     * @return the model that triggered the event
     */
    public DrawModel getModel() {
        return source;
    }

    /**
     * Returns the figure associated with this event. For events that affect the
     * entire drawing, such as <code>DRAWING_CLEARED</code> or
     * <code>DRAWING_CHANGED</code>, this method returns <code>null</code>.
     * 
     * @return the affected figure, or <code>null</code> if the event does not
     *         pertain to a single figure
     */
    public Figure getFigure() {
        return figure;
    }

    /**
     * Returns the type of the event, indicating the specific change that occurred
     * in the draw model.
     * 
     * @return the type of event
     */
    public Type getType() {
        return type;
    }
}
