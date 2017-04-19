package Enviroments;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import Element.Animation;
import Element.LightSpot;
import Mankind.Creature;
import Mankind.JointEnemy;
import Mankind.Player;
import aid.Log;

import com.mingli.toms.MusicId;
import com.mingli.toms.R;
import com.mingli.toms.Render;
import com.mingli.toms.World;

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
	public boolean pickable=true;
	Goal(char bi,float x,float y){
		super(bi,x, y);
		angleSpeed=0.2f;
		float pickWidth=w*1.99f;
		x1=x-pickWidth;
		x2=x+pickWidth;
		y1=y-pickWidth;
		y2=y+pickWidth;
		setTextureId(TexId.CUP);
		
		Log.i("Goal .x1:"+x1+"x2:"+x2+"y1:"+y1+"y2:"+y2);
		ls=new LightSpot(){
			public void loadTexture(int i){
				super.loadTexture(i);
				min=0.01f;
				max=1.0f;
			}
		};
		
		ls.setW(getW());
		ls.setH(Render.height/2);
		ls.setPosition(x, y+ls.getH()-getW());
		ls.loadTexture(TexId.LIGHTTAIL);
		
		a=new Animation();
		a.setW(pickWidth);
		a.setH(pickWidth);
		a.loadTexture(TexId.GOALCIRCLE);
	}
	void init(){
		setW(64);
		setH(64);
		super.init();
	}
//	public void drawElement(GL10 gl){
//		ls.drawElement(gl);
//		super.drawElement(gl);
//	}
	public void drawElement(GL10 gl){
		if(!pickable)return;
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
	

	public void loadSound() {
		setSoundId(MusicId.finalcoin);
	
	}

	public void playSound() {
		music.playSound(getSoundId(), 0);
	}
	public void picked() {
		// TODO Auto-generated method stub
		playSound();
		pickable=true;
		hasFirstBlood=false;
	}
	public void searchBoss(Player player, ArrayList<Creature> enemyList) {
		// TODO Auto-generated method stub
		int length=200;
		for(Creature c:enemyList){
			if(c instanceof JointEnemy){
				if(Math.abs(c.x-x)<length&&Math.abs(c.y-y)<length){
					((JointEnemy)c).player=player;
					pickable=false;
				}
			}
		}
	}

}
