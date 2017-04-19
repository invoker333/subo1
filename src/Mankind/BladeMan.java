package Mankind;

import javax.microedition.khronos.opengles.GL10;

import aid.Log;

import com.mingli.toms.Render;
import com.mingli.toms.World;

import element2.Tail;
import element2.TexId;
import Enviroments.GrassSet;
import Weapon.Blade;
import Weapon.TailGun;

public class BladeMan extends JointEnemy{


	public BladeMan(char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		// TODO Auto-generated constructor stub
		haveBlade();
		treadable=false;
		cloth.setTextureId(TexId.CLOTHENEMY);
		cap.setTextureId(TexId.CAPENEMY);
		setLifeMax(5*World.baseAttack);
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
		if (cd < cdMAX/2){
			int id=(int) (Math.random()*enemySet.cList.size());
			Creature chaser = enemySet.cList.get(id);
			chaseCheck(chaser);
		}
		
		if (cd++ > cdMAX) {
			cd = 0;
		} else return;
		super.randomAction();
		for (int i = 0; i < enemySet.cList.size(); i++) {
			Creature another = enemySet.cList.get(i);
			if (Math.abs(x - another.x) < another.getwEdge() + getwEdge()+realBlade.length
				&&Math.abs(y - another.y) < another.gethEdge() + gethEdge()+realBlade.length) {
				if(another.x-x>0)faceRight();
				else faceLeft();
				attack();
			}
		}
	}
}
