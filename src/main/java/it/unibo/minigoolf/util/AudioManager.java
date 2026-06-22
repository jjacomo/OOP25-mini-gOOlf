package it.unibo.minigoolf.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

/**
 * Handles the background music and sound effects of the game.
 *
 * @author dbakko
 */
public final class AudioManager {

    private static final String PATH_MENU = "/sounds/soundtrack/gOOlf_menu.wav";
    private static final String PATH_GAME = "/sounds/ambient/gOOlf_ambient.wav";

    private Clip currentClip;
    private String currentPath;

    /**
     * Constructs a new AudioManager.
     */
    public AudioManager() {
        // Default constructor
    }

    /**
     * Plays an audio file in a continuous loop.
     * If the same audio is already playing, it does nothing to avoid restarting it.
     *
     * @param path the resource path to the .wav file
     */
    private void playLoop(final String path) {
        if (path.equals(currentPath) && currentClip != null && currentClip.isRunning()) {
            return;
        }

        this.stop();

        try {
            final URL audioUrl = getClass().getResource(path);
            if (audioUrl == null) {
                throw new IllegalStateException("Audio file not found: " + path);
            }
            final AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioUrl);

            this.currentClip = AudioSystem.getClip();
            this.currentClip.open(audioIn);
            this.currentClip.loop(Clip.LOOP_CONTINUOUSLY);
            this.currentPath = path;

        } catch (final UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new IllegalStateException("Error playing the audio file: " + path, e);
        }
    }

    /**
     * Plays the main menu background music in a continuous loop.
     */
    public void playMenuMusic() {
        this.playLoop(PATH_MENU);
    }

    /**
     * Plays the game ambient background music in a continuous loop.
     */
    public void playGameMusic() {
        this.playLoop(PATH_GAME);
    }

    /**
     * Stops the currently playing audio and releases resources.
     */
    public void stop() {
        if (this.currentClip != null) {
            this.currentClip.stop();
            this.currentClip.close();
            this.currentClip = null;
            this.currentPath = null;
        }
    }
}
