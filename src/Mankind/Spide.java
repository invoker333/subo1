package Mankind;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.MusicId;

import aid.Log;
import Weapon.Hook;
import Weapon.TailBullet;
import Enviroments.GrassSet;
import element2.Tail;
import element2.TexId;

public class Spide extends Emplacement {

	public static float dsmax = 64;
	Tail tail;
	Creature catcher;
	int hirtIndexBorn = 300;
	int hirtIndex;

	public Spide(char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		setTextureId(TexId.THUNDER);
		setSoundId(MusicId.zhizhu);

		tail = new Tail(2);
		tail.width = 8;
		this.attack = 0;
		setG(0);
		
		setSoundIdAttack(MusicId.zhizhu);
	}

	void initbullet(EnemySet es) {
		this.es = es;
		b = new Hook(es, gra, this) {
			protected void gotTarget(Creature enemy) {
				if (catcher == null || catcher.isDead) {
					catcher = enemy;
				}
			}
		};
		b.loadTexture(TexId.THUNDER);
		dsmax = ((Hook) b).getRange();
		setRange(((Hook) b).getRange());
	}

	public void randomAction() {
		if (hirtIndex > hirtIndexBorn)
			super.randomAction();
		else
			hirtIndex++;
	}

	public void attacked(int a) {
//		super.attacked(a);
		playSound();
		hirtIndex = 0;
		catcher = null;
	}

	protected boolean targetCanbeCatched(Creature gp) {
		if (gp.isDead)
			return false;
		if (gp.equals(catcher))
			return false;

		return true;
	}

	public void drawElement(GL10 gl) {
		float colorRate=(float)hirtIndex/hirtIndexBorn;
		gl.glColor4f(1, colorRate, colorRate, 1);
		move();
		gravity();
		drawScale(gl);

		tail.drawElement(gl);
		b.drawElement(gl);
		gl.glColor4f(1, 1, 1, 1);
	}

	protected void moveCheck() {
		tailCheck();
		xSpeed = 0;
	}

	protected void gravityCheck() {
		ySpeed = 0;
	}

	protected void tailCheck() {
		if (catcher != null && catcher.isDead)
			catcher = null;

		tail.startTouch(x, y);
		float dsmax = Spide.dsmax;
		if (catcher != null && !catcher.isDead) {
			tail.tringer(catcher.x, catcher.y);

			final float tanxingxishu = 0.1f;
			final float zuni = 0.99f;

			stringCheck(catcher, dsmax, tanxingxishu, zuni);
		}
	}
}
