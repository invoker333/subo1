package Mankind;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.World;

import Enviroments.GrassSet;

public class TestEnemy extends Creature{
	public TestEnemy(char bi,GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
		setG(0);
		loadTexture();
		attack=0;
		setLifeMax(World.baseAttack * 20);// avoid blood play
		// TODO Auto-generated constructor stub
	}
	public TestEnemy(GrassSet gra, float x, float y) {
		this(' ',gra, x, y);
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
