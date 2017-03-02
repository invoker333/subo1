package Mankind;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.World;

import element2.ParticleBallRandom;
import Enviroments.GrassSet;

public class Enemy extends Creature {

	public Enemy(char bi,GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
		// setPosition(x,y);
		attack = World.baseAttack;
		turnLeft();
		// TODO Auto-generated constructor stub
	}

	public void randomAction() {
	}

	protected void init() {
		setFrameCount(4, 2);
		super.init();
		loadSound();
	}
	public void drawElement(GL10 gl) {
		// super.drawElement(gl);
		move();
		gravity();
		drawScale(gl);
		animation();
	}
	public void baseDrawElement(GL10 gl){
		float yAngle = getDirection() == 1 ? 180f : 0;
		gl.glRotatef(yAngle, 0, 1, 0);
		super.baseDrawElement(gl);
		gl.glRotatef(-yAngle, 0, 1, 0);
	}
	public void drawDeath(GL10 gl){
		if (getAlpha() <= 0){
			setPosition(0,0);
			return;// 死亡不画
		}
		super.drawDeath(gl);
	}

	 public void changeState(float step) {
		setxState(getxState() + step);
		if (getxState() + step >= getxCount()) {
			setState(0,0);
			setAnimationFinished(true);
		}
	}

	 public void attacked(int attack) {
		attack();
		playSound();
		super.attacked(attack);
	}

	public void jump() {
		attack();
		super.jump();
	}

	protected void tooRight() {
		super.tooRight();
		setAnimationFinished(true);
		turnLeft();
	}

	protected void tooLeft() {
		super.tooLeft();
		setAnimationFinished(true);
		turnRight();
	}
//	public void attackAnotherOne(EnemySet es){// thi effect too foot is good than all body
//		Creature another;
//		for (int i = 0; i < es.eList.size(); i++) {
//			another = es.eList.get(i);
//			if (Math.abs(x - another.x) < another.getwEdge() + getwEdge()
//					&& another.y - another.gethEdge() < y
//					&& another.y + another.gethEdge() > y - gethEdge()) {
//				tooClose(another, es);
//				
//			}
//		}
//	}
	 public void attackAnotherOne(EnemySet es){
		super.attackAnotherOne(es);
		Creature another;
		for (int i = 0; i < es.cList.size(); i++) {
			another = es.cList.get(i);
			if (Math.abs(x - another.x) < another.getwEdge() + getwEdge()
				&&Math.abs(y - another.y) < another.gethEdge() + gethEdge()) {
				tooClose(another,es);
				
			}
		}
	}
	protected void tooClose(Creature another, EnemySet es) {
	}

}
