package Enviroments;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Mankind.Creature;
import Mankind.Player;

public class GoreCoin extends Coin{

	private float ySpeed;
	private float g;
	GoreCoin(char bi,float x, float y) {
		super(bi,x, y);
		visible=false;
		// TODO Auto-generated constructor stub
	}
	public void use(Player player, ArrayList<Fruit> pickedList) {
		// TODO Auto-generated method stub
//		super.use(player, pickedList);
		g=1;
		angleSpeed=10;
//		ySpeed=(float) Math.sqrt(2*g*128);
		ySpeed=Math.abs(player.getySpeed()/player.getGra().getSpeedBack()*.8f);
		visible=true;
	}
	public void drawElement(GL10 gl){
		if(!visible)return;
		{
			if(ySpeed<0)visible=false;
			y+=ySpeed;
			ySpeed-=g;
		}
		super.drawElement(gl);
	}
}
