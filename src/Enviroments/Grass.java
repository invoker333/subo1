package Enviroments;

import javax.microedition.khronos.opengles.GL10;

import Element.Animation;
import Element.AnimationGrass;
import Menu.Square;

public class Grass extends Animation{


	public float[] data;
	private float green=1;
	private float red=1;
	private float blue=1;
	private boolean isburrow;
	public boolean notBroken=true;
	public Grass(char mapSign,float data[],int texId){
		this(data);
		this.mapSign=mapSign;
		setTextureId(texId);
	}
	public Grass(float data[]){//��̨��ʼxֵ�����ȣ���ʼ yֵ
		this.data=data;
		
		
		
		x=(data[0]+data[2])/2;
		y=(data[1]+data[3])/2;
		
		startX=x;
		startY=y;
		
		w=(data[2]-data[0])/2;
		h=(data[3]-data[1])/2;
		
		wEdge=w;
		hEdge=h;
		
		
		setxCount(1);
	}
	public void setRgb(float red,float green,float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;		
	}
	public void drawElement(GL10 gl){
//		gl.glColor4f(red, green, blue, 1);
		gl.glTranslatef(x, y, 0);
		baseDrawElement(gl);
		gl.glTranslatef(-x, -y, 0);
//		gl.glColor4f(1, 1, 1, 1);
	}
	public float getGreen() {
		return green;
	}
	public void setGreen(float green) {
		this.green = green;
	}
	public float getRed() {
		return red;
	}
	public void setRed(float red) {
		this.red = red;
	}
	public float getBlue() {
		return blue;
	}
	public void setBlue(float blue) {
		this.blue = blue;
	}
	public boolean isIsburrow() {
		return isburrow;
	}
	public void setIsburrow(boolean isburrow) {
		this.isburrow = isburrow;
	}
	public boolean tooDown(AnimationGrass animationGrass) {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean tooHigh(AnimationGrass animationGrass) {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean tooLeft(AnimationGrass animationGrass) {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean tooRight(AnimationGrass animationGrass) {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean turnDown(AnimationGrass creature) {
		// TODO Auto-generated method stub
		return false;
	}
	public void gored() {
		// TODO Auto-generated method stub
		
	}
}
