package Weapon;

import java.util.ArrayList;

import com.mingli.toms.MusicId;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

public class BoomGun extends Gun {


	public BoomGun(EnemySet es,GrassSet gra, Creature c,int bCount) {
		super(es,gra,  c, bCount);
		setBullet(bCount);
		// TODO Auto-generated constructor stub
		cd = 4 * super.cd;
		bSpeed*=1.5f;
	}

	protected void setBullet(int bCount) {
		if(gra==null)return;
		bList = new ArrayList<Bullet>();
		for (int i = 0; i < bCount; i++) {
//			bList.add( new Missile(enemySet));// 子弹敌对势力
			bList.add(i, new BoomBullet( enemySet, gra));//i bao
		}
		loadTexture();
		
		setSoundId(MusicId.missile);
	}
}
