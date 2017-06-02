package Mankind;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.MenuActivity;
import com.mingli.toms.Render;
import com.mingli.toms.World;

import Enviroments.GrassSet;
import element2.TexId;

public class BladeMan extends JointEnemy{


	private Creature chaser;
	public BladeMan(char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		// TODO Auto-generated constructor stub
		haveBlade();
		treadable=false;
		cloth.setTextureId(TexId.CLOTHENEMY);
		cap.setTextureId(TexId.CAPENEMY);
		cdMAX=2*World.baseActionCdMax;
	}
	public void drawElement(GL10 gl){
		super.drawElement(gl);
	}
	
	private void chaseCheck(Creature chaser) {
		float length=chaser.getwEdge() + getwEdge()+realBlade.length;
		float length2=length*2/3;
		float dx=x-chaser.x;
		if(dx>0) {
			if (dx>length) {
				turnLeft();
			} else if(dx<length2) {
				stopMove();
			}
		} else if(dx<0) {
			if (dx<-length) {
				turnRight();
			} else if(dx>-length2) {
				stopMove();
			}
		}
	}
	
	public void randomAction(){
		if (cd < cdMAX*1/2){
			stopMove();
		}else {
			
			if(chaser==null||chaser.isDead||Math.abs(chaser.x-x)>Render.width){
				int id=(int) (Math.random()*enemySet.cList.size());
				chaser = enemySet.cList.get(id);
			}
			chaseCheck(chaser);
			
			searchAndAttack();
		}
		
		if (cd++ > cdMAX) {//must attack 
			cd = 0;
		} else return;
		super.randomAction();
		
	}
	void searchAndAttack(){
//		for (int i = 0; i < enemySet.cList.size(); i++) {
//			Creature another = enemySet.cList.get(i);
			if (chaser!=null&&!chaser.isDead&&
					Math.abs(x - chaser.x) < chaser.getwEdge() + getwEdge()+realBlade.length
				&&Math.abs(y - chaser.y) < chaser.gethEdge() + gethEdge()+realBlade.length) {
				if(chaser.x-x>0)faceRight();
				else faceLeft();
				
				attack();
				cd=0;
			}
		}
//	}
}
