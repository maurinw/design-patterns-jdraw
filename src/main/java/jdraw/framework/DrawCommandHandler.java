/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

/**
 * The interface DrawCommandHandler defines the contract for handling the
 * command history in JDraw. It provides methods for adding commands to the
 * history, undoing and redoing commands, and managing macro commands through
 * scripts.
 *
 * @see DrawCommandHandler
 *
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */

public interface DrawCommandHandler {

    /**
     * Adds a new command to the command history at the current position. The added
     * command is not executed by this method. Commands that were stored after the
     * current position may be discarded.
     * 
     * @param cmd the draw command to be added to the command history.
     */
    void addCommand(DrawCommand cmd);

    /**
     * Undoes the last executed command in the command history. This action can be
     * reversed by calling {@link #redo}, unless new commands are added after the
     * undo operation, which could reset the redo history.
     * 
     * @see #redo
     */
    void undo();

    /**
     * Redoes the next command in the command history, effectively re-executing it
     * after an undo operation.
     * 
     * @see #undo
     */
    void redo();

    /**
     * Checks if an undo operation is currently possible. This method returns true
     * if there are commands in the history that can be undone.
     * 
     * @return true if an undo operation can be performed; false otherwise.
     * @see #undo
     */
    boolean undoPossible();

    /**
     * Checks if a redo operation is currently possible. This method returns true if
     * there are undone commands in the history that can be redone.
     * 
     * @return true if a redo operation can be performed; false otherwise.
     * @see #redo
     */
    boolean redoPossible();

    /**
     * Begins a script, allowing multiple commands to be grouped into a macro
     * command. All commands added after this method is called will be collected and
     * combined into a single macro command when {@link #endScript} is called.
     * 
     * @see #endScript
     */
    void beginScript();

    /**
     * Ends a script, combining all commands added since the last call to
     * {@link #beginScript} into a single macro command. This allows for batch
     * operations to be executed and managed as a single command.
     * 
     * @see #beginScript
     */
    void endScript();

    /**
     * Clears the entire command history, removing all commands from the list. This
     * operation resets the undo and redo states.
     */
    void clearHistory();
}
