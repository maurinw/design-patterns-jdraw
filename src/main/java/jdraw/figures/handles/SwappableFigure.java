package jdraw.figures.handles;

import jdraw.framework.Figure;

public interface SwappableFigure extends Figure {
    void swapHorizontal();

    void swapVertical();
}
