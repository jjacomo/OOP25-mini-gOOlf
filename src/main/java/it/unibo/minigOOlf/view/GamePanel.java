package it.unibo.minigOOlf.view;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;


public class GamePanel extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(34, 139, 34)); 
        JLabel prova = new JLabel("Testo di prova");
        this.add(prova);
        

    }
}