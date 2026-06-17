package it.unibo.minigoolf.view.panels;

import it.unibo.minigoolf.view.elements.UserInterfaceFactory;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Overlay panel displayed at the end of a hole.
 * Shows a sorted leaderboard of the players' shots.
 * 
 * @author dbakko
 */
public final class MidLeaderBoardPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final int TINT = 150;
    private static final int HEIGHT = 30;

    /**
     * Constructs a new overlay panel displaying the current scores after a hole is completed.
     * 
     * @param scores    a map containing the players' names and their accumulated shots
     * @param onNextMap the callback to execute when the "Next Hole" button is pressed
     * 
     */
    public MidLeaderBoardPanel(final Map<String, Integer> scores, final Runnable onNextMap) {
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());

        final JPanel menuBox = new JPanel();
        menuBox.setLayout(new BoxLayout(menuBox, BoxLayout.Y_AXIS));
        menuBox.setOpaque(false);

        final JLabel title = UserInterfaceFactory.createTitle("MAP COMPLETED!");
        title.setAlignmentX(CENTER_ALIGNMENT);
        menuBox.add(title);

        menuBox.add(Box.createVerticalStrut(HEIGHT));

        // To sort the scores
        final List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort(Map.Entry.comparingByValue());

        // The table
        final JPanel tablePanel = new JPanel(new GridLayout(sortedScores.size() + 1, 2, 40, 10));
        tablePanel.setOpaque(false);
        tablePanel.add(UserInterfaceFactory.createTitle("PLAYER"));
        tablePanel.add(UserInterfaceFactory.createTitle("SHOTS"));

        for (final Map.Entry<String, Integer> entry : sortedScores) {
            final JLabel nameLabel = UserInterfaceFactory.createLabel(entry.getKey());
            final JLabel scoreLabel = UserInterfaceFactory.createLabel(String.valueOf(entry.getValue()));
            tablePanel.add(nameLabel);
            tablePanel.add(scoreLabel);
        }

        menuBox.add(tablePanel);
        menuBox.add(Box.createVerticalStrut(HEIGHT));

        // Next button skips to the next map.
        final JButton nextButton = UserInterfaceFactory.createButton("NEXT HOLE");
        nextButton.setAlignmentX(CENTER_ALIGNMENT);
        nextButton.addActionListener(e -> onNextMap.run());
        menuBox.add(nextButton);
        this.add(menuBox, new GridBagConstraints());

        // Blocks mouse clicks from reaching the game while in mid-leaderboardpanel.
        this.addMouseListener(new MouseAdapter() { });
    }

    /** {@inheritDoc} */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0, TINT));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
