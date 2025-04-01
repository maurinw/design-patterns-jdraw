package jdraw.grid;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.function.Function;

import jdraw.framework.DrawGrid;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public final class FixedGrid implements DrawGrid {
    public final DrawView view;
    public final int size;

    public FixedGrid(DrawView view, int size) {
        this.view = view;
        this.size = size;
    }

    @Override
    public Point constrainPoint(Point p) {
        return translate(p);
    }

    public Point translate(Point p) {
        return new Point(round(p.x + size / 2), round(p.y + size / 2));
    }

    public int round(int value) {
        return Math.floorDiv(value, size) * size;
    }

    @Override
    public int getStepX(boolean right) {
        return right ? findMinimalDistance(view, this::findRightDifference)
                : findMinimalDistance(view, this::findLeftDifference);
    }

    private int findMinimalDistance(DrawView view, Function<Rectangle, Integer> distanceFunc) {
        return view
                .getSelection()
                .stream()
                .map(Figure::getBounds)
                .map(distanceFunc)
                .filter(x -> x > 0)
                .sorted()
                .findFirst()
                .orElse(size);
    }

    private int findRightDifference(Rectangle bounds) {
        int xBorderRight = bounds.x + bounds.width;
        return size - (xBorderRight - round(xBorderRight));
    }

    private int findLeftDifference(Rectangle bounds) {
        int xBorderLeft = bounds.x;
        return xBorderLeft - round(xBorderLeft);
    }

    @Override
    public int getStepY(boolean down) {
        return size;
    }

    @Override
    public void activate() {
        // not used
    }

    @Override
    public void deactivate() {
        // not used
    }

    @Override
    public void mouseDown() {
        // not used
    }

    @Override
    public void mouseUp() {
        // not used
    }
}
