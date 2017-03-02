package Menu;

import com.mingli.toms.Render;

public class CircleSlide {
	private float x;
	private float y;
	private float dx;
	private float dy;
	float xt;
	float yt;
	float s;
	//	float l2;
	private float p;
	private float sx;//沟谷定律分量
	private float sy;
	public void slide(float x,float y,float ex,float ey,float length){
		xt=Render.px+ex;
		yt=Render.py+ey;
		dx=xt-x;
		dy=yt-y;

		s=(float)Math.sqrt( (Math.pow(dx, 2)+Math.pow(dy, 2)));
		sx=dx/s;
		sy=dy/s;

		setP(length/s);
		setX(xt);
		setY(yt);
		if(s<=length){//圈里面操作

		}
		else {
			setDx(dx*p);
			setDy(dy*p);
			setX(x+dx);
			setY(y+dy);

		}
//			if(Math.abs(xStart-p.x)>100)magicCheck(ex, ey);
	}
	void home(float x,float y){
		this.x=x;
		this.y=y;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getSx() {
		return sx;
	}
	public void setSx(float sx) {
		this.sx = sx;
	}
	public float getSy() {
		return sy;
	}
	public void setSy(float sy) {
		this.sy = sy;
	}
	public float getDx() {
		return dx;
	}
	public void setDx(float dx) {
		this.dx = dx;
	}
	public float getDy() {
		return dy;
	}
	public void setDy(float dy) {
		this.dy = dy;
	}
	public float getP() {
		return p;
	}
	public void setP(float p) {
		this.p = p;
	}
}

