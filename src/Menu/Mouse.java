package Menu;

import javax.microedition.khronos.opengles.GL10;

import Element.Animation;
import Mankind.Player;

import com.mingli.toms.World;

import element2.TexId;

public class Mouse extends Animation{
	Animation an=new Animation();
	CircleSlide cs=new CircleSlide();
	Player p;
	float width=150,height=150;
	float tx,ty,tx1,ty1;
	float length=100;
	private float speedTime=1;
	public Mouse (Player p){
		this.p = p;		
		setW(getW()+length);
		setH(getH()+length);
		tx=width-getW();
		tx1=width+getW();
		ty=height-getH();
		ty1=height+getH();
		
		
		x=width;
		y=height;
		loadTexture(TexId.GREEN);
	}
	
	public void slide(float ex,float ey){
		if(ex>=tx&&ex<tx1&&
				ey>ty&&ey<ty1){
			cs.slide(x,y,ex,ey,length);
			
			an.x=cs.getX();
			an.y=cs.getY();
			
			p.setxSpeed(cs.getSx()*speedTime);
			p.setySpeed(cs.getSy()*speedTime);
			
			float sp=speedTime*0.5f;
			if(p.getxSpeed()>sp)p.setyState(2);
			else if(p.getxSpeed()<-sp)p.setyState(1);
			else if(p.getySpeed()>sp)p.setyState(3);
			else if(p.getySpeed()<-sp)p.setyState(0);
		}
		
	}
	public void slideUp(){
		cs.setDx(0);cs.setDy(0);
		p.setxSpeed(0);
		p.setySpeed(0);
	}
	public void loadTexture(int textureId){
		super.loadTexture(textureId);
		an.loadTexture(textureId);
//		new Thread(this).start();
	}
	public void drawElement(GL10 gl){
		
		super.drawElement(gl);
		an.x=x+cs.getDx();
		an.y=y+cs.getDy();
		gl.glTranslatef(an.x, an.y, 0);
		an.drawElement(gl);
		gl.glTranslatef(-an.x, -an.y, 0);
	}
//	public void resume(){
//		if(!isLiving()){
//			new Thread(this).start();
//			setLiving(true);
//		}
//	}
//	public void changePosition(){
////		x=Render.px+width;
////		y=Render.py+height;
//		changeWidth();
//		changeHeight();
//		
//		
//		super.changePosition();
//		
//	}
}
