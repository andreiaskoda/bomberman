package com.bomber.entities;

import handlers.GameKeys;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.bomber.Game;

public class Enemy2 extends B2DSprite {
	
	private float x1;
	private float y1;
	private int estado;
	private int totalBombs;
	private Texture up;
	private Texture down;
	private Texture left;
	private Texture right;
	
	/** Movimento:
	 * 1 = cima
	 * 2 = direita
	 * 3 = baixo
	 * 4 = esquerda */
	private int movimento;
	
	private final int UP = 1;
	private final int RIGHT = 2;
	private final int LEFT = 3;
	private final int DOWN = 4;
	
	
	public Enemy2(Body body) {
		super(body);
		setTextures();
		TextureRegion[] sprites = TextureRegion.split(down, 32, 32)[0];
		setAnimation(sprites, 1 / 10f);
		estado = 0;
		movimento = 1;
	}
	
	/** Arrumando as texturas utilizadas */
	private void setTextures() {
		up = Game.res.getTexture("e2up");
		down = Game.res.getTexture("e2down");
		left = Game.res.getTexture("e2left");
		right = Game.res.getTexture("e2right");
	}

	/** Atualizando o sistema - animação e estado do inimigo */
	public void update(float dt) {
		animation.update(dt);
		//System.out.println((int)((this.getPosition().x-16)/32)+","+(int)((this.getPosition().y-16)/32));
		//System.out.println(estado+"    antes");
		atualizaEstado();
		//System.out.println(estado+"   depois");
	}
	
	/** Movimentação do personagem para a direita */
	public void moveRight() {
		TextureRegion[] sprites = TextureRegion.split(right, 32, 32)[0];
		setAnimation(sprites, 1 / 10f);
		x1 = (int)this.getPosition().x+32;
		y1 = (int)this.getPosition().y;
		this.estado = RIGHT;
		//this.getBody().setLinearVelocity(1500f, 0);
	}
	
	/** Movimentação do personagem para a esquerda */
	public void moveLeft() {
		TextureRegion[] sprites = TextureRegion.split(left, 32, 32)[0];
		setAnimation(sprites, 1 / 10f);
		x1 = (int)this.getPosition().x-32;
		y1 = (int)this.getPosition().y;
		this.estado = LEFT;
		//this.getBody().setLinearVelocity(-1500f, 0);

	}
	
	/** Movimentação do personagem para cima */
	public void moveUp() {
		TextureRegion[] sprites = TextureRegion.split(up, 32, 32)[0];
		setAnimation(sprites, 1 / 10f);
		x1 = (int)this.getPosition().x;
		y1 = (int)this.getPosition().y+32;
		this.estado = UP;
		//this.getBody().setLinearVelocity(0, 15f);

	}

	/** Movimentação do personagem para baixo */
	public void moveDown() {
		TextureRegion[] sprites = TextureRegion.split(down, 32, 32)[0];
		setAnimation(sprites, 1 / 10f);
		x1 = (int)this.getPosition().x;
		y1 = (int)this.getPosition().y-32;
		this.estado = DOWN;
		//this.getBody().setLinearVelocity(0, -1500f);
	}	
	
	/** Verificando se está parado */
	public boolean estaParado(){ return estado == 0;}
	
	/** Atualizando o estado (parado, andando cima, baixo, direita ou esquerda) */
	private void atualizaEstado(){	
		/** Se estiver parado */
		if(estado == 0) return;
		
		/** Se estiver para andar para cima */
		else if(estado==UP){
			if((int)this.getPosition().y<y1){
				//System.out.println((int)this.getPosition().y);
				this.getBody().setLinearVelocity(0, 15000f);
			} else this.estado=0;		
			
		/** Se estiver para andar para baixo */
		} else if(estado==DOWN){
			if((int)this.getPosition().y>y1){
				//System.out.println((int)this.getPosition().y);
				this.getBody().setLinearVelocity(0, -15000f);
			} else this.estado=0;	
			
		/** Se estiver para andar para a esquerda */
		} else if(estado==LEFT){
			if((int)this.getPosition().x>x1){
				//System.out.println((int)this.getPosition().y);
				this.getBody().setLinearVelocity(-15000f, 0);
			} else this.estado=0;	
			
		/** Se estiver para andar para a direita */
		} else if(estado==RIGHT){
			if((int)this.getPosition().x<x1){
				//System.out.println((int)this.getPosition().y);
				this.getBody().setLinearVelocity(15000f, 0);
			} else this.estado=0;
		}
	}
	
}
