package Weapon;

import java.util.ArrayList;

import com.mingli.toms.MusicId;
import com.mingli.toms.World;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

public class BoomGun extends MissileGun{

	public BoomGun(EnemySet es, GrassSet gra, Creature c, int bCount) {
		super(es, gra, c, bCount);
		// TODO Auto-generated constructor stub
		bSpeed=1.4f*World.baseBSpeed;
	}
	protected void setBullet(int bCount) {
		if(gra==null)return;
		bList = new ArrayList<Bullet>();
		for (int i = 0; i < bCount; i++) {
//			bList.add( new Missile(enemySet, gra));// 子弹敌对势力
			bList.add(i, new BoomBullet( enemySet, gra));//i bao
		}
		loadTexture();
		setSoundId(MusicId.boomgun);
	}

}
