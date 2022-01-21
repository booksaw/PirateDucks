package io.github.pirateducks.tests;

import com.badlogic.gdx.Gdx;
import io.github.pirateducks.MyGdxGameTest;
import io.github.pirateducks.PirateDucks;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;


@RunWith(MyGdxGameTest.class)
public class UnitTest {

    /**
     * @Throws AssertionError and raises what Asset isn't loaded
     */
    @Test
    public void AssetsExists() {
        assertTrue("This will pass if it has the logo loaded", Gdx.files.internal("../core/assets/logo.png").exists());
        assertTrue("This will pass if it has the Main Theme loaded", Gdx.files.internal("../core/assets/Main_Theme.ogg").exists());
        assertTrue("This will pass if it has the map loaded", Gdx.files.internal("../core/assets/map.png").exists());
        assertTrue("This will pass if it has the map blurred loaded", Gdx.files.internal("../core/assets/map_blurred.png").exists());
        assertTrue("This will pass if it has the Ocean loaded", Gdx.files.internal("../core/assets/Ocean.ogg").exists());
        assertTrue("This will pass if it has the HeartWhite loaded", Gdx.files.internal("../core/assets/HeartWhite.png").exists());
        assertTrue("This will pass if it has the Heart loaded", Gdx.files.internal("../core/assets/Heart.png").exists());
        assertTrue("This will pass if it has the Half HeartWhite loaded", Gdx.files.internal("../core/assets/Half_HeartWhite.png").exists());
        assertTrue("This will pass if it has the Half Heart loaded", Gdx.files.internal("../core/assets/Half_Heart.png").exists());
        assertTrue("This will pass if it has the Empty HalfWhite loaded", Gdx.files.internal("../core/assets/Empty_HeartWhite.png").exists());
        assertTrue("This will pass if it has the Empty Heart loaded", Gdx.files.internal("../core/assets/Empty_Heart.png").exists());
        assertTrue("This will pass if it has the DuckBoat SideView loaded", Gdx.files.internal("../core/assets/DuckBoat_SideView.png").exists());
        assertTrue("This will pass if it has the DuckBoat TopView loaded", Gdx.files.internal("../core/assets/DuckBoat_TopView.png").exists());
        assertTrue("This will pass if it has the CannonBall loaded", Gdx.files.internal("../core/assets/CannonBall.png").exists());
        assertTrue("This will pass if it has the cannon shot loaded", Gdx.files.internal("../core/assets/cannon-shot.mp3").exists());
        assertTrue("This will pass if it has the You Win Screen loaded (victoryScreen)", Gdx.files.internal("../core/assets/victoryScreen/YouWinScreen.png").exists());
        assertTrue("This will pass if it has the Victory Screen Menu Button loaded (victoryScreen)", Gdx.files.internal("../core/assets/victoryScreen/buttons/VictoryScreenMenuButton.png").exists());
        assertTrue("This will pass if it has the Victory Screen Quit Button loaded (victoryScreen)", Gdx.files.internal("../core/assets/victoryScreen/buttons/VictoryScreenQuitButton.png").exists());
        assertTrue("This will pass if it has the continue button loaded (pausedScreen)", Gdx.files.internal("../core/assets/pauseScreen/continue.png").exists());
        assertTrue("This will pass if it has the pauseText loaded (pausedScreen)", Gdx.files.internal("../core/assets/pauseScreen/pausedText.png").exists());
        assertTrue("This will pass if it has quit loaded (pausedScreen)", Gdx.files.internal("../core/assets/pauseScreen/quit.png").exists());
        assertTrue("This will pass if it has the settings loaded (pausedScreen)", Gdx.files.internal("../core/assets/pauseScreen/settings.png").exists());
        assertTrue("This will pass if it has the card 0 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/card-0.png").exists());
        assertTrue("This will pass if it has the card 1 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/card-1.png").exists());
        assertTrue("This will pass if it has the card 2 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/card-2.png").exists());
        assertTrue("This will pass if it has the card 3 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/card-3.png").exists());
        assertTrue("This will pass if it has the card 4 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/card-4.png").exists());
        assertTrue("This will pass if it has the card 5 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/card-5.png").exists());
        assertTrue("This will pass if it has the card 6 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/card-6.png").exists());
        assertTrue("This will pass if it has the card 7 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/card-7.png").exists());
        assertTrue("This will pass if it has the card 8 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/card-8.png").exists());
        assertTrue("This will pass if it has the card 9 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/card-9.png").exists());
        assertTrue("This will pass if it has the countdown 0 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/countdown-0.png").exists());
        assertTrue("This will pass if it has the countdown 1 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/countdown-1.png").exists());
        assertTrue("This will pass if it has the countdown 2 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/countdown-2.png").exists());
        assertTrue("This will pass if it has the countdown 3 loaded(memorygame)", Gdx.files.internal("../core/assets/memoryGame/countdown-3.png").exists());
        assertTrue("This will pass if it has the countdown 4 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/countdown-4.png").exists());
        assertTrue("This will pass if it has the countdown 5 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/countdown-5.png").exists());
        assertTrue("This will pass if it has the countdown 6 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/countdown-6.png").exists());
        assertTrue("This will pass if it has the countdown 7 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/countdown-7.png").exists());
        assertTrue("This will pass if it has the countdown 8 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/countdown-8.png").exists());
        assertTrue("This will pass if it has the countdown 9 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/countdown-9.png").exists());
        assertTrue("This will pass if it has the countdown 10 loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/countdown-10.png").exists());
        assertTrue("This will pass if it has the header loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/header.png").exists());
        assertTrue("This will pass if it has the question mark loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/question-mark.png").exists());
        assertTrue("This will pass if it has the start game loaded (memorygame)", Gdx.files.internal("../core/assets/memoryGame/start-game.png").exists());
        assertTrue("This will pass if it has the quit loaded (mainMenuScreen)", Gdx.files.internal("../core/assets/mainMenuScreen/quit.png").exists());
        assertTrue("This will pass if it has the settings loaded (mainMenuScreen)", Gdx.files.internal("../core/assets/mainMenuScreen/settings.png").exists());
        assertTrue("This will pass if it has the start loaded (mainMenuScreen)", Gdx.files.internal("../core/assets/mainMenuScreen/start.png").exists());
        assertTrue("This will pass if it has the Cannon loaded (Langwith)", Gdx.files.internal("../core/assets/Langwith/Cannon.png").exists());
        assertTrue("This will pass if it has the BulletHellBackground loaded (Langwith)", Gdx.files.internal("../core/assets/Langwith/BulletHellBackground.png").exists());
        assertTrue("This will pass if it has the apple loaded (goodricke)", Gdx.files.internal("../core/assets/goodricke/apple.png").exists());
        assertTrue("This will pass if it has the banana loaded (goodricke)", Gdx.files.internal("../core/assets/goodricke/banana.png").exists());
        assertTrue("This will pass if it has the bomb loaded (goodricke)", Gdx.files.internal("../core/assets/goodricke/bomb.png").exists());
        assertTrue("This will pass if it has the goodrickemap loaded (goodricke)", Gdx.files.internal("../core/assets/goodricke/goodrickemap.png").exists());
        assertTrue("This will pass if it has the melon loaded (goodricke)", Gdx.files.internal("../core/assets/goodricke/melon.png").exists());
        assertTrue("This will pass if it has the GameOverMenuButton loaded (gameOverScreen)", Gdx.files.internal("../core/assets/gameOverScreen/buttons/GameOverMenuButton.png").exists());
        assertTrue("This will pass if it has the GameOverQuitButton loaded (gameOverScreen)", Gdx.files.internal("../core/assets/gameOverScreen/buttons/GameOverQuitButton.png").exists());
        assertTrue("This will pass if it has the GameOver loaded (gameOverScreen)", Gdx.files.internal("../core/assets/gameOverScreen/GameOver.png").exists());
    }

    @Test
    public void testGetTrue() {
        assertTrue(PirateDucks.getTrue());
    }

}
