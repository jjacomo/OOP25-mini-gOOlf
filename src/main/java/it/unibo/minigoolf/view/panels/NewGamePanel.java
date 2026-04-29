package it.unibo.minigoolf.view.panels;

import java.util.ArrayList;
import java.util.List;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;

/**
 * One of the possibile scenes, this is the menu where the user can choose the n° of
 * players, therefore it starts a singleplayer or a multiplayer match.
 * * @author dani
 * * TODO: only when the core singleplayer is working! (not really lol, done some part of it before ooops)
 */
public final class NewGamePanel extends JPanel {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;
    private static final int MAX_PLAYERS = 10;
    private static final int COLUMNS_COUNT = 2;

    private final List<JTextField> nameFields = new ArrayList<>();
    private final JPanel namesContainer;
    private final JTextField numInput;

    public NewGamePanel(final NavigationController navigationController) {
        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(Color.DARK_GRAY);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // N° of players
        final JPanel header = new JPanel();
        header.setOpaque(false);
        final JLabel instructionLabel = new JLabel("N° of players: ");
        instructionLabel.setForeground(Color.WHITE);
        header.add(instructionLabel);
        // Here the user inputs the n° and confirms with the button "OK"
        this.numInput = new JTextField("1", 3);
        final JButton confirmNumButton = new JButton("OK");
        header.add(numInput);
        header.add(confirmNumButton);
        this.add(header, BorderLayout.NORTH);

        // Using the GridBagLayout
        this.namesContainer = new JPanel(new GridBagLayout());
        this.namesContainer.setOpaque(false);

        // If someone increases the max n° of playes, to scroll the text fields (maybe not necessary)
        final JScrollPane scrollPane = new JScrollPane(namesContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        this.add(scrollPane, BorderLayout.CENTER);

        // Start match button, for now it doesn't do anything.
        final JButton startButton = new JButton("START MATCH");
        this.add(startButton, BorderLayout.SOUTH);

        // To limit the number of players, but could be redefined in the future
        confirmNumButton.addActionListener(e -> {
            try {
                final int n = Integer.parseInt(numInput.getText());
                if (n > 0 && n <= MAX_PLAYERS) { 
                    generateFields(n);
                } else {
                    JOptionPane.showMessageDialog(this, "Insert a number from 1 to 10.");
                }
            } catch (final NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "The number is not valid.");
            }
        });
        /* 
         Here I need something to pass the names to @fedesparvo1-a11y to initialize the game!
        */
        // One default field always present (single player)
        generateFields(1);
    }

    /**
     * This method initializes the number of text fields for the names of the players.
     * @param n number of players.
     */
    private void generateFields(int n) {
        namesContainer.removeAll();
        nameFields.clear();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20); // The space within the text fields
        gbc.anchor = GridBagConstraints.WEST;

        for (int i = 0; i < n; i++) {
            int row = i / COLUMNS_COUNT;
            int col = i % COLUMNS_COUNT;

            final JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
            p.setOpaque(false);

            final JLabel label = new JLabel("Player " + (i + 1) + ": ");
            label.setForeground(Color.WHITE);

            final JTextField field = new JTextField(12);
            nameFields.add(field);
            
            p.add(label);
            p.add(field);

            gbc.gridx = col;
            gbc.gridy = row;
            namesContainer.add(p, gbc);
        }

        namesContainer.revalidate();
        namesContainer.repaint();
    }
}

