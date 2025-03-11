package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window=new JFrame("Adventure Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setResizable(false); //so that the window cannot be resized

        GamePanel gamePanel=new GamePanel();
        window.add(gamePanel);
        window.pack(); //Adjusts the window size to fit the gamePanel

        window.setLocationRelativeTo(null); // to display the window at the centre by default
        window.setVisible(true);
        
        
        gamePanel.setupGame();
        gamePanel.startGameThread();

    }
}