package handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener{

	private boolean blockedUp=false;
	private boolean blockedDown=false;
	private boolean blockedLeft=false;
	private boolean blockedRight=false;
	private int ncu=0;
	private int ncd=0;
	private int ncr=0;
	private int ncl=0;
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if(fa.getUserData()!=null&&fb.getUserData()!=null){
		//System.out.println(fa.getUserData()+" <-fa , fb->"+fb.getUserData());
		if (fa.getUserData()!=null&&fb.getUserData().equals("UP")){
			
			ncu++;
			//this.setBlockedUp(true);
		}
		
		if (fa.getUserData()!=null&&fb.getUserData().equals("DOWN")){
			//this.setBlockedDown(true);
			ncd++;
		}
		
		if (fa.getUserData()!=null&&fb.getUserData().equals("LEFT")){
			//this.setBlockedLeft(true);
			ncl++;
		}
		
		if (fa.getUserData()!=null&&fb.getUserData().equals("RIGHT")){
			//this.setBlockedRight(true);
			ncr++;
		}
		}
	}

	@Override
	public void endContact(Contact contact) {
		
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if(fa.getUserData()!=null&&fb.getUserData()!=null){
			//System.out.println(fa.getUserData()+" <-fa , fb->"+fb.getUserData());
			if (fa.getUserData()!=null&&fb.getUserData().equals("UP")){
				//this.setBlockedUp(false);
				//System.out.println("up liberado");
				ncu--;
			}
			
			if (fa.getUserData()!=null&&fb.getUserData().equals("DOWN")){
				//this.setBlockedDown(false);
				ncd--;
				
			}
			
			if (fa.getUserData()!=null&&fb.getUserData().equals("LEFT")){
				//this.setBlockedLeft(false);
				//System.out.println("left liberado");
				ncl--;
				
			}
			
			if (fa.getUserData()!=null&&fb.getUserData().equals("RIGHT")){
				//this.setBlockedRight(false);
				//System.out.println("right liberado");
				ncr--;
			}
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
	public boolean isBlockedUp() {
		if(ncu>0){
			return true;
		}else{
			return false;
		}
		
	}

	public void setBlockedUp(boolean blockedUp) {
		this.blockedUp = blockedUp;
	}

	public boolean isBlockedDown() {
		if(ncd>0){
			return true;
		}else{
			return false;
		}
	}

	public void setBlockedDown(boolean blockedDown) {
		this.blockedDown = blockedDown;
	}

	public boolean isBlockedLeft() {
		if(ncl>0){
			return true;
		}else{
			return false;
		}
	}

	public void setBlockedLeft(boolean blockedLeft) {
		this.blockedLeft = blockedLeft;
	}

	public boolean isBlockedRight() {
		if(ncr>0){
			return true;
		}else{
			return false;
		}
	}

	public void setBlockedRight(boolean blockedRight) {
		this.blockedRight = blockedRight;
	}

	
	
	
	
}
