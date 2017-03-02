package Element;

import javax.microedition.khronos.opengles.GL10;

import element2.TexId;


public class GuidePost extends LightSpot {
	public GuidePost(){
		min=0.5f;
		setW(64);
		setH(64);
		loadTexture(TexId.GUIDEPOST);
	}
	private float angle;
	int stayIndex;
	public void tringer(float x, float y, float dx, float dy) {
		super.tringer(x, y);
		stayIndex=60;
		angle = (float) (Math.atan2(dy, dx) * 180/3.14);
	}

	public void drawElement(GL10 gl) {
		if(stayIndex--<0) tringer(0,0,0,0);
		
		colorChange();
		gl.glColor4f(red, green, blue, alpha);
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(angle, 0, 0, 1);
		super.baseDrawElement(gl);
		gl.glRotatef(-angle, 0, 0, 1);
		gl.glTranslatef(-x, -y, 0);
		gl.glColor4f(1, 1, 1, 1);
	}
}
