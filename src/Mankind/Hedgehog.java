package Mankind;

import com.mingli.toms.MusicId;
import com.mingli.toms.R;
import com.mingli.toms.World;

import element2.TexId;
import Enviroments.GrassSet;

public class Hedgehog extends Enemy {

	int creeperSoundId=0;
	public Hedgehog(char bi,GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
		setSoundId(MusicId.hedgehog);
		changeLifeRate(2);
		attack=(int) (0.2f*World.baseAttack);
	}
	void toCrepper(){
		setLife(getLifeMax());
		isDead=false;
		angle=0;
		rotateSpeed=0;
		
		setxSpeedMax(3);
		setxSpeedMin(-3);
		setW(64);
		setH(38);
		sethRate(0.85f);
		float x1=x;
		float y1=y;
		syncTextureSize();

		setTextureId(TexId.REDCREEPER);
		setSoundId(creeperSoundId);
	}
	protected void init(){

		setW(96);
		setH(60);
		sethRate(0.55f);
		setTextureId(TexId.HEDGEHOG);
		super.init();
	}
	protected void afterInit(){
		setxSpeedMax(3);
		setxSpeedMin(-3);
		super.afterInit();
	}
	public void attacked(int attack){
//		float life1=getLife();
		 super.attacked(attack);
//		 if(life1>=lifeToChange&&getLife()<lifeToChange)
			if(isDead&&getTextureId()==TexId.HEDGEHOG) toCrepper();
	}
	protected void tooClose(Creature another,EnemySet es){
		super.tooClose(another, es);
		
		if(getTextureId()==TexId.HEDGEHOG)es.attacked(another, attack);
		else {//creeper's code
			float dYspeed=another.getySpeed() - getySpeed();
	    	 if(dYspeed>5)return;//相对向上跳速度相差太大不踩
			
			if(Math.abs(another.y-another.gethEdge()-y) <=  getH()){// code from creeper
				float dx=Math.abs(x-another.x);
				float ds=getwEdge()+another.getwEdge();
				float rate=1-dx*dx/(ds*ds);
				
				int dsmax=(int) (rate* (gethEdge()+another.gethEdge()));
				float tanxingxishu= 0.2f;
				float zuni=1;
				yStandCheck(another, dsmax, tanxingxishu, zuni);
				another.setySpeed(another.getySpeed() + another.getG());
			}
		}
	}

	public void randomAction() {// 周期
//		switch ((int) (9 * Math.random())) {
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
