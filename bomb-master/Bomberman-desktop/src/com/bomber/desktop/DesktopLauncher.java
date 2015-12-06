package com.bomber.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bomber.Bombermanclass;
import com.bomber.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = Game.TITLE;
		cfg.width = Game.WIDHT*Game.SCALE;
		cfg.height = Game.HEIGHT*Game.SCALE;
		new LwjglApplication(new Game(),cfg);
		
	}
}
