package io.github.pirateducks.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.github.pirateducks.PirateDucks;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
//		config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		config.title = "Pirate Ducks";
//		config.fullscreen = true;
		new LwjglApplication(new PirateDucks(), config);
	}
}
