package handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements ContactListener{

	private boolean gameOver = false;
	private boolean e1dies = false;
	private boolean e2dies = false;
	private boolean e3dies = false;

	private int ncu = 0;
	private int ncd = 0;
	private int ncr = 0;
	private int ncl = 0;
	private int nct = 0;
	
	private int e1u = 0;
	private int e1d = 0;
	private int e1r = 0;
	private int e1l = 0;

	private int e2u = 0;
	private int e2d = 0;
	private int e2r = 0;
	private int e2l = 0;

	private int e3u = 0;
	private int e3d = 0;
	private int e3r = 0;
	private int e3l = 0;
	
	private Array<Body> bodiesToRemove;
	
	public MyContactListener() {
		super();
		bodiesToRemove = new Array<Body>();
	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		Fixture faux;
		
		if(fa.getUserData() != null && fb.getUserData() != null){
			
			if( (fb.getUserData().equals("player") &&
					(fa.getUserData().equals("CENTER_E1") 
							|| fa.getUserData().equals("CENTER_E2")
							|| fa.getUserData().equals("CENTER_E3")) )
					|| (fa.getUserData().equals("player") && fb.getUserData().equals("CENTER_E1") 
							|| fb.getUserData().equals("CENTER_E2")
							|| fb.getUserData().equals("CENTER_E3")) )
				this.gameOver = true;

			if(fb.getUserData().equals("activeBomb")){
				faux = fb;
				fb = fa;
				fa = faux;
			}
			
			if(fa.getUserData().equals("player") &&
				fb.getUserData().equals("inactiveBomb")){
				nct++;
			}
			
			if(fa.getUserData().equals("player") &&	fb.getUserData().equals("explosion")){
				this.gameOver = true;
			}
			
			if(fa.getUserData().equals("enemy1") &&	fb.getUserData().equals("explosion")) {
				this.e1dies = true;
				bodiesToRemove.add(fa.getBody());
			}
				
			if(fa.getUserData().equals("enemy2") &&	fb.getUserData().equals("explosion")) {
				this.e2dies = true;
				bodiesToRemove.add(fa.getBody());
			}
				
			if(fa.getUserData().equals("enemy3") &&	fb.getUserData().equals("explosion")) {
				this.e3dies = true;
				bodiesToRemove.add(fa.getBody());
			}
			
			/** Verificando em volta do bomberman */
			if (fa.getUserData()!=null && fb.getUserData().equals("UP"))			
				ncu++;

			if (fa.getUserData()!=null && fb.getUserData().equals("DOWN"))
				ncd++;
			
			
			if (fa.getUserData()!=null && fb.getUserData().equals("LEFT"))
				ncl++;
			
			
			if (fa.getUserData()!=null && fb.getUserData().equals("RIGHT"))
				ncr++;
			
			
			/** Verificando em volta do inimigo 1 */
			if (fa.getUserData()!=null && fb.getUserData().equals("UP_E1"))			
				e1u++;
			
			if (fa.getUserData()!=null && fb.getUserData().equals("DOWN_E1"))
				e1d++;
		
			if (fa.getUserData()!=null && fb.getUserData().equals("LEFT_E1"))
				e1l++;
			
			if (fa.getUserData()!=null && fb.getUserData().equals("RIGHT_E1"))
				e1r++;
			
			/** Verificando em volta do inimigo 2 */
			if (fa.getUserData()!=null && fb.getUserData().equals("UP_E2"))			
				e2u++;
			
			if (fa.getUserData()!=null && fb.getUserData().equals("DOWN_E2"))
				e2d++;
		
			if (fa.getUserData()!=null && fb.getUserData().equals("LEFT_E2"))
				e2l++;
			
			if (fa.getUserData()!=null && fb.getUserData().equals("RIGHT_E2"))
				e2r++;
			
			/** Verificando em volta do inimigo 3 */
			if (fa.getUserData()!=null && fb.getUserData().equals("UP_E3"))			
				e3u++;
			
			if (fa.getUserData()!=null && fb.getUserData().equals("DOWN_E3"))
				e3d++;
		
			if (fa.getUserData()!=null && fb.getUserData().equals("LEFT_E3"))
				e3l++;
			
			if (fa.getUserData()!=null && fb.getUserData().equals("RIGHT_E3"))
				e3r++;
			
		}
	}

	@Override
	public void endContact(Contact contact) {
		
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if(fa.getUserData()!=null && fb.getUserData()!=null){

			Fixture faux;
			if(fb.getUserData().equals("activeBomb")){
				faux = fb;
				fb = fa;
				fa = faux;
			}
			
			if(fa.getUserData().equals("player") && 
					fb.getUserData().equals("inactiveBomb"))
				nct--;
			
			/** Verificando em volta do bomberman */
			if (fa.getUserData()!=null && fb.getUserData().equals("UP"))
				ncu--;
			
			if (fa.getUserData()!=null && fb.getUserData().equals("DOWN"))
				ncd--;				
			
			if (fa.getUserData()!=null && fb.getUserData().equals("LEFT"))
				ncl--;				
			
			if (fa.getUserData()!=null && fb.getUserData().equals("RIGHT"))
				ncr--;
			
			/** Verificando em volta do inimigo 1 */
			if (fa.getUserData()!=null && fb.getUserData().equals("UP_E1"))
				e1u--;
			
			if (fa.getUserData()!=null && fb.getUserData().equals("DOWN_E1"))
				e1d--;				
			
			if (fa.getUserData()!=null && fb.getUserData().equals("LEFT_E1"))
				e1l--;				
			
			if (fa.getUserData()!=null && fb.getUserData().equals("RIGHT_E1"))
				e1r--;
			
			/** Verificando em volta do inimigo 2 */
			if (fa.getUserData()!=null && fb.getUserData().equals("UP_E2"))
				e2u--;
			
			if (fa.getUserData()!=null && fb.getUserData().equals("DOWN_E2"))
				e2d--;				
			
			if (fa.getUserData()!=null && fb.getUserData().equals("LEFT_E2"))
				e2l--;				
			
			if (fa.getUserData()!=null && fb.getUserData().equals("RIGHT_E2"))
				e2r--;

			/** Verificando em volta do inimigo 3 */
			if (fa.getUserData()!=null && fb.getUserData().equals("UP_E3"))
				e3u--;
			
			if (fa.getUserData()!=null && fb.getUserData().equals("DOWN_E3"))
				e3d--;				
			
			if (fa.getUserData()!=null && fb.getUserData().equals("LEFT_E3"))
				e3l--;				
			
			if (fa.getUserData()!=null && fb.getUserData().equals("RIGHT_E3"))
				e3r--;
			
		}
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
	}
	
	
	/** Funções para verificar se o bomberman pode andar */
	public boolean isOnTop(){
		if(nct>0)
			return true;
		
		return false;
	}
	
	public boolean isBlockedUp() {
		if(ncu>0)
			return true;
		else
			return false;
	}

	public boolean isBlockedDown() {
		if(ncd>0)
			return true;
		else
			return false;
	}

	public boolean isBlockedLeft() {
		if(ncl>0)
			return true;
		else
			return false;
	}

	public boolean isBlockedRight() {
		if(ncr>0)
			return true;
		else
			return false;
	}
	
	/** Funções para verificar se o inimigo 1 pode andar */
	public boolean enemy1blockedUp() {
		if(e1u>0)
			return true;
		else
			return false;
	}

	public boolean enemy1blockedDown() {
		if(e1d>0)
			return true;
		else
			return false;
	}

	public boolean enemy1blockedLeft() {
		if(e1l>0)
			return true;
		else
			return false;
	}

	public boolean enemy1blockedRight() {
		if(e1r>0)
			return true;
		else
			return false;
	}
	
	/** Funções para verificar se o inimigo 2 pode andar */
	public boolean enemy2blockedUp() {
		if(e2u>0)
			return true;
		else
			return false;
	}

	public boolean enemy2blockedDown() {
		if(e2d>0)
			return true;
		else
			return false;
	}

	public boolean enemy2blockedLeft() {
		if(e2l>0)
			return true;
		else
			return false;
	}

	public boolean enemy2blockedRight() {
		if(e2r>0)
			return true;
		else
			return false;
	}
	
	/** Funções para verificar se o inimigo 3 pode andar */
	public boolean enemy3blockedUp() {
		if(e3u>0)
			return true;
		else
			return false;
	}

	public boolean enemy3blockedDown() {
		if(e3d>0)
			return true;
		else
			return false;
	}

	public boolean enemy3blockedLeft() {
		if(e3l>0)
			return true;
		else
			return false;
	}

	public boolean enemy3blockedRight() {
		if(e3r>0)
			return true;
		else
			return false;
	}
	

	public boolean isTheGameOver(){	return gameOver; }
	
	public boolean isEnemy1dead() { return e1dies; }
	public boolean isEnemy2dead() { return e2dies; }
	public boolean isEnemy3dead() { return e3dies; }
	
	public Array<Body> getBodiesToRemove() { return bodiesToRemove; }
	
	public void resetGameOver(){
		this.gameOver = false;
		this.e1dies = false;
		this.e2dies = false;
		this.e3dies = false;

		 ncu = 0;
		 ncd = 0;
		 ncr = 0;
		 ncl = 0;
		 nct = 0;
		 
		 e1u = 0;
		 e1d = 0;
		 e1r = 0;
		 e1l = 0;

		 e2u = 0;
		 e2d = 0;
		 e2r = 0;
		 e2l = 0;

		 e3u = 0;
		 e3d = 0;
		 e3r = 0;
		 e3l = 0;
		
	}
	
}
