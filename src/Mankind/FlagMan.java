package Mankind;

import Enviroments.GrassSet;
import element2.Tail;
import element2.TexId;

public class FlagMan extends JointCreature{

	public FlagMan(char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		treadable=false;
		setFlag();
		turnRight();
		
	}
	private void setFlag() {
		
		haveBlade();
		realBlade.tail.setTextureId(TexId.FLAG);
		realBlade.setTextureId(TexId.QIGAN);
		cloth.setTextureId(TexId.CLOTH);
		cap.setTextureId(TexId.CAP);
		expression.setTextureId(TexId.EXPRESSION);
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
