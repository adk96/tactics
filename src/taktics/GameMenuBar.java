package taktics;

import taktics.Main;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;

class GameMenuBar extends JMenuBar {

    //кнопки redo и undo, стачные чтобы иметь к ним доступ из GamePanel
    static JMenuItem undoItem, redoItem;

    GameMenuBar() {
        super();
        createFileMenu();
        createEditMenu();
        createViewMenu();
    }

    //создание меню File
    private void createFileMenu() {
        //создание меню
        JMenu fileMenu = new JMenu("File");
        //создание подпункта
        JMenuItem newItem = new JMenuItem("New");
        //добавление комбинации клавиш
        newItem.setMnemonic(KeyEvent.VK_N);
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        //действие при нажатии
        newItem.addActionListener(actionEvent -> Main.getGame().newGame());
        //добавление в меню
        fileMenu.add(newItem);
        JMenuItem openItem = new JMenuItem("Open...");
        openItem.setMnemonic(KeyEvent.VK_O);
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        openItem.addActionListener(actionEvent -> Main.loadGame());
        fileMenu.add(openItem);
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setMnemonic(KeyEvent.VK_S);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        saveItem.addActionListener(actionEvent -> {
            if (!Main.getGame().saveFile.isEmpty()) Main.saveGame(Main.getGame().saveFile);
            else Main.saveGameAs();
        });
        fileMenu.add(saveItem);
        JMenuItem saveAsItem = new JMenuItem("Save As...");
        saveAsItem.setMnemonic(KeyEvent.VK_S);
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK));
        saveAsItem.addActionListener(actionEvent -> Main.saveGameAs());
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        JMenuItem settingsItem = new JMenuItem("Settings");
        settingsItem.setMnemonic(KeyEvent.VK_T);
        settingsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_MASK));
        settingsItem.addActionListener(actionEvent -> new GameSettings());
        fileMenu.add(settingsItem);
        fileMenu.addSeparator();
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic(KeyEvent.VK_E);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK));
        exitItem.addActionListener(actionEvent -> System.exit(0));
        fileMenu.add(exitItem);
        add(fileMenu);
    }

    //создание меню Edit
    private void createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        undoItem = new JMenuItem("Undo");
        undoItem.setMnemonic(KeyEvent.VK_Z);
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
        undoItem.addActionListener(actionEvent -> {
            //если нажали Ctrl+Z, то делаем новый сейв, чтобы можно было вернуться и загружаем предыдущий
            if (!Main.canDo(Main.getGame().gameTurn + 1)) Main.saveToList(Main.getGame().gameTurn);
            Main.getState(Main.getGame().gameTurn - 1);
            Main.getGame().resetSelection();
            //делаем кнопки доступными или нет
            undoItem.setEnabled(Main.canDo(Main.getGame().gameTurn - 1));
            redoItem.setEnabled(Main.canDo(Main.getGame().gameTurn + 1));
        });
        editMenu.add(undoItem);
        redoItem = new JMenuItem("Redo");
        redoItem.setMnemonic(KeyEvent.VK_Y);
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_MASK));
        redoItem.addActionListener(actionEvent -> {
            Main.getState(Main.getGame().gameTurn + 1);
            Main.getGame().resetSelection();
            undoItem.setEnabled(Main.canDo(Main.getGame().gameTurn - 1));
            redoItem.setEnabled(Main.canDo(Main.getGame().gameTurn + 1));
        });
        editMenu.add(redoItem);
        add(editMenu);
    }

    //создание меню View
    private void createViewMenu() {
        JMenu viewMenu = new JMenu("View");
        JMenuItem logItem = new JMenuItem("Show Log");
        logItem.addActionListener(actionEvent -> new GameLog());
        viewMenu.add(logItem);
        add(viewMenu);
    }
}
