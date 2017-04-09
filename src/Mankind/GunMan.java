package Mankind;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.World;

import Weapon.AutoBullet;
import Weapon.Bullet;
import Weapon.Gun;
import Weapon.TailGun;
import Enviroments.GrassSet;

public class GunMan extends JointCreature {

	private Gun gun;
	private int cdMAX=200;
	private int cd;
	public GunMan(char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		// TODO Auto-generated constructor stub
		haveGun();
treadable=false;
	}
	public void setEnemySet(EnemySet enemySet){
		super.setEnemySet(enemySet);
		gun=new TailGun(enemySet, gra, this, 1);
		gun.bSpeed=World.baseBSpeed;
	}
	public void drawElement(GL10 gl){
		super.drawElement(gl);
		gun.drawElement(gl);
	}
	public void randomAction() {// 周期
		if(cd++>cdMAX){
			cd=0;
		}else return ;
		int enemyId=-1;
		 double minDistance=10000*10000;// zheng wu qiong
		for(int i=0;i<enemySet.cList.size();i++){
			Creature gp = enemySet.cList.get(i);
			if(gp.isDead)continue;
			if (gp.x < Player.gx1
					|| gp.x > Player.gx2
					|| gp.y < Player.gy1
					|| gp.y > Player.gy2)continue; 			
			
			double d1=Math.pow(gp.x-x,2)+Math.pow(gp.y-y,2);
			
			if(minDistance>d1){
				minDistance=d1;
				enemyId=i;
			}
		}
		
		if(enemyId!=-1){
			Creature enemy = enemySet.cList.get(enemyId);
			
			float enemyAngle = (float) Math.atan2(enemy.y-y, enemy.x-x);
			gun.gunCheck(enemyAngle);
			setGunAngle(enemyAngle*180/3.14f);
		}
	}
}
