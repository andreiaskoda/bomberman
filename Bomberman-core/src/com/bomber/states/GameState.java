package com.bomber.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bomber.Game;

import handlers.GameStateManager;

public abstract class GameState {

	protected GameStateManager gsm;
	
	protected Game  game;
	
	protected SpriteBatch sb;
	protected OrthographicCamera cam; // segue o player
	protected OrthographicCamera hudCam; // s� para o hud
	
	
	
	protected GameState(GameStateManager gsm){
		this.gsm = gsm;
		game = gsm.game();
		sb = game.getSpriteBatch();
		cam = game.getCamera();
		hudCam = game.getHudCamera();
	}
	
	public abstract	 void handleInput();
	public abstract	 void update (float dt);
	public abstract	 void render();
	public abstract	 void dispose();	
}
