package Weapon;

import java.util.ArrayList;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

public class KindNessGun extends Gun{

	public KindNessGun(EnemySet es,GrassSet gra,  Creature c, int bCount) {
		super(es,  gra,c, bCount);
		// TODO Auto-generated constructor stub
	}
	protected void setBullet(int bCount) {
		bList = new ArrayList<Bullet>();
		for (int i = 0; i < bCount; i++) {
			int id=(int) (4*Math.random());
			if(id==0)
				bList.add(i, new Hook(enemySet, gra, player));// 子弹敌对势力
			else if(id==1)
				bList.add(i, new Bullet(enemySet, gra));// 子弹敌对势力
			else if(id==2)
				bList.add(i, new TailBullet(enemySet, gra));// 子弹敌对势力
			else if(id==3)
				bList.add(i, new Missile( enemySet, gra));// 子弹敌对势力
		}
		loadTexture();
	}

}
