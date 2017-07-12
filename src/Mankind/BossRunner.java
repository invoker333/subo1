package Mankind;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import android.view.Gravity;

import com.mingli.toms.MenuActivity;
import com.mingli.toms.R;
import com.mingli.toms.Render;
import com.mingli.toms.World;

import element2.TexId;

public class BossRunner extends JointCreature{

	private Creature chaser;
	String []strSet={
	"嘿嘿！火星可不是一般人能来的！"
			,"你能有多大能耐呢"
	,"追得上我你就来吧！"
	
	};
	int strId;
	private final int cdMax=World.baseActionCdMax;
	private int cd;
	private World world;

	public BossRunner(World world,char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		this.world = world;
		attack=0;
//		 setLifeMax(World.baseAttack*10);
		setJumpHeight(128);
		// TODO Auto-generated constructor stub
	}	
	public void die(){
		super.die();
		if(chaser!=null&&chaser instanceof Player)((Player)chaser).succeed();
	}
	protected void tooHigh(){}
	 public void randomAction(){
		
		
		 if(y<0)jump();
		  float length=Render.width*1/3;
		  float length2=Render.width*2/3;
		float dx=x-chaser.x;
		if(dx>0) {
			if (dx<length) {
				turnRight();
			} else if(dx<length2) {
				stopMove();
			}
		} else if(dx<0) {
			if (dx>-length) {
				turnLeft();
			} else if(dx>-length2) {
				stopMove();
			}
		}
		
		 if(cd++>cdMax) {
			 String word="";
			if (strId<strSet.length) {
				word=strSet[strId];
			 } else if(strId==strSet.length){
				 word="";
//				 world.sendLoadedMessage();
			 }
			world.showGlDialog(word,this,0,60);
			cd=0;
			strId++;
		} 
	}
	 public void setEnemySet(EnemySet es){
		 super.setEnemySet(es);
		 chaser=es.player;
		
			setxSpeedMax(chaser.getxSpeedMax()*1.5f);
			setxSpeedMin(-chaser.getxSpeedMax()*1.5f);
	 }
	 protected void tooLeft(){
		 super.tooLeft();
		 jump();
	 }
	 protected void tooRight(){
		 super.tooRight();
		 jump();
	 }
	  protected void speedBackTooRight() {}
	  protected void speedBackTooLeft() {}
}
