package it.unibo.minigoolf.view.texturemanager;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class responsible for loading and caching textures (images).
 */
public final class TextureManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextureManager.class);
    private static final Map<String, BufferedImage> TEXTURE_CACHE = new HashMap<>();

    private TextureManager() {
        throw new UnsupportedOperationException("TextureManager is a utility class");
    }

    /**
     * Loads a texture from the resource cache or from disk if not cached.
     * Uses a HashMap to store previously loaded textures for improved performance.
     *
     * @param texturePath the relative path to the texture file in
     *                    src/main/resources/
     * @return the loaded BufferedImage, or null if loading fails
     */
    public static BufferedImage loadTexture(final String texturePath) {
        if (TEXTURE_CACHE.containsKey(texturePath)) {
            return TEXTURE_CACHE.get(texturePath);
        }
        final URL url = TextureManager.class.getResource("/" + texturePath);
        if (url == null) {
            LOGGER.error("Failed to load texture: Resource not found: {}", texturePath);
            return null;
        }
        try {
            final BufferedImage image = ImageIO.read(url);
            TEXTURE_CACHE.put(texturePath, image);
            return image;
        } catch (final IOException e) {
            LOGGER.error("Failed to load texture: {}", texturePath, e);
            return null;
        }
    }
}
