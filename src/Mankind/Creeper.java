package Mankind;

import Enviroments.GrassSet;

import com.mingli.toms.MusicId;
import com.mingli.toms.World;

import element2.TexId;

public class Creeper extends Enemy {


	public Creeper(char bi,GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
		setSoundId(MusicId.creeper4);
		changeLifeRate(2);
		attack=0;
		treadable=false;
	}
	protected void init(){
//		setJumpHeight(8);
		setH(29);
		setW(w*3/4);
		sethRate(0.85f);
		setTextureId(TexId.CREEPER);
		setJumpHeight(10);
		super.init();
	}
	protected void afterInit() {
		setxSpeedMax(1);
		setxSpeedMin(-1);

		super.afterInit();
	}
	public void attackAnotherOne(EnemySet es){
//		Log.i("xSpeed"+xSpeed);
		if(xSpeed>getxSpeedMax()+1||xSpeed<getxSpeedMin()-1)
		for (int i = 0; i < friendSet.cList.size(); i++) {
			Creature another = friendSet.cList.get(i);
			if (!another.isDead&&another!=this&&
					Math.abs(x - another.x) < another.getwEdge() + getwEdge()
				&&Math.abs(another.y-another.gethEdge()-y) <=  getH()
					) {
				friendSet.attacked(another, World.baseAttack);
				another.setySpeed(another.getySpeed() + 5);
			}
		}
		
		Creature another;
		for (int i = 0; i < es.cList.size(); i++) {
			another = es.cList.get(i);
			if (!another.isDead&&
					Math.abs(x - another.x) < another.getwEdge() + getwEdge()
				&&Math.abs(another.y-another.gethEdge()-y) <=  getH()
					) {
				
//				tooClose(another);
				tooClose(another, es);
			}
		}
	}
	public boolean culTreadSpeedAndCanBeTread(Creature c){
//		if(false)
//		super.culTreadxSpeed(c);
		return false;
	}
	public void treaded(Creature player) {// ����
		super.treaded(player);
		float width=wEdge+player.gethEdge();
		float dx=player.x-x;
		player.setxSpeed(player.getxSpeed()+(float) (-player.getySpeed()*Math.sin(3.14f*dx/width)));
		this.xSpeed+=-player.getxSpeed()/2;
	}
	 protected void tooClose(Creature another,EnemySet es){
		 float dYspeed=another.getySpeed() - getySpeed();
    	 if(dYspeed>5)return;//相对向上跳速度相差太大不踩
		
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
