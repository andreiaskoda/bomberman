package com.bomber.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.bomber.Game;

import handlers.GameKeys;

public class Player extends B2DSprite {

	private float time;
	private float x1;
	private float y1;
	private int state;
	private int lastState;
	private int numBombs;
	private int totalBombs;
	private Texture bomberUp;
	private Texture bomberDown;
	private Texture bomberLeft;
	private Texture bomberRight;
	private Texture bomberdead;
	private Texture stopUp;
	private Texture stopDown;
	private Texture stopRight;
	private Texture stopLeft;
	
	/** Construtor do jogador */
	public Player(Body body) {
		super(body);
		setTextures();
		TextureRegion[] sprites = TextureRegion.split(bomberDown, 32, 32)[0];
		setAnimation(sprites, 1 / 10f);
		lastState = GameKeys.DOWN;
		state = 0;
		numBombs = 0;
		totalBombs = 3;
	}
	
	/** Arrumando as texturas utilizadas */
	private void setTextures() {
		bomberUp = Game.res.getTexture("bomberUp");
		bomberDown = Game.res.getTexture("bomberDown");
		bomberLeft = Game.res.getTexture("bomberLeft");
		bomberRight = Game.res.getTexture("bomberRight");
		bomberdead = Game.res.getTexture("bomberdead");
		stopUp = Game.res.getTexture("stopUp");
		stopDown = Game.res.getTexture("stopDown");
		stopRight = Game.res.getTexture("stopRight");
		stopLeft = Game.res.getTexture("stopLeft");
	}

	/** Atualizando o sistema - animação e estado do jogador */
	public void update(float dt) {
		animation.update(dt);
		updateState();
	}
	
	/** Movimentação do personagem para a direita */
	public void moveRight() {
		TextureRegion[] sprites = TextureRegion.split(bomberRight, 32, 32)[0];
		setAnimation(sprites, 1 / 10f);
		x1 = (int)this.getPosition().x+32;
		y1 = (int)this.getPosition().y;
		this.state = GameKeys.RIGHT;
		this.lastState = GameKeys.RIGHT;
	}
	
	/** Movimentação do personagem para a esquerda */
	public void moveLeft() {
		TextureRegion[] sprites = TextureRegion.split(bomberLeft, 32, 32)[0];
		setAnimation(sprites, 1 / 10f);
		x1 = (int)this.getPosition().x-32;
		y1 = (int)this.getPosition().y;
		this.state = GameKeys.LEFT;
		this.lastState = GameKeys.LEFT;
		//this.getBody().setLinearVelocity(-1500f, 0);

	}
	
	/** Movimentação do personagem para cima */
	public void moveUp() {
		TextureRegion[] sprites = TextureRegion.split(bomberUp, 32, 32)[0];
		setAnimation(sprites, 1 / 10f);
		x1 = (int)this.getPosition().x;
		y1 = (int)this.getPosition().y+32;
		this.state = GameKeys.UP;
		this.lastState = GameKeys.UP;
		//this.getBody().setLinearVelocity(0, 15f);

	}

	/** Movimentação do personagem para baixo */
	public void moveDown() {
		TextureRegion[] sprites = TextureRegion.split(bomberDown, 32, 32)[0];
		setAnimation(sprites, 1 / 10f);
		x1 = (int)this.getPosition().x;
		y1 = (int)this.getPosition().y-32;
		this.state = GameKeys.DOWN;
		this.lastState = GameKeys.DOWN;
		//this.getBody().setLinearVelocity(0, -1500f);
	}
	
	/** Animação para o bomberman parado */
	public void animationStop() {
		
		TextureRegion[] sprites;
		if(lastState == GameKeys.UP)
			sprites = TextureRegion.split(stopUp, 32, 32)[0];
		else if(lastState == GameKeys.LEFT)
			sprites = TextureRegion.split(stopLeft, 32, 32)[0];
		else if(lastState == GameKeys.RIGHT)
			sprites = TextureRegion.split(stopRight, 32, 32)[0];
		else
			sprites = TextureRegion.split(stopDown, 32, 32)[0];
		setAnimation(sprites, 1 / 10f);
	}
	
	
	//anima��o morrendo - criei este estado fantasma para a fun�ao est� parado n�o sobrescreva a anima��o
	public void animationDying() {
		
		TextureRegion[] sprites=TextureRegion.split(bomberdead, 32, 32)[0];
		state = -1;
		setAnimation(sprites, 1/2f);
	}
	
	
	/** Verificando se está parado */
	public boolean isStatic(){ return state == 0;}
	
	/** Atualizando o estado (parado, andando cima, baixo, direita ou esquerda) */
	private void updateState(){
		

		
		/** Se estiver parado */
		if(state == 0) {
			if(lastState == GameKeys.UP) {
				TextureRegion[] sprites = TextureRegion.split(stopUp, 32, 32)[0];
				setAnimation(sprites, 1 / 10f);
			} else if(lastState == GameKeys.DOWN) {
				TextureRegion[] sprites = TextureRegion.split(stopDown, 32, 32)[0];
				setAnimation(sprites, 1 / 10f);
			} else if(lastState == GameKeys.RIGHT) {
				TextureRegion[] sprites = TextureRegion.split(stopRight, 32, 32)[0];
				setAnimation(sprites, 1 / 10f);
			}  else if(lastState == GameKeys.LEFT){
				TextureRegion[] sprites = TextureRegion.split(stopLeft, 32, 32)[0];
				setAnimation(sprites, 1 / 10f);
			} 
			return;
		}
		
		/** Se estiver para andar para cima */
		else if(state == GameKeys.UP){
			if((int)this.getPosition().y<y1){
				this.getBody().setLinearVelocity(0, 15000f);
			} else this.state=0;		
			
		/** Se estiver para andar para baixo */
		} else if(state == GameKeys.DOWN){
			if((int)this.getPosition().y>y1){
				this.getBody().setLinearVelocity(0, -15000f);
			} else this.state=0;	
			
		/** Se estiver para andar para a esquerda */
		} else if(state == GameKeys.LEFT){
			if((int)this.getPosition().x>x1){
				this.getBody().setLinearVelocity(-15000f, 0);
			} else this.state = 0;	
			
		/** Se estiver para andar para a direita */
		} else if(state == GameKeys.RIGHT){
			if((int)this.getPosition().x<x1){
				this.getBody().setLinearVelocity(15000f, 0);
			} else this.state = 0;
		}
	}
	
	private void putBomb() { numBombs++; }
	private void explodedBomba() { numBombs--; }
	private int getBomb() { return numBombs; }
	private int getTotalBombs() { return totalBombs; }
	
		
}
	
	



