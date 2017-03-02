package Weapon;

import java.util.ArrayList;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

public class AutoBulletGun extends Gun{

	private  EnemySet es;
	public AutoBulletGun(EnemySet es, Creature c, int bCount) {
		super(es, c, bCount);
		// TODO Auto-generated constructor stub
		this.es = es;
	}
	protected void setBullet(int bCount) {
		bList = new ArrayList<Bullet>();
		for (int i = 0; i < bCount; i++) {
			bList.add(i, new AutoBullet(enemySet, player));// 子弹敌对势力
//			bList.add(i, new Huck(enemySet,  1000,player));// 子弹敌对势力
		}
		loadTexture();
	}
	protected void tringerCheck(Bullet bullet){
		int enemyId=-1;
		double minDistance=-1;
		for(int i=0;i<es.cList.size();i++){
			Creature gp = es.cList.get(i);
			double d1=Math.pow(gp.x-x,2)+Math.pow(gp.y-y,2);
//			final double  range2=;
//			if(d1>range2)continue;
			
			
			if(minDistance==-1){
				minDistance=d1;
				enemyId=i;
			}
			else if(minDistance>d1){
				minDistance=d1;
				enemyId=i;
			}
		}
		Creature goodPeople = null;
		if(enemyId!=-1)goodPeople=es.cList.get(enemyId);
		if(goodPeople==null||goodPeople.isDead)return;
	
		
		double javaAngle=Math.atan2(goodPeople.y-y, goodPeople.x-x);
		float trueAngle=(float) (180* javaAngle/Math.PI);
		final int speed=3;
	
		for(Bullet b:bList)
		if(!b.isFire())
		{
		b.tringer(x, y, bSpeed*Math.cos(javaAngle), bSpeed*Math.sin(javaAngle));
//			b.setEnemyId(enemyId);
			return;// only once
		}
	}

}
