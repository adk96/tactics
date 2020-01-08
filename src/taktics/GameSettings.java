package taktics;

import taktics.ColorChooserButton;
import taktics.Main;

import javax.swing.*;
import java.awt.*;

class GameSettings extends JFrame {

    //временные цвета
    private Color fieldColor = Main.getGame().fieldColor;
    private Color cellColor = Main.getGame().cellColor;
    private Color selectColor = Main.getGame().selectColor;

    GameSettings() {
        //создание окна настроек
        super("Settings");
        setSize(180, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setResizable(false);
        setLocationRelativeTo(Game.frame);

        //добавление элементов
        JLabel sizeLabel = new JLabel("Field size");

        JTextField sizeField = new JTextField();
        sizeField.setColumns(6);
        sizeField.setText("" + Main.getGame().fieldSize);

        //добавление кнопол для выбора цветов
        ColorChooserButton fieldColorButton = new ColorChooserButton(fieldColor);
        fieldColorButton.addColorChangedListener(newColor -> cellColor = newColor);

        ColorChooserButton cellColorButton = new ColorChooserButton(cellColor);
        cellColorButton.addColorChangedListener(newColor -> fieldColor = newColor);

        ColorChooserButton selectColorButton = new ColorChooserButton(selectColor);
        selectColorButton.addColorChangedListener(newColor -> selectColor = newColor);

        //добавление кнопок ОК и Отмена
        JButton okButton = new JButton("OK");
        okButton.addActionListener(actionEvent -> {
            //при нажатии ок
            try {
                int size = Integer.parseInt(sizeField.getText()); //считать размер поля
                if (size < 2 || size > 10) throw new Exception(); //он должен быть между 2 и 10
                Main.getGame().fieldSize = size; //если все норм, то применяем настройки
                Main.getGame().fieldColor = fieldColor;
                Main.getGame().cellColor = cellColor;
                Main.getGame().selectColor = selectColor;
                Main.getGame().newGame();
                dispose();
            } catch (java.lang.NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Enter an integer.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Field size must be between 2 and 10");
            }
        });
        JButton cancelButton = new JButton("Cancel");
        //при нажатии на отмену, просто зыкрыть окно
        cancelButton.addActionListener(actionEvent -> dispose());

        //добавление элементов в окно
        add(sizeLabel);
        add(sizeField);
        add(fieldColorButton);
        add(cellColorButton);
        add(selectColorButton);
        add(okButton);
        add(cancelButton);

        setVisible(true);
    }

}
