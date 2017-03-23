package Weapon;

import java.util.ArrayList;

import com.mingli.toms.Render;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;
import Mankind.Player;

public class AutoBulletGun extends Gun{

	private  EnemySet es;
	public AutoBulletGun(EnemySet es,GrassSet gra, Creature c, int bCount) {
		super(es,gra, c, bCount);
		// TODO Auto-generated constructor stub
		this.es = es;
		cd*=3;
	}
	protected void setBullet(int bCount) {
		bList = new ArrayList<Bullet>();
		for (int i = 0; i < bCount; i++) {
//			bList.add(i, new AutoBubble(enemySet, gra, player));// 子弹敌对势力
			bList.add(i, new AutoBullet(enemySet, gra, player));// 子弹敌对势力
//			bList.add(i, new Huck(enemySet,  1000,player));// 子弹敌对势力
		}
		loadTexture();
	}
	public boolean gunCheck(float ex, float ey) {
		if (bulletIndex >= bList.size())
			bulletIndex = 0;// 子弹重置计数器
		
		Bullet firstBullet = bList.get(bulletIndex++);
//		Log.i("mBullet","frame"+mainBullet.frame+" fire "+mainBullet.fire);
		if (firstBullet.isFire())
			return false;// 子弹停止的时候
		
		ex = Render.px + ex;
		ey = Render.py + ey;
		
		double dx = ex - player.x;
		double dy = ey - player.y;
		setAngle(Math.atan2(dy, dx));
		
		double s = 500;
		if (s < et) return false; // 只在范围内才发射

		
		playSound();
		for(Creature c:enemySet.cList){
			if(Math.abs(c.x-ex)<c.w&&
					Math.abs(c.y-ey)<c.h){
				
				cos = Math.cos(getAngle());
				sin = Math.sin(getAngle());

				x = (float) (gunLength * cos + player.x);
				y = (float) (gunLength * sin + player.y);
			((AutoBullet)firstBullet).tringer(x, y, c);
				return true;
			}
		}
		return false;
	}
	protected void tringerCheck(Bullet bullet){
//		if(false)super.tringerCheck(bullet);
		
		cos = Math.cos(getAngle());
		sin = Math.sin(getAngle());

		x = (float) (gunLength * cos + player.x);
		y = (float) (gunLength * sin + player.y);
		
		int enemyId=-1;
		 double minDistance=10000*10000;// zheng wu qiong
		for(int i=0;i<es.cList.size();i++){
			Creature gp = es.cList.get(i);
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
			Creature c=enemySet.cList.get(enemyId);
					for(Bullet b:bList)
					if(!b.isFire())
					{
						
					((AutoBullet)b).tringer(x, y, c);
						break;// only once
					}
		}
	}

}
