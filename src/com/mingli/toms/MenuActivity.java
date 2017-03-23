package com.mingli.toms;

import java.io.File;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.FruitSet;
import Mankind.Player;
import aid.Ad;
import aid.Client;
import aid.Log;
import aid.Producer;
import aid.Shop;
import aid.Tips;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends Activity {


	private SharedPreferences sp;
	private static Context content;
	private static Context myActivity;
	protected static int FPS;//
	public Ad ad;
	private int startTime;

	private StateWindow stateWindow;
	private World world;
	private ButtonController btnc;
	private StartMenu startMenu;
	private GameMenu gameMenu;
	private static MyHandler myHandler;

	private static Dialog dl;

	// public static boolean isGameRunning;

	// private ItemWindow itemWindow;

	private Editor editor;

	public static int screenWidth, screenHeight;


	int score;
	public int chance;
	public int coinCount;

	private Stagechoosser stageChooser;

	int mapIndex;
	int in[];
	String starString = "00000000000000000";
	public int[] star;

	private String itemString = "HT";// a sizeFruit and a Tomato will give to ever player 
	char[] item;
	
	private Shop shop;

	private Tips tips;

	private Producer animationShop;

	String geqian;

	static boolean titleMode=true;



	public void finish() {
		save();
		if (world != null)
			world.onDestroy();
		 stateWindow=null;
		 btnc=null;
		 gameMenu=null;
		 myHandler=null;
		 world=null;
		if(client!=null) client.closeStream();
		super.finish();
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// quanpin
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		// buxiumian
		
		myActivity = this;
		
		
		initNetController();
		
		initWindowSize();

		initSp();
		initGame();
		
		
		initAd();
		getMessage();
		initDialog();
		initTips();
		mapIndex = getMaxMapIndex();
	}

	private void initNetController() {
		client = new Client();
		new Thread(){
			public void run(){
				client.connect();
			}
		}.start();
	}

	private void initTips() {
		// TODO Auto-generated method stub
		tips=new Tips(this);
	}

	void initGame() {

		 if (myHandler == null)
		myHandler = new MyHandler();

		// if (world == null)
		world = new World(this,myHandler);
		addView(world);
		// addContentView(world, new
		// LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	}

	void initStartMenu() {
		
		startMenu = new StartMenu(this);
		startMenu.loadStartMenu();
		
//		if (startTime > 2) {
			showBanner((ViewGroup) findViewById(R.id.container));
//		}
	}

	private void initSp() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("gameStore", MODE_PRIVATE);
		startTime = sp.getInt("startTime", 1);
		Render.tietu = sp.getBoolean("tietu",true);
		
		editor = sp.edit();
		editor.putInt("started", startTime + 1);
		editor.commit();
	}

	private void initAd() {
		// TODO Auto-generated method stub
		ad = new Ad(this);
		ad.loadAppWallAd();
		ad.loadInterstitial();
	}

	private void initWindowSize() {
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		content = getBaseContext();
	}

		class MyHandler extends Handler {


		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (stateWindow != null)
				stateWindow.handleCheck(msg);
			 if(btnc!=null)btnc.handleCheck(msg);
			switch (msg.what) {
			case World.LOADED:
//				if(startMenu==null)// if it is loading game title or loading game
				if(titleMode) {
					initStartMenu();
					 if(stateWindow == null)
						 stateWindow = new StateWindow(MenuActivity.this, world, tips);
				} else {
					loadGameMenu();
				}
				break;
				
			case World.GAMEOVER:
				gameover();
				break;
			case World.SUCCEED:
				succeed();
				break;
			case DIALOG:
				showDialogView();
				break;
			}
		}

		private void gameover() {
			// TODO Auto-generated method stub
			save();
//			gameMenu.removeView();//没啥用
//			showInAd();
//			gameMenu.addView();
			gameMenu.gameover();
		}

		private void succeed() {
			// TODO Auto-generated method stub

			gameMenu.succeed();
			if (getMaxMapIndex() == mapIndex)// only the last stage save
				if(mapIndex<Map.max)editor.putInt("mapIndex", mapIndex + 1);
			editor.commit();
			save();
		}
	}

	void startGame() {
		// setContentView(world);
		if (startMenu != null){
			
			startMenu.hide();
			startMenu=null;
		}
		if (stageChooser != null){
			
			stageChooser.hide();
			stageChooser=null;
		}
//		removeAndAddView(world);
		titleMode=false;
		world.startGame(mapIndex);
		Log.d("startGame");
	}

	void loadGameMenu() {
		 if(stateWindow == null)
			 stateWindow = new StateWindow(this, world, tips);
			 
//		 if (gameMenu == null)
		gameMenu = new GameMenu(this, world);
//		 if(shopView==null)
		shop = new Shop(this, world);
		
		if(World.editMode)
			animationShop = new Producer(this, world, tips);
		
		// if (btnc == null)
		btnc = new ButtonController(MenuActivity.this, world, gameMenu);// 添加按键
		btnc.adController();

		// if (itemWindow == null)
	}

	protected void onDestroy() {
		if (world != null) {
			world.onDestroy();
			world = null;
		}
		super.onDestroy();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (startMenu != null){
				
				showExitDialog();
			}
			else if (stageChooser != null) {

				stageChooser.hide();
				// ////////////////
				loadTitleView();
				// super.onKeyDown(keyCode, event);
			} else if (world != null) {
				if (shop != null){
					shop.hideCheck();
				}
				
				if (animationShop != null){
					animationShop.hideCheck();
				}
				
				if (gameMenu!=null&&gameMenu.isHided()){
					pauseGame();
					gameMenu.showWindow(world);
				}else resumeGame();
				
			}
			return false;
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			showDialog(null, "FPS:" + FPS,0);
//			tietuCheck();
			// if(world!=null)world.succeed();

			// Intent intent=new Intent();
			// intent.setData(Uri.parse("http://www.baidu.com"));

			/*
			 * intent.setAction(Intent.ACTION_DIAL);
			 * intent.setData(Uri.parse("tel:10000")); startActivity(intent);
			 */

			return super.onKeyDown(keyCode, event);
		} else
			return super.onKeyDown(keyCode, event);
	}

//	int index;
//	private void tietuCheck() {
//		// TODO Auto-generated method stub
//		Log.i("gl:"+Render.gl);
//		if(Render.gl==null)return;
//		if(index++%2==0)Render.gl.glEnable(GL10.GL_TEXTURE_2D);
//		else Render.gl.glDisable(GL10.GL_TEXTURE_2D);
//	}

	void initDialog() {
		if (dl != null)
			return;
		dl = new Dialog(MenuActivity.this, R.style.myDialog);
		View v = getLayoutInflater().inflate(R.layout.dialog, null);
		dl.setContentView(v);
		dl.getWindow().setGravity(Gravity.LEFT | Gravity.TOP);

		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dl.cancel();
			}
		});
	}
	private static final int DIALOG = 1110;

	private static String speaker;

	private static String talk;

	private static int resId;
	private Client client;
	String username;

//	public static boolean titleMode;
	
	public static void showDialog(String speaker, String talk, int resId) {	
		MenuActivity.speaker = speaker;
		MenuActivity.talk = talk;
		MenuActivity.resId = resId;
		
		myHandler.sendEmptyMessage(DIALOG);
	}
	
	public static void showDialogView() {	
		
		TextView nameView = (TextView) dl.findViewById(R.id.speakername);
		TextView talkView = (TextView) dl.findViewById(R.id.dialogue);
		ImageView img=(ImageView) dl.findViewById(R.id.speaker);
		if(resId!=0)img.setImageResource(resId);
		Log.i("nameView+speaker " + nameView + speaker, "talkView " + talkView);
		if (speaker == null)
			nameView.setText("神秘人");
		else
			nameView.setText(speaker);
		if (talk == null)
			talkView.setText("...");
		else
			talkView.setText(talk);

		try{
			dl.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}

	private void showExitDialog() {
		showInAd();
		new AlertDialog.Builder(this)
				// 不能用getApplicationContext() 结果同上
				// .setTitle(" ")
				.setMessage("是继续游戏你还是关闭游戏呢？")
				.setPositiveButton("关闭游戏",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface di, int i) {
								if (world != null) {
									// Intent mainMenu=new
									// Intent(MenuActivity.this,MenuActivity.class);
									// startActivity(mainMenu);
									// loadStartMenu();
									world.onDestroy();
//									world = null;
									overridePendingTransition(R.anim.fadein,
											R.anim.fadeout);
//								} else {
									
									finish();
								}
							}
						}).setNegativeButton("继续游戏", null).show();
	}

//	public static void printToast(String str) {
//		Toast.makeText(content, str, Toast.LENGTH_SHORT).show();
//	}

	public void save() {
		editor.putInt("coin", coinCount);
		editor.putInt("chance", chance);
		editor.putInt("score", score);

		starString = "";
		for (int i = 0; i < star.length; i++) {
			starString += star[i];
		}
		editor.putString("starString", starString);
		
		item=FruitSet.getItemArray();
		Log.i("itemString"+new String(item));
		itemString=new String(item);
		editor.putString("itemString",itemString);
		// Log.i("starString"+new String(starString));
		editor.commit();
	}

	private void getMessage() {
		// TODO Auto-generated method stub
		coinCount = sp.getInt("coin", 500);
		chance = sp.getInt("chance", 300);
		score = sp.getInt("score", 0);
		
		starString = sp.getString("starString", starString);
		Log.i("starString"+starString);
		itemString =sp .getString("itemString", itemString);
		star = stringToInts(starString);
		item=itemString.toCharArray();
		username=sp.getString("username", UserName.randomName());
		geqian=sp.getString("geqian", "我是一个伟大的火星猎人");
		saveUserMessage();
	}
	void saveUserMessage(){
		editor.putString("username",username);
		editor.putString("geqian",geqian);
		// Log.i("starString"+new String(starString));
		editor.commit();
	}

	int getMaxMapIndex() {
		return sp.getInt("mapIndex", 1);
	}

	public void reLoadGame() {
		quitGame();
		startGame();
		// sort about mapIndex++ or not
	}

	public void nextStage() {
		quitGame();
		if (getMaxMapIndex() > mapIndex)
			mapIndex++;
		startGame();
	}

	public void startWithIndex(int index) {
		mapIndex = index;
		startGame();
	}

	public void quitGame() {
		// TODO Auto-generated method stub
//		if(stateWindow!=null)stateWindow.hide();
		if(btnc!=null)btnc.hide();
		if (world != null) {
			// world.onDestroy();
			// world = null;
			world.quitGame();
		}
		Log.d("quitGame");
		
		
	}

	void loadTitleView() {
		// TODO Auto-generated method stub
		titleMode=true;
		world.loadTitleView();
	}

	public void resumeGame() {
		// TODO Auto-generated method stub
		if(gameMenu!=null)	gameMenu.isHided();
		if (world != null)
			world.resume();
//		gameMenu.
	}
	protected void onResume(){
		super.onResume();
		if(startMenu!=null||stageChooser!=null)
		 resumeGame();
	}

	public void onPause() {
		super.onPause();
		pauseGame();
		if(startMenu==null&&stageChooser==null)
			if(gameMenu!=null)gameMenu.showWindow(world);
	}
	void pauseGame() {
		// TODO Auto-generated method stub
		if (world != null) {
			world.pauseGame();
		}
	}

	void showShop(View v) {
		if (world != null)
			world.pauseDraw();

		shop.showWindow(v);
	}

	private View getContentView111() {
		ViewGroup view = (ViewGroup) getWindow().getDecorView();
		// content = view.getChildAt(0);
		// return content.getChildAt(0);
		return view;
	}

	public void showAppWall() {
		// TODO Auto-generated method stub
		ad.showAppWallAd();
	}

	public void initStageChooser() {
		// TODO Auto-generated method stub

		if (startMenu != null)
			startMenu.hide();
		startMenu=null;
		// if(stageChooser==null)
		stageChooser = new Stagechoosser(this);
	
	}

	public void setStar(int sum) {
		// TODO Auto-generated method stub
		int id = this.mapIndex - 1;
		if (star[id] < sum) {
			// starString.r
			// starString.indexOf(mapIndex)= sum;
			star[id] = sum;
		}
		String ss = "";
		for (int i = 0; i < star.length; i++) {
			ss += star[i];
		}
		Log.i("Star" + ss);
	}

	public int[] stringToInts(String s) {
		int[] n = new int[s.length()];
		for (int i = 0; i < s.length(); i++) {
			n[i] = Integer.parseInt(s.substring(i, i + 1));
		}
		return n;
	}


	void addView(View child) {
		// TODO Auto-generated method stub
//		group.addView(child);
		this.addContentView(child, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	public void removeView(View view) {
		// TODO Auto-generated method stub
		if (view != null && view.getParent() != null) {
			((ViewGroup) view.getParent()).removeView(view);
//			((ViewGroup) view.getParent()).removeViewAt(index);
//			((ViewGroup) view.getParent()).removeAllViews();
		}
//		addView(view);
	}

	public void showBanner(ViewGroup shopadcontainer ) {
		// TODO Auto-generated method stub
		shopadcontainer.removeAllViews();
		removeView(ad.getBannerview());
		ad.showBanner(shopadcontainer);
		Log.i("show banner");
	}

	public void tietu(int i) {
		// TODO Auto-generated method stub
		if(i==0)
		editor.putBoolean("tietu", false);
		else
		editor.putBoolean("tietu", true);
		editor.commit();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("onActivityResult");
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		   case RESULT_OK:
		    String mapfile=data.getStringExtra("mapfile");//str即为回传的值
		    world.mapFile=new File(mapfile);
		    Log.i("mapfile"+mapfile);
		    
		    startGame();
//		    break;/
//		default:
//		    break;
		    }
		}

	public boolean getLifeFree() {
		// TODO Auto-generated method stub
		if(isNetworkAvailable(this)){
			showInAd();
			return true;
		}else {
			showDialog("网络错误", "请在联网状态下观看广告复活", R.drawable.coinicon);
		}
		if(World.rpgMode){
			btnc.freshItem();
			MenuActivity.showDialog("", "复活蛋已发放", R.drawable.egg);
			FruitSet.pickedList.add(FruitSet.shopList.get(0));
		}
		return false;
	}
	private void showInAd() {
		// TODO Auto-generated method stub
		ad.showInterstitial();
	}

	public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (cm == null) {   
        } else {
	//如果仅仅是用来判断网络连接
	 //则可以使用 cm.getActiveNetworkInfo().isAvailable();  
            NetworkInfo[] info = cm.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    }
                }
            }
        }
		return false;
	}

	public void showAnimationShop(View v) {
		// TODO Auto-generated method stub
		animationShop.showWindow(v);
	}



	public void adShow() {
		// TODO Auto-generated method stub
		int count=1;
		coinCount+=count;
		chance+=count;
//		showDialog("显示了广告", "金币+"+count+" 生命能量+"+count, R.drawable.coinicon);
		myHandler.sendEmptyMessage(World.COIN);
		myHandler.sendEmptyMessage(World.CHANCE);
	}

	public void adClicked() {
		// TODO Auto-generated method stub
		int count=10;
		coinCount+=count;
		chance+=count;
//		showDialog("点击了广告", "金币+"+count+" 生命能量+"+count, R.drawable.egg);
		myHandler.sendEmptyMessage(World.COIN);
		myHandler.sendEmptyMessage(World.CHANCE);
	}
	
}
