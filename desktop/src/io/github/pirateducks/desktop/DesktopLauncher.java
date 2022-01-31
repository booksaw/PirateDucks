package io.github.pirateducks.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.github.pirateducks.PirateDucks;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
//		config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		config.width = 848;
		config.height = 480;
		config.title = "Pirate Ducks";
//		config.fullscreen = true;
		config.addIcon("icons/DuckBoat_SideView_16x16.png", Files.FileType.Internal);
		config.addIcon("icons/DuckBoat_SideView_32x32.png", Files.FileType.Internal);
		config.addIcon("icons/DuckBoat_SideView_128x128.png", Files.FileType.Internal);
		new LwjglApplication(new PirateDucks(), config);
	}
}
