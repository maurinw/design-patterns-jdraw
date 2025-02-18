package jdraw.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serial;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import jdraw.framework.DrawModel;
import jdraw.framework.DrawModelEvent;
import jdraw.framework.DrawModelEvent.Type;
import jdraw.framework.DrawModelListener;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;
import jdraw.std.StdDrawModel;

public class DrawModelTest {

    private DrawModel m;
    private Figure f;

    protected DrawModel createDrawModel() {
        return new StdDrawModel();
    }

    @BeforeEach
    public void setUp() {
        m = createDrawModel();
        f = mock(Figure.class);
    }

    @Test
    @DisplayName("Added Figure is returned in getFigures()")
    public void testAddFigure1() {
        m.addFigure(f);
        assertEquals(f, m.getFigures().iterator().next(), "get back the Figure from the model");
    }

    @Test
    @DisplayName("Preserve order of added figures")
    public void testAddFigure2() {
        Figure f1 = mock(Figure.class);
        Figure f2 = mock(Figure.class);
        Figure f3 = mock(Figure.class);
        m.addFigure(f1);
        m.addFigure(f2);
        m.addFigure(f3);
        String msg = "order of adding figures is not preserved";
        Iterator<? extends Figure> it = m.getFigures().iterator();
        assertEquals(f1, it.next(), msg);
        assertEquals(f2, it.next(), msg);
        assertEquals(f3, it.next(), msg);
    }

    @Test
    @DisplayName("Assert that a FIGURE_ADDED Event is notified")
    public void testAddFigure3() {
        DrawModelListener listener = mock(DrawModelListener.class);
        m.addModelChangeListener(listener);
        m.addFigure(f);

        ArgumentCaptor<DrawModelEvent> argumentCaptor = ArgumentCaptor.forClass(DrawModelEvent.class);
        verify(listener).modelChanged(argumentCaptor.capture());
        assertEquals(DrawModelEvent.Type.FIGURE_ADDED, argumentCaptor.getValue().getType(),
                "addFigure should notify a FIGURE_ADDED event");
    }

    @Test
    @DisplayName("Assert unique figures in model")
    public void testAddFigure4() {
        m.addFigure(f);
        m.addFigure(f);
        Iterator<? extends Figure> it = m.getFigures().iterator();
        it.next();
        assertFalse(it.hasNext(), "figures in the model should be unique");
    }

    @Test
    @DisplayName("Nothing happens if figure is added twice")
    public void testAddFigure5() {
        m.addFigure(f);
        DrawModelListener dml = mock(DrawModelListener.class);
        m.addModelChangeListener(dml);
        m.addFigure(f);
        verify(dml, never().description("no notification if figure is already contained in model")).modelChanged(any());
    }

    @Test
    @DisplayName("Model must register observer in figure")
    public void testAddFigure6() {
        m.addFigure(f);
        verify(f, description("model has to register a listener in the figure")).addFigureListener(any());
    }

    @Test
    @DisplayName("Nothing happens if figure was not in model")
    public void testRemoveFigure1() {
        DrawModelListener dml = mock(DrawModelListener.class);
        m.addModelChangeListener(dml);
        m.removeFigure(f);
        verify(dml, never().description("no notification expected, figure was not contained in model"))
                .modelChanged(any());
    }

    @Test
    @DisplayName("Remove model as observer when figure is removed from model")
    public void testRemoveFigure2() {
        m.addFigure(f);
        m.removeFigure(f);
        verify(f, description("model must register a listener in a figure which is added")).addFigureListener(any());
        verify(f, description("listeners registered by the model must be removed")).removeFigureListener(any());
    }

    @Test
    @DisplayName("Add/remove same FigureListener when figure is added/removed from model")
    public void testRemoveFigure3() {
        ArgumentCaptor<FigureListener> listener1 = ArgumentCaptor.forClass(FigureListener.class);
        ArgumentCaptor<FigureListener> listener2 = ArgumentCaptor.forClass(FigureListener.class);
        m.addFigure(f);
        m.removeFigure(f);
        verify(f).addFigureListener(listener1.capture());
        verify(f).removeFigureListener(listener2.capture());
        assertSame(listener1.getValue(), listener2.getValue(),
                "the listener which has been registered must also be removed");
    }

    @Test
    @DisplayName("Verify that FigureListener which is registered is also removed")
    public void testRemoveFigure4() {
        Figure f1 = mock(Figure.class);
        Figure f2 = mock(Figure.class);

        ArgumentCaptor<FigureListener> listenerf11 = ArgumentCaptor.forClass(FigureListener.class);
        ArgumentCaptor<FigureListener> listenerf12 = ArgumentCaptor.forClass(FigureListener.class);
        ArgumentCaptor<FigureListener> listenerf21 = ArgumentCaptor.forClass(FigureListener.class);
        ArgumentCaptor<FigureListener> listenerf22 = ArgumentCaptor.forClass(FigureListener.class);

        m.addFigure(f1);
        m.addFigure(f2);
        m.removeFigure(f1);
        m.removeFigure(f2);

        verify(f1).addFigureListener(listenerf11.capture());
        verify(f1).removeFigureListener(listenerf12.capture());
        verify(f2).addFigureListener(listenerf21.capture());
        verify(f2).removeFigureListener(listenerf22.capture());

        assertSame(listenerf11.getValue(), listenerf12.getValue(),
                "the listener which has been registered by the model must also be removed");
        assertSame(listenerf21.getValue(), listenerf22.getValue(),
                "the listener which has been registered by the model must also be removed");
    }

    @Test
    @DisplayName("Verify DRAWING_CLEARED is notified only once upon removeAllFigures()")
    public void testRemoveAllFigures1() {
        DrawModelListener listener = mock(DrawModelListener.class);

        m.addFigure(mock(Figure.class));
        m.addFigure(mock(Figure.class));
        m.addFigure(mock(Figure.class));
        m.addModelChangeListener(listener);
        m.removeAllFigures();

        assertTrue(m.getFigures().findAny().isEmpty(), "All figures should have been removed");

        ArgumentCaptor<DrawModelEvent> arg = ArgumentCaptor.forClass(DrawModelEvent.class);
        verify(listener, description("removeAllFigures should notify only once a DRAWING_CLEARED event"))
                .modelChanged(arg.capture());
        assertSame(Type.DRAWING_CLEARED, arg.getValue().getType(),
                "removeAllFigures should notify only once a DRAWING_CLEARED event");
    }

    @Test
    @DisplayName("removeAllFigures removes listeners in all figures")
    public void testRemoveAllFigures2() {
        Figure f1 = mock(Figure.class);
        Figure f2 = mock(Figure.class);

        m.addFigure(f1);
        m.addFigure(f2);
        m.removeAllFigures();

        assertTrue(m.getFigures().findAny().isEmpty(), "All figures should have been removed");

        verify(f1, description("registered listener should be removed")).removeFigureListener(any());
        verify(f2, description("registered listener should be removed")).removeFigureListener(any());
    }

    @Test
    @DisplayName("Bring front figure to back")
    public void testSetFigureIndex1() {
        DrawModelListener listener = mock(DrawModelListener.class);
        Figure f1 = mock(Figure.class);
        Figure f2 = mock(Figure.class);
        Figure f3 = mock(Figure.class);

        m.addFigure(f1);
        m.addFigure(f2);
        m.addFigure(f3);
        m.addModelChangeListener(listener);
        m.setFigureIndex(f3, 0);

        Iterator<? extends Figure> it = m.getFigures().iterator();
        assertEquals(f3, it.next(), "f3 should be at position 0");
        assertEquals(f1, it.next(), "f1 should be at position 1");
        assertEquals(f2, it.next(), "f2 should be at position 2");

        ArgumentCaptor<DrawModelEvent> arg = ArgumentCaptor.forClass(DrawModelEvent.class);
        verify(listener, description("setFigureIndex should notify a DRAWING_CHANGED event"))
                .modelChanged(arg.capture());
        assertSame(Type.DRAWING_CHANGED, arg.getValue().getType(),
                "setFigureIndex should notify a DRAWING_CHANGED event");
    }

    @Test
    @DisplayName("Only added figures may be reordered")
    public void testSetFigureIndex2() {
        Figure f1 = mock(Figure.class);
        Figure f2 = mock(Figure.class);
        Figure f3 = mock(Figure.class);

        m.addFigure(f1);
        m.addFigure(f2);
        m.addFigure(f3);

        assertThrows(IllegalArgumentException.class, () -> m.setFigureIndex(f, 1));
    }

    @Test
    @DisplayName("Reordering works only with existing position as index")
    public void testSetFigureIndex3() {
        Figure f1 = mock(Figure.class);
        Figure f2 = mock(Figure.class);

        m.addFigure(f1);
        m.addFigure(f2);

        m.setFigureIndex(f2, 0);
        m.setFigureIndex(f2, 1);

        assertThrows(IndexOutOfBoundsException.class, () -> m.setFigureIndex(f2, 2));
        assertTrue(m.getFigures().anyMatch(f -> f == f2),
                "in case that an IndexOutOfBoundsException occurs, the figure must not be removed from the model");
    }

    @Test
    @DisplayName("Reordering not allowed to a negative index")
    public void testSetFigureIndex4() {
        Figure f1 = mock(Figure.class);
        Figure f2 = mock(Figure.class);

        m.addFigure(f1);
        m.addFigure(f2);

        assertThrows(IndexOutOfBoundsException.class, () -> m.setFigureIndex(f2, -1));
        assertTrue(m.getFigures().anyMatch(f -> f == f2),
                "in case that an IndexOutOfBoundsException occurs, the figure must not be removed from the model");
    }

    @Test
    @DisplayName("DrawModelEvent must refer to the changed figure")
    public void testNotification() {
        Figure f = new Figure() {
            @Serial
            private static final long serialVersionUID = 1L;
            private FigureListener listener;

            @Override
            public void draw(Graphics g) {
            }

            @Override
            public void move(int dx, int dy) {
                listener.figureChanged(new FigureEvent(this));
            }

            @Override
            public boolean contains(int x, int y) {
                return false;
            }

            @Override
            public void setBounds(Point origin, Point corner) {
            }

            @Override
            public Rectangle getBounds() {
                return null;
            }

            @Override
            public List<? extends FigureHandle> getHandles() {
                return null;
            }

            @Override
            public void addFigureListener(FigureListener listener) {
                this.listener = listener;
            }

            @Override
            public void removeFigureListener(FigureListener listener) {
            }

            @Override
            public Figure clone() {
                return null;
            }
        };
        DrawModelListener listener = mock(DrawModelListener.class);

        m.addFigure(f);
        m.addModelChangeListener(listener);
        f.move(1, 1);

        ArgumentCaptor<DrawModelEvent> argumentCaptor = ArgumentCaptor.forClass(DrawModelEvent.class);
        verify(listener).modelChanged(argumentCaptor.capture());
        assertEquals(DrawModelEvent.Type.FIGURE_CHANGED, argumentCaptor.getValue().getType(),
                "Model should notify a FIGURE_CHANGED event when figures change");
        assertEquals(f, argumentCaptor.getValue().getFigure(),
                "Model should refer to the changed figure in a FIGURE_CHANGED event");
    }

}
