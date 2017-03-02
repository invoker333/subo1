package Menu;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.World;

public class ColorState extends State{
	private float red=1;
	private float green=1;
	private float blue=1;
	public ColorState(float x1, float y1, float x2, float y2) {
		super(x1, y1, x2, y2);
		// TODO Auto-generated constructor stub
	}
	public void drawElement(GL10 gl){
		gl.glColor4f(World.red+red, World.green+green, World.blue+blue, World.alpha);
		super.drawElement(gl);
		gl.glColor4f(1,1,1,1);
	}
	public void animation(float dr,float dg,float db) {
		setRgb(getRed()+dr,getGreen()+dg, getBlue()+db);		
	}
	public void setRgb(float red,float green,float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		
	}
	public void resetRgb() {
		red=1;green=1;blue=1;
	}
	public float getRed() {
		return red;
	}
	public void setRed(float red) {
		this.red = red;
	}
	public float getGreen() {
		return green;
	}
	public void setGreen(float green) {
		this.green = green;
	}
	public float getBlue() {
		return blue;
	}
	public void setBlue(float blue) {
		this.blue = blue;
	}
}
