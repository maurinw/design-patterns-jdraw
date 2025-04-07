package jdraw.figures;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jdraw.framework.DrawModel;
import jdraw.framework.Figure;
import jdraw.framework.FigureGroup;

public class Group extends AbstractFigure implements FigureGroup {

    private List<Figure> parts;

    public Group(DrawModel model, List<Figure> parts) {
        super();
        if (parts == null || parts.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.parts = model
                .getFigures()
                .filter(parts::contains)
                .collect(Collectors.toList());
    }

    @Override
    public void draw(Graphics g) {
        parts.forEach(figure -> figure.draw(g));
    }

    @Override
    public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            parts.forEach(figure -> figure.move(dx, dy));
            notifyObservers();
        }
    }

    @Override
    public Rectangle getBounds() {
        return parts
                .stream()
                .map(Figure::getBounds)
                .reduce(this::add)
                .orElse(new Rectangle());
    }

    /**
     * Reducer
     * 
     * @param r1
     * @param r2
     * @return
     */
    private Rectangle add(Rectangle r1, Rectangle r2) {
        r1.add(r2);
        return r1;
    }

    @Override
    public boolean contains(int x, int y) {
        return parts
                .stream()
                .anyMatch(figure -> figure.contains(x, y));
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        // empty for now
    }

    @Override
    public Stream<Figure> getFigureParts() {
        return parts.stream();
    }
}
