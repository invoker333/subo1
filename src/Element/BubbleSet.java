package Element;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.Render;

import element2.Set;
import element2.TexId;

public class BubbleSet extends Set {
	ArrayList<Bubble> bubList;
	protected int tringerIndex;

	public BubbleSet(int bubCount) {
		setBubble(bubCount);
		loadTexture();
	}

	void setBubble(int bubCount) {
		bubList = new ArrayList<Bubble>();
		Bubble bub;
		for (int i = 0; i < bubCount; i++) {
			bubList.add(bub = new Bubble());
			bub.tringer((float) (Math.random()) * Render.width, Render.height
					* 2f * (float) (Math.random() - 0.5f));
		}
	}
	public void tringer(float x, float y,int count) {
		for(int i=0;i<count;i++){
			
			if(tringerIndex>=bubList.size())tringerIndex=0;
			Bubble boom = bubList.get(tringerIndex++);
			boom.tringer(x, y);
		}
	}
	public void tringerScreen(int count) {
		for(int i=0;i<count;i++){
			if(tringerIndex>=bubList.size())tringerIndex=0;
			Bubble boom = bubList.get(i++);
			boom.tringer(Render.px+(float)Math.random()*Render.width, Render.py+(float)Math.random()*Render.height);
		}
	}
	public void tringerExplode(int rate,float x, float y,int count) {
		for(int i=0;i<count;i++){
			
			if(tringerIndex>=bubList.size())tringerIndex=0;
			Bubble boom = bubList.get(tringerIndex++);
			
			
			boom.rate=rate;
//			boom.setW(boom.agoW);
			boom.dSize=10;
			
			boom.tringerExplode(x, y);
		}
	}
	public void loadTexture() {
		Bubble bub;
		for (int i = 0; i < bubList.size(); i++) {
			bub = bubList.get(i);
			int j = (int) (3 * Math.random());
			switch (j) {
			case 0:
				bub.setTextureId(TexId.RED);
				break;
			case 1:
				bub.setTextureId(TexId.GREEN);
				break;
			case 2:
				bub.setTextureId(TexId.BLUE);
				break;
			}
			bub.setTexture();
		}

	}

	public void drawElement(GL10 gl) {
		for (Bubble bub:bubList) {
			bub.drawElement(gl);
		}
	}

	public void pause() {
	}

	public void resume() {
	}
}

class Bubble extends AnimationMove {
	float x0, y0;
//	private float height = 1.3f * Render.height;
	protected float dSize = 1.2f;
	float agoW=64;
	int rate=1;
	Bubble() {
//		agoW=getW();
		ySpeed = 5f;
	}

	void tringer(float x, float y) {
		setW(5);
		setH(5);
		setPosition(x, y);
		x0 = x;
		y0 = y;
		xSpeed=(float) (rate*2*(Math.random()-0.5));
	}
	void tringerExplode(float x,float y){
		tringer(x,  y);
		ySpeed=(float) (rate*2*(Math.random()-0.5));
	}
	public void gravity() {
		super.gravity();
		setW(getW() + dSize);
		setH(getH() + dSize);
		if(getW()<=0)
		setW(1);
		setH(1);
		gravityCheck();
	}
	public void drawElement(GL10 gl){
		move();
		gravity();
		gl.glTranslatef(x, y, 0);
		
		float scaTime=getW()/agoW;
		float returnTime=agoW/getW();
		gl.glScalef(scaTime, scaTime, 0);
		
		super.baseDrawElement(gl);
		
		gl.glScalef(returnTime, returnTime, 0);
		gl.glTranslatef(-x, -y, 0);
	}

	protected void gravityCheck() {
		if (y > Render.py+Render.height)
			resetBubble();
	}

	void resetBubble() {
		x = x0;
		y = y0;
		setW(1);
		setH(1);
	}
}
