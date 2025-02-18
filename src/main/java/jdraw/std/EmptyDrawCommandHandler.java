/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.std;

import jdraw.framework.DrawCommand;
import jdraw.framework.DrawCommandHandler;

/**
 * An implementation of the DrawCommandHandler interface that performs no
 * operations. This class serves as a dummy command handler, allowing the
 * application to function without any undo or redo capabilities. It provides a
 * basic, non-functional command handler that can be used when command history
 * management is not required or when the application is in a state where such
 * functionality is not yet implemented.
 * 
 * Although this handler allows the application to start and run, it does not
 * support any command execution, undo, or redo operations, effectively
 * rendering these actions as no-ops.
 * 
 * This can be useful for testing, prototyping, or running the application in
 * environments where undo/redo functionality is not critical.
 * 
 * @author Christoph Denzler &amp; Dominik Gruntz
 */
public class EmptyDrawCommandHandler implements DrawCommandHandler {

    @Override
    public void addCommand(DrawCommand cmd) {
        // No operation performed. Commands are not stored or executed.
    }

    @Override
    public void undo() {
        // No operation performed. Undo functionality is not supported.
    }

    @Override
    public void redo() {
        // No operation performed. Redo functionality is not supported.
    }

    @Override
    public boolean undoPossible() {
        // Always returns false as undo functionality is not available.
        return false;
    }

    @Override
    public boolean redoPossible() {
        // Always returns false as redo functionality is not available.
        return false;
    }

    @Override
    public void beginScript() {
        // No operation performed. Scripting functionality is not supported.
    }

    @Override
    public void endScript() {
        // No operation performed. Scripting functionality is not supported.
    }

    @Override
    public void clearHistory() {
        // No operation performed. No history is maintained in this handler.
    }
}
