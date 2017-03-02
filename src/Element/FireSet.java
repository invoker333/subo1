package Element;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.mingli.toms.Render;

import element2.TexId;

public class FireSet extends BubbleSet{
	private  float x;
	private  float y;
	private float T;
	public FireSet(int fireCount) {
		super(fireCount);
		// TODO Auto-generated constructor stub
	
	}
	public FireSet(int fireCount,float x,float y){
		this(fireCount);
		this.x = x;
		this.y = y;
	}
	
	void setBubble(int fireCount) {
		bubList = new ArrayList<Bubble>();
		Fire fire = null;
		for (int i = 0; i < fireCount; i++) {
			bubList.add(fire = new Fire());
		}
		T=fire.timeAll/fireCount;
	}
	public void loadTexture() {
		Bubble fire;
		for (int i = 0; i < bubList.size(); i++) {
			fire = bubList.get(i);
			fire.setTextureId(TexId.FIRE);
////			
//		/*	int j = (int) (3 * Math.random());
//			switch (j) {
//			case 0:
//				fire.setTextureId(TexId.FIRE);
//				break;
//			case 1:
//				fire.setTextureId(TexId.FIRE2);
//				break;
//			case 2:
//				fire.setTextureId(TexId.CANDLEPOR);
//				break;
//			}*/
//			
			fire.setTexture();
		}
//
	}
	int index;
	public void drawElement(GL10 gl){
		super.drawElement(gl);
		if(index++%T==0)tringer(x + 16 * (float) Math.random(), y, 1);
	}
	public void tringerExplode(int rate,float x, float y,int count){
		super.tringerExplode(rate, x, y, count);
	}
}
class Fire extends Bubble{
	final float frontTime=10;
	final float backTime=15;
	float timeAll=frontTime+backTime;
	
	private final float alphaMid=0f;
	private float alpha=alphaMid;
	private float blue=alphaMid;
	private float green=alphaMid;
	private float red=alphaMid;
	float dAlpha=0.05f;
	int index;
//	private float alphaSpeed=0.003f;
	public void incAlpha(float dAlpha){
		this.red += dAlpha;
		this.green += dAlpha;
		this.blue += dAlpha;
		this.alpha+= dAlpha;
		
	}public void drawElement(GL10 gl){
		
		incAlpha(dAlpha);
		gl.glColor4f(red, green, blue, alpha);
		super.drawElement(gl);
		
		gl.glColor4f(1, 1, 1, 1);
	}
	Fire(){
		super();
		dSize=-1f;
		setW(64);
		setH(128);
		ySpeed=6;
	}
	public void tringer(double d, double e) {
		tringer((float)x,(float)y);
	}
	void tringer(float x, float y) {
		super.tringer(x, y);
		incAlpha(alphaMid-alpha);//huan yuan yan se
	}
	protected void gravityCheck() {
//		Log.v("xSpeed+ySpeed", xSpeed+" "+ySpeed );
		final float alphaMax=1.5f;
		final float sizeMax=32f;
		final float minSize = 20;
		
		if(index++<frontTime){
			dSize=sizeMax/frontTime;//64/20
//			dSize-=0.25f;//x=a*t*t/2+vt    a=(x-vt)*2/t^2=
			dAlpha=(alphaMax-alphaMid)/frontTime;//
		}
		else{
			dSize=(minSize-sizeMax)/backTime;//(64-24)/40
			dAlpha=-(alphaMax+alphaMid)/backTime;//1.5/40
		}
		if (alpha<0)
			resetBubble();
	}
	void resetBubble() {
		super.resetBubble();
		index=0;
		incAlpha(alphaMid-alpha);//huan yuan yan se
//		incAlpha(0-alpha);//huan yuan yan se
	}
}