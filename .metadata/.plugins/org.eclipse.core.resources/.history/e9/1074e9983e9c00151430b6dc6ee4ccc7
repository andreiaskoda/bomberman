package com.bomber.states;

import static handlers.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.bomber.Game;
import com.bomber.entities.Player;
import com.bomber.entities.Bomb;
import handlers.GameKeys;
import handlers.GameStateManager;
import handlers.MyContactListener;

public class Play extends GameState{

	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera b2dCam;
	
	//private Body player.getBody();
	
	private TiledMap tileMap;
	private float tileSize;
	private OrthogonalTiledMapRenderer tmr;
	
	private Player player ;
	private MyContactListener cl ;
	private ArrayList<Bomb> bombs;
	
	
	public Play(GameStateManager gsm){
		
		
		super(gsm);
		
		//arruamndo as parada do b2d
		world = new World(new Vector2(0,0),true);
		
		world.setContactListener(cl =new MyContactListener());
		b2dr= new Box2DDebugRenderer();
		
		//cria player
		createPlayer();
		
		//cria tile
		createTiles();
		
		
		
		//cria lista de bombas
		bombs= new ArrayList<Bomb> ();
		
		
		
		
		//arrumando a camera
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false , 480/PPM, 480/PPM);
		
		/////////////////////////////////////////////////////////////////
		
	
	}

	@Override
	public void handleInput() {
		
		if(GameKeys.isDown(GameKeys.RIGHT)&&player.estaParado()&&!cl.isBlockedRight()){
			
			player.moveRight();
		}
		else if(GameKeys.isDown(GameKeys.LEFT)&&player.estaParado()&&!cl.isBlockedLeft()){
			player.moveLeft();
		}
		else if(GameKeys.isDown(GameKeys.UP)&&player.estaParado()&&!cl.isBlockedUp()){
			player.moveUp();
		}
		else if(GameKeys.isDown(GameKeys.DOWN)&&player.estaParado()&&!cl.isBlockedDown()){
			player.moveDown();
		}
		else if(GameKeys.isPressed(GameKeys.ENTER)&&player.estaParado()){
			System.out.println("setbomb");
			this.setBomb();
		}
		
	}

	@Override
	public void update(float dt) {
		handleInput();
		
		world.step(dt, 6, 2);
		player.update(dt);
		
		
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
		
		
		//draw tilemap
		tmr.setView(cam);
		tmr.render();
		
		//draw player 
		sb.setProjectionMatrix(cam.combined);
		player.render(sb);
		
		//renderiza todas as bombas
		for(Bomb b:bombs){
		b.render(sb);
		}
		// draw b2d world
		//b2dr.render(world, b2dCam.combined);
		
		
		
		
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	private void setBomb(){
		BodyDef bdef= new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
	
		
		bdef.position.set((int)player.getPosition().x,(int)player.getPosition().y);
		bdef.type = BodyType.StaticBody;
		Body body = world.createBody(bdef);
		shape.setAsBox(15/PPM, 15/PPM);
		fdef.shape =shape;
		fdef.isSensor=true;
		body.createFixture(fdef);
		Bomb b = new Bomb(body);
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
		body.createFixture(fdef);
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
	
	
	
	
}
