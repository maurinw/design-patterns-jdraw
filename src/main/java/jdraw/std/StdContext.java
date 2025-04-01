/*
 * Copyright (c) 2024 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */
package jdraw.std;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import jdraw.figures.LineTool;
import jdraw.figures.OvalTool;
import jdraw.figures.RectTool;
import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawToolFactory;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.grid.FixedGrid;

/**
 * The StdContext class provides a standard implementation of the DrawContext
 * interface for the JDraw application. It manages the interaction between the
 * user interface and the drawing model, including the creation and handling of
 * menus, tools, and file operations. The context also initializes and manages
 * the available drawing tools, and sets up menu actions for editing and
 * managing figures within the drawing.
 * 
 * StdContext includes standard implementations of common menu items such as
 * Undo, Redo, Select All, and Clear. It also supports file operations such as
 * Open and Save through the use of file choosers with specific filters for
 * supported file formats.
 * 
 * This class acts as a central controller within the JDraw application,
 * facilitating the coordination of user actions, tool management, and model
 * updates.
 * 
 * @see DrawView
 * @see DrawModel
 * @see DrawTool
 * @see Figure
 * @see DrawCommandHandler
 * 
 * @author Dominik Gruntz &amp; Christoph Denzler
 * @version 2.6
 */
@SuppressWarnings("serial")
public class StdContext extends AbstractContext {

    /**
     * Constructs a standard context with a default set of drawing tools.
     * 
     * @param view the view that is displaying the actual drawing
     */
    public StdContext(DrawView view) {
        this(view, null);
    }

    /**
     * Constructs a standard context. The drawing tools available can be customized
     * by providing a list of tool factories.
     * 
     * @param view          the view that is displaying the actual drawing
     * @param toolFactories a list of DrawToolFactories that are available to the
     *                      user
     */
    public StdContext(DrawView view, List<DrawToolFactory> toolFactories) {
        super(view, toolFactories);
    }

    /**
     * Creates and initializes the "Edit" menu, providing options for undoing and
     * redoing actions, selecting all figures, clearing the drawing, and adjusting
     * the order of figures within the model.
     * 
     * @return the initialized "Edit" menu
     */
    @Override
    protected JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");

        final JMenuItem undo = new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke("control Z"));
        editMenu.add(undo);
        undo.addActionListener(e -> {
            final DrawCommandHandler h = getModel().getDrawCommandHandler();
            if (h.undoPossible()) {
                h.undo();
            }
        });

        final JMenuItem redo = new JMenuItem("Redo");
        redo.setAccelerator(KeyStroke.getKeyStroke("control Y"));
        editMenu.add(redo);
        redo.addActionListener(e -> {
            final DrawCommandHandler h = getModel().getDrawCommandHandler();
            if (h.redoPossible()) {
                h.redo();
            }
        });

        editMenu.addSeparator();

        JMenuItem sa = new JMenuItem("Select All");
        sa.setAccelerator(KeyStroke.getKeyStroke("control A"));
        editMenu.add(sa);
        sa.addActionListener(e -> {
            getModel().getFigures().forEachOrdered(f -> getView().addToSelection(f));
            getView().repaint();
        });

        editMenu.addSeparator();

        editMenu.add("Cut").setEnabled(false);
        editMenu.add("Copy").setEnabled(false);
        editMenu.add("Paste").setEnabled(false);

        editMenu.addSeparator();

        JMenuItem clear = new JMenuItem("Clear");
        editMenu.add(clear);
        clear.addActionListener(e -> {
            getModel().removeAllFigures();
        });

        editMenu.addSeparator();
        JMenuItem group = new JMenuItem("Group");
        group.setEnabled(false); // Future implementation can enable grouping functionality
        editMenu.add(group);

        JMenuItem ungroup = new JMenuItem("Ungroup");
        ungroup.setEnabled(false); // Future implementation can enable ungrouping functionality
        editMenu.add(ungroup);

        editMenu.addSeparator();

        JMenu orderMenu = new JMenu("Order...");
        JMenuItem frontItem = new JMenuItem("Bring To Front");
        frontItem.addActionListener(e -> bringToFront(getView().getModel(), getView().getSelection()));
        orderMenu.add(frontItem);
        JMenuItem backItem = new JMenuItem("Send To Back");
        backItem.addActionListener(e -> sendToBack(getView().getModel(), getView().getSelection()));
        orderMenu.add(backItem);
        editMenu.add(orderMenu);

        JMenu grid = new JMenu("Grid...");
        editMenu.add(grid);

        grid.add(addFixedGridMenu("No Grid", null));
        grid.add(addFixedGridMenu("Fixed 20", new FixedGrid(getView(), 20)));
        grid.add(addFixedGridMenu("Fixed 100", new FixedGrid(getView(), 100)));

        return editMenu;
    }

    private JMenuItem addFixedGridMenu(String label, FixedGrid grid) {
        JMenuItem entry = new JMenuItem(label);
        entry.addActionListener(e -> getView().setGrid(grid));
        return entry;
    }

    /**
     * Creates and initializes the "File" menu, providing options for opening,
     * saving,
     * and exiting the application. It sets up file choosers with filters for
     * supported
     * file formats to manage file input and output operations.
     * 
     * @return the initialized "File" menu
     */
    @Override
    protected JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");

        JMenuItem open = new JMenuItem("Open");
        fileMenu.add(open);
        open.setAccelerator(KeyStroke.getKeyStroke("control O"));
        open.addActionListener(e -> doOpen());

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke("control S"));
        fileMenu.add(save);
        save.addActionListener(e -> doSave());

        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(exit);
        exit.addActionListener(e -> System.exit(0));

        return fileMenu;
    }

    /**
     * Registers the default drawing tools available in the context, starting with
     * the
     * rectangle tool. Additional tools can be added as needed.
     */
    @Override
    protected void doRegisterDrawTools() {
        DrawTool rectangleTool = new RectTool(this);
        DrawTool ovalTool = new OvalTool(this);
        DrawTool lineTool = new LineTool(this);
        addTool(rectangleTool);
        addTool(ovalTool);
        addTool(lineTool);
    }

    /**
     * Adjusts the order of figures by moving the selected figures to the front of
     * the drawing. This operation repositions the selected figures at the end of
     * the figure list such that it will be drawn last.
     * 
     * @param model     the drawing model where the order is adjusted
     * @param selection the figures to be moved to the front
     */
    public void bringToFront(DrawModel model, List<Figure> selection) {
        // the figures in the selection are ordered according to the order in the model
        List<Figure> orderedSelection = model.getFigures().filter(f -> selection.contains(f))
                .collect(Collectors.toList());
        Collections.reverse(orderedSelection);
        int pos = (int) model.getFigures().count();
        for (Figure f : orderedSelection) {
            model.setFigureIndex(f, --pos);
        }
    }

    /**
     * Adjusts the order of figures by moving the selected figures to the back of
     * the drawing. This operation repositions the selected figures at the beginning
     * of the figure list such that it will be drawn first.
     * 
     * @param model     the drawing model where the order is adjusted
     * @param selection the figures to be moved to the back
     */
    public void sendToBack(DrawModel model, List<Figure> selection) {
        // the figures in the selection are ordered according to the order in the model
        List<Figure> orderedSelection = model.getFigures().filter(f -> selection.contains(f))
                .collect(Collectors.toList());
        int pos = 0;
        for (Figure f : orderedSelection) {
            model.setFigureIndex(f, pos++);
        }
    }

    /**
     * Handles the saving of a drawing to a file. Opens a file chooser dialog that
     * allows the user to select a location and format for saving the current
     * drawing. The writing of a graphics needs to be implemented.
     */
    private void doSave() {
        JFileChooser chooser = new JFileChooser(getClass().getResource("").getFile());
        chooser.setDialogTitle("Save Graphic");
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        chooser.setFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.draw)", "draw"));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.xml)", "xml"));
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("JDraw Graphics (*.json)", "json"));

        int res = chooser.showSaveDialog(this);

        if (res == JFileChooser.APPROVE_OPTION) {
            // save graphic
            File file = chooser.getSelectedFile();
            FileFilter filter = chooser.getFileFilter();
            if (filter instanceof FileNameExtensionFilter && !filter.accept(file)) {
                file = new File(chooser.getCurrentDirectory(),
                        file.getName() + "." + ((FileNameExtensionFilter) filter).getExtensions()[0]);
            }
            System.out.println("save current graphic to file " + file.getName() + " using format "
                    + ((FileNameExtensionFilter) filter).getExtensions()[0]);
        }
    }

    /**
     * Handles the opening of a new drawing from a file. Opens a file chooser dialog
     * that allows the user to select a drawing file to open. Currently, this dialog
     * accepts files with the .draw extension, but the real import needs to be
     * implemented.
     */
    private void doOpen() {
        JFileChooser chooser = new JFileChooser(getClass().getResource("").getFile());
        chooser.setDialogTitle("Open Graphic");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public String getDescription() {
                return "JDraw Graphic (*.draw)";
            }

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().endsWith(".draw");
            }
        });
        int res = chooser.showOpenDialog(this);

        if (res == JFileChooser.APPROVE_OPTION) {
            // read jdraw graphic
            System.out.println("read file " + chooser.getSelectedFile().getName());
        }
    }
}
