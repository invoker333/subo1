package Mankind;

import javax.microedition.khronos.opengles.GL10;

import element2.TexId;
import Enviroments.GrassSet;
import Weapon.AutoBullet;
import Weapon.Bullet;
import Weapon.TailBullet;

public class EpAuto extends Emplacement{

//	Bullet ab;

	public EpAuto(char bi,GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
		// TODO Auto-generated constructor stub
	}
	void initbullet(EnemySet es) {
		this.enemySet = es;
		 b=new AutoBullet(es, gra, this,30);
		b.loadTexture(TexId.GREEN);
		// b.speed=20;
	}
	 void bulletCheck(Creature targetPeople, double javaAngle) {
		if (!b.isFire()) {
			float cos=(float) Math.cos(javaAngle);
			float sin=(float) Math.sin(javaAngle);
			
			((AutoBullet)b).tringer(x+w*cos, y+w*sin,targetPeople);
			playSound(attackSoundId);
			cd=0;
		}
	}

	public void drawElement(GL10 gl) {
		super.drawElement(gl);
	}
}
