 package jdraw.figures;

 import java.awt.Color;
 import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
 
 /**
  * The Rect class represents a rectangle figure in JDraw.
  *
  * This class uses the java.awt.Rectangle class to manage geometric properties
  * and drawing of the rectangle. It reuses existing functionality for handling
  * rectangular shapes.
  *
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
         setBounds(new Point(x, y), new Point(x + w, y + h));
     }

     /**
      * Constructs a new rectangle at position x, y with zero height and width.
      *
      * @param x the x-coordinate of the upper-left corner of the rectangle
      * @param y the y-coordinate of the upper-left corner of the rectangle
      */
      public Rect(int x, int y) {
        this(x, y, 0, 0);
    }
 
     /**
      * Renders the rectangle on the given graphics context.
      *
      * The rectangle is filled with white and outlined with a black border.
      *
      * @param g the graphics context used for drawing
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
 