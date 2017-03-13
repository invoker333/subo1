package Mankind;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;

public class TestEnemy extends Creature{

	public TestEnemy(GrassSet gra, float x, float y) {
		super(' ',gra, x, y);
		setG(0);
		loadTexture();
		setLifeMax(getLifeMax() * 10);// avoid blood play
		// TODO Auto-generated constructor stub
	}
	protected void init(){
		w=gra.getGrid()/4f;
		h=gra.getGrid()/4f;
		
		super.init();
	}
	 protected void moveCheck(){
			xSpeed=0;
		}
		 protected void gravityCheck(){
			ySpeed=0;
		}
	 public void setPosition(float x,float y){
		super.setPosition(x, y);
		xSpeed=0;ySpeed=0;
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
