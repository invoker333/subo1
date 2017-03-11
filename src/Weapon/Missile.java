package Weapon;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.Log;
import com.mingli.toms.World;

import element2.TexId;
import Element.Boom;
import Element.LightSpot;
import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

public class Missile extends TailBullet{
	
	protected  LightSpot ls;

	public Missile(EnemySet es,GrassSet gra) {
		super(es,gra);
		attack=4*World.baseAttack;
		// TODO Auto-generated constructor stub
		ls = new Boom();
		
		setSize(20, 20);
		tail.width=(int) (w);
	}
	
	
	protected void gotTarget(Creature enemy){
		ls.tringer(x, y);
		Log.i("boom.x"+x+"  y  "+y);
		ls.angle=(float) (360f*Math.random());
//		super.gotTarget(enemy);
		
		int size=eList.size();//to avoid if the size is changed in attacking
		for (int i = 0; i < size; i++) {
			enemy = eList.get(i);
			if (!enemy.isDead)

				if (Math.pow(enemy.x-x,2)+Math.pow(enemy.y-y,2)<Math.pow(64+Math.max(enemy.getwEdge(),enemy.gethEdge()),2)) {
					es.attacked(enemy, attack);				
				}
		}
		
		resetBullet();
	}
	public void drawElement(GL10 gl){
		ls.drawElement(gl);
		super.drawElement(gl);
	}

}

 class BoomBullet extends  Missile{

	private GrassSet gra;
	public BoomBullet(EnemySet es,GrassSet gra) {
		super( es,gra);
		// TODO Auto-generated constructor stub
		this.gra = gra;
	}
	
	public void gravity(){
		final float g=1;
		ySpeed-=g;
		super.gravity();
	}
	
	
}