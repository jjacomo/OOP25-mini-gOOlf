package it.unibo.minigoolf.view.elements;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

/**
 * Factory to build any UI element such as buttons, labels and textfields.
 */
public final class UserInterfaceFactory {

    private static final Color ACCENT_COLOR = Color.WHITE;
    private static final Font MAIN_FONT = new Font("Comic Sans MS", Font.PLAIN, 24);
    private static final Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 22);
    private static final int WIDTH = 200;
    private static final int HEIGHT = 60;

    private UserInterfaceFactory() { }

    public static JButton createButton(final String text) {
        final JButton button = new JButton(text);
        button.setFont(MAIN_FONT);
        button.setBackground(ACCENT_COLOR);
        button.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return button;
    }

    public static JLabel createTitle(final String text) {
        final JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(ACCENT_COLOR);
        return label;
    }

    public static JTextField createTextField(final int columns) {
        final JTextField field = new JTextField(columns);
        field.setBackground(Color.GRAY);
        field.setForeground(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return field;
    }
    /**
     * Creates and shows a Yes/No confirmation dialog.
     *
     * @param parent  the parent component of the dialog (usually 'this' from the calling panel)
     * @param message the message to display
     * @param title   the title of the dialog window
     * @return the integer representing the user's choice (e.g., JOptionPane.YES_OPTION)
     */
    public static int showConfirmDialog(final Component parent, final String message, final String title) {
        return JOptionPane.showConfirmDialog(
                parent,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
    }
}


