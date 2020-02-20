package com.me.helicopter_rush.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.me.helicopter_rush.constants;
import com.me.helicopter_rush.helicopter_rush;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = constants.GAME_WIDTH;
		config.height = constants.GAME_HEIGHT;
		new LwjglApplication(new helicopter_rush(), config);
	}
}
