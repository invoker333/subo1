package com.mingli.toms;

import Mankind.Player;
import aid.Circle;
import aid.CircleSurface;
import aid.MyMoveView;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SlidingDrawer;

/**
 * Created by Administrator on 2016/7/13.
 */
@SuppressLint("NewApi")
public class ButtonController {
	private MenuActivity acti;
	private World world;
	private GameMenu gameMenu;
	// private View.OnTouchListener ontl;

	private ItemWindow itemWindow;
	private CircleSurface circleSurface;
	private Player player;
	private Drawable ob;

	ButtonController(Activity acti, World world, GameMenu gameMenu) {
		this.acti = (MenuActivity) acti;
		this.world = world;
		this.gameMenu = gameMenu;
		player=world.player;
	}
	void freshItem(){
		if(itemWindow!=null)itemWindow.freshItem();
	}

	public void adController() {
		if (buttonView == null) {
			buttonView = (RelativeLayout) acti.getLayoutInflater().inflate(
					R.layout.buttonset, null);
		itemWindow = new ItemWindow(acti,(SlidingDrawer) buttonView	.findViewById(R.id.slidingDrawer1), world);
		if(!World.rpgMode)itemWindow.hide();
			
		menu = (View) buttonView.findViewById(R.id.pause);
//			View left = buttonView.findViewById(R.id.left);
//			View right = (View) buttonView.findViewById(R.id.right);
			SeekBar directionSeekBar = (SeekBar) buttonView
					.findViewById(R.id.directionSeekBar);
			ride = buttonView.findViewById(R.id.ride);
			View shopButton = buttonView.findViewById(R.id.shopbutton);
			View buildButton = buttonView.findViewById(R.id.buildButton);
			jumpSeekBar = (mySeekBar) buttonView
					.findViewById(R.id.jumpSeekbar1);
			attack = (View) buttonView.findViewById(R.id.attack);
			circle = (Circle) buttonView.findViewById(R.id.circle1);
			
			if(Player.extendsBladeFruitId==-1)attack.setVisibility(View.INVISIBLE);
			if(Player.extendsGunFruitId==-1)circle.setVisibility(View.INVISIBLE);
			
			circle.player = world.player;
			circleSurface = (CircleSurface) buttonView
					.findViewById(R.id.circlesurface1);
			circleSurface.player = world.player;
			// circle.setOnTouchListener(ontl);
			if(world.editMode){
				directionSeekBar.setOnSeekBarChangeListener(editSeekListener);
				jumpSeekBar.setOnSeekBarChangeListener(editSeekListener);
			}else {
				directionSeekBar.setOnSeekBarChangeListener(seekListener);
				jumpSeekBar.setOnSeekBarChangeListener(seekListener);
			}
			
			ontl = new ClickGame();
			jumpSeekBar.setOnTouchListener(ontl);
//			left.setOnTouchListener(ontl);
//			right.setOnTouchListener(ontl);
			attack.setOnTouchListener(ontl);
			ride.setOnTouchListener(ontl);
			shopButton.setOnTouchListener(ontl);
			buildButton.setOnTouchListener(ontl);
			
			eraser = (ImageButton) buttonView.findViewById(R.id.eraser);
			if(!World.editMode)eraser.setVisibility(View.INVISIBLE);
			eraser.setOnTouchListener(ontl);
			ob=eraser.getBackground();
			if(!World.editMode)buildButton.setVisibility(View.INVISIBLE);
			// itembutton.setOnTouchListener(ontl);
			menu.setOnTouchListener(ontl);

			// world.setOnTouchListener(ontl);
			MyMoveView v=(MyMoveView) buttonView.findViewById(R.id.maskview);
			v.tm=world.touchMove;
		}
		acti.getWindow().addContentView(
				buttonView,
				new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
						ActionBar.LayoutParams.MATCH_PARENT));

		if(itemWindow!=null)itemWindow.initWindow();
	}

	/*
	 * private void showItemMenu() { // TODO Auto-generated method stub
	 * itemMenu.showWindow(); }
	 */
	/*
	 * public void showGameMenu() { // world.pause(); gameMenu.showWindow(menu);
	 * }
	 */

	/*
	 * public void handleCheck(Message msg) { if (msg.what == World.SUCCEED)
	 * showGameMenu(); }
	 */

	class ClickGame implements OnTouchListener {

		@Override
		// touch.onTouch(v, event);
		
		
		public boolean onTouch(View v, MotionEvent event) {
			world.playerMoveIndex=0;

			if(itemWindow!=null)itemWindow.closeDrawer();
			// if(true)return true;
			switch (v.getId()) {
//			case R.id.left:
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					player.setLeftData(true);
//					v.setBackgroundResource(R.drawable.button_left);
//				} else if (event.getAction() == MotionEvent.ACTION_UP) {
//					Player.downData[0] = false;
//					v.setBackgroundResource(R.drawable.button_left1);
//				}
//				break;
//			case R.id.right:
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					player.setRightData(true);
//					v.setBackgroundResource(R.drawable.button_right);
//				} else if (event.getAction() == MotionEvent.ACTION_UP) {
//					Player.downData[1] = false;
//					v.setBackgroundResource(R.drawable.button_right1);
//				}
//				break;
			case R.id.jumpSeekbar1:
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					player.downData[3] = true;
				} else 
					if (event.getAction() == MotionEvent.ACTION_UP) {
					player.downData[3] = false;
//					((ProgressBar) acti.findViewById(R.id.jumpSeekbar1)).setProgress(75);
				}
				break;
			case R.id.attack:
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					player.downData[2] = true;
					v.setBackgroundResource(R.drawable.button_attack1);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					player.downData[2] = false;
					v.setBackgroundResource(R.drawable.button_attack);
				}
				break;
			case R.id.pause:
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.drawable.pause);
					acti.pauseGame();
					gameMenu.showWindow(v);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.setBackgroundResource(R.drawable.pause1);
				}
				break;
			case R.id.ride:

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					player.downData[6] = true;
					v.setBackgroundResource(R.drawable.ride2);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// Player.downData[6]=false;
					v.setBackgroundResource(R.drawable.ride);
				}
				break;
			/*
			 * case R.id.itembutton: if (event.getAction() ==
			 * MotionEvent.ACTION_DOWN) { //
			 * v.setBackgroundResource(R.drawable.pause1); // world.pause();
			 * showItemMenu(); } else if (event.getAction() ==
			 * MotionEvent.ACTION_UP) { //
			 * v.setBackgroundResource(R.drawable.pause); } break;
			 */
			case R.id.shopbutton:

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					acti.showShop(v);
				}
				break;
			case R.id.buildButton:

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if(World.editMode)acti.showAnimationShop(v);
				}
				break;
			case R.id.eraser:
				TouchMove tm = world.touchMove;
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if(tm.deleteMuchMode){
						tm.deleteMuchMode=false;
						eraser.setBackground(ob);
					}
					else {
							tm.deleteMuchMode=true;
							eraser.setBackgroundResource(R.drawable.greenrect);
					}
				}
				break;
			/*
			 * case R.id.circle1: if (event.getAction() ==
			 * MotionEvent.ACTION_DOWN) { Player.GunAngle=(float)
			 * circle.getAngle(); } else if (event.getAction() ==
			 * MotionEvent.ACTION_UP) { Player.GunAngle=4;// -3.14----3.14 }
			 * break;
			 */
			}
			// if(touch!=null)
			// if(v==world)
			// if(v.getId()==R.id.maskview)
			// touch.onTouch(v, event);
			return false;
			
		}
		// // TODO Auto-generated method stub
		//
		// }
	};
	private SeekBar.OnSeekBarChangeListener editSeekListener = new SeekBar.OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			switch (seekBar.getId()) {
			case R.id.jumpSeekbar1:
				player.py=progress/100f*(player.getGra().getMapHeight()*player.getGra().getGrid()-Render.height);
				break;
			case R.id.directionSeekBar:
				player.px=progress/100f*(player.getGra().getMapWidth()*player.getGra().getGrid()-Render.width);
			}

		}
	};
	private SeekBar.OnSeekBarChangeListener seekListener = new SeekBar.OnSeekBarChangeListener() {

		private int agoProgress;

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			world.playerMoveIndex=0;
			// TODO Auto-generated method stub
			switch (seekBar.getId()) {
			case R.id.jumpSeekbar1:
				player.downData[3] = false;
				seekBar.setProgress(75);
				break;
			case R.id.directionSeekBar:
				player.downData[0]=false;
				player.downData[1]=false;
				player.downData[7]=false;
				agoProgress=50;
				seekBar.setProgress(50);
				break;
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			if(seekBar.getId()==R.id.jumpSeekbar1)player.downData[3] = true;
			
			else if(seekBar.getId()==R.id.directionSeekBar){
//				player.doubleDownCheck();
			} 
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			switch (seekBar.getId()) {
			case R.id.jumpSeekbar1:
				player.downData[3] = true;
				Player.jumpProgress=progress;
				
				break;
			case R.id.directionSeekBar:
				slideCheck(progress);
				if (progress < 50) {
					player.downData[0] = true;
					player.downData[1] = false;
				}else if (progress > 50) {
					player.downData[1] = true;
					player.downData[0] = false;
				}
				break;
			}

		}

		private void slideCheck(int progress) {
			// TODO Auto-generated method stub
			if(agoProgress!=50
					&&agoProgress!=50
							&&Math.abs(progress-agoProgress)>2) {
				//				doubleClicked=true;
				player.downData[7]=true;
			}
				
			agoProgress=progress;
		}
	};
	public View.OnTouchListener ontl;
	private View menu;
	RelativeLayout buttonView;
	private Circle circle;
	private View attack;
	private View ride;
	private mySeekBar jumpSeekBar;
	private ImageButton eraser;

	public void hide() {
		// TODO Auto-generated method stub
		acti.removeView(buttonView);
	}
	public void handleCheck(Message msg) {
		// TODO Auto-generated method stub
		switch(msg.what){
		case World.BLADEICON:
			attack.setVisibility(View.VISIBLE);
			break;
		case World.NOBLADEICON:attack.setVisibility(View.INVISIBLE);break;
		case World.GUNICON:circle.setVisibility(View.VISIBLE);break;
		case World.NOGUNICON:circle.setVisibility(View.INVISIBLE);break;
		case World.TREADICON:ride.setVisibility(View.VISIBLE);break;
		case World.NOTREADICON:ride.setVisibility(View.INVISIBLE);break;
		case World.JUMP:
//			jumpSeekBar.setSecondaryProgress((int) (Player.curJumpProgress));
		}
	}
}
