package com.mingli.toms;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import element2.FireworkSet;
import element2.Tail;
import Element.Animation;
import Element.BubbleSet;
import Element.Curtain;
import Element.LightSpotSet;
import Mankind.Player;
import Weapon.AutoBullet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TouchMove implements OnTouchListener {
	int ad, au, ad1, au1;
	float ex, ey, ex1, ey1;
	float ux, uy, ux1, uy1;
	private float ex2;
	private float ey2;
	private LightSpotSet lightSpotSet;
	private Player player;
	private Tail tail;

//	private Gun gun;
	private Curtain ct;
//	private World world;
	private BubbleSet fireSet;
	private  FireworkSet fireWorkSet;
	private  ArrayList<Animation> animationList;
	private Animation editTarget;
	private boolean build8;
//	private boolean moved;
	private Animation[] build8group;
	private float grid=64;

	public TouchMove( LightSpotSet lightSpotSet, Player player){
//		this.bts = bts;
		this(player);
		this.lightSpotSet = lightSpotSet;
	}
	public TouchMove(Player player) {
		// TODO Auto-generated constructor stub
		this.player = player;
		build8group=new Animation[8];
		for(int i=0;i<build8group.length;i++){
			build8group[i]=new Animation();
			build8group[i].loadTexture();
		}
	}



	public TouchMove( Tail tail, Player player) {
		this(player);
		this.tail = tail;
	}

	public TouchMove(  Curtain ct, Player player2) {
		// TODO Auto-generated constructor stub
		this(player2);
		this.ct=ct;
	}
/*	public TouchMove(  Tail tail2,World world,
			Player player2) {
		this(    tail2, player2);
		this.ab=ab2;
		this.world = world;
		
		// TODO Auto-generated constructor stub
	}*/
	public TouchMove(  BubbleSet fireSet, Player player2) {
		// TODO Auto-generated constructor stub
		this(player2);
		this.fireSet=fireSet;
	}
	public TouchMove(  FireworkSet fireWorkSet,
			Player player2) {
		this( player2);
		// TODO Auto-generated constructor stub
		this.fireWorkSet = fireWorkSet;
	}
/*	public Touch(ButtonSet bts,   Tail tail2,
			Player player2) {
		this(tail2,player2);
		this.bts=bts;
		// TODO Auto-generated constructor stub
	}*/
	
	public TouchMove(Tail touchTail, Player player2,
			ArrayList<Animation> animationList) {
		this(touchTail,player2);
		this.animationList = animationList;
		
		for(int i=0;i<build8group.length;i++){
			animationList.add(build8group[i]);
		}
		
	}
	public boolean onTouch(View v, MotionEvent e) {
		if(lightSpotSet!=null)lightSpotSet.tringer(e.getX(), MenuActivity.screenHeight - e.getY());
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			ex = e.getX();
			ey = MenuActivity.screenHeight - e.getY();
			ex=Render.rate*ex;
			ey=ey*Render.rate;
			if(tail!=null)
				tail.startTouch(Render.px+ex,Render.py+ey);
//			moved=false;
			if(player!=null){
				player.startTouch(ex, ey);
//				calcuPlayerSpeed(ex,ey);
//				player.setGuideSpeed(xGuideSpeed,yGuideSpeed);
			}
			
			if(World.editMode)searchEditTarget(Render.px+ex, Render.py+ey);			
			break;
		case MotionEvent.ACTION_UP:
//			touchTailIndex=0;
			
//			Log.i("TouchMove.ACTION_Up");
			ux = e.getX();
			uy = MenuActivity.screenHeight - e.getY();
			ux=Render.rate*ux;
			uy=uy*Render.rate;
//			au = bts.buttonCheckUp(ux, uy);
////			player.actionCheckUp(au);
			
			if(tail!=null)tail.startTouch(ux, uy);
			
			
			if(player!=null){
				player.endTouch(ux, uy);
				
//				if(ySpeed==player.getySpeedMax()&&
//						(xSpeed==player.getxSpeedMax()||
//							xSpeed==player.getxSpeedMin())) {
//						break;
//					}
				
//				player.setSpeed(xGuideSpeed,yGuideSpeed);
				
			}
			stopEditTarget(ux,uy);	
			break;
		case MotionEvent.ACTION_MOVE:
//			moved=true;
			ex2 = e.getX();
			ey2 = MenuActivity.screenHeight - e.getY();
			ex2=Render.rate*ex2;ey2=ey2*Render.rate;
			if(lightSpotSet!=null)lightSpotSet.tringer(Render.px+ex2,Render.py+ey2);
//			if(tail!=null&&touchCount++==2){
//				touchCount=0;
//				tail.tringer(Render.px+ex2,Render.py+ey2);
//			}
			if(tail!=null)
				tail.tringer(Render.px+ex2,Render.py+ey2);
			
			if (ct!=null) {
				if(ex2>ex)ct.open();
				else ct.close();
			}
			
			
			if(player!=null){
				if(!(World.editMode&&moveEditTarget())){					
					player.moveAction(ex2, ey2);
				}
//					calcuPlayerSpeed(ex2,ey2);
//					player.setGuideSpeed(xGuideSpeed,yGuideSpeed);
			}
			
//			if (e.getActionIndex() == 0) {
//				ex = e.getX();
//				ey = MenuActivity.screenHeight - e.getY();
//				ad3=bts.buttonCheckMove(ex, ey);
//			}

//			player.actionCheckUp(ad3);
			// mouse.slide(ex,ey);
			// if(ey>ey3)dy=1;else dy=-1;//����
			// player.changeSize(player.getW()+dy,player.getH()+dy);
			// ex3=ex;ey3=ey;
			
			
			
			break;
		}
		return true;
	}
	private void stopEditTarget(float ux, float uy) {
		// TODO Auto-generated method stub
		if(!World.editMode||editTarget==null)return;
		
		build8CHeck(ux,uy);
		
		float xx=editTarget.x;
		float yy=editTarget.y;
		float grid=player.getGra().getGrid();
		
		float dx=xx%grid;
		float dy=yy%grid;
		float dg=grid/2;
		
		xx=xx-dx  +dg;
		yy=yy-dy  +editTarget.gethEdge();
		
		if(xx<0)xx-=grid;
		if(yy<0)yy-=grid;
		editTarget.setStartXY(xx, yy);
		
		
//		if(World.editMode)editTarget=null;
	}

	private void build8CHeck(float ex,float ey) {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		if(build8&&editTarget!=null){
			build8group[0].setStartXY(editTarget.x-grid,editTarget.y+grid);
			build8group[1].setStartXY(editTarget.x,		  editTarget.y+grid);
			build8group[2].setStartXY(editTarget.x+grid,editTarget.y+grid);
			build8group[3].setStartXY(editTarget.x-grid,editTarget.y);
			build8group[4].setStartXY(editTarget.x+grid,editTarget.y);
			build8group[5].setStartXY(editTarget.x-grid,editTarget.y-grid);
			build8group[6].setStartXY(editTarget.x,editTarget.y-grid);
			build8group[7].setStartXY(editTarget.x+grid,editTarget.y-grid);
			
			for(Animation aa:build8group){
				if (Math.abs(ex - aa.x) < aa.getW()
						&& Math.abs(ey - aa.y) < aa.getH()) {
					animationList.add(new Animation(aa.x,aa.y));
				}
			}
			return;
		}
	}
	private boolean moveEditTarget() {
		// TODO Auto-generated method stub
		build8=false;
		if(editTarget!=null){
			float xx = Render.px+ex2;
			float yy = Render.py+ey2;
			
			editTarget.setStartXY(xx, yy);
			return true;
		}
		return false;
	}
	private void searchEditTarget(float ex, float ey) {
		
		Animation a;
		build8=true;
		for(int i=animationList.size()-1;i>-1;i--){
			a=animationList.get(i);
			if (Math.abs(ex - a.x) < a.getW()
					&& Math.abs(ey - a.y) < a.getH()) {
				editTarget=a;
				return;
			}
		}
	}
	public void drawElement(GL10 gl){
		
		if(editTarget!=null&&
				editTarget.x>Player.gx1&&editTarget.x<Player.gx2
				&&editTarget.y>Player.gy1&&editTarget.y<Player.gy2) {
				for(Animation aa:build8group){
//					aa.drawElement(gl);
					
					
					gl.glTranslatef(aa.x, aa.y, 0);
					editTarget.baseDrawElement(gl);
					gl.glTranslatef(-aa.x, -aa.y, 0);
				}
		}
	}
	private void calcuPlayerSpeed111(float x, float y) {
		final float grid=player.getGra().getGrid();
		x=Render.px+x;
		y=Render.py+y;
		
//		if(x<player.x)
//			x=x-x%grid+-player.getwEdge()+1;//1 point let it stand zhenghao bu diao xiaqu
//		else
//			x=x-x%grid+grid+player.getwEdge()-1;
//		// jump to edge
//		y=y-y%grid+player.gethEdge();
		final int jumpH=64;
		
		float dx=x  -player.x;
		float dy=y   -player.y;
		float jumpY;
		
		double time;
		
		//		if(dx>0)time=dx/player.getSpeedMax();
//		else time=dx/player.getSpeedMin();
		if(dy>0){
			jumpY=dy+jumpH;
			yGuideSpeed=Math.sqrt(2f*player.getG()*jumpY);
//			Log.i("player.getySpeedMax()"+player.getySpeedMax());
			time=Math.sqrt(2*jumpY/player.getG())+Math.sqrt(2*jumpH/player.getG());			// jump time
			xGuideSpeed=dx/time;
		}
		else if(dy<=0){
//			if(player.dropCheck(ex,ey))return;
			final double ys=Math.sqrt(2*player.getG()*jumpH);
			yGuideSpeed=ys;
			///////////////////////
			jumpY=-dy+jumpH;
			time=Math.sqrt(2*jumpH/player.getG())+Math.sqrt(2*jumpY/player.getG());
			xGuideSpeed=dx/time;
		}
		
		if(xGuideSpeed>player.getxSpeedMax()){
			xGuideSpeed=player.getxSpeedMax();
		}
		else if(xGuideSpeed<player.getxSpeedMin()){
			xGuideSpeed=player.getxSpeedMin();
		}
		if(yGuideSpeed>player.getySpeedMax()){
			yGuideSpeed=player.getySpeedMax();
		}
	}
	private double yGuideSpeed;
	private double xGuideSpeed;
	float ex3, ey3, dy;
}
