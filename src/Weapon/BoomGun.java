package Weapon;

import java.util.ArrayList;

import com.mingli.toms.MusicId;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

public class BoomGun extends Gun {

	private GrassSet gra;

	public BoomGun(EnemySet es,Creature c,GrassSet gra, int bCount) {
		super(es,  c, bCount);
		this.gra = gra;
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
