package it.unibo.minigoolf.view.elements;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory to build any UI element such as buttons, labels and textfields.
 * 
 *  @author dbakko
 */
public final class UserInterfaceFactory {

    private static final Logger LOGGER = Logger.getLogger(UserInterfaceFactory.class.getName());

    private static final Color ACCENT_COLOR = Color.WHITE;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 60;
    private static final float MAINFONT = 24;
    private static final float TITLEFONT = 22;
    private static final float LABELFONT = 18;
    private static final String MAINFONT_PATH = "/font/upheavtt.ttf";
    private static final String ALTFONT = "SansSerif";
    private static Font mainFont;
    private static Font titleFont;
    private static Font labelFont;

    static {
        boolean isFontLoaded = false;

        try (InputStream is = UserInterfaceFactory.class.getResourceAsStream(MAINFONT_PATH)) {
            if (is != null) {
            final Font baseFont = Font.createFont(Font.TRUETYPE_FONT, is);
            mainFont = baseFont.deriveFont(Font.PLAIN, MAINFONT);
            titleFont = baseFont.deriveFont(Font.BOLD, TITLEFONT);
            labelFont = baseFont.deriveFont(Font.PLAIN, LABELFONT);
            isFontLoaded = true;
        } else {
                LOGGER.log(Level.WARNING, "Font file not found in resources! Using fallback fonts.");
            }
        } catch (final FontFormatException | IOException e) {
            LOGGER.log(Level.WARNING, "Error loading the custom font. Using fallback fonts.", e);
        }
        if (!isFontLoaded) {
            mainFont = new Font(ALTFONT, Font.PLAIN, (int) MAINFONT);
            titleFont = new Font(ALTFONT, Font.BOLD, (int) TITLEFONT);
            labelFont = new Font(ALTFONT, Font.PLAIN, (int) LABELFONT);
        }
    }

    private UserInterfaceFactory() { }

    /**
     * Creates a standard button with the default factory font and size.
     * 
     * @param text the text to display on the button
     * 
     * @return the formatted JButton
     */
    public static JButton createButton(final String text) {
        final JButton button = new JButton(text);
        button.setFont(mainFont);
        button.setBackground(ACCENT_COLOR);
        button.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        return button;
    }

    /**
     * Creates a title label with a large bold font.
     * 
     * @param text the title text
     * 
     * @return the formatted title JLabel
     */
    public static JLabel createTitle(final String text) {
        final JLabel label = new JLabel(text);
        label.setFont(titleFont);
        label.setForeground(ACCENT_COLOR);
        return label;
    }

    /**
     * Creates a standard label with the default factory font size.
     * 
     * @param text the label text
     * 
     * @return the formatted JLabel
     */
    public static JLabel createLabel(final String text) {
        final JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setForeground(ACCENT_COLOR);
        return label;
    }

    /**
     * Creates a standard text field with a predefined font size and 
     * a specific number of columns to determine its preferred width.
     * 
     * @param columns the number of columns to use to calculate the preferred width
     * 
     * @return the formatted JTextField
     */
    public static JTextField createTextField(final int columns) {
        final JTextField field = new JTextField(columns);
        field.setFont(labelFont);
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return field;
    }

    /**
     * Creates and shows a Yes/No confirmation dialog with English button labels,
     * regardless of the system locale.
     *
     * @param parent  the parent component of the dialog (usually 'this' from the calling panel)
     * @param message the message to display
     * @param title   the title of the dialog window
     * @return {@link JOptionPane#YES_OPTION} or {@link JOptionPane#NO_OPTION}
     */
    public static int showConfirmDialog(final Component parent, final String message, final String title) {
        final Object[] options = {"Yes", "No"};
        final int result = JOptionPane.showOptionDialog(
                parent,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);
        // showOptionDialog returns the index of the chosen option (0=Yes, 1=No)
        // or CLOSED_OPTION (-1) if the dialog is dismissed.
        if (result == 0) {
            return JOptionPane.YES_OPTION;
        } else if (result == 1) {
            return JOptionPane.NO_OPTION;
        }
        return JOptionPane.CLOSED_OPTION;
    }
}
