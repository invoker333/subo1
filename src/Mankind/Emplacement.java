package Mankind;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.MusicId;
import com.mingli.toms.World;

import aid.Log;
import element2.TexId;
import Element.Animation;
import Element.BoomSet;
import Enviroments.GrassSet;
import Weapon.AutoBullet;
import Weapon.Bullet;
import Weapon.Missile;
import Weapon.TailBullet;

public class Emplacement extends Creature {
	 private  final int cdMax = World.baseActionCdMax;
	Bullet b;
//	Animation guideCircle;
	protected double bSpeed = 5;
	private int range = 500;
	double range2 = range * range;
	private int angleSpeed;
	private Creature targetPeople;
	int cd=cdMax;// bullet's cd 

	// int gunSoundId;
	public Emplacement(char bi,GrassSet gra, float x, float y) {
		super(bi,gra,x,y);
		// TODO Auto-generated constructor stub
	setJumpHeight(10);
	setxSpeedMax(1);
	setxSpeedMin(-1);
	}// 炮台

	protected void init(){
		setTextureId(TexId.PAOTA);
		setxSpeedMax(0);
		setxSpeedMin(-0);
		changeLifeRate(3);
		setSoundId(MusicId.hedgehog);
		setSoundIdAttack(MusicId.emplacementAttack);
		
		float rate =gra.getGrid()/ (w*2);
		setwRate(rate);
		sethRate(rate);
		super.init();
	}
	
	public void setEnemySet(EnemySet es){
		super.setEnemySet(es);
		initbullet(es);
	}
	
	void initbullet(EnemySet es) {
		this.enemySet = es;
//		b = new TailBullet(es, gra, 3.33f);
		b = new Missile(es, gra);
		b.loadTexture();
		// b.speed=20;
	}
	
	public void randomAction() {
		super.randomAction();
		if(cd++<cdMax)return;
		
		
		if (enemySet == null)
			return;
		
		
		// it only make sure to attack one enemy
		// super.randomAction();
		// Creature goodPeople=null;
		
		int enemyId = -1;
		double minDistance = -1;
		
		if (targetPeople != null&&!targetPeople.isDead) 
			minDistance = Math.pow(targetPeople.x - x, 2)+ Math.pow(targetPeople.y - y, 2);
		if (minDistance > range2||minDistance==-1){
			targetPeople=null;
			for (int i = 0; i < enemySet.cList.size(); i++) {
				Creature gp = enemySet.cList.get(i);
				if(!targetCanbeCatched(gp))continue;
				double d1 = Math.pow(gp.x - x, 2) + Math.pow(gp.y - y, 2);
				if (d1 > range2)
					continue;

				if (minDistance == -1) {
					minDistance = d1;
					enemyId = i;
				} else if (minDistance > d1) {
					minDistance = d1;
					enemyId = i;
				}
			}
			if (enemyId != -1)
				targetPeople = enemySet.cList.get(enemyId);
			else return;
		}
		
		if (targetPeople == null ||targetPeople.isDead)
			return;
		

		/*
		 * 度转弧度 π/180×角度 弧度变角度 180/π×弧度
		 */
		double javaAngle = Math.atan2(targetPeople.y - y, targetPeople.x - x);
		float trueAngle = (float) ((180 * javaAngle / Math.PI) // to 360
		+ 360) % 360;// from -180--180to 0-360;
		final int speed = 7;
		setAngle(getAngle() % 360);

		float dAngle = trueAngle - getAngle();// /////// from here

		// anglecheck
		if (dAngle > 180)
			dAngle -= 360;
		else if(dAngle<-180)dAngle+=360;

		if (dAngle > speed) {
			angleSpeed = speed;
			setAngle(getAngle() + angleSpeed);
		} else if (-speed > dAngle) {
			angleSpeed = -speed;
			setAngle(getAngle() + angleSpeed);
		} else {
			setAngle(trueAngle);
			
			bulletCheck(targetPeople, javaAngle);
		}
	}

	protected  boolean targetCanbeCatched(Creature gp) {
		// TODO Auto-generated method stub
		if( gp.isDead)return false;
		return true;
	}

	void bulletCheck(Creature targetPeople, double javaAngle) {
		if (!b.isFire()) {
			float cos=(float) Math.cos(javaAngle);
			float sin=(float) Math.sin(javaAngle);
			
			b.tringer(x+w*cos, y+w*sin, bSpeed * cos,bSpeed * sin);
			
			playSound(attackSoundId);
			cd=0;
		}
	}

	public void drawElement(GL10 gl) {
		super.drawElement(gl);
		b.drawElement(gl);
	}
	public void attacked(int a){
		playSound();
		super.attacked(a);
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
		range2=range*range;
	}
}
