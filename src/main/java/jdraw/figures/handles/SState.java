package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public class SState implements HandleState {

    private final SwappableFigure owner;

    public SState(SwappableFigure owner) {
        this.owner = owner;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
    }

    @Override
    public Point getLocation() {
        var bounds = owner.getBounds();
        return new Point(bounds.x + bounds.width / 2, bounds.y + bounds.height);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        var bounds = owner.getBounds();
        owner.setBounds(new Point(bounds.x, bounds.y), new Point(bounds.x + bounds.width, y));
        if (y < bounds.y) {
            owner.swapVertical();
        }
    }

    @Override
    public Figure getOwner() {
        return owner;
    }

    @Override
    public HandleState swapHorizontal() {
        return this;
    }

    @Override
    public HandleState swapVertical() {
        return new NState(owner);
    }
}
