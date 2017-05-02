package Weapon;

import java.util.ArrayList;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

import com.mingli.toms.MusicId;
import com.mingli.toms.World;

import element2.TexId;

public class GuangDanGun extends Gun{

	public GuangDanGun(EnemySet es, GrassSet gra, Creature c, int bCount) {
		super(es, gra, c, bCount);
		// TODO Auto-generated constructor stub
		cd=5*cd;
	}
	protected void setBullet(int bCount) {
		bList = new ArrayList<Bullet>();
		Bullet b;
		final int ww=64;
		for (int i = 0; i < bCount; i++) {
			bList.add(i,b= new Bullet(enemySet,gra));// 子弹敌对势力
//			bList.add(i, new Huck(enemySet,  1000,player));// 子弹敌对势力
			b.w=ww;
			b.h=ww;
			b.attack=5*World.baseAttack;
			b.setTextureId(TexId.HIKARI);
		}
		loadTexture();
		setSoundId(MusicId.guangdanqiang);
	}
}
