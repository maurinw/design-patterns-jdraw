/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

import java.io.Serializable;

/**
 * The DrawCommand interface defines the contract for commands that can be
 * executed and undone. These commands are intended to be used with a command
 * history, allowing actions to be redone and undone as needed.
 *
 * Implementations of this interface must provide functionality for both
 * executing and undoing a command, enabling reversible operations in the
 * application.
 *
 * @see DrawCommandHandler
 *
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */

public interface DrawCommand extends Serializable {

    /**
     * Execures (or re-executes) the command. This method should be called to
     * perform the operation that the command represents, typically after it has
     * been undone.
     */
    void redo();

    /**
     * Reverses the command's action. This method should be called to undo the
     * action that was previously executed by the command, effectively restoring the
     * state prior to the command's execution.
     */
    void undo();
}
