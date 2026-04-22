package it.unibo.minigoolf.view.elements;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

public final class UserInterfaceFactory {

    private static final Color ACCENT_COLOR = Color.WHITE;
    private static final Font MAIN_FONT = new Font("Comic Sans MS", Font.PLAIN, 14);
    private static final Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 22);

    private UserInterfaceFactory() {}

    public static JButton createButton(String text) {
        JButton button= new JButton(text);
        button.setFont(MAIN_FONT);
        button.setBackground(ACCENT_COLOR);
        button.setPreferredSize(new Dimension(0, 50));
        return button;
    }

    public static JLabel createTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(ACCENT_COLOR);
        return label;
    }

    public static JTextField createTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return field;
    }
}

