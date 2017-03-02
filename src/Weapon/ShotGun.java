package Weapon;

import java.util.ArrayList;

import com.mingli.toms.World;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

public class ShotGun extends Gun{

	private int count=5;
	public ShotGun(EnemySet es, Creature c, int bCount) {
		super(es,  c, bCount);
//		count = bCount-1;
		cd=2*super.cd;
		bSpeed = 18;// 射速系数
	}
	public void gunCheck(float ex, float ey){
		gunCheck(ex,ey,count);
	}
	public void gunCheck(float ex, float ey,int count){
		super.gunCheck(ex, ey);
		otherBulletCheck(count);
	}
	public void alwaysFire(){
		super.alwaysFire();
		otherBulletCheck(count);
	}
	public void gunCheck(float angle){
		super.gunCheck(angle);
		otherBulletCheck(count);
	}
	private void otherBulletCheck(int count) {
//		this.count=count-1;//去掉上个方法触发的一发
		double superAngle=getAngle();
		
		for(int i=0;i<count;i++){
			if(bulletIndex>=bList.size())bulletIndex=0;//子弹重置计数器
			Bullet b = bList.get(bulletIndex++);
			
			//180=pi 45=pi/4=0.8 30=0.6
			if (b.isFire())
				continue;// 子弹停止的时候
			setAngle(0.6*(Math.random()-0.5)+superAngle);
			
			tringerCheck(b);
			
		}
		setAngle(superAngle);
	}
	protected void setBullet(int bCount) {
		bList = new ArrayList<Bullet>();
//		LightSpotSet lss=new BoomSet(10);
		for (int i = 0; i < bCount; i++) {
//			bList.add(i, new TailBullet(enemySet, 100));// 子弹敌对势力
			bList.add(i, new ToBigBullet(enemySet));// 子弹敌对势力
		}
		loadTexture();
	}
	/*protected void setBullet(int bCount) {
		bList = new ArrayList<Bullet>();
		LightSpotSet lss=new BoomSet(10);
		for (int i = 0; i < bCount; i++) {
//			bList.add(i, new TailBullet(enemySet, 100));// 子弹敌对势力
			bList.add(i, new Missile(enemySet, 100, lss));// 子弹敌对势力
		}
		loadTexture();
	}*/
}
