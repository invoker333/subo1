package com.mingli.toms;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import element2.FireworkSet;
import element2.Tail;
import element2.TexId;
import Element.Animation;
import Element.BubbleSet;
import Element.Curtain;
import Element.LightSpotSet;
import Mankind.Player;
import Weapon.AutoBullet;
import aid.Log;
import android.provider.ContactsContract.DeletedContacts;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TouchMove implements OnTouchListener {
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
//	private boolean moved;
	private Animation[] build8group;
	private final float grid=64;
	private Animation cloner;
	private World world;
	private Animation deleter;
	boolean deleteEdgeMode;
	private boolean moveCloneMode;

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
//			build8group[i].loadTexture();
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
			World world) {
		this(touchTail,player2);
		this.world = world;
		deleter=new Animation();
		deleter.loadTexture(TexId.GUIDERECT);
		this.animationList = world.animationList;
	}
	public boolean onTouch(View v, MotionEvent e) {
		if(lightSpotSet!=null)lightSpotSet.tringer(e.getX(), MenuActivity.screenHeight - e.getY());
		float ex2;
		float ey2;
		float ex = 0;
		float ey;
		float ux;
		float uy;
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
			
			if(World.editMode)startEditTarget(Render.px+ex, Render.py+ey);			
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
			}
			stopEditTarget(ux,uy);	
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i("TouchMove.move");
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
				if(!World.editMode || !moveEditTarget(ex2,ey2)){					
					player.moveAction(ex2, ey2);
				}
			}
			break;
		}
		return true;
	}
	private void stopEditTarget(float ux, float uy) {
		// TODO Auto-generated method stub
		moveCloneMode=false;
		if(!World.editMode||editTarget==null)return;
		
		build8CHeck();
		
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
		
		if(deleteEdgeMode){
			deleteTarget();
			deleteEdgeMode=false;
			return;
		}
		
		if(World.editMode)editTarget=null;
	}
	private void deleteTarget() {
		animationList.remove(editTarget);
//			world.removeDraw(editTarget);
		editTarget.setPosition(0, 400);
		editTarget=null;
	}

	private void build8CHeck() {
		// TODO Auto-generated method stub
		if(cloner!=null){
			build8group[0].setPosition(cloner.x-grid,cloner.y+grid);
			build8group[1].setPosition(cloner.x,		  cloner.y+grid);
			build8group[2].setPosition(cloner.x+grid,cloner.y+grid);
			build8group[3].setPosition(cloner.x-grid,cloner.y);
			build8group[4].setPosition(cloner.x+grid,cloner.y);
			build8group[5].setPosition(cloner.x-grid,cloner.y-grid);
			build8group[6].setPosition(cloner.x,cloner.y-grid);
			build8group[7].setPosition(cloner.x+grid,cloner.y-grid);
			return;
		}
	}
	private boolean moveEditTarget(float ex2, float ey2) {
		
		float xx = Render.px+ex2;
		float yy = Render.py+ey2;
		
		
		
		if(moveCloneMode||deleteMuchMode){
			
			Log.i("MoveCloneMode=true");
			float grid=player.getGra().getGrid();
			float dg=grid/2;
			
			float tx=xx-xx%grid+dg;
			float ty=yy-yy%grid+dg;
//			Log.i("xx+tx"+xx+" "+tx);
			
			boolean noDul=true;// here has no animation
	
			for(int i=0;i<animationList.size();i++){
				Animation a=animationList.get(i);
				if(Math.abs(tx-a.x)<grid&&
						Math.abs(ty-a.y)<grid) {
					
					if(deleteMuchMode){
						editTarget=a;
						deleteTarget();
						return true;
					}
					
					noDul=false;
					Log.i("noDul=false;");
					break;
				}
			}
			if(moveCloneMode)if(noDul){
				Log.i("noDul=true;  newAnimation(cloner);");
				newAnimation(tx,ty);
			}
			moveViewCheck(ex2,ey2);
			return true;
		}
		
		
		cloner=null;
		if(editTarget!=null){
			editTarget.setStartXY(xx, yy);
			
			if(Math.abs(xx-deleter.x)<deleter.w
					&&Math.abs(yy-deleter.y)<deleter.h){
				deleteEdgeMode=true;
			}else deleteEdgeMode=false;
			moveViewCheck(ex2,ey2);
			return true;
		}
		return false;
	}
	private void moveViewCheck(float ex2, float ey2) {
		// TODO Auto-generated method stub
		final float length=128;
		final float devi=10;
		if(ex2<length){
			float dx=length-ex2;player.px-=dx/devi;
		}else{
			float px2=(Render.width-length);
			if(ex2>px2){
				float dx=ex2-px2;
				player.px+=dx/devi;
			}
		}
		
		if(ey2<length){
			float dy=length-ey2;player.py-=dy/devi;
		}else {
			float py2=(Render.height-length);
			if(ey2>py2){
				float dy=ey2-py2;
				player.py+=dy/devi;
			}
		}
	}
	private void startEditTarget(float ex, float ey) {
		
		
		Animation a;
		for(int i=animationList.size()-1;i>-1;i--){
			a=animationList.get(i);
			if (Math.abs(ex - a.x) < a.getW()
					&& Math.abs(ey - a.y) < a.getH()) {
				editTarget=a;
				if(deleteMuchMode){
					deleteTarget();
					return;
				}
				cloner=a;
				return;//avoid clone
			}
		}
		
		if(!deleteMuchMode)
		if(cloner!=null) {
			for(Animation aa:build8group){
				if (Math.abs(ex - aa.x) < grid/2
					&& Math.abs(ey - aa.y) < grid/2) {
					cloner=newAnimation(aa.x,aa.y);
					aa.setPosition(0, (float) (100*Math.random()));//to let look clearly
					moveCloneMode=true;
					// new cloner is root
					build8CHeck();
					
					break;
				}
			}
		}
	}
	private Animation newAnimation(float x,float y) {
		// TODO Auto-generated method stub
		Animation newAnimation = cloner.clone();
		newAnimation.setStartXY(x, y);
		animationList.add(newAnimation);
		world.addDrawAnimation(newAnimation);
		return newAnimation;
	}
	float alphaClone=1f;
	 float alphaSpeed=0.02f;
	public boolean deleteMuchMode;
	public void drawElement(GL10 gl){
		if(editTarget!=null){
			deleter.setPosition(Render.px+Render.width/2, Render.py+Render.height-deleter.h);
			if(deleteEdgeMode)gl.glColor4f(1	, 0, 0, 1);
			
			deleter.drawTranElement(gl);
		}
		
		Animation cloner=this.cloner;// avoid main thread let editTarget to be null
		if(cloner!=null&&
				cloner.x>Player.gx1&&cloner.x<Player.gx2
				&&cloner.y>Player.gy1&&cloner.y<Player.gy2) {
			alphaClone+=alphaSpeed;
			if(alphaClone<0.4f||alphaClone>1.6f)alphaSpeed=-alphaSpeed;
			
			for(Animation aa:build8group){
					gl.glColor4f(alphaClone, 2*alphaClone, alphaClone, alphaClone);
					gl.glTranslatef(aa.x, aa.y, 0);
					cloner.baseDrawElement(gl);
					gl.glTranslatef(-aa.x, -aa.y, 0);
					gl.glColor4f(1, 1, 1, 1);
				}
		}
	}

}
