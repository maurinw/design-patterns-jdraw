package jdraw.figures.handles;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

public interface HandleState {

    public Point getLocation();

    public Cursor getCursor();

    public void dragInteraction(int x, int y, MouseEvent e, DrawView v);

    public Figure getOwner();

    /**
     * Gibt den horizontalen Gegenzustand zurück
     * 
     * @return Den neuen Gegenzustand, oder this, wenn es keinen solchen gibt
     */
    HandleState swapHorizontal();

    /**
     * Gibt den vertikalen Gegenzustand zurück
     * 
     * @return Den neuen Gegenzustand, oder this, wenn es keinen solchen gibt
     */
    HandleState swapVertical();

}
