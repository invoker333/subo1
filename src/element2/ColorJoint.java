package element2;

import javax.microedition.khronos.opengles.GL10;

import Mankind.Creature;

public class ColorJoint extends Joint {

	private float red=1;
	private float green=1;
	private float blue=1;
	private float alpha=1;
	public ColorJoint(Creature p, float x1, float y1, float x2, float y2, float xp,
			float yp, int textureId) {
		super(p, x1, y1, x2, y2,xp,yp, textureId);
	}

	public ColorJoint(Creature p, float x1, float y1, float x2, float y2, float xp,
			float yp, int textureId, int myDirection) {
		super(p, x1, y1, x2, y2, xp, yp, textureId,myDirection);
	}
	public void drawElement(GL10 gl){
		gl.glColor4f(red, green, blue, alpha);
		super.drawElement(gl);
		gl.glColor4f(1, 1, 1, 1);
	}
	public void setColor(float red,float green,float blue,float alpha){
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
}
