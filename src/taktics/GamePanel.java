package taktics;

import taktics.Main;

import javax.swing.*;
import java.awt.*;

class GamePanel extends JPanel {

    GamePanel() {
        GameMouseAdapter mouseAdapter = new GameMouseAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    //заполнение фона белым цветом
    private void fillBackground(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    //рисование ячеек
    private void drawCells(Graphics g) {
        for (Cell s : Main.getGame().cells) {
            int x = getWidth() / 2 + s.x;
            int y = getHeight() / 2 + s.y;
            g.setColor(Main.getGame().fieldColor);
            g.fillRect(x, y, Game.CELL_SIZE, Game.CELL_SIZE);
            if (s.exists) {
                if (!s.selected) {
                    g.setColor(Main.getGame().cellColor);
                } else {
                    g.setColor(Main.getGame().selectColor);
                }
                g.fillOval(x + 2, y + 2, (Game.CELL_SIZE - 4), (Game.CELL_SIZE - 4));
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        fillBackground(g);
        drawCells(g);
        GameFrame.status.setText("Player " + (Main.getGame().gameTurn % 2 + 1) + " | Turn: "+Main.getGame().gameTurn);
    }

}
