package Mankind;

import Enviroments.GrassSet;

import com.mingli.toms.MusicId;
import com.mingli.toms.World;

import element2.TexId;

public class Baller extends Enemy {

	public Baller(char bi,GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
		setScore(5);
		changeLifeRate(0.5f);
		setSoundId(MusicId.baller);
	}
	protected void init() {

		// TODO Auto-generated constructor stub
		setTextureId(TexId.BALLER);
		super.init();
	}
	protected void afterInit(){
		setAm(0.1f);
		final float speedMax = 1f;
		setxSpeedMax(speedMax);
		setxSpeedMin(-speedMax);
		setJumpHeight(8);
		setG(0.1f);
		super.afterInit();
	}
	
	public void attackAnotherOne(EnemySet es){
		Creature another;
		for (int i = 0; i < es.cList.size(); i++) {
			another = es.cList.get(i);
			if (!another.isDead&&
					Math.abs(x - another.x) < another.getwEdge() + getwEdge()
					&& another.y - another.gethEdge() < y
					&& another.y + another.gethEdge() > y - gethEdge()) {
				tooClose(another, es);
				
			}
		}
	}

//	protected void culYScaleRate() {
//		setyScaleRate( getxScaleRate());
//	}
	public void die(){
		final int lifechange=World.baseLifeMax/4;// 2^3-1 3 times contain self 1 times
		if(getLifeMax()<lifechange)super.die();
		else {
			changeSize(sizeRate*(float) Math.sqrt(1/2f));// right but why don't know
			y++;// to avoid land
			loadTexture();
			
			isDead=false;
			setLife(getLifeMax());
			setLifeMax(getLifeMax()/2);
			
			
			friendList.add((Creature) this.clone());
		}
		
	}

	protected void tooClose(Creature another,EnemySet es) {
		// TODO Auto-generated method stub
		float dsmax=getwEdge()+another.getwEdge();
		final 	float tanxingxishu= 0.1f;
		final 	float zuni=1;
//		xWheelCheck(another, dsmax,tanxingxishu,zuni);
//		another.wheelCheck(this,  dsmax,tanxingxishu,zuni);
		if(another.getxSpeed()/(x-another.x)>0)// 
			another.xPushCheck(this,  dsmax,tanxingxishu,zuni);
		
		super.tooClose(another, es);
	}
	public void randomAction() {// 周期
		randomWave(getW()/2);
		final int ruscue=2;
		
		if(getLife()<getLifeMax())setLife(getLife() + ruscue);
		
		for(int i=0;i<friendList.size();i++){
			Creature c=friendList.get(i);
			if(
					Math.abs(c.x-x)<getwEdge()+c.getwEdge()
					&&Math.abs(c.y-y)<gethEdge()+c.gethEdge()
			){
//				if(c.isDead)
//					c.isDead=false;
				
				if(c.getLife()<c.getLifeMax())c.setLife(c.getLife() + ruscue);
				else {
					
					if(c.direction==1)c.turnRight();
					else if(c.direction==-1)c.turnLeft();
				}
//				return;
			}
		}
//		switch ((int) (5 * Math.random())) {
//		case 0:
//			turnRight();
//			break;
//		case 1:
//			turnLeft();
//			break;
//		case 2:
//			stopMove();
//			break;
//		case 3:
//			if (isJumpAble())
//				jump();
//			break;
//		case 4:
//			if (isAnimationFinished()) {
//				attack();break;
//			}
//			if (fdirection != 0 && getDirection() != fdirection) {
//				setDirection(fdirection);
//				attack();break;
//			}
//			break;
//		}
	}
}
