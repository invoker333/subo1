package Weapon;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

public class AutoBubble extends AutoBullet {
	public AutoBubble(EnemySet es, Creature s) {
		super(es,  s);
		// TODO Auto-generated constructor stub
		frameMax =180;
		speedBackup[0] = speed;
		speedBackup[1] = getW();
		speedBackup[2] = getH();
		attack=1;
	}

	float speedBackup[] = new float[3];

	public void setFire() {
		super.setFire();
		speed = speedBackup[0];
		setW(speedBackup[1]);
		setH(speedBackup[2]);
		refresh();
		// return true;
	}

	private void refresh() {
		firstBlood = true;
		fruSpeed = 0;
		dLength = getW();
		fruTime = 1;
		fruTimeBack = 1;
	}

	protected void disappear() {
		super.disappear();
		speed = speedBackup[0];
	}

	boolean firstBlood = true;

	protected void gotTarget(Creature enemy) {
		if (firstBlood) {
			float dSize = enemy.getW() > enemy.getH() ? enemy.getW() - getW()
					: enemy.getH() - getH();
			fruSpeed = (float) Math.sqrt(2 * fruA * dSize);
			firstBlood = false;
		}
		if(frame++>frameMax){
			resetBullet();return;
		}
		speed = speed *0.9f;
		es.attacked(enemy, attack);
//		enemy.setxSpeed(speed);
//		enemy.setySpeed(speed);
//		final tanxingxishu=0.1f;
		 float dsmax=Math.max(enemy.w, enemy.h)/2;
		stringCheck(enemy, dsmax, tanxingxishu, 0.9f);
	}

	float fruTime = 1f, fruTimeBack = 1;
	float fruSpeed;
	float fruA = 0.05f;
	private float dLength;

	public void drawElement(GL10 gl) {
		if (fruSpeed > 0) {
			fruSpeed -= fruA;
			dLength += fruSpeed;
			fruTime = dLength / getW();
			fruTimeBack = 1 / fruTime;
		}
		move();
		gravity();
		shot();
		gl.glTranslatef(x, y, 0);
		gl.glScalef(fruTime, fruTime, 0);
		super.baseDrawElement(gl);
		gl.glScalef(fruTimeBack, fruTimeBack, 0);
		gl.glTranslatef(-x, -y, 0);
	}
}
