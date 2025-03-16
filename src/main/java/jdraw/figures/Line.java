package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class Line extends AbstractFigure{

    private final Line2D line;

    public Line(int x, int y) {
        line = new Line2D.Double(x, y, x, y);
    }

    @Override
    public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            line.setLine(
                line.getX1() + dx,
                line.getY1() + dy,
                line.getX2() + dx,
                line.getY2() + dy
            );
            notifyObservers();
        }
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        if (line.getX1() != origin.x || line.getY1() != origin.y || line.getX2() != corner.x || line.getY2() != corner.y) {
            line.setLine(origin, corner);
        }
        notifyObservers();
    }

    @Override
    public Rectangle getBounds() {
        return line.getBounds();
    }


    @Override
    public boolean contains(int x, int y) {
        return line.ptLineDistSq(x, y) < 16;
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine((int)line.getX1(), (int)line.getY1(), (int)line.getX2(), (int)line.getY2());
    }
}
