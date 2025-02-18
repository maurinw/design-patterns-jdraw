/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

import java.util.stream.Stream;

/**
 * The FigureGroup interface defines the contract for figures that are composed
 * of multiple sub-figures, such as group figures in the JDraw application. This
 * interface provides methods to access and manage the constituent parts of a
 * composite figure, enabling operations that involve multiple figures as a
 * single unit.
 * 
 * Implementing this interface allows figures to be treated as a group while
 * still maintaining access to their individual components, facilitating complex
 * operations like grouping, ungrouping, and collective transformations.
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface FigureGroup {

    /**
     * Returns a stream of the individual parts that make up the composite figure.
     * This method provides access to the sub-figures within a group figure,
     * allowing iteration and manipulation of each component.
     * 
     * @return a stream of figures that are part of the composite figure
     */
    Stream<Figure> getFigureParts();
}
