package Weapon;

import java.util.ArrayList;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

import com.mingli.toms.MusicId;
import com.mingli.toms.World;

public class MissileGun extends Gun {


	public MissileGun(EnemySet es,GrassSet gra, Creature c,int bCount) {
		super(es,gra,  c, bCount);
		setBullet(bCount);
		// TODO Auto-generated constructor stub
		cd = 4 * super.cd;
		bSpeed=0.5f*World.baseBSpeed;
		
	}

	protected void setBullet(int bCount) {
		if(gra==null)return;
		bList = new ArrayList<Bullet>();
		for (int i = 0; i < bCount; i++) {
			bList.add( new Missile(enemySet, gra));// 子弹敌对势力
//			bList.add(i, new BoomBullet( enemySet, gra));//i bao
		}
		loadTexture();
		setSoundId(MusicId.missile);
	}
}
