package com.mygdx.fourq;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.useVsync(true);
		config.setWindowedMode(480, 640);
		config.setResizable(false);
		config.setWindowIcon("C:\\Users\\TEMP.DESKTOP-1NFA8JO\\IdeaProjects\\4th-Quarter-Project\\assets\\harveybasket.png");
		config.setTitle("Fourth Quarter Game");
		new Lwjgl3Application(new Game(), config);


	}
}