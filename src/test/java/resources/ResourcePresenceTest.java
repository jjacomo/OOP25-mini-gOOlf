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
        final URL audioUrl = getClass().getResource("/soundtrack/gOOlf_menu.wav");
        assertNotNull(audioUrl, "The menu soundtrack '/soundtrack/gOOlf_menu.wav' is missing!");
    }

    @Test
    void testTitleIsPresent() {
        final URL titleUrl = getClass().getResource("/title.png");
        assertNotNull(titleUrl, "The title image '/title.png' is missing!");
    }

    @Test
    void testBackgroundImagesArePresent() {
        final URL bgldUrl = getClass().getResource("/background/leaderboard_bg1.png");
        assertNotNull(bgldUrl, "The background image '/background/leaderboard_bg1.png' is missing!");
        final URL bgmnUrl = getClass().getResource("/background/menu_bg1.png");
        assertNotNull(bgmnUrl, "The background image '/background/menu_bg1.png' is missing!");
        final URL bgngUrl = getClass().getResource("/background/newgame_bg1.png");
        assertNotNull(bgngUrl, "The background image '/background/newgame_bg1.png' is missing!");
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
}
