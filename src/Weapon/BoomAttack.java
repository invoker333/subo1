package Weapon;

import javax.microedition.khronos.opengles.GL10;

import Element.Boom;
import Element.LightSpot;
import Mankind.Creature;
import aid.Log;

public class BoomAttack {
	protected  LightSpot ls;
	private Bullet bullet;
	public BoomAttack(Bullet bullet) {
		this.bullet = bullet;
		// TODO Auto-generated constructor stub
		ls = new Boom();
	}
	protected void gotTarget(Creature enemy){
		ls.tringer(bullet.x, bullet.y);
		Log.i("boom.x"+bullet.x+"  y  "+bullet.y);
		ls.angle=(float) (360f*Math.random());
//		super.gotTarget(enemy);
		
//		int size=bullet.eList.size();//to avoid if the size is changed in attacking
		for (int i = 0; i < bullet.eList.size(); i++) {
			enemy = bullet.eList.get(i);
			if (!enemy.isDead)

				if (Math.pow(enemy.x-bullet.x,2)+Math.pow(enemy.y-bullet.y,2)<Math.pow(64+Math.max(enemy.getwEdge(),enemy.gethEdge()),2)) {
					bullet.es.attacked(enemy, bullet.attack);				
				}
		}
		
		bullet.resetBullet();
	}
	public void drawElement(GL10 gl){
		ls.drawElement(gl);
	}
}
