package taktics;

import taktics.Game;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Main {

    private static Game game;
    private static ArrayList<Game> games;

    public static void initGames(int n) { 
        if (games != null) games.clear();
        games = new ArrayList<>();
        for (int i=0; i<n; i++) games.add(null);
    }

    public static void clearList() {
        games.clear();
    }

    public static boolean canDo(int turn) {
        return (turn >= 0 && turn < games.size() && games.get(turn) != null);
    }

    public static void saveToList(int turn) {
        games.set(turn, getGame().copy());
    }

    public static void getState(int turn) {
        game = games.get(turn);
    }

    public static void deleteUndo(int turn) {
        for (int i = games.size() - 1; i > turn + 1; i--) {
            games.set(i, null);
        }
    }

    public static void main(String[] args) {
        game = new Game();
    }


    //возвращает объект Game для доступа к нему из других классов
    public static Game getGame() {
        return game;
    }

    public static void loadGame() {
        //вывод окна выбора файла для загрузки игры
        JFileChooser fileopen = new JFileChooser();
        fileopen.showDialog(null, "Load game");
        loadGame(fileopen.getSelectedFile().getAbsolutePath());
    }

    public static void loadGame(String path) {
        try {
            //чтение объекта Game из файла
            FileInputStream fin = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fin);
            game = (Game) in.readObject();
            in.close();
            fin.close();
        } catch (java.lang.NullPointerException e) {
            //если пользователь нажмет на отмену, ничего не делать
        } catch (Exception e) {
            //показать сообщение об ошибке, если не удаль прочитать файл
            JOptionPane.showMessageDialog(Game.frame, "Failed to load\n" + e.toString());
        }
    }

    public static void saveGame(String path) {
        try {
            //запись объекта Game в файл
            FileOutputStream fout = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(game);
            out.close();
            fout.close();
        } catch (java.lang.NullPointerException e) {
        } catch (Exception e) {
            JOptionPane.showMessageDialog(Game.frame, "Failed to save\n" + e.toString());
        }
    }

    public static void saveGameAs() {
        JFileChooser fileopen = new JFileChooser();
        fileopen.setSelectedFile(new File("game.sav"));
        fileopen.showDialog(null, "Save game");
        game.saveFile = fileopen.getSelectedFile().getAbsolutePath();
        saveGame(game.saveFile);
    }

}
