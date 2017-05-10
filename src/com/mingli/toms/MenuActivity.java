package com.mingli.toms;

import java.io.File;
import java.util.ArrayList;

import onlineStageActivity.OnlineFileActivity;
import Enviroments.FruitSet;
import Mankind.BattleMan;
import aid.Ad;
import aid.Client;
import aid.ConsWhenConnecting;
import aid.Log;
import aid.Producer;
import aid.RandomMap;
//import aid.RandomMap;
import aid.Shop;
import aid.Tips;
import aid.UserName;
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
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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


	public int score;
	public int chance;
	public int coinCount;

	private Stagechoosser stageChooser;

	int mapIndex;
	int in[];
	String starString = "00000000000000000";
	public int[] star;
	public static int userId=-1;

	private String itemString = "HT";// a sizeFruit and a Tomato will give to ever player 
	char[] item;
	
	private Shop shop;

	private Tips tips;

	private Producer animationShop;


	static boolean titleMode=true;


	protected void onDestroy() {
//		System.exit(0);
		finish();//you zhe ju zhi jie bu chongxin create
		super.onDestroy();
	}
	public void finish() {
			if (world != null) {
				world.onDestroy();
				world = null;
			}	
			titleMode=true;
//			saveUserMessage();
			editor.putBoolean("openMode", World.openMode);
			editor.commit();
			 stateWindow=null;
			 btnc=null;
			 gameMenu=null;
			 myHandler=null;
			
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
		
		
		
		initWindowSize();

		initSp();
		initGame();
		
		
		initAd();
		getMessage();
		initDialog();
		initTips();
		
		initNetController();// must after getMessage();
		
		mapIndex = getMaxMapIndex();
	}

	private void initNetController() {
		if(client==null)client = new Client(MenuActivity.this);
		new Thread(){
			public void run(){
				while(userName==null||userName==""||userId<0){
					World.timeRush(1000);
				}
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
		
		if(client!=null)Client.send(ConsWhenConnecting.REQUEST_PIMING_INFO+userId);
		getUserId();
		initPaimingInfo();
		
		if (startTime > 2) {
			showBanner((ViewGroup) findViewById(R.id.container));
		}
	}

	private void getUserId() {
		// TODO Auto-generated method stub
		if(MenuActivity.userId<10)
			Client.send(ConsWhenConnecting.REQUEST_NEW_USER_ID+userName+" "+score);
	}

	private void initSp() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("gameStore", MODE_PRIVATE);
		startTime = sp.getInt("startTime", 1);
		
		editor = sp.edit();
		editor.putInt("startTime", startTime + 1);
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
			 
			 if(startMenu!=null)startMenu.handleCheck(msg);
			 
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
			case World.DIALOG:
				showDialogView();
				break;
			case World.REQUEST_TO_STARTGAME:
				startGame();
				break;
			}
		}

		private void gameover() {
			// TODO Auto-generated method stub
		
//			gameMenu.removeView();//没啥用
//			showInAd();
//			gameMenu.addView();
			gameMenu.gameover();
		}

		private void succeed() {
			// TODO Auto-generated method stub

			gameMenu.succeed();
			if(world.isOutMapResource())return;
			if (getMaxMapIndex() == mapIndex)// only the last stage save
				if(mapIndex<Map.max){
					editor.putInt("mapIndex", mapIndex + 1);
					editor.commit();
				}
		}
		
	}

	void startGame() {
		if (startMenu != null){
			startMenu.hide();
			startMenu=null;
		}
		if (stageChooser != null){
			stageChooser.hide();
			stageChooser=null;
		}
		
		titleMode=false;
		
		world.startGame(mapIndex);
		if(mapIndex==Map.max+1){
			World.openMode=true;
		}
		
		Log.d("startGame");
	}

	void loadGameMenu() {
		 if(stateWindow == null)
			 stateWindow = new StateWindow(this, world, tips);
			 
//		 if (gameMenu == null)
		 
		 if(gameMenu!=null)gameMenu.returnCurStateAndHide();
		 
		gameMenu = new GameMenu(this, world);
//		 if(shopView==null)
		shop = new Shop(this, world);
		
		if(World.editMode)
			animationShop = new Producer(this, world);
		
		 if (btnc != null)btnc.hide();
		btnc = new ButtonController(MenuActivity.this, world, gameMenu);// 添加按键
		btnc.adController();

		// if (itemWindow == null)
		if(mapIndex==1)showGuideDialog();
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
				
				if (gameMenu!=null&&gameMenu.returnCurStateAndHide()){
					pauseGame();
					gameMenu.showWindow(world);
				}else {
					if (world != null)
						world.resume();
				}
				
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

	private static String speaker;

	private static String talk;

	private static int resId;
	private static int direction1;
	private static int direction2;
	private Client client;
	public String userName;
	public ArrayList<Info4> userInfoList=new ArrayList<Info4>();
	private String paimingString;
	String selectedToSaveOnlineFileName="";
	private final String requestFoItemMap = "requestFoItemMap.txt";
	private RandomMap randomMap;
	static BattleActivity battleActi;
	public void initPaimingInfo(){
		paimingString=sp.getString("paimingString", "00000"+userName+" "+score+"1");
		
		String[] sa = paimingString.split(" ");
		userInfoList.clear();
		try{
			for(int i=0;i<sa.length;i+=3){
//				userInfoList.add(new Info4(Integer.parseInt(sa[i]),sa[i+1],Integer.parseInt(sa[i+2]),Integer.parseInt(sa[i+3])));
				userInfoList.add(new Info4(0,sa[i],Integer.parseInt(sa[i+1]),Integer.parseInt(sa[i+2])));
				// default id is 0
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		myHandler.sendEmptyMessage(World.NOTIFY_PAIMING_CAHNGED);
	}
	
	
//	Info3 inf;
//	for(int i=0;i<10;i++){
//		userInfoList.add(inf=new Info3(i+1,userName.randomName(),(int)(score*Math.random()+0.5)));
//	}
//	userInfoList.set(5, new Info3(6,userName,score));
	
	public void savePaiming(String paimingString) {
		this.paimingString = paimingString;
		// TODO Auto-generated method stub
		
		editor.putString("paimingString", paimingString);
		editor.commit();
	}

//	public static boolean titleMode;
	
	public static void showDialog(String speaker, String talk, int resId,int direction1,int direction2) {	
		MenuActivity.direction1=direction1;
		MenuActivity.direction2=direction2;
		MenuActivity.speaker = speaker;
		MenuActivity.talk = talk;
		MenuActivity.resId = resId;
		
		myHandler.sendEmptyMessage(World.DIALOG);
		
	}
	
	public static void showDialog(String speaker, String talk, int resId) {	
		showDialog(speaker, talk, resId, Gravity.TOP,Gravity.CENTER_HORIZONTAL);
	}
	public void showGuideDialog(){
		final Dialog d=new Dialog(this,R.style.toumingDialog);
		View v = getLayoutInflater().inflate(R.layout.osguidelayout, null);
		v.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				d.cancel();
				return false;
			}
		});
		ViewGroup.LayoutParams layoutParams = new  ViewGroup.LayoutParams(screenWidth, screenHeight);
		d.setContentView(v,layoutParams);
		d.show();
	}
	
	public static void showDialogView() {	
		dl.getWindow().setGravity(direction1|direction2);
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
				.setMessage("确定退出游戏吗？")
				.setPositiveButton("退出游戏",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface di, int i) {
								if (world != null) {
									world.onDestroy();
//									world = null;
									overridePendingTransition(R.anim.fadein,
											R.anim.fadeout);
									finish();
									System.exit(0);
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
		Client.send(ConsWhenConnecting.REQUEST_UPDATE_SCORE+userId+" "+score);

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
		
		World.openMode=sp.getBoolean("openMode", false);
		
		starString = sp.getString("starString", starString);
		Log.i("starString"+starString);
		itemString =sp .getString("itemString", itemString);
		star = stringToInts(starString);
		item=itemString.toCharArray();
		userName=sp.getString("userName", UserName.randomName());
		if(!sp.contains("userName")){
			editor.putString("userName", userName);
		}
		userId=sp.getInt("userId", 5);
	}
	void saveUserMessage(String sn){
		userName=sn;
		startMenu.setUserName(userName);
		Client.send(ConsWhenConnecting.REQUEST_UPDATE_NAME+userId+" "+userName);
		editor.putString("userName",userName);
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
		if (getMaxMapIndex() >= mapIndex)
			mapIndex++;
		// =to more 1 than max stageIndex to got last stage
		startGame();
	}

	public void startWithIndex(int index) {
		mapIndex = index;
		world.mapFile=null;world.mapString=null;world.mapCharSet=null;
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
		Log.d("quitGame+gameSaved");
		save();
		
	}

	void loadTitleView() {
		// TODO Auto-generated method stub
		titleMode=true;
		world.loadTitleView();
	}

	public void resumeGame() {
		// TODO Auto-generated method stub
		if(gameMenu!=null)	gameMenu.returnCurStateAndHide();
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

	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		battleActi=null;
		Log.i("onActivityResult");
		if(dl!=null)dl.dismiss();
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		   case RESULT_OK:
		    String mapfile=data.getStringExtra("mapfile");//str即为回传的值
		    	if(mapfile!=null&&mapfile!=""){
		    		
		 		    Log.i("mapfile"+mapfile);
					startGame(new File(mapfile));
		    	}
		    String onlineFileSelected=data.getStringExtra(OnlineFileActivity.ONLINE_STAGE_ITEM_SELECTED);
		    if(onlineFileSelected!=null&&onlineFileSelected!=""){
		    	Client.send(ConsWhenConnecting.REQUEST_THIS_ONE_ONLINE_STAGE+onlineFileSelected);
		    	selectedToSaveOnlineFileName=onlineFileSelected;
		    }
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

	public void saveUserId(String s) {
		// TODO Auto-generated method stub
		userId=Integer.parseInt(s);
		editor.putInt("userId", userId);
	}

	public void showBuyLifeShop(View v) {
		// TODO Auto-generated method stub
		showShop(v);
		shop.toBuyLife();
	}




//	void 

	public void sendOnlineStageRequest(){
		client.send(ConsWhenConnecting.REQUEST_ONLINE_STAGE);
		showDialog("提示", "已发送请求", R.drawable.fog);
	}
	public void showOnlineStage(String gotonString) {
		Intent intent = new Intent();
		intent.putExtra(OnlineFileActivity.ONLINE_STAGE_STRING_FROM_NET, gotonString);
		
		intent.setClass(this, onlineStageActivity.OnlineFileActivity.class);
		this.startActivityForResult(intent, 0);
	}

	public void getTheOnlineStage(String ss) {
		// TODO Auto-generated method stub
		if(selectedToSaveOnlineFileName.equals(requestFoItemMap)){
			if(randomMap==null)randomMap=new RandomMap(world);
			
			Map map=new Map(ss);
			
			if(World.Item3Mode)randomMap.set3Item(map);
			else randomMap.setWholeItem(map);
			showDialog("地图更新成功", "随机地图更新成功", 0);
			return;
		}
		world.saveMap(selectedToSaveOnlineFileName,ss);
		startGame(ss);
	}
	
	void requestFoItemMap(){
		Client.send(ConsWhenConnecting.REQUEST_THIS_ONE_ONLINE_STAGE+requestFoItemMap);
    	selectedToSaveOnlineFileName=requestFoItemMap;
	}
	private void startGame(File file) {
		// TODO Auto-generated method stub
		 world.mapFile=file;
		 world.mapString=null;
		 world.mapCharSet=null;
		 myHandler.sendEmptyMessage(World.REQUEST_TO_STARTGAME);
	}
	private void startGame(String ss) {
		// TODO Auto-generated method stub
		world.mapString=ss;
		world.mapFile=null;
		 world.mapCharSet=null;
		Log.i(ss);
		myHandler.sendEmptyMessage(World.REQUEST_TO_STARTGAME);
	}
	public void randomChalenge() {
		// TODO Auto-generated method stub
		if(randomMap==null){
			randomMap=new RandomMap(world);
			Map map=new Map(-333, this);
			if(World.Item3Mode)randomMap.set3Item(map);
			else randomMap.setWholeItem(map);
		}
	
		
		char []cs;
		if(World.Item3Mode){
			cs=randomMap.getRandom333Map(16,1);
		}
		else {
			cs=randomMap.getRandomWholeMap(16,1);
		}
		world.mapCharSet=cs;
		world.mapString=null;
		world.mapFile=null;
		myHandler.sendEmptyMessage(World.REQUEST_TO_STARTGAME);
	}
	public void battleAction(String ss) {
		// TODO Auto-generated method stub
		// id x y angle gunid actionid fdirection
		String[] strSet = ss.split(" ");
		world.battleAction(strSet);
	}
	public void roomSetMessage(String ss) {
		// TODO Auto-generated method stub
		if(battleActi!=null)battleActi.roomSetMessage(ss);
	}
	public void roomOneInfo(String ss) {
		// TODO Auto-generated method stub
		if(battleActi!=null)battleActi.roomOneInfo(ss);
	}
	public void intentToFileChooser() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(this, fileRW.FileActivity.class);
		this.startActivityForResult(intent, 0);
	}
	public void intentToBattleMode() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(this, BattleActivity.class);
		this.startActivityForResult(intent, 111);
	}
	public void addForce(int force, int userId) {
		// TODO Auto-generated method stub
		world.addForce(force,userId);
	}
}
