/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.std;

import jdraw.framework.DrawCommand;
import jdraw.framework.Figure;

/**
 * The MoveCommand class implements the DrawCommand interface to represent a
 * move operation on a figure within the JDraw application. This command
 * encapsulates the details of a movement, including the figure being moved and
 * the distance moved along the x and y axes. It supports undo and redo
 * functionality, allowing the movement to be reversed or reapplied as part of
 * the command pattern.
 * 
 * MoveCommand is used to facilitate movement actions in the editor, ensuring
 * that changes can be tracked, undone, or redone, thus enhancing the
 * flexibility and usability of the drawing application.
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 */
public class MoveCommand implements DrawCommand {
    private static final long serialVersionUID = 6532903929787642788L;

    /** The figure being moved by this command. */
    private final Figure f;

    /** The distance, in pixels, that the figure is moved horizontally. */
    private final int dx;

    /** The distance, in pixels, that the figure is moved vertically. */
    private final int dy;

    /**
     * Constructs a MoveCommand with the specified figure and movement distances.
     * This command records the figure and the distances it was moved along the x
     * and y axes, allowing these movements to be undone or redone.
     * 
     * @param f  the figure that was moved by this command
     * @param dx the number of pixels the figure was moved along the x-axis
     * @param dy the number of pixels the figure was moved along the y-axis
     */
    public MoveCommand(Figure f, int dx, int dy) {
        this.f = f;
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Reapplies the move operation stored in this command. This method moves the 
     * figure by the recorded distances along the x and y axes, effectively redoing 
     * the movement.
     */
    @Override
    public void redo() {
        f.move(dx, dy);
    }

    /**
     * Reverses the move operation stored in this command. This method moves the 
     * figure by the negative of the recorded distances, effectively undoing the 
     * movement.
     */
    @Override
    public void undo() {
        f.move(-dx, -dy);
    }
}
