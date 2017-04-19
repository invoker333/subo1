package Mankind;

import aid.Log;
import element2.Tail;
import element2.TexId;
import Enviroments.GrassSet;

public class FlagMan extends JointCreature{

	public FlagMan(char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		treadable=false;
		setFlag();
		turnRight();
		
	}
	private void setFlag() {
		
		haveBlade();
		realBlade.tail=new Tail(25,TexId.REDCREEPER);
		realBlade.tail.setTextureId(TexId.FLAG);
		realBlade.setTextureId(TexId.QIGAN);
//		realBlade.angstart=60;
		realBlade.angstart=75;
		realBlade.h=4;
		realBlade.loadTexture();
	}
	protected void tooRight() {
		super.tooRight();
//		setAnimationFinished(true);
		turnLeft();
	}
	protected void tooLeft() {
		super.tooLeft();
//		setAnimationFinished(true);
		turnRight();
	}
	public void die(){}

}
