package Weapon;

import java.util.ArrayList;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

import com.mingli.toms.MusicId;
import com.mingli.toms.Render;

public class HookGun extends Gun {

	public HookGun(EnemySet es, GrassSet gra, Creature c, int bCount) {
		super(es,  gra, c, bCount);
		cd=3*super.cd;
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
		setSoundId(MusicId.zhizhu);
	}
	public boolean gunCheck(float ex, float ey) {
		float grid = gra.getGrid()/2;
		float ex1 = Render.px + ex;
		float ey1 = Render.py + ey;
		for(Bullet b:bList){
			Hook h=(Hook) b;
			if(Math.abs(h.hookGrass.x-ex1)<grid&&
					Math.abs(h.hookGrass.y-ey1)<grid){
				h.hookGrass.isDead=true;
				h.hookGrass.setPosition(0,111);
				return true;
			}
		}
		return false;
//		return super.gunCheck(ex, ey);// no shot but only relax
	}
}
