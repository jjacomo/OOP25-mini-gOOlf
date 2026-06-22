package it.unibo.minigoolf.view.panels;

import java.util.ArrayList;
import java.util.List;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.Serial;
import java.awt.GridBagConstraints;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
 * 
 * @author dbakko
 */
public final class NewGamePanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final int MAX_PLAYERS = 10;
    private static final int COLUMNS_COUNT = 2;
    private static final int MARGINS = 20;
    private static final String PATH = "/background/newgame_bg.png";

    private transient Image backgroundImage;

    /** List of text fields used to input the names of the players. */
    private final List<JTextField> nameFields = new ArrayList<>();

    /** Container panel that holds the generated player name input fields. */
    private final JPanel namesContainer;

    /** Text field used to input the desired number of players. */
    private final JTextField numInput;

    /**
     * Constructs the panel for setting up a new game.
     * 
     * @param navigationController the controller used to handle scene transitions
     */
    public NewGamePanel(final NavigationController navigationController) {

        final java.net.URL bgUrl = getClass().getResource(PATH);
        if (bgUrl != null) {
            final ImageIcon bgIcon = new ImageIcon(bgUrl);
            this.backgroundImage = bgIcon.getImage();
        } else {
            this.setBackground(Color.DARK_GRAY);
        }

        this.setLayout(new BorderLayout(MARGINS, MARGINS));
        this.setBorder(BorderFactory.createEmptyBorder(MARGINS, MARGINS, MARGINS, MARGINS));

        // Top panel
        final JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        header.setOpaque(false);

        // Label "N° of players:"
        final JLabel instructionLabel = UserInterfaceFactory.createTitle("Number of players: ");
        instructionLabel.setForeground(Color.WHITE);
        header.add(instructionLabel);
        this.numInput = UserInterfaceFactory.createTextField(3);
        header.add(numInput);

        // OK Button
        final JButton confirmNumButton = UserInterfaceFactory.createButton("OK");
        header.add(confirmNumButton);

        this.add(header, BorderLayout.NORTH);

        // Using the GridBagLayout
        this.namesContainer = new JPanel(new GridBagLayout());
        this.namesContainer.setOpaque(false);

        // If someone increases the max n° of playes, to scroll the text fields
        final JScrollPane scrollPane = new JScrollPane(namesContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        this.add(scrollPane, BorderLayout.CENTER);

        // To limit the number of players
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

        // Bottom panel
        final JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        bottomPanel.setOpaque(false);

        // Back to main menu button
        final JButton backButton = UserInterfaceFactory.createButton("BACK");
        backButton.addActionListener(e -> navigationController.goToMainMenu());
        bottomPanel.add(backButton);

        // Start match button
        final JButton startButton = UserInterfaceFactory.createButton("START MATCH");
        startButton.addActionListener(e -> {
            final List<String> playerNames = new ArrayList<>();
            for (int i = 0; i < nameFields.size(); i++) {
                String name = nameFields.get(i).getText().trim();
                if (name.isEmpty()) {
                    name = "Player " + (i + 1);
                }
                playerNames.add(name);
            }
            navigationController.setupMatchAndStart(playerNames);
        });
        bottomPanel.add(startButton);

        this.add(bottomPanel, BorderLayout.SOUTH);
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
            // If no name is chosen, default names "player + i" are created
            final JLabel label = UserInterfaceFactory.createLabel("Player " + (i + 1) + ": ");
            final JTextField field = UserInterfaceFactory.createTextField(12);
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

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        if (this.backgroundImage != null) {
            g.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}

