/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

/**
 * The DrawContext interface defines the contract for managing the environment
 * in which the drawing operations are performed within the JDraw application.
 * It provides access to the drawing view and model, as well as methods for
 * interacting with the user interface, such as displaying status messages,
 * managing menus, and handling drawing tools.
 * 
 * This interface serves as a central point for coordinating the editor's
 * behavior, offering convenience methods to manage tools and UI elements, and
 * facilitating the interaction between the drawing model and the view. It plays
 * a key role in the application's architecture by providing the necessary
 * functionality to control and customize the drawing environment.
 * 
 * @see DrawView
 * @see DrawModel
 * @see DrawTool
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface DrawContext {

    /**
     * Returns the drawing view associated with this context. The view is
     * responsible for displaying the figures and handling user interactions within
     * the drawing area.
     * 
     * @return the draw view controlled by this context
     */
    DrawView getView();

    /**
     * Returns the draw model presented by this context's view. This model stores
     * the figures, and this method provides a direct access to the model,
     * equivalent to calling getView().getModel().
     * 
     * @return the draw model presented by this context's view.
     */
    DrawModel getModel();

    /**
     * Displays a status message in the user interface. This method is typically
     * used to provide feedback or information to the user, such as operation
     * status, or error messages.
     * 
     * @param msg the status message to be displayed
     */
    void showStatusText(String msg);

    /**
     * Adds a menu to the editor's user interface. This allows the application to
     * dynamically extend its menu system, integrating new actions and options
     * available to the user.
     * 
     * @param menu the menu to be added
     */
    void addMenu(javax.swing.JMenu menu);

    /**
     * Removes a menu from the editor's user interface. This method allows for
     * dynamic modification of the menu system, enabling the removal of menus that
     * are no longer needed or relevant.
     * 
     * @param menu the menu to be removed
     */
    void removeMenu(javax.swing.JMenu menu);

    /**
     * Adds a new drawing tool to the editor's set of available tools. Tools are
     * used by the user to create figures. This method must ensure that any tool is
     * contained only once in the list.
     * 
     * @param tool a new draw tool to add to the editor.
     */
    void addTool(DrawTool tool);

    /**
     * Returns the currently active drawing tool. The active tool determines the
     * current operation mode of the editor, such as drawing rectangles, drawing
     * circles or selecting and moving figures.
     * 
     * @return the currently active tool
     */
    DrawTool getTool();

    /**
     * Sets the active drawing tool to the specified tool. This method allows the
     * application to change the user's current mode of interaction within the
     * drawing view, such as switching from drawing rectangles to selecting figures.
     * 
     * @param tool the tool to be used by the draw view
     */
    void setTool(DrawTool tool);

    /**
     * Sets the default tool for the editor, typically used for basic operations
     * such as selecting and moving figures. This method is used to reset the tool
     * state to a standard interaction mode.
     */
    void setDefaultTool();

    /**
     * Makes the drawing view visible to the user. This method is responsible for
     * displaying the main drawing window, allowing user interaction with the
     * figures and tools.
     */
    void showView();
}
