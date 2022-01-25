package io.github.pirateducks.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import io.github.pirateducks.MyGdxGameTest;
import io.github.pirateducks.PirateDucks;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;

/**
 * This class is used to target the class PirateDucks with tests
 */
@RunWith(MyGdxGameTest.class)
public class PirateDucksTest {

    /**
     * Setting up before running this test class
     */
    @BeforeClass
    public static void init() {
        // Use Mockito to mock the OpenGL methods since we are running headlessly
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;

        // Mock the graphics class.
        Gdx.graphics = Mockito.mock(Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(500);
        when(Gdx.graphics.getHeight()).thenReturn(500);
    }

    /**
     * Used to test if the PirateDucks.getScaledLocaiton provides correct results
     */
    @Test
    public void testScaling() {

        OrthographicCamera camera = new OrthographicCamera();
        camera.viewportWidth = 500;
        camera.viewportHeight = 500;

        // checking on static location
        assertEquals(PirateDucks.getScaledLocation(new Vector2(50.1f, 50.1f), camera), new Vector2(50.1f, 50.1f));

        camera.viewportWidth = 100;
        camera.viewportHeight = 100;
        // checking when scaling is required
        assertEquals(PirateDucks.getScaledLocation(new Vector2(50, 50), camera), new Vector2(10, 10));
    }

}
