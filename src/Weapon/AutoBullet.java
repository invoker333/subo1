package Weapon;

import Element.Boom;
import Element.BoomSet;
import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;
import aid.Log;

import com.mingli.toms.MusicId;
import com.mingli.toms.R;
import com.mingli.toms.Render;

import element2.TexId;

public class AutoBullet extends ToBigBullet {
	private Creature player;
	float speed = 15;
	private Creature enemy;
//	private int enemyId;
	public AutoBullet(EnemySet es, GrassSet gra,Creature player,float speed) {
		this(es, gra, player);
		this.speed=speed;
	}
	
	public AutoBullet(EnemySet es, GrassSet gra,Creature player) {
		super(es, gra);
		// TODO Auto-generated constructor stub
		loadTexture(TexId.RED);
		loadSound(MusicId.bubble);
		this.player=player;
	}
	boolean grassCheck(){
		return false;
//		return super.grassCheck();
	}
	public void tringer(float x, float y,Creature enemy) {
		this.enemy = enemy;
		setFire();
		frame=0;
		setPosition(x, y);
		speedCheck(enemy.x,enemy.y);
		// bList.add(this);
		playSound();
	}
	public void tringerCheck(float ex, float ey) {
		float xt = Render.px + ex;
		float yt = Render.py + ey;
		if (!isFire()) {
			Creature enemy ;
			for (int i = 0; i < eList.size(); i++) {
				 enemy = eList.get(i);
				 if(enemy.isDead)continue;
				if (Math.abs(xt - enemy.x) < enemy.getW()
						&& Math.abs(yt - enemy.y) < enemy.getH()) {
					
					tringer(player.x,player.y,enemy);
					playSound();
					return;
				}
			}
		}
	}

	void speedCheck(float x,float y) {
		float dx = x - this.x;
		float dy = y-this.y;
		float s = (float) Math.sqrt((Math.pow(dx, 2) + Math.pow(dy, 2)));
		//s==0 bug 但是问题还没出现
		xSpeed = speed * dx / s;
		ySpeed = speed * dy / s;
	}
	public void resetBullet(){
		speed=10;
		super.resetBullet();
	}
	void targetCheck(){
//		speed+=0.2;
//		if(enemyId==-1||enemyId>=eList.size())return;
//		Creature enemy = eList.get(enemyId);
		
		
		if (isFire()) {
			speedCheck(enemy.x,enemy.y);
			if(Math.abs(x-enemy.x)<(enemy.getW()+getW())&&Math.abs(y-enemy.y)<(enemy.getH()+getH())){
				gotTarget(enemy);
			}
		}
	}


	public void loadSound() {
		setSoundId(MusicId.gun);
	}
	

}

