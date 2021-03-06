package com.space.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.space.game.SpaceGame;
import com.space.tools.Logger;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Logger logger = Logger.getInstance();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new SpaceGame(), config);
	}
}
