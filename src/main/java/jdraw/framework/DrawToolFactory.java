/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw.framework;

/**
 * The DrawToolFactory interface defines a factory for creating instances of
 * draw tools in the JDraw application. A draw tool is a mode of interaction
 * within the drawing editor, such as drawing figures or manipulating existing
 * ones. The factory provides methods to create these tools and configure their
 * names and icons, which are used in the tool palette of the graphics editor.
 * 
 * This interface allows for customization of the tool's appearance and behavior
 * in the editor, enabling dynamic addition and management of different drawing
 * tools.
 * 
 * @see DrawTool
 * @see DrawContext
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.5
 */
public interface DrawToolFactory {

    /**
     * Returns the name of the tool created by this factory. If a custom name has
     * been set using {@link #setName(String)}, it will override the default name
     * provided by the tool implementation.
     * 
     * @return the name of the draw tool
     * @see DrawTool#getName()
     */
    String getName();

    /**
     * Sets a custom name for the tool created by this factory. This name will
     * override the default name defined in the tool implementation, allowing for
     * customized display names in the editor's tool palette or menus.
     * 
     * @param name the custom name for the draw tool
     */
    void setName(String name);

    /**
     * Returns the path of the icon for the tool created by this factory. If a
     * custom icon path has been set using {@link #setIconName(String)}, it will
     * override the default icon provided by the tool implementation.
     * 
     * @return the path to the icon of the draw tool
     * @see DrawTool#getIcon()
     */
    String getIconName();

    /**
     * Sets a custom path for the icon of the tool created by this factory. This
     * icon path will override the default icon defined in the tool implementation,
     * allowing for a customized appearance in the editor's tool palette.
     * 
     * @param name the custom path to the draw tool icon
     */
    void setIconName(String name);

    /**
     * Creates and returns a new instance of a draw tool that operates within the
     * given drawing context. This method initializes the tool based on the current
     * settings of the factory, including any custom names or icons.
     * 
     * @param context the draw context in which the tool will operate
     * @return a new draw tool instance
     */
    DrawTool createTool(DrawContext context);
}
