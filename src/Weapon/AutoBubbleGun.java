package Weapon;

import java.util.ArrayList;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

public class AutoBubbleGun extends AutoBulletGun{

	public AutoBubbleGun(EnemySet es, GrassSet gra, Creature c, int bCount) {
		super(es, gra, c, bCount);
		// TODO Auto-generated constructor stub
	}
	protected void setBullet(int bCount) {
		bList = new ArrayList<Bullet>();
		for (int i = 0; i < bCount; i++) {
			bList.add(i, new AutoBubble(enemySet, gra, player));// 子弹敌对势力
		}
		loadTexture();
	}

}
