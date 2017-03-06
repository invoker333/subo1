package Mankind;

import Weapon.Hook;
import element2.Tail;
import element2.TexId;
import Enviroments.GrassSet;

public class Spide3 extends Spide{

	public Spide3(char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		// TODO Auto-generated constructor stub
	}
	private Creature[] cc;

	public Spide3(char bi,GrassSet gra, float x, float y,Creature[] cc) {
		super(bi,gra, x, y);
		tail = new Tail(cc.length + 1);
		tail.width=8;
		this.cc = cc;
		for(Creature c:cc)
			c.stopMove();
	
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
	  
	   protected void tailCheck() {
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
				final float zuni = 0.95f;

				a.stringCheck(c, dsmax, tanxingxishu, zuni);
				a = c;
			}
		}

}
