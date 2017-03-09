package com.mingli.toms;

import Clothes.Circle;
import Clothes.CircleSurface;
import Clothes.Shop;
import Mankind.Player;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SlidingDrawer;

/**
 * Created by Administrator on 2016/7/13.
 */
public class ButtonController {
	private MenuActivity acti;
	private World world;
	private GameMenu gameMenu;
	// private View.OnTouchListener ontl;

	Touch touch;
	private ItemWindow itemWindow;
	private CircleSurface circleSurface;
	private Player player;

	ButtonController(Activity acti, World world, GameMenu gameMenu) {
		this.acti = (MenuActivity) acti;
		this.world = world;
		this.gameMenu = gameMenu;
		player=world.player;
		touch = world.touch;
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
			// View itembutton=acti.findViewById(R.id.itembutton);
			mySeekBar jumpSeekBar = (mySeekBar) buttonView
					.findViewById(R.id.jumpSeekbar1);
			attack = (View) buttonView.findViewById(R.id.attack);
			circle = (Circle) buttonView.findViewById(R.id.circle1);
			
			if(Player.bladeFruitId==-1)attack.setVisibility(View.INVISIBLE);
			if(Player.gunFruitId==-1)circle.setVisibility(View.INVISIBLE);
			
			circle.player = world.player;
			circleSurface = (CircleSurface) buttonView
					.findViewById(R.id.circlesurface1);
			circleSurface.player = world.player;
			// circle.setOnTouchListener(ontl);
			directionSeekBar.setOnSeekBarChangeListener(seekListener);
			jumpSeekBar.setOnSeekBarChangeListener(seekListener);
			ontl = new ClickGame();
			jumpSeekBar.setOnTouchListener(ontl);
//			left.setOnTouchListener(ontl);
//			right.setOnTouchListener(ontl);
			attack.setOnTouchListener(ontl);
			ride.setOnTouchListener(ontl);
			shopButton.setOnTouchListener(ontl);
			buildButton.setOnTouchListener(ontl);
			if(!World.editMode)buildButton.setVisibility(View.INVISIBLE);
			// itembutton.setOnTouchListener(ontl);
			menu.setOnTouchListener(ontl);

			// world.setOnTouchListener(ontl);

			View blank = buttonView.findViewById(R.id.maskview);
			blank.setOnTouchListener(ontl);
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
			case R.id.attack:
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Player.downData[2] = true;
					v.setBackgroundResource(R.drawable.button_attack1);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					Player.downData[2] = false;
					v.setBackgroundResource(R.drawable.button_attack);
				}
				break;
			case R.id.jumpSeekbar1:
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Player.downData[3] = true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					Player.downData[3] = false;
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
					Player.downData[6] = true;
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
			case R.id.maskview:// 防止触碰其他按键 导致x,y 不正常
				if (touch != null)
					touch.onTouch(v, event);
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

	private SeekBar.OnSeekBarChangeListener seekListener = new SeekBar.OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			world.playerMoveIndex=0;
			// TODO Auto-generated method stub
			switch (seekBar.getId()) {
			case R.id.jumpSeekbar1:
				Player.downData[3] = false;
				break;
			case R.id.directionSeekBar:
				Player.downData[0]=false;
				Player.downData[1]=false;
				seekBar.setProgress(50);
				break;
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			if(seekBar.getId()==R.id.jumpSeekbar1)Player.downData[3] = true;
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			switch (seekBar.getId()) {
			case R.id.jumpSeekbar1:

				Player.downData[3] = true;
				float rate = (progress + 25) / 100f;
				Player.jumpRate = rate < 1 ? rate : 1;
				break;
			case R.id.directionSeekBar:
				if (progress < 50) {
					player.setLeftData(true);
					Player.downData[1] = false;
				}else if (progress > 50) {
					player.setRightData(true);
					Player.downData[0] = false;
				}
				break;
			}

		}
	};
	public View.OnTouchListener ontl;
	private View menu;
	RelativeLayout buttonView;
	private Circle circle;
	private View attack;
	private View ride;

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
		}
	}
}
