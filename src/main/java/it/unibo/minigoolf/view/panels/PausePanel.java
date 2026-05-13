package it.unibo.minigoolf.view.panels;

import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.view.elements.UserInterfaceFactory;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;

/**
 * The pause menu. It is NOT a panel within the cardlayout! It's an overlay if gamepanel.
 * * @author dani
 */
public final class PausePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int TINT = 150;

    public PausePanel(final NavigationController navController) {
        this.setOpaque(false); 
        this.setLayout(new GridBagLayout());

        final JPanel menuBox = new JPanel();
        menuBox.setLayout(new BoxLayout(menuBox, BoxLayout.Y_AXIS));
        menuBox.setOpaque(false);

        final JButton resumeButton = UserInterfaceFactory.createButton("RESUME");
        resumeButton.setAlignmentX(CENTER_ALIGNMENT);
        resumeButton.addActionListener(e -> navController.resumeGame());
        menuBox.add(resumeButton);

        final JButton quitButton = UserInterfaceFactory.createButton("QUIT");
        quitButton.setAlignmentX(CENTER_ALIGNMENT);
        quitButton.addActionListener(e -> {
            final int choice = UserInterfaceFactory.showConfirmDialog(this, "Do you want to save before quitting?", "Quit Game");

            if (choice == JOptionPane.YES_OPTION) {
                // TODO: Logica di salvataggio da implementare!
                navController.quitToMenu();
                
            } else if (choice == JOptionPane.NO_OPTION) {
                navController.quitToMenu();
            }

        });
        menuBox.add(quitButton);

        this.add(menuBox, new GridBagConstraints());
        // This is needed so if the player clicks with his mouse during pause-state, the ball won't react
        this.addMouseListener(new MouseAdapter() { }); 
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        // Tinted effect for the background
        g.setColor(new Color(0, 0, 0, TINT));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
