package jdraw.figures;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class AbstractRectangularFigure extends AbstractFigure {

    private final Rectangle rectangle;

    protected AbstractRectangularFigure(int x, int y) {
        this.rectangle = new Rectangle(x, y, 0, 0);
    }

    @Override
    public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            rectangle.translate(dx, dy);
            notifyObservers();
        }
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        Rectangle original = new Rectangle(rectangle);
        rectangle.setFrameFromDiagonal(origin, corner);
        if (!original.equals(rectangle)) {
            notifyObservers();
        }
        
    }

    @Override
    public Rectangle getBounds() {
        return rectangle.getBounds();
    }

    @Override
    public boolean contains(int x, int y) {
        return rectangle.contains(x, y);
    }
}
