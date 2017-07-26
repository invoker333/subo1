package Mankind;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import Weapon.Hook;

import com.mingli.toms.MusicId;

import element2.Tail;
import element2.TexId;

public class Spide extends Emplacement {

	public static float dsmax = 64;
	Tail tail;
	Creature catcher;
	int hirtIndexBorn = 300;
	int hirtIndex=hirtIndexBorn;

	public Spide(char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		setTextureId(TexId.THUNDER);
		setSoundId(MusicId.zhizhu);

		tail = new Tail(2,TexId.WIPE);
		tail.w = 2;
		this.attack = 0;
		setG(0);
		treadable=false;
		
		setSoundIdAttack(MusicId.zhizhu);
	}

	void initbullet(EnemySet es) {
		this.enemySet = es;
		bSpeed=25;
		b = new Hook(es, gra, this) {
			protected void gotTarget(Creature enemy) {
				if(enemy.equals(b.enemyGrass)){
					b.resetBullet();return;
				}
				if (catcher == null || catcher.isDead) {
					catcher = enemy;
				}
				b.resetBullet();return;
			}
		};
		b.loadTexture(TexId.THUNDER);
		dsmax = ((Hook) b).getRange();
		setRange(((Hook) b).getRange());
	}
	public boolean culTreadSpeedAndCanBeTread(Creature c){
		return false;
	}
	public void randomAction() {
		if(catcher!=null&&!catcher.isDead)return;
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
		x=startX;
	}

	protected void gravityCheck() {
		ySpeed = 0;
		y=startY;
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
