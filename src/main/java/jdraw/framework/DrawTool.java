/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.Icon;

/**
 * The DrawTool interface defines the contract for tools used in the JDraw
 * application to interact with the drawing view. A tool represents a mode of
 * operation within the drawing editor, such as creating new figures, selecting
 * existing ones, or performing other manipulations. Tools are typically
 * activated via a toolbar or menu, allowing the user to switch between
 * different functionalities seamlessly. All input events targeted to the
 * drawing view are forwarded to its current tool.
 * 
 * Each tool is responsible for handling mouse events within the drawing view,
 * and can adjust the cursor to provide visual feedback corresponding to the
 * tool's function. Implementations of this interface must define the behavior
 * for activation, deactivation, and interaction events to customize the drawing
 * experience.
 * 
 * @see DrawView
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface DrawTool {

    /**
     * Activates the tool for use in the drawing view. This method is called
     * whenever the user switches to this tool, making it the active tool in the
     * editor. Implementations should use this method to initialize or reset the
     * tool's state as necessary.
     */
    void activate();

    /**
     * Deactivates the tool, performing any necessary cleanup when the user switches
     * to a different tool. This method is called whenever another tool becomes
     * active, allowing the current tool to release resources or finalize
     * operations. Implementations should ensure proper cleanup and may call
     * {@code super.deactivate()} if extending from a superclass.
     */
    void deactivate();

    /**
     * Handles mouse down events within the drawing view. This method is called when
     * the user presses a mouse button, and should be implemented to define the
     * tool's behavior at the start of a mouse interaction, such as beginning a new
     * figure or selecting an existing one.
     * 
     * @param x the x-coordinate of the mouse position at the time of the event
     * @param y the y-coordinate of the mouse position at the time of the event
     * @param e the mouse event containing additional state information, such as
     *          modifier keys
     */
    void mouseDown(int x, int y, MouseEvent e);

    /**
     * Handles mouse drag events within the drawing view. This method is called when
     * the user moves the mouse while holding down a button, and should be
     * implemented to define the tool's behavior during the drag, such as resizing a
     * figure or moving it across the canvas.
     * 
     * @param x the current x-coordinate of the mouse position
     * @param y the current y-coordinate of the mouse position
     * @param e the mouse event containing additional state information, such as
     *          modifier keys
     */
    void mouseDrag(int x, int y, MouseEvent e);

    /**
     * Handles mouse up events within the drawing view. This method is called when
     * the user releases a mouse button, and should be implemented to define the
     * tool's behavior at the end of a mouse interaction, such as completing the
     * drawing of a figure or finalizing a selection.
     * 
     * @param x the x-coordinate of the mouse position at the time of the event
     * @param y the y-coordinate of the mouse position at the time of the event
     * @param e the mouse event containing additional state information, such as
     *          modifier keys
     */
    void mouseUp(int x, int y, MouseEvent e);

    /**
     * Returns the cursor to be used by the drawing view when this tool is active.
     * The cursor provides visual feedback to the user, indicating the current mode
     * of interaction based on the selected tool.
     * 
     * @return the cursor associated with this draw tool
     */
    Cursor getCursor();

    /**
     * Returns an icon representing this tool. This icon is displayed in the toolbar
     * or other UI components to visually represent the tool, allowing the user to
     * identify and select it.
     * 
     * @return an icon representing the tool
     */
    Icon getIcon();

    /**
     * Returns the name of this tool. The name is used in UI elements such as menu
     * entries or tooltips to describe the tool's function to the user.
     * 
     * @return the name of the tool
     */
    String getName();
}
