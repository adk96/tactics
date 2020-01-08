package taktics;

import java.io.Serializable;

public class Cell implements Serializable, Cloneable {
    int x, y, raw, col;
    boolean exists, selected;

    Cell copy() throws CloneNotSupportedException {
        return (Cell) this.clone();
    }

    //класс ячейки
    Cell(int x, int y, int col, int raw) {
        // координаты относительно центра окна
        this.x = x;
        this.y = y;
        //столбец и строка
        this.col = ++col;
        this.raw = ++raw;
        //существует (т.е. не убрана)
        exists = true;
        //выбрана мышкой
        selected = false;
    }

}
