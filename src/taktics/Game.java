package taktics;

import taktics.Main;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Game implements Serializable, Cloneable {

    static int CELL_SIZE = 48; //размер ячейки в пикселях

    public static GameFrame frame; //окно игры

    public String saveFile = ""; //путь к файлу, в который последний раз сохранялась игра


    public int gameTurn; //номер хода
    String gameLog; //лог ходов

    public ArrayList<Cell> cells; //ячейки

    Cell firstCell, lastCell; //первая и последняя выделенные ячейки
    int vDir, hDir; //направление выбора по вертикали и горизонтали

    Color fieldColor, cellColor, selectColor; //цвета квадратов и кругов
    int fieldSize; //размер поля

    public Game() {
        //создается окно игры
        frame = new GameFrame();
        //инициализация массива
        cells = new ArrayList<>();
        //сброс настроек по умолчанию
        resetSettings();
        //новая игра
        newGame();
        //таймер для перерисовки
        new Timer(50, actionEvent -> frame.panel.repaint()).start();
    }

    public Game copy() {
        try {
            Game g = (Game) this.clone();
            g.cells = new ArrayList<>();
            for (Cell c: cells) g.cells.add(c.copy());
            
            
            return g;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    //инициализвания новой игры
    void newGame() {
        //очистка ячеек
        cells.clear();
        //сброс переменных
        gameTurn = 0;
        gameLog = "";
        GameMenuBar.undoItem.setEnabled(false);
        GameMenuBar.redoItem.setEnabled(false);

        CELL_SIZE = frame.panel.getHeight() / (fieldSize * 2);
        if (fieldSize * 2 * CELL_SIZE > frame.panel.getWidth()) {
            CELL_SIZE = frame.panel.getWidth() / (fieldSize * 2);
        }

        if (CELL_SIZE % 2 != 0) CELL_SIZE += 1;

        //размещение ячеек на поле
        float dx, dy, n;
        for (int row = 0; row < fieldSize * 2 - 1; row++) {
            dy = -((fieldSize - .5f) * (CELL_SIZE + 2)) + row * (CELL_SIZE + 2);

            if (row < fieldSize) n = fieldSize + row;
            else if (row == fieldSize) n = fieldSize * 2 - 2;
            else n = fieldSize * 2 - (row - fieldSize + 2);

            for (int col = 0; col < n; col++) {
                dx = -((n / 2 - .5f) * (CELL_SIZE + 2)) + col * (CELL_SIZE + 2);
                cells.add(new Cell((int) dx, (int) dy, col, row));
            }
        }

        Main.initGames(cells.size());
    }

    //проверка на победу, если все ячейки убраны, то игра заканчивается и начинается новая
    void checkWinner() {
        int left = 0;
        for (Cell c : cells) {
            if (c.exists) left++;
        }
        if (left <= 1) {
            JOptionPane.showMessageDialog(null, "Player " + ((gameTurn + left) % 2 + 1) + " win.");
            newGame();
        }
    }

    //очистка выбора
    void resetSelection() {
        for (Cell c : cells) {
            if (c.selected) c.selected = false;
        }
    }

    //сброс настроек
    private void resetSettings() {
        fieldColor = Color.BLUE;
        cellColor = Color.GREEN;
        selectColor = Color.RED;
        fieldSize = 5;
    }

    void selectCells(Cell c1, Cell c2) {
        resetSelection();
        int dirX, dirY, x = frame.panel.getWidth() / 2 + c1.x, y = frame.panel.getHeight() / 2 + c1.y;
        dirX = Integer.compare(c2.x, c1.x);
        dirY = Integer.compare(c2.raw, c1.raw);
        Cell c;
        do  {
            c = getActiveCell(x, y);
            if (c.exists) {
                c.selected = true;
                x += (CELL_SIZE + 2) / 2 * dirX;
                y += (CELL_SIZE + 2) * dirY;
            } else {
                break;
            }
        } while (!c.equals(c2));
    }

    boolean goodSelection(Cell c1, Cell c2) {
        return ((c1.raw == c2.raw) || (Math.abs((c2.x - c1.x) * 2 / CELL_SIZE) == Math.abs(c2.raw - c1.raw)));
    }

    //возвращает ячейку на которую показывает курсор
    Cell getActiveCell(int mX, int mY) {
        for (Cell s : cells) {
            //если кооринаты мыши попадают на одну из ячеек
            if (new Rectangle(frame.panel.getWidth() / 2 + s.x,
                    frame.panel.getHeight() / 2 + s.y,
                    CELL_SIZE, CELL_SIZE).contains(mX, mY)
            )
                return s;
        }
        return null;
    }

}
