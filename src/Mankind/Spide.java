package Mankind;

import javax.microedition.khronos.opengles.GL10;

import Weapon.Hook;
import Weapon.TailBullet;

import com.mingli.toms.Log;

import Enviroments.GrassSet;
import element2.Tail;
import element2.TexId;

public class Spide extends Emplacement {

	public static float dsmax = 32;
	Tail tail;
	private Creature[] cc;

	public Spide(char bi,GrassSet gra, float x, float y,Creature[] cc) {
		this(bi,gra, x, y);
		this.cc = cc;
		for(Creature c:cc)
			c.stopMove();
	}

	public Spide(char bi,GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
		setTextureId(TexId.THUNDER);
		setSoundId(EnemySet.SPIDE);
		
		final int length=3;
		cc=new Creature[length];
		tail = new Tail(cc.length + 1);
		tail.width = 8;
		this.attack=0;
	}

	void initbullet(EnemySet es) {
		this.es = es;
		b = new Hook(es,  this){
			protected void gotTarget(Creature enemy) {
				for(int i=0;i<cc.length;i++){
					if(cc[i]==null || cc[i].isDead) {
						cc[i]=enemy;
					}
				}
			}
		};
		b.loadTexture(TexId.THUNDER);
		dsmax =((Hook) b).getRange();
		setRange(((Hook) b).getRange());
		
	}
	  protected boolean targetCanbeCatched(Creature gp) {
		  if(gp.isDead)return false;
		 for(int i=0;i<cc.length;i++)
			 if(gp.equals(cc[i]))return false;
		 
		return true;
		}
	  public void randomAction() {
		  for(int i=0;i<cc.length;i++){
			  Creature c = cc[i];
			  if(c!=null)
			 {
				if (c.isDead)cc[i]=null ;
				else	return;// if has target return
			}
		  }
		  super.randomAction();
	  }
	
	

	public void drawElement(GL10 gl) {
		move();gravity();
		drawScale(gl);
		
		tail.drawElement(gl);
		b.drawElement(gl);
	}
	
	
	public void move(){
		tailCheck();
		xSpeed=0;
	}
	public void gravity(){
		ySpeed=0;
//		y+=getG();
	}

	private void tailCheck() {
		Creature a = this;
		tail.startTouch(x, y);
		
		float dsmax=Spide.dsmax;
		for (Creature c : cc) {
			if (c==null||c.isDead) {
				dsmax+=Spide.dsmax;
				continue;
			}
			tail.tringer(c.x, c.y);

			final float tanxingxishu = 0.1f;
			final float zuni = 0.999f;

			a.stringCheck(c, dsmax, tanxingxishu, zuni);
			a = c;
		}
	}
}
