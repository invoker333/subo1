package Mankind;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import element2.TexId;

public class BladeMan extends JointEnemy{


	public BladeMan(char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		// TODO Auto-generated constructor stub
		haveBlade();
		treadable=false;
		cloth.setTextureId(TexId.CLOTHENEMY);
		cap.setTextureId(TexId.CAPENEMY);
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
		if (cd < cdMAX*4/5){
			int id=(int) (Math.random()*enemySet.cList.size());
			Creature chaser = enemySet.cList.get(id);
			chaseCheck(chaser);
		}else {
			searchAndAttack();
			stopMove();
		}
		
		if (cd++ > cdMAX) {
			cd = 0;
		} else return;
		super.randomAction();
		
	}
	void searchAndAttack(){
		for (int i = 0; i < enemySet.cList.size(); i++) {
			Creature another = enemySet.cList.get(i);
			if (!another.isDead&&
					Math.abs(x - another.x) < another.getwEdge() + getwEdge()+realBlade.length
				&&Math.abs(y - another.y) < another.gethEdge() + gethEdge()+realBlade.length) {
				if(another.x-x>0)faceRight();
				else faceLeft();
				
				attack();
				cd=0;
			}
		}
	}
}
