package main;

import javax.swing.*;

public class Main {

    public static JFrame window;
    public static GamePanel gamePanel;

    public static void main(String[] args) {
        window = new JFrame("Lost Keys of Eldoria");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
