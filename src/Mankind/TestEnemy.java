package Mankind;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;

public class TestEnemy extends Spide{

	public TestEnemy(GrassSet gra, float x, float y) {
		super(' ',gra, x, y);
		// TODO Auto-generated constructor stub
	}
	protected void init(){
		w=gra.getGrid()/4f;
		h=gra.getGrid()/4f;
		
		super.init();
	}
	public void drawElement(GL10 gl) {
		red-=0.01;
		gl.glColor4f(red, 1, 1, 1);
		drawScale(gl);
		gl.glColor4f(1, 1, 1, 1);
	}
	public void attacked(int a){
		red=1;
	}
}
