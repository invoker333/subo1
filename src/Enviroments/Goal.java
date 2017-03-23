package Enviroments;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import Element.Animation;
import Element.LightSpot;
import Mankind.Player;
import aid.Log;

import com.mingli.toms.MusicId;
import com.mingli.toms.R;
import com.mingli.toms.Render;

import element2.TexId;


public class Goal extends RotateFruit{


	public float x1;
	public float x2;
	public float y1;
	public float y2;
/*	public Goal(int count) {
		super(count);
		// TODO Auto-generated constructor stub
		loadSound();
//		rate=5;
	}*/
	LightSpot ls;
	Animation a;
	private boolean hasFirstBlood=true;
	Goal(char bi,float x,float y){
		super(bi,x, y);
		x1=x-getW();
		x2=x+getW();
		y1=y-getH();
		y2=y+getH();
		setTextureId(TexId.CUP);
		
		Log.i("Goal .x1:"+x1+"x2:"+x2+"y1:"+y1+"y2:"+y2);
		ls=new LightSpot(){
			public void loadTexture(int i){
				super.loadTexture(i);
				min=-1f;
				max=1.0f;
			}
		};
		
		ls.setW(getW());
		ls.setH(Render.height/2);
		ls.setPosition(x, y+ls.getH()-getW());
		ls.loadTexture(TexId.LIGHTTAIL);
		
		a=new Animation();
//		final float d=getW()*1.4f;
		final float d=getW()*1.99f;
		a.setW(d);
		a.setH(d);
		a.loadTexture(TexId.GOALCIRCLE);
//		ls.
	}
	void init(){
		setW(128);
		setH(128);
		super.init();
	}
//	public void drawElement(GL10 gl){
//		ls.drawElement(gl);
//		super.drawElement(gl);
//	}
	public void drawElement(GL10 gl){
		ls.drawElement(gl);
		
		gl.glEnable(GL10.GL_CULL_FACE);//背面裁剪
		angle+=angleSpeed;
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(angle, 0, 1, 1f);
		baseDrawElement(gl);
		gl.glRotatef(-angle, 0, 1, 1f);
		if(hasFirstBlood)a.drawScale(gl);
		gl.glTranslatef(-x, -y, 0);
		gl.glDisable(GL10.GL_CULL_FACE);//背面裁剪
		
			a.randomWave(a.w*4f/5f);
	}
	
/*	public Goal(float x, float y,int count) {
		this(count);
		float h=256;
		float w=256;
		this.x=x;this.y=y;
		tringer(x,y, count);
		x1=x-w;x2=x+w;
		y1=y-h;y2=y+h;
		// TODO Auto-generated constructor stub
	}
	public void drawElement(GL10 gl){
		super.drawElement(gl);
		if(index++%5==0)
			tringerExplode(5,x,y,1);
	}*/

	public void loadSound() {
		setSoundId(MusicId.finalcoin);
	
	}

	public void playSound() {
		music.playSound(getSoundId(), 0);
	}
	public void picked() {
		// TODO Auto-generated method stub
		playSound();
		hasFirstBlood=false;
	}

}
