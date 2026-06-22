package resources;

import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test class to verify that all vital static resources 
 * (fonts, audio, images) are correctly packaged in the project.
 */
class ResourcePresenceTest {

    @Test
    void testCustomFontIsPresent() {
        assertDoesNotThrow(() -> {
            try (InputStream is = getClass().getResourceAsStream("/font/upheavtt.ttf")) {
                assertNotNull(is, "The custom font file '/font/upheavtt.ttf' is missing from resources!");
            }
        });
    }

    @Test
    void testMenuSoundtrackIsPresent() {
        final URL audioUrl = getClass().getResource("/sounds/soundtrack/gOOlf_menu.wav");
        assertNotNull(audioUrl, "The menu soundtrack '/sounds/soundtrack/gOOlf_menu.wav' is missing!");
    }

    @Test
    void testAmbientNewGameIsPresent() {
        final URL audioUrl = getClass().getResource("/sounds/ambient/gOOlf_ambient.wav");
        assertNotNull(audioUrl, "The ambient sound '/sounds/ambient/gOOlf_ambient.wav' is missing!");
    }

    @Test
    void testTitleIsPresent() {
        final URL titleUrl = getClass().getResource("/title.png");
        assertNotNull(titleUrl, "The title image '/title.png' is missing!");
    }

    @Test
    void testBackgroundImagesArePresent() {
        final URL bgldUrl = getClass().getResource("/background/leaderboard_bg.png");
        assertNotNull(bgldUrl, "The background image '/background/leaderboard_bg.png' is missing!");
        final URL bgmnUrl = getClass().getResource("/background/menu_bg.png");
        assertNotNull(bgmnUrl, "The background image '/background/menu_bg.png' is missing!");
        final URL bgngUrl = getClass().getResource("/background/newgame_bg.png");
        assertNotNull(bgngUrl, "The background image '/background/newgame_bg.png' is missing!");
    }

    @Test
    void testTutorialImageIsPresent() {
        final URL tutUrl = getClass().getResource("/tutorial/tutorial_img.jpeg");
        assertNotNull(tutUrl, "The background image '/tutorial/tutorial_img.jpeg' is missing!");
    }

    @Test
    void testMedalIconsArePresent() {
        final URL goldUrl = getClass().getResource("/medals/gold.png");
        assertNotNull(goldUrl, "The gold medal icon '/medals/gold.png' is missing!");

        final URL silverUrl = getClass().getResource("/medals/silver.png");
        assertNotNull(silverUrl, "The silver medal icon '/medals/silver.png' is missing!");

        final URL bronzeUrl = getClass().getResource("/medals/bronze.png");
        assertNotNull(bronzeUrl, "The bronze medal icon '/medals/bronze.png' is missing!");
    }

    @Test
    void testSurfaceTexturesArePresent() {
        final String[] textures = {
            "surfaces/grass.png",
            "surfaces/sand.png",
            "surfaces/dirt.png",
            "surfaces/ice.png",
            "surfaces/boost/boost.png",
            "surfaces/wind/right_arrow.png",
            "surfaces/wind/left_arrow.png",
            "surfaces/wind/down_arrow.png",
            "surfaces/wind/up_arrow2.png",
        };
        for (final String path : textures) {
            assertNotNull(it.unibo.minigoolf.view.texturemanager.TextureManager.loadTexture(path),
                    "TextureManager failed to load surface texture: " + path);
        }
    }
}
