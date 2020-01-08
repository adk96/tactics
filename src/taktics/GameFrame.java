package taktics;

import taktics.Main;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class GameFrame extends JFrame {

    static JLabel status; //надпись в статусбаре

    GamePanel panel; //по сути, холст
    GameMenuBar menuBar; //верхнее меню

    GameFrame() {
        //выполняется конструктор JFrame
        super("Java Taktics");
        //размер окна
        setSize(800, 600);
        //закрывать всю программу при закрытии главного окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //расположение по центру экрана
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        //создание и добавление элементов окна
        //"холст"
        panel = new GamePanel();
        add(panel);
        //статус бар
        JPanel statusBar = new JPanel();
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(statusBar, BorderLayout.SOUTH);
        statusBar.setPreferredSize(new Dimension(getWidth(), 18));
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
        status = new JLabel();
        status.setHorizontalAlignment(SwingConstants.LEFT);
        statusBar.add(status);
        //менюбар
        menuBar = new GameMenuBar();
        setJMenuBar(menuBar);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                try {
                    Game.CELL_SIZE = panel.getHeight() / (Main.getGame().fieldSize * 2);
                    if (Main.getGame().fieldSize * 2 * Game.CELL_SIZE > panel.getWidth()) {
                        Game.CELL_SIZE = panel.getWidth() / (Main.getGame().fieldSize * 2);
                    }

                    if (Game.CELL_SIZE % 2 != 0) Game.CELL_SIZE += 1;

                    float dx, dy, n;
                    int p = 0;
                    for (int row = 0; row < Main.getGame().fieldSize * 2 - 1; row++) {
                        dy = -((Main.getGame().fieldSize - .5f) * (Game.CELL_SIZE + 2)) + row * (Game.CELL_SIZE + 2);

                        if (row < Main.getGame().fieldSize) n = Main.getGame().fieldSize + row;
                        else if (row == Main.getGame().fieldSize) n = Main.getGame().fieldSize * 2 - 2;
                        else n = Main.getGame().fieldSize * 2 - (row - Main.getGame().fieldSize + 2);

                        for (int col = 0; col < n; col++) {
                            dx = -((n / 2 - .5f) * (Game.CELL_SIZE + 2)) + col * (Game.CELL_SIZE + 2);
                            Main.getGame().cells.get(p).x = (int) dx;
                            Main.getGame().cells.get(p).y = (int) dy;
                            p++;
                        }
                    }
                } catch (NullPointerException e) {}
            }
        });

        //показать окно
        setVisible(true);
    }

}
