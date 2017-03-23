package com.mingli.toms;

import element2.FireworkSet;
import element2.Tail;
import Element.BubbleSet;
import Element.Curtain;
import Element.LightSpotSet;
import Mankind.Player;
import Weapon.AutoBullet;
import Weapon.Gun;
import aid.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


public class Touch implements OnTouchListener {
	int ad, au, ad1, au1;
	float ex, ey, ex1, ey1;
	float ux, uy, ux1, uy1;
	private float ex2;
	private float ey2;
	private LightSpotSet lightSpotSet;
	private Player player;
	private Tail tail;

	public Touch( Tail tail, Player player) {
		this(  player);
		this.tail = tail;
	}
//	private Gun gun;
	private Curtain ct;
	private World world;
	private BubbleSet fireSet;
	private  FireworkSet fireWorkSet;

	public Touch( LightSpotSet lightSpotSet, Player player){
//		this.bts = bts;
		this(player);
		this.lightSpotSet = lightSpotSet;
	}
	public Touch(Player player) {
		// TODO Auto-generated constructor stub
		this.player = player;
		
	}




	public Touch( Curtain ct, Player player2) {
		// TODO Auto-generated constructor stub
		this(player2);
		this.ct=ct;
	}
	public Touch( Tail tail2,World world,
			Player player2) {
		this(  tail2, player2);
		this.world = world;
		
		// TODO Auto-generated constructor stub
	}
	public Touch( AutoBullet ab2, BubbleSet fireSet, Player player2) {
		// TODO Auto-generated constructor stub
		this(player2);
		this.fireSet=fireSet;
	}
	public Touch( FireworkSet fireWorkSet,
			Player player2) {
		this(player2);
		// TODO Auto-generated constructor stub
		this.fireWorkSet = fireWorkSet;
	}
/*	public Touch(ButtonSet bts,  AutoBullet ab2, Tail tail2,
			Player player2) {
		this(ab2,tail2,player2);
		this.bts=bts;
		// TODO Auto-generated constructor stub
	}*/
	public boolean onTouch(View v, MotionEvent e) {
		if(lightSpotSet!=null)lightSpotSet.tringer(e.getX(), MenuActivity.screenHeight - e.getY());
		switch (e.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			ex = e.getX();
			ey = MenuActivity.screenHeight - e.getY();
			ex=Render.rate*ex;ey=ey*Render.rate;
//			if ((ad = bts.buttonCheckDown(ex, ey,e.getDownTime())) == 4)
//				menu.reaction();
			
			
//			if(player.gun!=null)player.gun.gunCheck(ex, ey);
			if(fireSet!=null)
				fireSet.tringer(Render.px+ex,Render.py+ey,1);
			if(tail!=null){
				tail.startTouch(Render.px+ex,Render.py+ey);
//					
			}
			if(world!=null)world.astarSearch(Render.px+ex,Render.py+ey);
////			else
////				player.actionCheckDown(ad);
			if(fireWorkSet!=null)
				fireWorkSet.tringer(Render.px+ex,Render.py+ey,15,20,fireWorkSet.getCount());
			
//			
//			if(player!=null){
//				calcuPlayerSpeed(ex,ey);
//			}
			
			
			break;
		case MotionEvent.ACTION_UP:
			Log.i("Touch.ACTION_Up");
			
		
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			ex = e.getX(0);
			ey = MenuActivity.screenHeight - e.getY(0);
			ex=Render.rate*ex;ey=ey*Render.rate;
			ex1 = e.getX(1);
			ey1 = MenuActivity.screenHeight - e.getY(1);
			ex2=Render.rate*ex2;ey2=ey2*Render.rate;
			
			
//			if(gun!=null)gun.gunCheck(ex, ey);
			if(player.gun!=null)player.gun.gunCheck(ex1, ey1);
			
			if(tail!=null){
				tail.startTouch(Render.px+ex,Render.py+ey);
			}
			if(tail!=null){
				tail.startTouch(Render.px+ex1,Render.py+ey1);
			}
			
			
			
//			if ((ad = bts.buttonCheckDown(ex, ey,e.getDownTime())) == 4)
//				menu.reaction();
////			else player.tringerCheck(ex, ey);
////			else
////				player.actionCheckDown(ad);
//
//			if ((ad1 = bts.buttonCheckDown(ex1, ey1,e.getDownTime())) == 4)
//				menu.reaction();
////			else player.tringerCheck(ex1, ey1);
////			else
////				player.actionCheckDown(ad1);
//			break;
		case MotionEvent.ACTION_POINTER_UP:
//			if (e.getActionIndex() == 0) {
//				ux = e.getX();
//				uy = MenuActivity.screenHeight - e.getY();
//				au = bts.buttonCheckUp(ux, uy);
////				player.actionCheckUp(au);
//			} else {
//				ux1 = e.getX(1);
//				uy1 = MenuActivity.screenHeight - e.getY(1);
//				au1 = bts.buttonCheckUp(ux1, uy1);
////				player.actionCheckUp(au1);
//			}
//
			break;
		case MotionEvent.ACTION_MOVE:
			ex2 = e.getX(0);
			ey2 = MenuActivity.screenHeight - e.getY(0);
			ex2=Render.rate*ex2;ey2=ey2*Render.rate;
			if(tail!=null)
				tail.tringer(Render.px+ex2+20,Render.py+ey2);
			break;
		}
		return true;
	}

//	private int fingerCount;
};