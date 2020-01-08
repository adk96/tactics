package taktics;
import taktics.Main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


//адаптер мыши
class GameMouseAdapter extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            //если мышью кликнули на ячейке, делаем выбранную ячейку красной
            Cell s = Main.getGame().getActiveCell(e.getX(), e.getY());
            if (s != null && s.exists) {
                s.selected = true;
                Main.getGame().firstCell = s;
                Main.getGame().lastCell = s;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            //если мышь отпустили
            boolean turn = false;
            for (int i = 0; i < Main.getGame().cells.size(); i++) {
                //если хоть одна ячейка была выбрана, то ход сделан
                if (Main.getGame().cells.get(i).selected) {
                    if (!turn) {
                        turn = true;
                        //сохранить в список
                        Main.saveToList(Main.getGame().gameTurn);
                        Main.deleteUndo(Main.getGame().gameTurn);
                        GameMenuBar.undoItem.setEnabled(true);
                        GameMenuBar.redoItem.setEnabled(false);
                        Main.getGame().gameTurn++;
                    }
                    Main.getGame().cells.get(i).exists = false;
                    Main.getGame().cells.get(i).selected = false;
                }
            }
            //добавить запись в лог
            if (turn && Main.getGame().firstCell != null && Main.getGame().lastCell != null) {
                Main.getGame().gameLog += "{" + Main.getGame().firstCell.col + "," + Main.getGame().firstCell.raw + "}-{" +
                        Main.getGame().lastCell.col + "," + Main.getGame().lastCell.raw + "}\n";
            }
            Main.getGame().firstCell = null;
            Main.getGame().lastCell = null;
            Main.getGame().vDir = 0;
            Main.getGame().hDir = 0;
            Main.getGame().checkWinner();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        try {
            //если мышью двигают с зажатой кнопкой, проверяем направление и выделяем новые ячейки
            Cell s = Main.getGame().getActiveCell(e.getX(), e.getY());
            //если выбрана ячейка до которой можно проложить путь от первой, то проложить этот путь
            if (s != null && Main.getGame().firstCell != null && Main.getGame().lastCell != null &&
                    Main.getGame().goodSelection(s, Main.getGame().firstCell) ) {
                Main.getGame().selectCells(Main.getGame().firstCell, s);
                Main.getGame().lastCell = s;
            }
        } catch (Exception ex) {
                ex.printStackTrace();
        }
    }

}