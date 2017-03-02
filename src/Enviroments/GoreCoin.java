package Enviroments;

import javax.microedition.khronos.opengles.GL10;

import Mankind.Creature;
import Mankind.Player;

public class GoreCoin extends Coin{

	private float dy;
	private float dx;
	static int v0=(int) Math.sqrt(2*64);
	GoreCoin(float x, float y) {
		super(x, y);
		
		// TODO Auto-generated constructor stub
	}
	public void setAnimationFinished(boolean animationFinished){
		super.setAnimationFinished(animationFinished);
	}
	public boolean loadAble(Player player){
		
		playSound();
		player.increaseScoreBy(score);
		xSpeed=(float) (Math.random()-0.5);
		ySpeed=player.getySpeed()*0.2f+v0;
		g=player.getG();
		return false;
	}
	float xSpeed,ySpeed,g;
	public void drawElement(GL10 gl){
		gl.glTranslatef(dx+=xSpeed, dy+=(ySpeed-=g), 0);
		super.drawElement(gl);
		gl.glTranslatef(-dx, -dy, 0);
		if(ySpeed<-0){
			xSpeed=0;
			ySpeed=0;
			g=0;
			setAnimationFinished(false);
		}
	}
}
