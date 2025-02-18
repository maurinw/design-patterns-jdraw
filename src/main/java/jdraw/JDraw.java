/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package jdraw;

import jdraw.framework.DrawContext;

import org.apache.log4j.BasicConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The JDraw class serves as the main entry point for the JDraw graphic editor
 * application. Its main method initializes the application by accessing an
 * implementation of DrawContext, which is responsible for setting up and
 * displaying the main window of the editor. This setup includes creating a
 * JFrame and initializing its components such as menus, tools, and other GUI
 * elements.
 * 
 * Configuration changes to the main window, like adding new menus or tools,
 * should be implemented in the class that implements the DrawContext interface.
 * The specific implementation of DrawContext to be used is configured through
 * the Spring configuration file specified by default as `jdraw-context.xml`, or
 * an alternative configuration file can be provided as a command-line argument.
 * 
 * To run the application, use the following command:
 * 
 * <PRE>
 * java jdraw.JDraw [config]
 * </PRE>
 * 
 * where `config` is an optional XML file for Spring framework configuration.
 * 
 * Logging is set up using log4j, configured via a log4j.properties file found
 * in the classpath.
 * 
 * @see jdraw.framework.DrawView
 * @see jdraw.framework.DrawContext
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.1
 */
public final class JDraw {

    /** Default XML configuration file for Spring. */
    private static final String DEFAULT_CONTEXT = "jdraw-context.xml";

    /**
     * The Spring XML configuration file to be used, defaults to the default
     * context.
     */
    private static String springContext = DEFAULT_CONTEXT;

    /** The Spring application context, managing the beans for the application. */
    private static ClassPathXmlApplicationContext ctx;

    /**
     * The main method that starts the JDraw application. It configures log4j, sets
     * the Spring context configuration file if provided, and initializes the
     * drawing context to launch the main application window.
     * 
     * Usage: <br>
     * <code>java jdraw.JDraw [config]</code> <br>
     * where `config` is an optional path to an XML configuration file for Spring.
     * 
     * @param args command line arguments; if provided, the first argument is used
     *             as the Spring configuration XML file.
     */
    public static void main(final String[] args) {
        // Configure log4j using a properties file found on the classpath
        BasicConfigurator.configure();

        if (args.length > 0) {
            springContext = args[0];
        }

        DrawContext drawContext = getContext();
        drawContext.showView();
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private JDraw() {
        // Prevent instantiation.
    }

    /**
     * Retrieves the drawing context from the Spring application context, which
     * provides access to the core functionalities of the JDraw application. This
     * method initializes the Spring context if it hasn't been already.
     * 
     * @return an instance of DrawContext as configured in the Spring XML file.
     */
    public static DrawContext getContext() {
        if (ctx == null) {
            ctx = new ClassPathXmlApplicationContext(springContext);
        }
        return ctx.getBean("drawContext", DrawContext.class);
    }

}
