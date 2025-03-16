/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


/**
 * The Rect class represents a rectangle figure in JDraw. This class utilizes
 * the java.awt.Rectangle class to handle the geometric properties and drawing
 * of the rectangle, thereby reusing existing functionality for managing
 * rectangular shapes.
 * 
 * This implementation of the Figure interface provides basic functionality for
 * rendering, moving, and manipulating rectangle figures within the drawing
 * application. It includes methods for drawing the rectangle, adjusting its
 * bounds, and determining if a point lies within the rectangle.
 * 
 * Note: The current implementation does not yet notify listeners of changes,
 * nor does it provide actual handles or cloning functionality. These areas are
 * marked for future development.
 * 
 * @author Christoph Denzler &amp; Dominik Gruntz
 */
public class Rect extends AbstractRectangularFigure {

    private static final long serialVersionUID = 9120181044386552132L;

    /**
     * Constructs a new rectangle with the specified position and dimensions.
     * 
     * @param x the x-coordinate of the upper-left corner of the rectangle
     * @param y the y-coordinate of the upper-left corner of the rectangle
     * @param w the width of the rectangle
     * @param h the height of the rectangle
     */
    public Rect(int x, int y, int w, int h) {
        super(x, y);
    }

    /**
     * Create a new Rectangle at position x, y with zero w and h.
     * 
     * @param x the x-coordinate of the upper-left corner of the rectangle
     * @param y the y-coordinate of the upper-left corner of the rectangle

     */
    public Rect(int x, int y) {
        this(x, y, 0, 0);
    }

    /**
     * Renders the rectangle onto the specified graphics context. The rectangle is
     * filled with white color and outlined with a black border.
     * 
     * @param g the graphics context to use for drawing
     */
    @Override
    public void draw(Graphics g) {
        Rectangle rectangle = getBounds();
        g.setColor(Color.WHITE);
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g.setColor(Color.BLACK);
        g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
