package com.mingli.toms;

import Enviroments.FruitSet;
import Mankind.Player;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/10.
 */

public class GameMenu {
	private PopupWindow popupWindow;
	MenuActivity acti;
	private PopWindowTouchEvent touchEvent;
	private RatingBar speed;
	private RatingBar coin;
	private RatingBar fruit;
	private RatingBar summarize;
	private World world;

	View rateGrid;
	View resume, next;
//	private boolean starShowable;
	private int sum;
	private TextView gameTitle;
	private ViewGroup gameMenuAdContainer;
	private LayoutParams lp;
	Player player;
	private View getLifeAbou;
	
	GameMenu(MenuActivity acti, World world) {
		this.acti = acti;
		this.world = world;
		this.touchEvent = new PopWindowTouchEvent((MenuActivity) acti);
		player=world.player;
	}

	public void showWindow(View v) {
		long tim = System.currentTimeMillis();
		if (popupWindow != null && popupWindow.isShowing())
			return;// 防止重新出发
		
		// 获取自定义布局文件activity_popupwindow_left.xml的视图
		else if (popupWindow == null) {
			popupWindow_view = acti.getLayoutInflater().inflate(
					R.layout.gamemenu, null);
			// 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
			popupWindow = new PopupWindow(popupWindow_view,
					WindowManager.LayoutParams.MATCH_PARENT,
					WindowManager.LayoutParams.MATCH_PARENT, true);
			popupWindow.setAnimationStyle(R.style.AnimationFade);

			gameMenuAdContainer=(ViewGroup) popupWindow_view.findViewById(R.id.gamemenuadcontainer);
			
			touchEvent.findView(popupWindow_view);// 寻找自定义事件的
			findView(popupWindow_view);
			rateGrid=popupWindow_view.findViewById(R.id.ratinggrid);
			rateGrid.setVisibility(View.INVISIBLE);
			lp=rateGrid.getLayoutParams();
			lp.height=30;
			
			
			View.OnTouchListener otl = new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					touchEvent.touchAction(v, event);
					isHided();
					return false;
				}
			};
//			popupWindow_view.setOnTouchListener(otl);
			/////////////
		}
		// 这里是位置显示方式,在屏幕的左侧
		
		
		acti.showBanner(gameMenuAdContainer);
		popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
//		popupWindow.update();
		
		
		if(!player.isDead&&!player.isGotGoal())normalMenu();
		
		Log.i("popTime", "" + (System.currentTimeMillis() - tim));
		
//		showStar();
	}

	private void findView(View v) {
		// TODO Auto-generated method stub
		speed = (RatingBar) v.findViewById(R.id.ratingBar1);
		coin = (RatingBar) v.findViewById(R.id.ratingBar2);
		fruit = (RatingBar) v.findViewById(R.id.ratingBar3);
		summarize = (RatingBar) v.findViewById(R.id.ratingBar4);
		
		gameTitle=(TextView)v.findViewById(R.id.gameMenuTitle);
		
		gameTitle.setText("第"+acti.mapIndex+"关");
		
		getLifeAbou=v.findViewById(R.id.getLifeabout);
		
		
	}

	void showStar() {
//		if(!starShowable)return;
		lp.height=LayoutParams.WRAP_CONTENT;
		
		Log.i("LayoutParams.WRAP_CONTENT"+LayoutParams.WRAP_CONTENT);
		rateGrid.setVisibility(View.VISIBLE);
//		world.culStar();
		speed.setProgress(world.getSpeedStar());
		// speed.setRating(world.getSpeedStar());
		coin.setProgress(world.getCoinStar());
		
		
		fruit.setProgress(world.getItemStar());
		sum=(world.getSpeedStar() + world.getCoinStar() + world
				.getItemStar()) / 2;
		acti.setStar(sum);
		summarize.setProgress(sum);
		summarize.setVisibility(View.INVISIBLE);
		initAnimation();
	}

	private void initAnimation() {
		Animation a=new ScaleAnimation(0, 1, 1, 1);
		a.setDuration(500);
		speed.setAnimation(a);
		coin.setAnimation(a);
		fruit.setAnimation(a);
		Log.i("coin.getAnimation()"+coin.getAnimation());
		a.start();
		a.setAnimationListener(listener);
	}
	void initSummaryAnimation(){
		summarize.setVisibility(View.VISIBLE);
		Animation b=new ScaleAnimation(0, 1, 1,1);
		b.setDuration(500);
		summarize.setAnimation(b);
		b.start();
	}
	AnimationListener listener=new AnimationListener(){

		@Override
		public void onAnimationEnd(Animation a) {
			initSummaryAnimation();
		}

		@Override
		public void onAnimationRepeat(Animation a) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation a) {
			// TODO Auto-generated method stub
			
		}
		
	};
//	private Animation a;
	private View popupWindow_view;

	boolean isHided() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			return false;
		}
		return true;
	}

	class PopWindowTouchEvent {

		// private Activity acti;
		private MenuActivity menuActivity;
		// private View v;
		// private MotionEvent e;
		CheckBox ex, music;

		PopWindowTouchEvent(MenuActivity acti) {
			// this.acti = acti;
			this.menuActivity = acti;

		}

		void findView(View view) {
			view.findViewById(R.id.tomenu).setOnTouchListener(otl);
			(resume = view.findViewById(R.id.resume)).setOnTouchListener(otl);
			view.findViewById(R.id.retry).setOnTouchListener(otl);
			(next = view.findViewById(R.id.next)).setOnTouchListener(otl);
			 next.setVisibility(View.INVISIBLE);
			ex = (CheckBox) view.findViewById(R.id.ex);
			ex.setOnTouchListener(otl);
			music = (CheckBox) view.findViewById(R.id.music);
			music.setOnTouchListener(otl);
			
			
			Button save = (Button) view.findViewById(R.id.savemap);
			save.setOnTouchListener(otl);
			Button buyLife = (Button) view.findViewById(R.id.buyLife);
			buyLife.setOnTouchListener(otl);
			
			
			(view.findViewById(R.id.backtostagechoosser)).setOnTouchListener(otl);
			(view.findViewById(R.id.getLifeFree)).setOnTouchListener(otl);
			
			if(!World.editMode){
				save.setVisibility(View.INVISIBLE);
			}
		}

		;

		private View.OnTouchListener otl = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				touchAction(v, event);
				return false;
			}
		};

		public void touchAction(View v, MotionEvent e) {
			// this.v = v;
			// this.e = e;
			if (e.getAction() == MotionEvent.ACTION_DOWN
					|| e.getAction() == MotionEvent.ACTION_POINTER_DOWN)
				if(v.getId()!=R.id.getLifeFree)acti.ad.hideInterstitial();
				switch (v.getId()) {
				case R.id.tomenu:
					menuActivity.quitGame();
					menuActivity.loadTitleView(0);
					// viewList.get(0).setBackgroundResource(R.drawable.tomenu);
					break;
				case R.id.resume:
					// viewList.get(1).setBackgroundResource(R.drawable.back);
					menuActivity.resumeGame();
					break;
				case R.id.backtostagechoosser:
					// viewList.get(1).setBackgroundResource(R.drawable.back);
					menuActivity.quitGame();
					menuActivity.initStageChooser();
					break;
				case R.id.getLifeFree:
					// viewList.get(1).setBackgroundResource(R.drawable.back);
					
					if(menuActivity.getLifeFree()){
						player.reLife();
					}
					break;
				case R.id.savemap:
					world.saveMap();
					break;
				case R.id.buyLife:
					acti.showShop(world);
					break;
				case R.id.retry:
					// viewList.get(2).setBackgroundResource(R.drawable.retry);
					menuActivity.reLoadGame();
					break;
				case R.id.next:
					// viewList.get(3).setBackgroundResource(R.drawable.next);
					menuActivity.nextStage();
					break;
				case R.id.ex:
					if (v == ex) {
						if (ex.isChecked())
							World.music.noEx();
						else
							World.music.hasEx();
					}
					break;
				case R.id.music:
					if (v == music) {
						if (music.isChecked())
							World.music.noBgm();
						else
							World.music.hasBgm();
					}
					break;
				}
			boolean h = isHided();
			if(!h)acti.resumeGame();
		}
	}

	public void normalMenu() {
		// TODO Auto-generated method stub
//		starShowable=false;
		showWindow(world);
		resume.setVisibility(View.VISIBLE);
		getLifeAbou.setVisibility(View.INVISIBLE);
		gameTitle.setText("第"+acti.mapIndex+"关");
		lp.height=0;
	}
	
	public void gameover() {
		// TODO Auto-generated method stub
//		starShowable=false;
		showWindow(world);
		if(world.gameTime==0)gameTitle.setText("时间结束！");
		else gameTitle.setText("体力耗尽！");
		resume.setVisibility(View.INVISIBLE);
		next.setVisibility(View.INVISIBLE);
		getLifeAbou.setVisibility(View.VISIBLE);
		lp.height=0;
	}

	public void succeed() {
		// TODO Auto-generated method stub
		
//		starShowable=true;
		showWindow(world);
		gameTitle.setText("成功过关！");
		resume.setVisibility(View.INVISIBLE);
		next.setVisibility(View.VISIBLE);
		getLifeAbou.setVisibility(View.INVISIBLE);
		
		showStar();
	}
	void removeView(){
		acti.removeView(popupWindow_view);
	}
	void addView(){
		acti.addView(popupWindow_view);
	}
}
