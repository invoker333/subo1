package Weapon;

import java.util.ArrayList;

import com.mingli.toms.World;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

public class HookGun extends Gun {

	public HookGun(EnemySet es, GrassSet gra, Creature c, int bCount) {
		super(es,  gra, c, bCount);
		
		cd=5*super.cd;
		// TODO Auto-generated constructor stub
	}
	protected void setBullet(int bCount) {
		bList = new ArrayList<Bullet>();
		Hook h;
		for (int i = 0; i < bCount; i++) {
			bList.add(i,h= new Hook(enemySet,  gra, player));// 子弹敌对势力
			h.setRange(500);
		}
		bSpeed=((Hook)bList.get(0)).speed;// set first speed
		loadTexture();
	}
}
