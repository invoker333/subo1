package Weapon;

import Mankind.Creature;
import element2.Joint;

public class GunDraw extends Joint{
//	private ArrayList<Creature> sList;
//	private int attack=100;
//	private boolean fire;
//	private EnemySet es;
//	private  Creature p;
	
	private int holdAngle;

	public GunDraw(int holdAngle,Creature p, float x1, float y1, float x2, float y2, float xp,
			 float yp, int textureId, int i, int j) {
		super(p, x1, y1, x2, y2, xp, yp, textureId,i,j);
		this.holdAngle = holdAngle;
	}

	public GunDraw(Creature p, float x1, float y1, float x2, float y2, float xp,
			 float yp, int textureId) {
		super(p, x1, y1, x2, y2, xp, yp, textureId);
	}

	public GunDraw(int holdAngle,Creature p, float x1, float y1, float x2, float y2, float xp,
			 float yp, int textureId) {
		super(p, x1, y1, x2, y2, xp, yp, textureId);
		this.holdAngle = holdAngle;
	}
	
	 public void setAngle(float angle){
		super.setAngle(angle);
		holdIndex=0;
	}
	int holdIndex;// hold time index
	public void positionCheck(){
		
		
//		if(holdIndex++<60)return;
//		if(direction==0) {//逆时针为证
			if (getAngle() > getSpeed()+holdAngle)
				//dagree>0
				setDagree(-getSpeed());
			else if (getAngle() < -getSpeed()+holdAngle)
				setDagree(getSpeed());
			else {
				setDagree(0);
				setAngle(holdAngle);
			}
		}
}
