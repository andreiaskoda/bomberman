package com.bomber.states;

import static handlers.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.bomber.Game;
import com.bomber.entities.GLaDOS;
import com.bomber.entities.Enemy;
import com.bomber.entities.Player;
import com.bomber.entities.Bomb;

import handlers.B2DVars;
import handlers.GameKeys;
import handlers.GameStateManager;
import handlers.MyContactListener;

public class Play extends GameState{

	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera b2dCam;
	
	private TiledMap tileMap;
	private float tileSize;
	private OrthogonalTiledMapRenderer tmr;
	
	private Player player;
	private Enemy enemy1;
	private Enemy enemy2;
	private Enemy enemy3;
	private GLaDOS glados;
	private MyContactListener cl ;
	private ArrayList<Bomb> bombs;
	
	private BitmapFont mBitmapFont;
	 
	private boolean flag = true;
	
	
	public Play(GameStateManager gsm){		
		super(gsm);

		world = new World(new Vector2(0,0),true);
		
		world.setContactListener(cl =new MyContactListener());
		b2dr = new Box2DDebugRenderer();
		
		// Criando jogador
		createPlayer();
		Fixture f;
		
		// Criando inimigos
		createEnemy1();
		createEnemy2();
		createEnemy3();
		
		// Criando lista de bombas
				bombs = new ArrayList<Bomb> ();

		
		// Criando a inteligência artificial que irá controlar os inimigos
		glados = new GLaDOS(enemy1, enemy2, enemy3,bombs);
		
		// Criando tiles
		createTiles();

		
		// Arrumando a camera
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false , 480/PPM, 480/PPM);
		
		/////////////////////////////////////////////////////////////////
		FileHandle fontFile = Gdx.files.internal("arial.ttf");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);

	    mBitmapFont = generator.generateFont(20);
	    generator.dispose();

	    mBitmapFont.setColor(0f, 0f, 0f, 1); 
	}

	@Override
	public void handleInput() {
		
		if(cl.isTheGameOver()){
			
			if(GameKeys.isPressed(GameKeys.ENTER))
				this.reset();
			
			return;
		}
		
		if(GameKeys.isDown(GameKeys.RIGHT) && 
				player.isStatic() && !cl.isBlockedRight())
			player.moveRight();
		
		else if(GameKeys.isDown(GameKeys.LEFT) && 
				player.isStatic() && !cl.isBlockedLeft())
			player.moveLeft();
		
		else if(GameKeys.isDown(GameKeys.UP) && 
				player.isStatic() && !cl.isBlockedUp())
			player.moveUp();
		
		else if(GameKeys.isDown(GameKeys.DOWN) && 
				player.isStatic() && !cl.isBlockedDown())
			player.moveDown();
		
		else if(GameKeys.isPressed(GameKeys.ENTER) && player.isStatic())
			this.setBomb();
		
		 else if(player.isStatic()) 
			player.animationStop();
		
	}

	@Override
	public void update(float dt) {
		
		if(cl.isTheGameOver() && flag == true){
			player.animationDying();
			flag = false;
		}
		
		handleInput();
		glados.moveEnemy(player, cl);
		
		world.step(dt, 6, 2);
		
		// Coletando corpos
		Array<Body> bodies = cl.getBodiesToRemove();
		
		for(int i = 0; i < bodies.size; i++) {
			Body b = bodies.get(i);
			world.destroyBody(b);
			glados.died();
		}
		bodies.clear();
		
		player.update(dt);
		
		if(!cl.isEnemy1dead())
			enemy1.update(dt);
		
		if(!cl.isEnemy2dead())
			enemy2.update(dt);
		
		if(!cl.isEnemy3dead())
			enemy3.update(dt);
		
		
		for(int i =0;i<bombs.size();i++){
			Bomb b = bombs.get(i);
			b.update(dt);
			if(b.kabumTime()){
				Body body =b.getBody();
				bombs.remove(i);
				i--;
				world.destroyBody(body);
			}
		}
	}

	@Override
	public void render() {
		
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Desenha mapa
		tmr.setView(cam);
		tmr.render();
		
		// Renderiza todas as bombas
		for(Bomb b:bombs)
			b.render(sb);
				
		sb.setProjectionMatrix(cam.combined);
		player.render(sb);
		
		if(!cl.isEnemy1dead())
			enemy1.render(sb);
		if(!cl.isEnemy2dead())
			enemy2.render(sb);
		if(!cl.isEnemy3dead())
			enemy3.render(sb);
				
		if(cl.isTheGameOver()){
			player.render(sb);	
			sb.begin();
			Texture tex = Game.res.getTexture("gameover");
			sb.draw(tex, 120,150,240, 180);
			mBitmapFont.draw(sb, "Press ENTER to restart", 120, 100);
			sb.end();
		}
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	private void setBomb(){
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		
		bdef.position.set((int)player.getPosition().x,(int)player.getPosition().y);
		bdef.type = BodyType.StaticBody;
		Body body = world.createBody(bdef);
		shape.setAsBox(15/PPM, 15/PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_BOMB;
		fdef.isSensor = true;
		body.setSleepingAllowed(false);
		body.createFixture(fdef).setUserData("inactiveBomb");
		Bomb b = new Bomb(body,cl);
		bombs.add(b);
		
				
	}
	
	private void createPlayer(){
		
		BodyDef bdef= new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		
		//player
		bdef.position.set(48.1f/PPM,48.1f/PPM);
		bdef.type = BodyType.DynamicBody;
		Body body = world.createBody(bdef);
		shape.setAsBox(15/PPM, 15/PPM);
		fdef.shape =shape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		body.setSleepingAllowed(false);
		body.createFixture(fdef).setUserData("player");;
		body.setLinearDamping(7000);
		
		//criando sensor up
		shape.setAsBox(5, 10,new Vector2(0,15),0);
		fdef.shape=shape;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData("UP");
		
		//criando sensor down
		shape.setAsBox(5, 10,new Vector2(0,-15),0);
		fdef.shape=shape;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData("DOWN");
		
		//criando sensor right	
		shape.setAsBox(10, 5,new Vector2(15,0),0);
		fdef.shape=shape;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData("RIGHT");
				
		//criando sensor left	
		shape.setAsBox(10, 5,new Vector2(-15,0),0);
		fdef.shape=shape;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData("LEFT");
		
		player = new Player (body);
		body.setUserData(player);
	}
	
	private void createEnemy1(){
		
		BodyDef bdef= new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		
		//enemy1
		bdef.position.set(432.1f/PPM, 48.1f/PPM);
		bdef.type = BodyType.DynamicBody;
		Body body = world.createBody(bdef);
		shape.setAsBox(15/PPM, 15/PPM);
		fdef.shape = shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
		body.setSleepingAllowed(false);
		body.createFixture(fdef).setUserData("enemy1");
		body.setLinearDamping(7000);
		
		//criando sensor centro
		shape.setAsBox(5, 10,new Vector2(0,15),0);
		fdef.shape=shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION
				| B2DVars.BIT_PLAYER;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("CENTER_E1");
		
		//criando sensor up
		shape.setAsBox(5, 10,new Vector2(0,15),0);
		fdef.shape=shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData("UP_E1");
		
		//criando sensor down
		shape.setAsBox(5, 10,new Vector2(0,-15),0);
		fdef.shape=shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData("DOWN_E1");
		
		//criando sensor right	
		shape.setAsBox(10, 5,new Vector2(15,0),0);
		fdef.shape=shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData("RIGHT_E1");
				
		//criando sensor left	
		shape.setAsBox(10, 5,new Vector2(-15,0),0);
		fdef.shape=shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData("LEFT_E1");

		enemy1 = new Enemy (body, Game.res.getTexture("e1up"), 
				Game.res.getTexture("e1down"), Game.res.getTexture("e1left"),
				Game.res.getTexture("e1right"));
		body.setUserData(enemy1);
	}
	
	private void createEnemy2(){
		
		BodyDef bdef= new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		
		//enemy2
		bdef.position.set(432.1f/PPM, 432.1f/PPM);
		bdef.type = BodyType.DynamicBody;
		Body body = world.createBody(bdef);
		shape.setAsBox(15/PPM, 15/PPM);
		fdef.shape =shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
		body.setSleepingAllowed(false);
		body.createFixture(fdef).setUserData("enemy2");;
		body.setLinearDamping(7000);
		
		//criando sensor centro
		shape.setAsBox(5, 10,new Vector2(0,15),0);
		fdef.shape=shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION
				| B2DVars.BIT_PLAYER;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("CENTER_E2");
		
		//criando sensor up
		shape.setAsBox(5, 10,new Vector2(0,15),0);
		fdef.shape=shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData("UP_E2");
		
		//criando sensor down
		shape.setAsBox(5, 10,new Vector2(0,-15),0);
		fdef.shape=shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData("DOWN_E2");
		
		//criando sensor right	
		shape.setAsBox(10, 5,new Vector2(15,0),0);
		fdef.shape=shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData("RIGHT_E2");
				
		//criando sensor left	
		shape.setAsBox(10, 5,new Vector2(-15,0),0);
		fdef.shape=shape;
		fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
		fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
		fdef.isSensor=true;
		body.createFixture(fdef).setUserData("LEFT_E2");
		
		enemy2 = new Enemy (body, Game.res.getTexture("e2up"), 
				Game.res.getTexture("e2down"), Game.res.getTexture("e2left"),
				Game.res.getTexture("e2right"));
		body.setUserData(enemy2);
	}

	private void createEnemy3(){
	
	BodyDef bdef= new BodyDef();
	PolygonShape shape = new PolygonShape();
	FixtureDef fdef = new FixtureDef();
	
	//enemy3
	bdef.position.set(48.1f/PPM, 432.1f/PPM);
	bdef.type = BodyType.DynamicBody;
	Body body = world.createBody(bdef);
	shape.setAsBox(15/PPM, 15/PPM);
	fdef.shape =shape;
	fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
	fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
	body.setSleepingAllowed(false);
	body.createFixture(fdef).setUserData("enemy3");;
	body.setLinearDamping(7000);
	
	//criando sensor centro
	shape.setAsBox(5, 10,new Vector2(0,15),0);
	fdef.shape=shape;
	fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
	fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION
			| B2DVars.BIT_PLAYER;
	fdef.isSensor = true;
	body.createFixture(fdef).setUserData("CENTER_E3");
	
	//criando sensor up
	shape.setAsBox(5, 10,new Vector2(0,15),0);
	fdef.shape=shape;
	fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
	fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
	fdef.isSensor=true;
	body.createFixture(fdef).setUserData("UP_E3");
	
	//criando sensor down
	shape.setAsBox(5, 10,new Vector2(0,-15),0);
	fdef.shape=shape;
	fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
	fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
	fdef.isSensor=true;
	body.createFixture(fdef).setUserData("DOWN_E3");
	
	//criando sensor right	
	shape.setAsBox(10, 5,new Vector2(15,0),0);
	fdef.shape=shape;
	fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
	fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
	fdef.isSensor=true;
	body.createFixture(fdef).setUserData("RIGHT_E3");
			
	//criando sensor left	
	shape.setAsBox(10, 5,new Vector2(-15,0),0);
	fdef.shape=shape;
	fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
	fdef.filter.maskBits = B2DVars.BIT_BOMB | B2DVars.BIT_EXPLOSION;
	fdef.isSensor=true;
	body.createFixture(fdef).setUserData("LEFT_E3");
	
	enemy3 = new Enemy (body, Game.res.getTexture("e3up"), 
			Game.res.getTexture("e3down"), Game.res.getTexture("e3left"),
			Game.res.getTexture("e3right"));
	body.setUserData(enemy3);
}
	
	private void createTiles(){
		//load map
		tileMap= new TmxMapLoader().load("teste2.tmx");
		tmr = new OrthogonalTiledMapRenderer(tileMap);
				
		TiledMapTileLayer layer=(TiledMapTileLayer) tileMap.getLayers().get("layer1");
		tileSize = layer.getTileHeight();
		createLayer(layer);
	}
	
	private void createLayer(TiledMapTileLayer layer){
		
		BodyDef bdef= new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		
		//todas a cells
		for(int row =0 ; row< layer.getHeight();row++){
			for( int col =0 ; col< layer.getWidth();col++){
				Cell cell = layer.getCell(col,row);
						
				if(cell==null)continue;
				if(cell.getTile()==null)continue;
					
				bdef.type = BodyType.StaticBody;
				bdef.position.set((col+0.5f)*tileSize/PPM,(row+0.5f)*tileSize/PPM);
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[5];
				v[0] = new Vector2(-tileSize/2/PPM,-tileSize/2/PPM);
				v[1] = new Vector2(-tileSize/2/PPM,tileSize/2/PPM);
				v[2] = new Vector2(tileSize/2/PPM,tileSize/2/PPM);
				v[3] = new Vector2(tileSize/2/PPM,-tileSize/2/PPM);
				v[4] = new Vector2(-tileSize/2/PPM,-tileSize/2/PPM);
				cs.createChain(v);
				fdef.friction =0;
				fdef.shape =cs;
						
				world.createBody(bdef).createFixture(fdef).setUserData("chao");			
			}
		}
				
	}
	
	public void reset(){
		//destruir player bombas e bixos
		world.destroyBody(this.player.getBody());
		world.destroyBody(this.enemy1.getBody());
		world.destroyBody(this.enemy2.getBody());
		world.destroyBody(this.enemy3.getBody());

		//tirar as bombas buga a solidifica��o delas (descobrir o porque)
		
//		for(int i =0;i<bombs.size();i++){
//			Bomb b = bombs.get(i);
//			
//			
//				
//				Body body =b.getBody();
//				bombs.remove(i);
//				i--;
//				world.destroyBody(body);
//		}
		
		
		
		//tirar o game over do cl
		cl.resetGameOver();
		
		
		//recriar tudo
		createPlayer();
		createEnemy1();
		createEnemy2();
		createEnemy3();
		
	}
	
}
