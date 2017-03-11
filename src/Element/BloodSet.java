package Element;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.Log;
import com.mingli.toms.Render;

import element2.Set;
import element2.TexId;

import Mankind.Creature;
import Mankind.Player;

public class BloodSet extends Set {
	ArrayList<AnimationGravity> bloodList = new ArrayList<AnimationGravity>(10);

	public BloodSet(int count) {
		init(count);
		loadTexture();
	}
	public BloodSet(int count,int textureId) {
		this(count);
		setTextureId(textureId);
		for(AnimationGravity b:bloodList)
			b.setTextureId(textureId);
	}

	public void resume() {
	}


	void init(int count) {
		for (int i = 0; i < count-1; i++) {
			bloodList.add(new BloodSized());
		}
		bloodList.add(new Blood());
	}

	int tringerIndex;

	public void tringerUp(float x, float y, float speedMin, float speedMax,
			int count) {
		// ��Ͳһ������������
		float dSpeed = speedMax - speedMin;
		for (int i = 0; i < count; i++) {
			if (tringerIndex >= bloodList.size())
				tringerIndex = 0;
			AnimationGravity blood = bloodList.get(tringerIndex++);
			blood.setPosition(x, y);// bu neng dan du diao x y
			blood.setxSpeed((int) ((2 * Math.random() - 1) * dSpeed));
			blood.setySpeed((int) (Math.random() * dSpeed));
		}
	}

	public void tringerExplode(float x, float y, float speedMin,
			float speedMax, int count) {
		float dSpeed = speedMax - speedMin;
		for (int i = 0; i < count; i++) {
			if (tringerIndex >= bloodList.size())
				tringerIndex = 0;
			AnimationGravity blood = bloodList.get(tringerIndex++);
			blood.setPosition(x, y);
			blood.setxSpeed((int) ((2 * Math.random() - 1) * dSpeed));
			blood.setySpeed((int) ((2 * Math.random() - 1) * dSpeed));
		}
	}

	public void tringerDirection(float x, float y, float xSpeed, float ySpeed,
			int count) {
		// float dSpeed=speedMax-speedMin;
		Log.i("bloodtringercoujnt",""+count);
		for (int i = 0; i < count; i++) {
			if (tringerIndex >= bloodList.size())
				tringerIndex = 0;
			AnimationGravity blood = bloodList.get(tringerIndex++);
			blood.setPosition(x, y);
			blood.setxSpeed(xSpeed);
			blood.setySpeed(ySpeed);
		}
	}
	public void tringerGod(int direction,float speedMin, float speedMax, int count) {  //IT looks useless
		float dSpeed=speedMax-speedMin;
		for (int i = 0; i < count; i++) {
			if(tringerIndex>=bloodList.size())tringerIndex=0;
			 AnimationGravity rain = bloodList.get(tringerIndex++);
			double random=Math.random();
			if(random<0.36){// 0.36=720/2000 �Ӳ��淢������
				if(direction==-1){
					rain.setPosition(Player.gx1,(float) (Math.random()*Render.height));
				}else if(direction==1){
					rain.setPosition(Player.gx2,(float) (Math.random()*Render.height));
				}
				
			}else{//>0.36 �����淢������
				rain.setPosition( (float) (Render.px+Math.random()*Render.width),Player.gy2);
			}
				
			
			rain.setxSpeed((float) (-direction*Math.random()*dSpeed));
			rain.setySpeed((float) (-Math.random()*dSpeed));
		}
	}

	public void tringer(float x, float y, float speedMin, float speedMax,
			int count) {
		float dSpeed = speedMax - speedMin;
		for (int i = 0; i < count; i++) {
			if (tringerIndex >= bloodList.size())
				tringerIndex = 0;
			AnimationGravity blood = bloodList.get(tringerIndex++);
			blood.setPosition(x, y);
			blood.setxSpeed((int) ((2 * Math.random() - 1) * dSpeed));
			blood.setySpeed((int) (-Math.random() * dSpeed));
		}
	}

	public void loadTexture() {
		for (AnimationGravity blood : bloodList)
//			blood.setTexture();
			blood.loadTexture(TexId.BLOOD);
	}
	public void drawElement(GL10 gl) {
//		if (bloodList.isEmpty())return;
//		gl.glColor4f(1, 0, 0, 1);
		
		for (AnimationMove c : bloodList)
			if (c.x > Player.gx1 && c.x < Player.gx2 && c.y > Player.gy1
					&& c.y < Player.gy2)
				c.drawElement(gl);
//		gl.glColor4f(1, 1, 1, 1);
	}

	public void tringer(double d, double e, double random, double random2,
			int count) {
		tringer((float) d, (float) (e), (float) random, (float) random2, count);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return bloodList.size();
	}
}

class Blood extends AnimationGravity {
	Blood() {
		setW(8);
		setH(8);
		g=0.7f;
	}
}

class BloodSized extends AnimationGravity {
	int index;
	private float agoW=8;
	private float dSize;
	BloodSized() {
		setW(agoW);
		setH(agoW);
		g=0.5f;
	}
	// private float alphaSpeed=0.003f;
	public void drawElement(GL10 gl) {
		move();
		
		gravity();
		gravityCheck();
		
		
		if(getW()<=0)setW(1);//chu 数 为零 bug
		
		gl.glTranslatef(x, y, 0);
		float scaTime = getW() / agoW;
		float returnTime = agoW / getW();
		gl.glScalef(scaTime, scaTime, 0);
		super.baseDrawElement(gl);
		gl.glScalef(returnTime, returnTime, 0);
		gl.glTranslatef(-x, -y, 0);
	}

	protected void gravityCheck() {
		// Log.v("xSpeed+ySpeed", xSpeed+" "+ySpeed );
		setW(getW() + dSize);
		int startIndex=5;
		int endIndex=30;
//		int startIndex=15;
//		int endIndex=15;
		if (index++ < startIndex) {
			dSize = 16f/startIndex;// 16/20
			// dSize-=0.25f;//x=a*t*t/2+vt a=(x-vt)*2/t^2=
		} else {
			dSize = -16f/(endIndex-startIndex);// (16-6)/40
			
			if (index>endIndex)
				resetBlood();
		}
	}
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		reLoadBlood();
	}
	
	private void reLoadBlood() {
		setW(1);
		setH(1);
		index=0;
	}
	void resetBlood(){
		x=0;y=0;
		xSpeed=0;
		ySpeed=0;
	}
}
