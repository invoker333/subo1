﻿package Mankind;

import javax.microedition.khronos.opengles.GL10;

import android.view.Gravity;

import com.mingli.toms.Render;
import com.mingli.toms.World;

import Weapon.AutoBullet;
import Weapon.Bullet;
import Weapon.Gun;
import Weapon.TailGun;
import Enviroments.GrassSet;

public class GunMan extends JointEnemy {

	private Gun gun;

	public GunMan(char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		// TODO Auto-generated constructor stub
		haveGun();
		setLifeMax(5*World.baseAttack);
		treadable = false;
	}

	public void setEnemySet(EnemySet enemySet) {
		super.setEnemySet(enemySet);
		gun = new Gun(enemySet, gra, this, 1);
		gun.bSpeed = World.baseBSpeed;
	}

	public void drawElement(GL10 gl) {
		super.drawElement(gl);
		gun.drawElement(gl);
	}
	
	

	public void randomAction() {// 周期
		if (cd < cdMAX/2)for(Creature chaser:enemySet.cList)  
			chaseCheck(chaser);
		
		
		if (cd++ > cdMAX) {
			cd = 0;
		} else
			return;
		int enemyId = -1;
		double minDistance = 10000 * 10000;// zheng wu qiong
		for (int i = 0; i < enemySet.cList.size(); i++) {
			Creature gp = enemySet.cList.get(i);
			if (gp.isDead)
				continue;
			if (gp.x < Player.gx1 || gp.x > Player.gx2 || gp.y < Player.gy1
					|| gp.y > Player.gy2)
				continue;

			double d1 = Math.pow(gp.x - x, 2) + Math.pow(gp.y - y, 2);

			if (minDistance > d1) {
				minDistance = d1;
				enemyId = i;
			}
		}

		if (enemyId != -1) {
			Creature enemy = enemySet.cList.get(enemyId);

			float enemyAngle = (float) Math.atan2(enemy.y - y, enemy.x - x);
			gun.gunCheck(enemyAngle);
			setGunAngle(enemyAngle * 180 / 3.14f);
		}
	}

	private void chaseCheck(Creature chaser) {
		float length=Render.width*1/4;
		  float length2=Render.width*1/3;
		float dx=x-chaser.x;
		if(dx>0) {
			if (dx<length) {
				turnRight();
			} else if(dx<length2) {
				stopMove();
			}
		} else if(dx<0) {
			if (dx>-length) {
				turnLeft();
			} else if(dx>-length2) {
				stopMove();
			}
		}
	}
}
