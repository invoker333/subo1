package Mankind;

import javax.microedition.khronos.opengles.GL10;

import aid.Log;

import com.mingli.toms.World;

import element2.Tail;
import element2.TexId;
import Enviroments.GrassSet;
import Weapon.Blade;
import Weapon.TailGun;

public class BladeMan extends JointCreature{

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
	public void randomAction(){
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
