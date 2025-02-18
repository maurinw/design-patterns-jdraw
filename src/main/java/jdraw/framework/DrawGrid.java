/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

import java.awt.Point;

/**
 * The DrawGrid interface defines the behavior of a grid system used to restrict
 * and align coordinates within the JDraw application. It provides methods to
 * constrain mouse input to predefined grid points, control movement steps for
 * selections, and manage the activation and deactivation of the grid system.
 * 
 * Implementations of this interface allow for customizable grid behaviors, such
 * as snapping figures to grid lines or defining specific step sizes for
 * keyboard-based movement. This interface is primarily used by the draw view to
 * enforce consistent alignment and movement rules across the drawing area.
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface DrawGrid {

    /**
     * Constrains the given point to align with the grid. This method adjusts the
     * provided mouse coordinates to the nearest valid grid point, which can help in
     * snapping figures to specific positions or maintaining alignment.
     * 
     * @param p the original mouse coordinates
     * @return the constrained coordinates adjusted to the nearest grid point
     */
    Point constrainPoint(Point p);

    /**
     * Returns the step size for horizontal movement when a selection is moved using
     * the arrow keys. The step size determines how far the selection moves
     * horizontally, ensuring consistent movement aligned with the grid.
     * 
     * @param right true if the selection is moved to the right, false if moved to
     *              the left
     * @return the positive step size in the horizontal direction
     */
    int getStepX(boolean right);

    /**
     * Returns the step size for vertical movement when a selection is moved using
     * the arrow keys. The step size defines how far the selection moves vertically,
     * aligning movements with the grid.
     * 
     * @param down true if the selection is moved down, false if moved up
     * @return the positive step size in the vertical direction
     */
    int getStepY(boolean down);

    /**
     * Activates the grid. This method is called when the grid is set by calling
     * method setGrid on a draw view, allowing the implementation to perform any
     * necessary setup or initialization.
     */
    void activate();

    /**
     * Deactivates the grid, typically before another grid system is being
     * installed. This method allows the implementation to perform clean-up
     * operations or reset state as needed when the grid is no longer in use.
     */
    void deactivate();

    /**
     * Indicates that a mouse interaction has started. This method can be used to
     * set up any data or state required for the interaction, such as initializing
     * variables or preparing the grid for upcoming actions.
     * 
     * This method is invoked each time a mouse button is pressed, even if it occurs
     * during an ongoing interaction.
     */
    void mouseDown();

    /**
     * Indicates that a mouse interaction has ended. This method can be used to
     * clean up any data or state that was set up during the interaction, such as
     * finalizing positions or resetting temporary values.
     * 
     * This method is called each time a mouse button is released, including when
     * multiple buttons are used during a single interaction.
     */
    void mouseUp();
}
