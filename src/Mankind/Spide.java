package Mankind;

import javax.microedition.khronos.opengles.GL10;

import Weapon.Hook;
import Weapon.TailBullet;

import com.mingli.toms.Log;

import Enviroments.GrassSet;
import element2.Tail;
import element2.TexId;

public class Spide extends Emplacement {

	public static float dsmax = 64;
	Tail tail;
	Creature catcher;
	public Spide(char bi,GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
		setTextureId(TexId.THUNDER);
		setSoundId(EnemySet.SPIDE);
		
		tail=new Tail(2);
		tail.width = 8;
		this.attack=0;
		setG(0);
	}

	void initbullet(EnemySet es) {
		this.es = es;
		b = new Hook(es,  this){
			protected void gotTarget(Creature enemy) {
					if(catcher==null || catcher.isDead) {
						catcher=enemy;
				}
			}
		};
		b.loadTexture(TexId.THUNDER);
		dsmax =((Hook) b).getRange();
		setRange(((Hook) b).getRange());
	}
	  protected boolean targetCanbeCatched(Creature gp) {
		  if(gp.isDead)return false;
			 if(gp.equals(catcher))return false;
		 
		return true;
		}
	
	

	public void drawElement(GL10 gl) {
		move();gravity();
		drawScale(gl);
		
		tail.drawElement(gl);
		b.drawElement(gl);
	}
	
	
	 protected void moveCheck(){
		tailCheck();
		
//		xSpeed=0;
//		super.moveCheck();
	}
	 protected void gravityCheck(){
//		ySpeed=0;
//		y+=getG();
//		super.gravityCheck();
//		 Log.i(" ys : "+ySpeed+" g:"+getG());
	}

	protected void tailCheck() {
		 if(catcher!=null&&catcher.isDead)catcher=null;
		 
		tail.startTouch(x, y);
		float dsmax=Spide.dsmax;
		if (catcher!=null&&!catcher.isDead) {
			tail.tringer(catcher.x, catcher.y);

			final float tanxingxishu = 0.1f;
			final float zuni = 0.99f;

			stringCheck(catcher, dsmax, tanxingxishu, zuni);
		}
	}
}
