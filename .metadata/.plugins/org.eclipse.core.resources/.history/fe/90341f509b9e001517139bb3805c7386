package com.bomber.entities;

import static handlers.B2DVars.PPM;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.bomber.Game;
import com.badlogic.gdx.graphics.g2d.Animation;

import handlers.B2DVars;
import handlers.MyContactListener;

public class Bomb extends B2DSprite {

	private float time;
	private int estado;
	private boolean kabum;
	
	private MyContactListener cl;
	
	//variavel que diz se o player setou a bomba e ainda está em cima dela
	private boolean playerEmCima;
	
	
	
	private Texture xpUp;
	private Texture xpDown;
	private Texture xpLeft;
	private Texture xpRight;
	private Texture xpCenter;
	private Texture xpTipUp;
	private Texture xpTipDown;
	private Texture xpTipLeft;
	private Texture xpTipRight;
	
	// ESTADOS (definir melhores tempos)
	//0-normal
	//1-quase explodindo 
	//2-explodir
	
	public Bomb(Body body,MyContactListener cl) {
		super(body);		
		time = 0;
		estado = 0;
		kabum = false;
		playerEmCima = true;
		xpUp = Game.res.getTexture("xpUp");
		xpDown = Game.res.getTexture("xpDown");
		xpRight = Game.res.getTexture("xpRight");
		xpLeft = Game.res.getTexture("xpLeft");
		xpCenter = Game.res.getTexture("xpCenter");
		xpTipUp = Game.res.getTexture("xpTipUp");
		xpTipDown = Game.res.getTexture("xpTipDown");
		xpTipRight = Game.res.getTexture("xpTipRight");
		xpTipLeft = Game.res.getTexture("xpTipLeft");
		this.cl=cl;
	}
	
	public void update(float dt) {
		animation.update(dt);
		time+=dt;

		atualizaEstado();
		if(estado==2)
			criaExplosao();
	}
	
	private void atualizaEstado(){
		
		
		
		//obs tive varios problemas para fazer esta parte, a solução que encontrei para desbugar o contato foi recriar uma fixture
		if(this.playerEmCima==true){
			
			//se o player não está mais em cima desta bomba
			if(!cl.isOnTop()){
				//destroi a fixture antiga
				this.getBody().destroyFixture(this.getBody().getFixtureList().first());
				//mesmas caracteristicas porem não é mais um sensor
				PolygonShape shape = new PolygonShape();
				FixtureDef fdef = new FixtureDef();
				shape.setAsBox(15/PPM, 15/PPM);
				fdef.shape = shape;
				fdef.isSensor = false;
				body.setSleepingAllowed(false);
				body.createFixture(fdef);
				
				
				//renomeia para bomba ativa (só pra ajudar a desbugar):
				//bomba inativa é um sensor
				//bomba ativa não é sensor
				
				
				this.getBody().getFixtureList().first().setUserData("bombaAtiva");
				//boolean para não entrar mais nesse if
				playerEmCima=false;
			}
		}
		
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
	
	/* (non-Javadoc)
	 * @see com.bomber.entities.B2DSprite#render(com.badlogic.gdx.graphics.g2d.SpriteBatch)
	 */
	public void render (SpriteBatch sb){
		float stateTime;
		sb.begin();
		if(estado==0){
			Texture tex = Game.res.getTexture("bomba");
			sb.draw(tex,(int)body.getPosition().x-16, (int)body.getPosition().y-16,32,32);
		}
		else if (estado==1) {
			Texture tex = Game.res.getTexture("bombaExp");
			TextureRegion[] sprites = TextureRegion.split(tex, 32, 32)[0];
		} else {
			sb.draw(xpCenter,(int)body.getPosition().x-16, (int)body.getPosition().y-16,32,32);
			Texture tex = Game.res.getTexture("red");
			
			/** Verificando se hÃ¡ bloco Ã  direita da bomba */
			if((int)((this.getPosition().x-16)/32) < 13 && ((this.getPosition().y-16)/32)%2 != 0) {
				sb.draw(xpRight,(int)body.getPosition().x-15+32, (int)body.getPosition().y-16,32,32);
				if((int)((this.getPosition().x-16)/32) < 12)
					sb.draw(xpTipRight,(int)body.getPosition().x-15+64, (int)body.getPosition().y-16,32,32);
			}
			
			/** Verificando se hÃ¡ bloco Ã  esquerda da bomba */
			if((int)((this.getPosition().x-16)/32) > 1 && ((this.getPosition().y-16)/32)%2 != 0) {
				sb.draw(xpLeft,(int)body.getPosition().x-16-32, (int)body.getPosition().y-16,32,32);
				if((int)((this.getPosition().x-16)/32) > 2)
					sb.draw(xpTipLeft,(int)body.getPosition().x-16-64, (int)body.getPosition().y-16,32,32);
				
			}
			
			/** Verificando se hÃ¡ bloco abaixo da bomba */
			if((int)((this.getPosition().y-16)/32) > 1 && ((this.getPosition().x-16)/32)%2 != 0) {			
				sb.draw(xpDown,(int)body.getPosition().x-16, (int)body.getPosition().y-16-32,32,32);
				if((int)((this.getPosition().y-16)/32) > 2)
					sb.draw(xpTipDown,(int)body.getPosition().x-16, (int)body.getPosition().y-16-64,32,32);
				
			}
			
			/** Verificando se hÃ¡ bloco acima da bomba */
			if((int)((this.getPosition().y-16)/32) < 13 && ((this.getPosition().x-16)/32)%2 != 0) {			
				sb.draw(xpUp,(int)body.getPosition().x-16, (int)body.getPosition().y-16+32,32,32);
				if((int)((this.getPosition().y-16)/32) < 12)
					sb.draw(xpTipUp,(int)body.getPosition().x-16, (int)body.getPosition().y-16+64,32,32);
		
			}
			
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
			
			this.getBody().createFixture(fdef).setUserData("explosao");
		}
		
		if((int)((this.getPosition().x-16)/32) %2!=0){
			shape.setAsBox((15)/PPM, (15+32*2)/PPM);
			fdef.shape =shape;
			fdef.isSensor=true;
			this.getBody().createFixture(fdef).setUserData("explosao");
		}
	}
	
}
