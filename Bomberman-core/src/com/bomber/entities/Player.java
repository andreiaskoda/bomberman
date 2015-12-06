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
	private int estado;
	private int numBombs;
	private int totalBombs;
	private Texture bomberUp;
	private Texture bomberDown;
	private Texture bomberLeft;
	private Texture bomberRight;
	private Texture bomberdead;
	
	public Player(Body body) {
		super(body);
		bomberUp = Game.res.getTexture("bomberUp");
		bomberDown = Game.res.getTexture("bomberDown");
		bomberLeft = Game.res.getTexture("bomberLeft");
		bomberRight = Game.res.getTexture("bomberRight");
		bomberdead = Game.res.getTexture("bomberdead");
		TextureRegion[] sprites = TextureRegion.split(bomberDown, 32, 32)[0];
		setAnimation(sprites, 1 / 12f);
		estado = 0;
		numBombs = 0;
		totalBombs = 3;
	}

	public void update(float dt) {
		animation.update(dt);
		//System.out.println((int)((this.getPosition().x-16)/32)+","+(int)((this.getPosition().y-16)/32));
		//System.out.println(estado+"    antes");
		atualizaEstado();
		//System.out.println(estado+"   depois");
	}
	
	// Movimentação do personagem para a direita
	public void moveRight() {
		TextureRegion[] sprites = TextureRegion.split(bomberRight, 32, 32)[0];
		setAnimation(sprites, 1 / 12f);
		x1 = (int)this.getPosition().x+32;
		y1 = (int)this.getPosition().y;
		this.estado=GameKeys.RIGHT;
		//this.getBody().setLinearVelocity(1500f, 0);
	}
	
	// Movimentação do personagem para a esquerda
	public void moveLeft() {
		TextureRegion[] sprites = TextureRegion.split(bomberLeft, 32, 32)[0];
		setAnimation(sprites, 1 / 12f);
		x1 = (int)this.getPosition().x-32;
		y1 = (int)this.getPosition().y;
		this.estado=GameKeys.LEFT;
		//this.getBody().setLinearVelocity(-1500f, 0);

	}
	
	// Movimentação do personagem para cima
	public void moveUp() {
		TextureRegion[] sprites = TextureRegion.split(bomberUp, 32, 32)[0];
		setAnimation(sprites, 1 / 12f);
		x1 = (int)this.getPosition().x;
		y1 = (int)this.getPosition().y+32;
		this.estado=GameKeys.UP;
		//this.getBody().setLinearVelocity(0, 15f);

	}

	// Movimentação do personagem para baixo
	public void moveDown() {
		TextureRegion[] sprites = TextureRegion.split(bomberDown, 32, 32)[0];
		setAnimation(sprites, 1 / 12f);
		x1 = (int)this.getPosition().x;
		y1 = (int)this.getPosition().y-32;
		this.estado=GameKeys.DOWN;
		//this.getBody().setLinearVelocity(0, -1500f);
	}
	
	// Verificando se está parado
	public boolean estaParado(){
		if(estado == 0) return true;
		else return false;
	}
	
	// Atualizando o estado (parado, andando cima, baixo, direita ou esquerda)
	private void atualizaEstado(){
		if(estado == 0) return;
		else if(estado==GameKeys.UP){
			if((int)this.getPosition().y<y1){
				//System.out.println((int)this.getPosition().y);
				this.getBody().setLinearVelocity(0, 15000f);
			} else this.estado=0;			
			
		} else if(estado==GameKeys.DOWN){
			if((int)this.getPosition().y>y1){
				//System.out.println((int)this.getPosition().y);
				this.getBody().setLinearVelocity(0, -15000f);
			} else this.estado=0;			
			
		} else if(estado==GameKeys.LEFT){
			if((int)this.getPosition().x>x1){
				//System.out.println((int)this.getPosition().y);
				this.getBody().setLinearVelocity(-15000f, 0);
			} else this.estado=0;			
			
		} else if(estado==GameKeys.RIGHT){
			if((int)this.getPosition().x<x1){
				//System.out.println((int)this.getPosition().y);
				this.getBody().setLinearVelocity(15000f, 0);
			} else this.estado=0;
		}
	}
	
	private void colocouBomba() { numBombs++; }
	private int getBombas() { return numBombs; }
	private int getTotalBombas() { return totalBombs; }
		
		
}
	
	



