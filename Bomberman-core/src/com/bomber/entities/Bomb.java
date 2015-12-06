package com.bomber.entities;

import static handlers.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.bomber.Game;

import handlers.B2DVars;

public class Bomb extends B2DSprite {

	private float time;
	private int estado;
	private boolean kabum;
	
	// ESTADOS
	//0-normal(t<3)
	//1-quase explodindo 3<t<4)
	//2-explodir no prox update
	
	public Bomb(Body body) {
		super(body);		
		time = 0;
		estado = 0;
		kabum = false;
	}
	
	public void update(float dt) {
		animation.update(dt);
		time+=dt;

		atualizaEstado();
		if(estado==2)
			criaExplosao();
	}
	
	private void atualizaEstado(){
		if(time <= 2)
			estado = 0;
		else if(time >= 2 && time <= 3)
			estado = 1;
		else if(time>3&& time <3.5f)
			estado = 2;
		else
			kabum = true;
	}
	
	public boolean kabumTime(){	return kabum; }
	
	public void render (SpriteBatch sb){
		sb.begin();
		if(estado==0){
			Texture tex = Game.res.getTexture("bomba");
			sb.draw(tex,(int)body.getPosition().x-16, (int)body.getPosition().y-16,32,32);
		}
		else if (estado==1) {
			Texture tex = Game.res.getTexture("bomba2");
			sb.draw(tex,(int)body.getPosition().x-16, (int)body.getPosition().y-16,32,32);
		} else {
			Texture tex = Game.res.getTexture("red");
			if((int)((this.getPosition().y-16)/32) %2!=0)
				sb.draw(tex, (int)this.getPosition().x-(15+32*2),(int)this.getPosition().y-15 ,32*5,32);
			if((int)((this.getPosition().x-16)/32) %2!=0)				
				sb.draw(tex, (int)this.getPosition().x-15,(int)this.getPosition().y-(15+32*2) ,32,32*5);
		}
		
		sb.end();
	}

	private void criaExplosao(){
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();

		if((int)((this.getPosition().y-16)/32) %2!=0){
			shape.setAsBox((15+32*2)/PPM, (15)/PPM);
			fdef.shape =shape;
			fdef.isSensor=true;
			
			this.getBody().createFixture(fdef);
		}
		
		if((int)((this.getPosition().x-16)/32) %2!=0){
			shape.setAsBox((15)/PPM, (15+32*2)/PPM);
			fdef.shape =shape;
			fdef.isSensor=true;
			this.getBody().createFixture(fdef);
		}
	}
	
}
