package Enviroments;

import javax.microedition.khronos.opengles.GL10;

import aid.Log;
import android.R.anim;

import com.mingli.toms.World;

import element2.TexId;
import Element.AnimationGrass;
import Mankind.EnemySet;

public class GrassPrick extends Grass {

	int angle;
	public GrassPrick(char bi, float[] data, int angle) {
		super(bi,data, 0);
		// TODO Auto-generated constructor stub
		if(angle==0||angle==180)setTextureId(TexId.PRICKX);
		else setTextureId(TexId.PRICK);
		this.angle = angle;
	}
	public boolean tooDown(AnimationGrass a){
		if(angle==90)attack(a);
		return false;
	}
	public boolean tooHigh(AnimationGrass animationGrass) {
		if(angle==270)attack(animationGrass);
		// TODO Auto-generated method stub
		return false;
	}
	public boolean tooLeft(AnimationGrass animationGrass) {
		// TODO Auto-generated method stub
		if(angle==0)attack(animationGrass);
		return false;
	}
	public boolean tooRight(AnimationGrass animationGrass) {
		if(angle==180)attack(animationGrass);
		Log.i("grassPrick x y"+x+y+"animation.x y"+animationGrass.x+animationGrass.y);
		// TODO Auto-generated method stub
		return false;
	}
	
	void attack(AnimationGrass a){
		a.attacked(2*World.baseAttack);
	}
	
	public void drawElement(GL10 gl){
		
		drawDirection( gl);
		
	}
	
	 private void drawDirection(GL10 gl) {
		// TODO Auto-generated method stub
		 switch(angle){
		 case 0:
			 super.drawElement(gl); break;
		 case 180:
			 gl.glTranslatef(x, y, 0);
			 gl.glRotatef(180, 0, 1, 0);
			 baseDrawElement(gl);
			 gl.glRotatef(-180, 0, 1, 0);
			 gl.glTranslatef(-x, -y, 0);
			 break;
		 case 90:
			 super.drawElement(gl);
			 break;
		 case 270:
			 gl.glTranslatef(x, y, 0);
			 gl.glRotatef(180, 1, 0, 0);
				baseDrawElement(gl);
			 gl.glRotatef(-180, 1, 0, 0);
			 gl.glTranslatef(-x, -y, 0);
			 break;
		 }
	}
//	public void syncTextureSize(){
//		 x=(data[0]+data[2])/2f;
//		 y=(data[1]+data[3])/2f;
//		 float  w=(data[2]-data[0])/2f;
//		
//		
//		fbSpi.clear();
//		fbSpi.put(new float[]{
//				-w,-w,getDepth(),
//				w,-w,getDepth(),
//				w,w,getDepth(),
//				-w,w,getDepth(),
//				-w,-w,getDepth(),
//			}
//		);
//		
//		// let it can be ci ren
////		w=(data[2]-data[0])/6;
////		 switch(angle){
////		 case 0:
////			 data[0]=data[2]-w;
////			 break;
////		 case 180:
////			 data[2]=data[0]+w;
////			 break;
////		 case 90:
////			 data[1]=data[3]-w;
////			 break;
////		 case 270:
////			 data[3]=data[1]+w;
////			 break;
////		 }
//	}
}
