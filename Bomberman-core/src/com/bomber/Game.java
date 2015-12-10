package com.bomber;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import handlers.Content;
import handlers.GameInputProcessor;
import handlers.GameKeys;
import handlers.GameStateManager;


public class Game implements ApplicationListener {
	
	
	public static final String TITLE = "Bomber-Cueio";
	public static final int WIDHT = 350;
	public static final int  HEIGHT = 350;
	public static final int  SCALE =2;
	
	public static final float STEP = 1/60f ;
	private float accum;
	
	
	
	private SpriteBatch sb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	private GameStateManager gsm;
	
	public static Content res;
	
	
	public SpriteBatch getSpriteBatch(){return sb;}
	public OrthographicCamera getCamera(){return cam;}
	public OrthographicCamera getHudCamera(){return hudCam;}
	
public void create (){

	
	Gdx.input.setInputProcessor(new GameInputProcessor());
	
	res = new Content();
	loadTextures();
	sb = new SpriteBatch();
	cam = new OrthographicCamera();
	cam.setToOrtho(false,480,480);
	hudCam = new OrthographicCamera();
	hudCam.setToOrtho(false,480,480);
	gsm = new GameStateManager(this);
}

private void loadTextures() {
	res.loadTexture("bomberCima.png", "bomberUp");
	res.loadTexture("bomberDireita.png", "bomberRight");
	res.loadTexture("bomberEsquerda.png", "bomberLeft");
	res.loadTexture("bomberBaixo.png", "bomberDown");
	res.loadTexture("bomberdead.png", "bomberdead");
	res.loadTexture("bomba.png", "bomba");
	res.loadTexture("bombaExplodindo.png", "bombaExp");
	res.loadTexture("paradoCima.png", "stopUp");
	res.loadTexture("paradoBaixo.png", "stopDown");
	res.loadTexture("paradoDireita.png", "stopRight");
	res.loadTexture("paradoEsquerda.png", "stopLeft");
	res.loadTexture("xpBaixo.png", "xpDown");
	res.loadTexture("xpCima.png", "xpUp");
	res.loadTexture("xpEsquerda.png", "xpLeft");
	res.loadTexture("xpDireita.png", "xpRight");
	res.loadTexture("xpCentro.png", "xpCenter");
	res.loadTexture("xpPontaCima.png", "xpTipUp");
	res.loadTexture("xpPontaBaixo.png", "xpTipDown");
	res.loadTexture("xpPontaDireita.png", "xpTipRight");
	res.loadTexture("xpPontaEsquerda.png", "xpTipLeft");
	res.loadTexture("gameover.png", "gameover");
	
	// Textures dos inimigos
	res.loadTexture("inimigo1baixo.png", "e1down");
	res.loadTexture("inimigo1cima.png", "e1up");
	res.loadTexture("inimigo1direita.png", "e1right");
	res.loadTexture("inimigo1esquerda.png", "e1left");
	res.loadTexture("inimigo2baixo.png", "e2down");
	res.loadTexture("inimigo2cima.png", "e2up");
	res.loadTexture("inimigo2direita.png", "e2right");
	res.loadTexture("inimigo2esquerda.png", "e2left");
	res.loadTexture("inimigo3baixo.png", "e3down");
	res.loadTexture("inimigo3cima.png", "e3up");
	res.loadTexture("inimigo3direita.png", "e3right");
	res.loadTexture("inimigo3esquerda.png", "e3left");
}

	public void render ()	{
		
		accum += Gdx.graphics.getDeltaTime();
		
		while(accum>=STEP){
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
			GameKeys.update();
		}
	
	}
	public void resize(int w,int h){
	
	}
	public void pause () {
	
	}
	public void resume()	{
	
	}
	public void dispose(){
	
	}

}
