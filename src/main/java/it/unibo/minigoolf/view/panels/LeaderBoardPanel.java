package it.unibo.minigoolf.view.panels;

import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.view.elements.UserInterfaceFactory;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * One of the possibile scenes, this is the leaderboard, a table with
 * the scores of each player. 
 * * @author dani
 */ 
public final class LeaderBoardPanel extends JPanel {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    private final JPanel tableContainer;

    public LeaderBoardPanel(final NavigationController navController) {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.DARK_GRAY); // Colore neutro per ora, pronto per l'immagine

        final JPanel menuBox = new JPanel();
        menuBox.setLayout(new BoxLayout(menuBox, BoxLayout.Y_AXIS));
        menuBox.setOpaque(false);
        final JLabel title = UserInterfaceFactory.createTitle("LEADERBOARD");
        title.setAlignmentX(CENTER_ALIGNMENT);
        menuBox.add(title);
        menuBox.add(Box.createVerticalStrut(30));

        // Container which stores the scores
        this.tableContainer = new JPanel();
        this.tableContainer.setOpaque(false);
        this.tableContainer.setLayout(new BoxLayout(tableContainer, BoxLayout.Y_AXIS));
        menuBox.add(this.tableContainer);

        menuBox.add(Box.createVerticalStrut(40));

        // Go back to menu button
        final JButton backButton = UserInterfaceFactory.createButton("BACK TO MENU");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> navController.goToMainMenu());
        menuBox.add(backButton);

        this.add(menuBox, new GridBagConstraints());
    }

    /**
     * Updates the leaderboard with the latest scores.
     * @param scores the cumulative scores from the last match
     */
    public void updateScores(final Map<String, Integer> scores) {
        this.tableContainer.removeAll();

        if (scores == null || scores.isEmpty()) {
            final JLabel emptyLabel = new JLabel("EMPTY!");
            emptyLabel.setForeground(Color.WHITE);
            emptyLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 25));
            this.tableContainer.add(emptyLabel);
        } else {
            // To order the scores
            final List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
            sortedScores.sort(Map.Entry.comparingByValue());

            final JPanel table = new JPanel(new GridLayout(sortedScores.size() + 1, 2, 40, 10));
            table.setOpaque(false);
            
            table.add(UserInterfaceFactory.createTitle("PLAYER"));
            table.add(UserInterfaceFactory.createTitle("TOTAL SHOTS"));
            
            // Cosmetic: adds the medal icons, for now those are just emojis, TODO: Try it in different PCs
            int rank = 0;
            for (final Map.Entry<String, Integer> entry : sortedScores) {
                String nameText = entry.getKey();
                if (rank == 0) nameText += " 🥇";
                else if (rank == 1) nameText += " 🥈";
                else if (rank == 2) nameText += " 🥉";

                final JLabel nameLabel = new JLabel(nameText);
                nameLabel.setForeground(Color.WHITE);
                nameLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));

                final JLabel scoreLabel = new JLabel(String.valueOf(entry.getValue()));
                scoreLabel.setForeground(Color.WHITE);
                scoreLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));

                table.add(nameLabel);
                table.add(scoreLabel);
                rank++;
            }
            this.tableContainer.add(table);
        }
        this.revalidate();
        this.repaint();
    }
}





