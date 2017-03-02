package Mankind;

import com.mingli.toms.R;

import element2.TexId;

import Enviroments.GrassSet;

public class Creeper extends Enemy {


	public Creeper(char bi,GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
		setSoundId(EnemySet.CREEPER);
		changeLifeRate(2);
	}
	protected void init(){
//		setJumpHeight(8);
		setH(38);
		sethRate(0.85f);
		setTextureId(TexId.CREEPER);
		setJumpHeight(10);
		super.init();
	}
	protected void afterInit() {
		setxSpeedMax(3);
		setxSpeedMin(-3);

		super.afterInit();
	}
	public void attackAnotherOne(EnemySet es){
		Creature another;
		for (int i = 0; i < es.cList.size(); i++) {
			another = es.cList.get(i);
			if (Math.abs(x - another.x) < another.getwEdge() + getwEdge()
				&&Math.abs(another.y-another.gethEdge()-y) <=  getH()
					) {
				
				tooClose(another);
				tooClose(another, es);
			}
		}
	}
	protected void tooClose(Creature another){
		 float dYspeed=another.getySpeed() - getySpeed();
    	 if(dYspeed>10)return;//相对向上跳速度相差太大不踩
		
		float dx=Math.abs(x-another.x);
		float ds=getwEdge()+another.getwEdge();
		float rate=1-dx*dx/(ds*ds);
		
		int dsmax=(int) (rate* (gethEdge()+another.gethEdge()));
		float tanxingxishu= 0.2f;
		float zuni=1;
		yStandCheck(another, dsmax, tanxingxishu, zuni);
		another.setySpeed(another.getySpeed() + another.getG());
	}

	public void randomAction() {// 周期



//		switch ((int) (6 * Math.random())) {
//		case 0:
//			turnRight();
//			break;
//		case 1:
//			turnLeft();
//			break;
//		case 2:
//			stopMove();
//			break;
//		}
	}
}
