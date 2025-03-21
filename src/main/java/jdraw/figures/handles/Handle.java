package jdraw.figures.handles;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

public class Handle implements FigureHandle {

    private static final int HANDLE_SIZE = 6;
    private HandleState state;

    public Handle(HandleState state) {
        this.state = state;
    }

    @Override
    public Cursor getCursor() {
        return state.getCursor();
    }

    @Override
    public Point getLocation() {
        return state.getLocation();
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        state.dragInteraction(x, y, e, v);
    }

    @Override
    public Figure getOwner() {
        return state.getOwner();
    }

    @Override
    public void draw(Graphics g) {
        Point loc = getLocation();
        g.setColor(Color.WHITE);
        g.fillRect(loc.x - HANDLE_SIZE / 2, loc.y - HANDLE_SIZE / 2, HANDLE_SIZE, HANDLE_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(loc.x - HANDLE_SIZE / 2, loc.y - HANDLE_SIZE / 2, HANDLE_SIZE, HANDLE_SIZE);
    }

    @Override
    public boolean contains(int x, int y) {
        Point loc = getLocation();
        return Math.abs(x - loc.x) < HANDLE_SIZE / 2 && Math.abs(y - loc.y) < HANDLE_SIZE / 2;
    }

    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        // TODO Empty for now - impl with undo / redo
    }

    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
        // TODO Empty for now - impl with undo / redo
    }

    public HandleState getState() {
        return state;
    }

    public void setState(HandleState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.hashCode() + ", " + state.getClass().getName();
    }

}
