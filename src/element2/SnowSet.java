package element2;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Element.AnimationMove;
import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.Player;

import com.mingli.toms.Render;

public class SnowSet extends Set{
	ArrayList<Creature> weatherList = new ArrayList<Creature>(10);
	protected GrassSet gra;
	int tringerIndex;
	
	public SnowSet(int count, GrassSet gra) {
		this.gra=gra;
		init(count);
		loadTexture();
	}
	public void loadTexture() {
		for(Creature snow:weatherList)
			snow.loadTexture(TexId.SNOW);
	}

	void init(int count) {
		for (int i = 0; i < count; i++) {
			weatherList.add(new Rain(gra));
		}
	}

	
	public void tringer(int x,float speedMin, float speedMax, int count) {
		float dSpeed=speedMax-speedMin;
		for (int i = 0; i < count; i++) {
			if(tringerIndex>=weatherList.size())tringerIndex=0;
			Creature rain = weatherList.get(tringerIndex++);
			double random=Math.random();
			if(random<0.36){// 0.36=720/2000 �Ӳ��淢������
				if(x==-1){
					rain.setPosition(Player.gx1,(float) (Math.random()*Render.height));
				}else if(x==1){
					rain.setPosition(Player.gx2,(float) (Math.random()*Render.height));
				}
				
			}else{//>0.36 �����淢������
				rain.setPosition( (float) (Render.px+Math.random()*Render.width),Player.gy2);
			}
				
			
			rain.setxSpeed((float) (-x*Math.random()*dSpeed));
			rain.jump((float) (-Math.random()*dSpeed));
		}
	}
	public void tringerDirection(float x, float y, float xSpeed, float ySpeed, int count) {
//		float dSpeed=speedMax-speedMin;
		for (int i = 0; i < count; i++) {
			if(tringerIndex>=weatherList.size())tringerIndex=0;
			Creature firework = weatherList.get(tringerIndex++);
			firework.setPosition(x,y);// bunengdandudiao x y
			firework.setxSpeed(xSpeed);
			firework.jump(ySpeed);
		}
	}
	
	public void tringerUp(float x, float y, float speedMin, float speedMax, int count) {
		//��Ͳһ������������
		float dSpeed=speedMax-speedMin;
		for (int i = 0; i < count; i++) {
			if(tringerIndex>=weatherList.size())tringerIndex=0;
			Creature firework = weatherList.get(tringerIndex++);
			firework.setPosition(x,y);// bunengdandudiao x y
			firework.setxSpeed((float) ((2*Math.random()-1)*dSpeed));
			firework.jump((float) (Math.random()*dSpeed));
		}
	}
	public void tringer(float x, float y, float speedMin, float speedMax, int count) {
		float dSpeed=speedMax-speedMin;
		for (int i = 0; i < count; i++) {
			if(tringerIndex>=weatherList.size())tringerIndex=0;
			Creature firework = weatherList.get(tringerIndex++);
			firework.setPosition(x,y);
			firework.setxSpeed((float) ((2*Math.random()-1)*dSpeed));
			firework.jump((float) ((2*Math.random()-1)*dSpeed));
		}
	}
	
	public void tringer(double d, double e, double random, double random2,
			int count) {
		tringer((float)d,(float)(e),(float)random,(float)random2,count);
	}
	//�������罦
	public void drawElement(GL10 gl) {
		if (!weatherList.isEmpty())
			for (AnimationMove c : weatherList)
//				if (c.x > Player.gx1 && c.x < Player.gx2 && c.y > Player.gy1
//						 && c.y < Player.gy2)
						c.drawElement(gl);
	}
	
}
class Rain extends ParticleBallRandom {
	
	float windRate=1/200f;
	protected void speedCheck(){
		if(xSpeed>0) xSpeed-=windRate*xSpeed*xSpeed;//���ǹ�ʽЧ���á���
		else xSpeed+=windRate*xSpeed*xSpeed;
	}
	public Rain(GrassSet gra) {
		super(gra);
		// TODO Auto-generated constructor stub
	}
	protected void init(){
		setW(32);
		setH(32);
		setwRate(0.1f);
		sethRate(0.1f);
		super.init();
	}
	protected void afterInit(){
		setG(0.1f);
		super.afterInit();
	}
	public void jump(float rate) {
		setySpeed(rate);
		setAf(0.01f); // ���е������Ͷ���
	}
}
