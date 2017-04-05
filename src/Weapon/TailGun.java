package Weapon;

import java.util.ArrayList;

import com.mingli.toms.MusicId;
import com.mingli.toms.World;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

public class TailGun extends Gun{

	public TailGun(EnemySet es, GrassSet gra, Creature c, int bCount) {
		super(es,  gra, c, bCount);
		// TODO Auto-generated constructor stub
		cd=(int) (3.5*super.cd);
		
	}

	protected void setBullet(int bCount) {
		bSpeed=1.25f*World.baseBSpeed;
		bList = new ArrayList<Bullet>();
		for (int i = 0; i < bCount; i++) {
			bList.add(i, new TailBullet(enemySet, gra, 4));// 子弹敌对势力
		}
		loadTexture();
		setSoundId(MusicId.juji);
	}
}
