package Weapon;

import java.util.ArrayList;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

public class AutoBulletGun extends Gun{

	private  EnemySet es;
	public AutoBulletGun(EnemySet es,GrassSet gra, Creature c, int bCount) {
		super(es,gra, c, bCount);
		// TODO Auto-generated constructor stub
		this.es = es;
	}
	protected void setBullet(int bCount) {
		bList = new ArrayList<Bullet>();
		for (int i = 0; i < bCount; i++) {
			bList.add(i, new AutoBullet(enemySet, gra, player));// 子弹敌对势力
//			bList.add(i, new Huck(enemySet,  1000,player));// 子弹敌对势力
		}
		loadTexture();
	}
	protected void tringerCheck(Bullet bullet){
//		if(false)super.tringerCheck(bullet);
		int enemyId=0;
		Creature gp= es.cList.get(0); ;
		double minDistance=Math.pow(gp.x-x,2)+Math.pow(gp.y-y,2);
		
		for(int i=1;i<es.cList.size();i++){
			gp= es.cList.get(i);
			double d1=Math.pow(gp.x-x,2)+Math.pow(gp.y-y,2);
			
			if(minDistance>d1){
				minDistance=d1;
				enemyId=i;
			}
		}
		
		if(enemyId!=-1){
			Creature c=enemySet.cList.get(enemyId);
					double javaAngle=Math.atan2(c.y-y, c.x-x);
					for(Bullet b:bList)
					if(!b.isFire())
					{
						cos = Math.cos(getAngle());
						sin = Math.sin(getAngle());

						x = (float) (gunLength * cos + player.x);
						y = (float) (gunLength * sin + player.y);
					((AutoBullet)b).tringer(x, y, c);
						break;// only once
					}
		}
	}

}
