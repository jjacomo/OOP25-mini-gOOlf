package it.unibo.minigoolf.view.elements;

import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link UserInterfaceFactory}.
 * Verifies that all UI components are built with the correct properties,
 * fonts, and colors as defined by the factory constants.
 * 
 * @author dbakko
 */
class UserInterfaceFactoryTest {

    private static final Color ACCENT_COLOR = Color.WHITE;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 60;
    private static final float MAINFONT = 24;
    private static final float TITLEFONT = 22;
    private static final float LABELFONT = 18;
    private static final String TEXTFONTASSERT = "The font should be initialized";

    @Test
    void testCreateButton() {
        final String text = "START GAME";
        final JButton button = UserInterfaceFactory.createButton(text);

        assertNotNull(button, "The button should not be null");
        assertEquals(text, button.getText(), "The button text should match the input");
        assertEquals(ACCENT_COLOR, button.getBackground(), "The background color should be the ACCENT_COLOR");

        // Check the dimensions
        final Dimension expectedSize = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
        assertEquals(expectedSize, button.getPreferredSize(), "The button should have the correct preferred size");

        // Check the font properties
        final Font font = button.getFont();
        assertNotNull(font, TEXTFONTASSERT);
        assertEquals(Font.PLAIN, font.getStyle(), "Button font should be PLAIN");
        assertEquals(MAINFONT, font.getSize(), "Button font size should be 24");
    }

    @Test
    void testCreateTitle() {
        final String titleText = "MINIGOOLF";
        final JLabel title = UserInterfaceFactory.createTitle(titleText);

        assertNotNull(title, "The title label should not be null");
        assertEquals(titleText, title.getText(), "The title text should match the input");
        assertEquals(ACCENT_COLOR, title.getForeground(), "The foreground color should be the ACCENT_COLOR");

        // Check the font (title must be BOLD and size 22)
        final Font font = title.getFont();
        assertNotNull(font, TEXTFONTASSERT);
        assertEquals(Font.BOLD, font.getStyle(), "Title font should be BOLD");
        assertEquals(TITLEFONT, font.getSize(), "Title font size should be 22");
    }

    @Test
    void testCreateLabel() {
        final String labelText = "Player 1:";
        final JLabel label = UserInterfaceFactory.createLabel(labelText);

        assertNotNull(label, "The label should not be null");
        assertEquals(labelText, label.getText(), "The label text should match the input");
        assertEquals(ACCENT_COLOR, label.getForeground(), "The foreground color should be the ACCENT_COLOR");

        // Check the font (normal label must be PLAIN and size 18)
        final Font font = label.getFont();
        assertNotNull(font, TEXTFONTASSERT);
        assertEquals(Font.PLAIN, font.getStyle(), "Label font should be PLAIN");
        assertEquals(LABELFONT, font.getSize(), "Label font size should be 18");
    }

    @Test
    void testCreateTextField() {
        final int columns = 15;
        final JTextField textField = UserInterfaceFactory.createTextField(columns);

        assertNotNull(textField, "The text field should not be null");
        assertEquals(columns, textField.getColumns(), "The number of columns should match the input");

        // Check colors (white background and black text for readability)
        assertEquals(Color.WHITE, textField.getBackground(), "TextField background should be WHITE");
        assertEquals(Color.BLACK, textField.getForeground(), "TextField foreground should be BLACK");

        // Check the font (it uses the same font as the labels)
        final Font font = textField.getFont();
        assertNotNull(font, TEXTFONTASSERT);
        assertEquals(Font.PLAIN, font.getStyle(), "TextField font should be PLAIN");
        assertEquals(LABELFONT, font.getSize(), "TextField font size should be 18");

        // Check if a line border was applied correctly
        final Border border = textField.getBorder();
        assertNotNull(border, "TextField should have a border");
        assertTrue(border instanceof LineBorder, "TextField border should be a LineBorder");

        final LineBorder lineBorder = (LineBorder) border;
        assertEquals(Color.GRAY, lineBorder.getLineColor(), "The border color should be GRAY");
    }
}
