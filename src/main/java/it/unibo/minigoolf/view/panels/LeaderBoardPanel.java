package it.unibo.minigoolf.view.panels;

import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.view.elements.UserInterfaceFactory;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.Serial;

/**
 * One of the possibile scenes, this is the leaderboard, a table with
 * the scores for each player. 
 * 
 * @author dbakko
 */ 
public final class LeaderBoardPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final int HEIGHT = 40;
    private static final String PATH_BG = "/background/leaderboard_bg.png";
    private static final String PATH_MEDALG = "/medals/gold.png";
    private static final String PATH_MEDALS = "/medals/silver.png";
    private static final String PATH_MEDALB = "/medals/bronze.png";

    /** The background image of the leaderboard panel. */
    private transient Image backgroundImage;

    /** The container panel that holds the dynamically generated scores table. */
    private final JPanel tableContainer;

    /**
     * Constructs the leaderboard panel, initializing the UI components and the background.
     *
     * @param navController the navigation controller used to return to the main menu
     */
    public LeaderBoardPanel(final NavigationController navController) {
        this.setLayout(new GridBagLayout());

        // To import the background image
        final java.net.URL bgUrl = getClass().getResource(PATH_BG);
        if (bgUrl != null) {
            final ImageIcon bgIcon = new ImageIcon(bgUrl);
            this.backgroundImage = bgIcon.getImage();
        } else {
            this.setBackground(Color.DARK_GRAY);
        }

        final JPanel menuBox = new JPanel();
        menuBox.setLayout(new BoxLayout(menuBox, BoxLayout.Y_AXIS));
        menuBox.setOpaque(false);
        menuBox.add(Box.createVerticalStrut(HEIGHT));

        // Container which stores the scores
        this.tableContainer = new JPanel();
        this.tableContainer.setOpaque(false);
        this.tableContainer.setLayout(new BoxLayout(tableContainer, BoxLayout.Y_AXIS));
        menuBox.add(this.tableContainer);
        menuBox.add(Box.createVerticalStrut(HEIGHT));

        // Go back to menu button
        final JButton backButton = UserInterfaceFactory.createButton("BACK TO MENU");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> navController.goToMainMenu());
        menuBox.add(backButton);
        this.add(menuBox, new GridBagConstraints());
    }

    /**
     * Updates the leaderboard with the latest scores.
     * 
     * @param scores the cumulative scores from the last match
     */
    public void updateScores(final Map<String, Integer> scores) {
        this.tableContainer.removeAll();

        if (scores == null || scores.isEmpty()) {
            final JLabel emptyLabel = UserInterfaceFactory.createLabel("EMPTY!");
            this.tableContainer.add(emptyLabel);
        } else {
            // To order the scores
            final List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
            sortedScores.sort(Map.Entry.comparingByValue());

            final JPanel table = new JPanel(new GridLayout(sortedScores.size() + 1, 2, 40, 10));
            table.setOpaque(false);

            table.add(UserInterfaceFactory.createTitle("PLAYER"));
            table.add(UserInterfaceFactory.createTitle("TOTAL SHOTS"));

            int rank = 0;
            for (final Map.Entry<String, Integer> entry : sortedScores) {
                final JLabel nameLabel = UserInterfaceFactory.createLabel(entry.getKey());

                // Medals icons near 1,2,3 player
                String imagePath = "";
                if (rank == 0) {
                    imagePath = PATH_MEDALG;
                } else if (rank == 1) {
                    imagePath = PATH_MEDALS;
                } else if (rank == 2) {
                    imagePath = PATH_MEDALB;
                }

                if (!imagePath.isEmpty()) {
                    final java.net.URL medalUrl = getClass().getResource(imagePath);
                    if (medalUrl != null) {
                        final ImageIcon originalIcon = new ImageIcon(medalUrl);
                        final Image scaledImg = originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                        nameLabel.setIcon(new ImageIcon(scaledImg));
                        nameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
                    } else {
                        if (rank == 0) {
                            nameLabel.setText(entry.getKey() + " (1st)");
                        } else if (rank == 1) {
                            nameLabel.setText(entry.getKey() + " (2nd)");
                        } else if (rank == 2) {
                            nameLabel.setText(entry.getKey() + " (3rd)");
                        }
                    }
                }

                final JLabel scoreLabel = UserInterfaceFactory.createLabel(String.valueOf(entry.getValue()));
                table.add(nameLabel);
                table.add(scoreLabel);
                rank++;
            }

            this.tableContainer.add(table);
        }
        this.revalidate();
        this.repaint();
    }

    // To scale the background image properly
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (this.backgroundImage != null) {
            g.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}





