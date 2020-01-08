package taktics;

import taktics.Main;

import javax.swing.*;

public class GameLog extends JFrame {

    //окно со списком сделанных ходов
    GameLog() {
        super("Log");
        setSize(200,400);
        //при закрытии, закрывать только это окно
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //расположить поверх главного окна
        setLocationRelativeTo(Game.frame);
        //запретить изменение размера
        setResizable(false);

        //добавить неизменяемое текстовое поле и игровым логом
        JTextArea area = new JTextArea();
        area.setText(Main.getGame().gameLog);
        area.setEditable(false);
        //добавить скроллбар, если текст не влазит в окно
        JScrollPane scroll = new JScrollPane(area);
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        add(scroll);

        setVisible(true);
    }

}
