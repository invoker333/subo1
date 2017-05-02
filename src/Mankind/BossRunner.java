package Mankind;

import Enviroments.GrassSet;
import android.view.Gravity;

import com.mingli.toms.MenuActivity;
import com.mingli.toms.R;
import com.mingli.toms.Render;
import com.mingli.toms.World;

public class BossRunner extends JointCreature{

	private Creature chaser;
	String []strSet={
	"嘿嘿！火星可不是一般人能来的！"
//	,"我永远效忠于伟大的“冰棍”首领！",
//	"我们的计划是冰冻整个火星！",	
//	"所有反抗者都会变成冰渣！哈哈哈！",
	};
	int strId;
	private final int cdMax=World.baseActionCdMax;
	private int cd=cdMax;
	private World world;

	public BossRunner(World world,char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		this.world = world;
		if(world!=null)world.storySpeaker=this;
		attack=0;
		// TODO Auto-generated constructor stub
	}	
	protected void tooHigh(){}
	 public void randomAction(){
		 if(y<0)jump();
		 int direction1=Gravity.CENTER_HORIZONTAL;
		 int direction2;
		  float length=Render.width*1/3;
		  float length2=Render.width*2/3;
		float dx=x-chaser.x;
		if(dx>0) {
			direction1=Gravity.RIGHT;
			if (dx<length) {
				turnRight();
			} else if(dx<length2) {
				stopMove();
			}
		} else if(dx<0) {
			direction1=Gravity.LEFT;
			if (dx>-length) {
				turnLeft();
			} else if(dx>-length2) {
				stopMove();
			}
		}
		
		 if(cd++>cdMax) {
			if (strId<strSet.length) {
				  float mid = (Render.height/2+Render.py);
				  if(y<mid)direction2=Gravity.TOP;
				  else direction2=Gravity.BOTTOM;
				MenuActivity.showDialog("冰寒集团跑腿", strSet[strId], R.drawable.toukuienemy,direction1,direction2);
			 } else if(strId==strSet.length){
				 world.sendLoadedMessage();
			 }
			cd=0;
			strId++;
		} 
	}
	 public void setEnemySet(EnemySet es){
		 super.setEnemySet(es);
		 chaser=es.player;
		 setLifeMax(chaser.attack*10);
//			setxSpeedMax(chaser.getxSpeedMax()*2);
//			setxSpeedMin(-chaser.getxSpeedMax()*2);
	 }
	 protected void tooLeft(){
		 super.tooLeft();
		 jump();
	 }
	 protected void tooRight(){
		 super.tooRight();
		 jump();
	 }
}
