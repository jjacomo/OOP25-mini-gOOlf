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
import it.unibo.minigoolf.view.elements.UserInterfaceFactory;

/**
 * One of the possibile scenes, this is the menu where the user can choose the n° of
 * players, therefore it starts a singleplayer or a multiplayer match.
 * * @author dani
 * TODO: Ricordati di usare la UI Factory!Questa classe è stata creata prima, da aggiustare!
 */
public final class NewGamePanel extends JPanel {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;
    private static final int MAX_PLAYERS = 10;
    private static final int COLUMNS_COUNT = 2;
    private static final int MARGINS = 20;

    private final List<JTextField> nameFields = new ArrayList<>();
    private final JPanel namesContainer;
    private final JTextField numInput;

    public NewGamePanel(final NavigationController navigationController) {
        this.setLayout(new BorderLayout(MARGINS, MARGINS));
        this.setBackground(Color.DARK_GRAY);
        this.setBorder(BorderFactory.createEmptyBorder(MARGINS, MARGINS, MARGINS, MARGINS));

        final JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        header.setOpaque(false);

        // Back to menu button
        final JButton backButton = new JButton("BACK");
        backButton.addActionListener(e -> navigationController.goToMainMenu());
        header.add(backButton);
        header.add(javax.swing.Box.createHorizontalStrut(50)); 

        // Label "N° of players:"
        final JLabel instructionLabel = new JLabel("N° of players: ");
        instructionLabel.setForeground(Color.WHITE);
        header.add(instructionLabel);
        this.numInput = new JTextField("1", 3);
        header.add(numInput);
        
        //OK Button
        final JButton confirmNumButton = new JButton("OK");
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

        // Start match button, it starts a new single/multiplayermatch
        final JButton startButton = new JButton("START MATCH");
        this.add(startButton, BorderLayout.SOUTH);
        startButton.addActionListener(e -> {
            // Creates the list of players with given names
            final List<String> playerNames = new ArrayList<>();
            for (int i = 0; i < nameFields.size(); i++) {
                String name = nameFields.get(i).getText().trim();
                
                // Adds a default name if no name is provided
                if (name.isEmpty()) {
                    name = "Player " + (i + 1);
                }
                playerNames.add(name);
            }
            navigationController.setupMatchAndStart(playerNames);
        });

    }

    /**
     * This method initializes the number of text fields for the names of the players.
     * 
     * @param n number of players.
     */
    private void generateFields(final int n) {
        namesContainer.removeAll();
        nameFields.clear();

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(MARGINS, MARGINS, MARGINS, MARGINS); // The space within the text fields
        gbc.anchor = GridBagConstraints.WEST;

        for (int i = 0; i < n; i++) {
            final int row = i / COLUMNS_COUNT;
            final int col = i % COLUMNS_COUNT;

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

