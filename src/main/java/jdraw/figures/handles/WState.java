package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public class WState implements HandleState {
    private final SwappableFigure owner;

    public WState(SwappableFigure owner) {
        this.owner = owner;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
    }

    @Override
    public Point getLocation() {
        var bounds = owner.getBounds();
        return new Point(bounds.x, bounds.y + bounds.height / 2);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        var bounds = owner.getBounds();
        owner.setBounds(new Point(x, bounds.y), new Point(bounds.x + bounds.width, bounds.y + bounds.height));
        if (x > bounds.x + bounds.width) {
            owner.swapHorizontal();
        }
    }

    @Override
    public Figure getOwner() {
        return owner;
    }

    @Override
    public HandleState swapHorizontal() {
        return new EState(owner);
    }

    @Override
    public HandleState swapVertical() {
        return this;
    }
}
