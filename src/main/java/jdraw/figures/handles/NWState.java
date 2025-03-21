package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public class NWState implements HandleState {

    private final SwappableFigure owner;

    public NWState(SwappableFigure owner) {
        this.owner = owner;
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
    }

    @Override
    public Point getLocation() {
        return owner.getBounds().getLocation();
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        var bounds = owner.getBounds();
        owner.setBounds(new Point(x, y), new Point(bounds.x + bounds.width, bounds.y + bounds.height));
        if (x > bounds.x + bounds.width) {
            owner.swapHorizontal();
        }
        if (y > bounds.y + bounds.height) {
            owner.swapVertical();
        }
    }

    @Override
    public Figure getOwner() {
        return owner;
    }

    @Override
    public HandleState swapHorizontal() {
        return new NEState(owner);
    }

    @Override
    public HandleState swapVertical() {
        return new SWState(owner);
    }
}
