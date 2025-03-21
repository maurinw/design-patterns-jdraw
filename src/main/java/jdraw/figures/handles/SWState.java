package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public class SWState implements HandleState {
    private final SwappableFigure owner;

    public SWState(SwappableFigure owner) {
        this.owner = owner;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
    }

    @Override
    public Point getLocation() {
        var bounds = owner.getBounds();
        return new Point(bounds.x, bounds.y + bounds.height);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        var bounds = owner.getBounds();
        owner.setBounds(new Point(x, y), new Point(bounds.x + bounds.width, bounds.y));
        if (x > bounds.x + bounds.width) {
            owner.swapHorizontal();
        }
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
        return new SEState(owner);
    }

    @Override
    public HandleState swapVertical() {
        return new NWState(owner);
    }
}
