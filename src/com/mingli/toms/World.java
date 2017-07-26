package com.mingli.toms;

import java.io.File;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Element.Animation;
import Element.BloodSet;
import Element.BoomSet;
import Element.BubbleSet;
import Element.Curtain;
import Element.Draw;
import Element.FireSet;
import Element.GuidePost;
import Element.LightSpotSet;
import Enviroments.BackGround;
import Enviroments.CoinSet;
import Enviroments.FruitSet;
import Enviroments.Goal;
import Enviroments.GrassSet;
import Mankind.BattleMan;
import Mankind.Creature;
import Mankind.Emplacement;
import Mankind.EnemySet;
import Mankind.Player;
import Mankind.PlayerSet;
import aid.Log;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Toast;
import element2.FireworkSet;
import element2.GlDialog;
import element2.HikariSet;
import element2.LightningSet;
import element2.ParticleSet;
import element2.SnowSet;
import element2.Tail;
import element2.TexId;
import fileRW.FileActivity;

public class World extends GLSurfaceView implements Runnable {
//	private static final int FILEMAP = -111;
	public static long flash = 1000 / 60;
	// public boolean isRunning;
	public Player player;
	private GrassSet gra;
	private EnemySet enemySet;
	private EnemySet playerSet;
	private BackGround bg;
	private CoinSet cs;
	private Map map;

	private ParticleSet ps;
	private HikariSet hikariSet;
	private FireworkSet fireWorkSet;
	private SnowSet snowSet;
	public LightningSet lightningSet;
	private BoomSet boomSet;
	public static Music music;

	public MenuActivity acti;
	public static float red;
	public static float green;
	public static float blue;
	public static float alpha = 1;
	private ArrayList<Draw> drawList = new ArrayList<Draw>();
	static ArrayList<Draw> recycleList = new ArrayList<Draw>();
	public static int dStage = 4;// �ؿ��ֶ�

	private LightSpotSet lightSpotSet;
	public Tail touchTail;

	private BubbleSet bubbleSet;
	private GuidePost guidePost;
	private Curtain ct;
	public int playerMoveIndex;
	private int mapMoveIndex;
	TexId texId;

	static  boolean RAMPAGE;
	static  boolean NOWEPON ;
	static  boolean ALWAYSGUN ;

	final static int TIME = 0, COIN = 1, SCORE = 2, CHANCE = 3, LIFE2 = 4,	LIFE = 5;
	
	 static final int SUCCEED=6;
	static final int GAMEOVER = 7;
	static final int LOADED = 8;
	public static final int BLADEICON = 9;
	public static final int TREADICON=10;
	public static final int GUNICON=11;
	
	public static final int NOBLADEICON = 12;
	public static final int NOTREADICON=13;
	public static final int NOGUNICON=14;
	public static final int JUMP = 15;
	public static final int NOTIFY_PAIMING_CAHNGED = 16;
	public static final int REQUEST_TO_STARTGAME = 17;
	public static final int DIALOG = 18;
	final  int[] mes = new int[14];
	public static final int baseAttack = 100;
	
	public static final int BLUE_FORCE = 1;
	public static final int RED_FORCE = 2;
	public int force_in_battle=-11111;
	
	public static int baseLifeMax=baseAttack/10;
	Handler handler;
	private FruitSet fruitSet;
	private Render render;
	private BloodSet snowBloodSet;
	public  File mapFile;
	String mapName;
	public static int curMapIndex;
	
	
	 String getStr(int index) {
		return "" + mes[index];
	}

	public static void recycleDraw(Draw draw) {
		recycleList.add(draw);
	}
	void saveMap(String name,String fileString){
		File f = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+FileActivity.TOMS_MAP_PATH,name);
		map.saveMap(fileString, f);
	}
	void saveMap(){
		
		File f = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+FileActivity.TOMS_MAP_PATH);
		if(!f.exists()) {
			f.mkdirs();
		} 
		{
			File[] fs = f.listFiles();
			int id=fs.length;
			String string = "world"+(++id)+".txt";
			f=new File(f,string);
//			Log.i(f.getName());
			while(f.exists())f=new File(f,"world"+(++id+".txt"));
//			Log.i(f.getName());
		}
		
		map.saveMap(gra.saveMap(), f);
		Toast.makeText(acti, "地图保存成功", Toast.LENGTH_SHORT).show();
	}

	public World(Context context,Handler handler) {//
		super(context);
		this.acti = (MenuActivity) context;
		this.handler = handler;
		music = new Music(context);

//		this.setDebugFlags(1111111111);
		texId = new TexId(context);
		render=new Render(this, texId);
		setRenderer(render);
		this.setRenderMode(RENDERMODE_WHEN_DIRTY);// ��������Ⱦ
		
		
		
		new Thread() {
			public void run() {
				while (!render.created)// time to load texture  Thread not asame
					timeRush();
//				loadTitleView(0);
				loadTitleView();
				long t1=System.currentTimeMillis();
				Log.i("loadGameTime"+(System.currentTimeMillis()-t1+"ms"));
			}

		}.start();
		
	}
	private void initAnimationShopList() {
		// TODO Auto-generated method stub
		
		
//		char[] ch={'s','g','0','t','w','f','1','A','T','C','n','U','a','x','b','c','d','e','i','I','H','Z','z','2','↑','↓','←','→','B','D','K','M','O','S','j','J','E','F','V','l','L',13};
		
		Map map=new Map(-99,acti);
		GrassSet gs=new GrassSet(gra.getGrid(), map.charData, lightningSet, this);
		
		
		
		animationshopList=new ArrayList<Animation>();
//		animationshopList=gs.animationList;
		
		
		for(int i=0;i<gs.animationList.size();i++){
			Animation ani=gs.animationList.get(i);
			// did not add empty
			if(ani.mapSign!=' '){
				animationshopList.add(ani);// 
			}
		}
//		MenuActivity.showDialog("remove whitch has no icon", "", 0);
		
		Log.i("ani.getClass().getName()"+animationshopList.get(0).getClass().getName());
		Log.i("对象 instanceof 类"+(animationshopList.get(0) instanceof Emplacement));
		
		
		for(int i=0;i<animationshopList.size();i++){
			Animation ani=animationshopList.get(i);
			Log.i(ani.getClass().getName());
//			ani.name=""+ani.mapSign;//////////
		
//			if(ani.mapSign=='j'||ani.mapSign=='E'){
			if(ani instanceof Creature){
				Log.i("ani.mapSign："+ani.mapSign);
				((Creature) ani).setEnemySet(enemySet);
//				MenuActivity.showDialog("has added enemySet!", "", 0);
			}
			
		}
	}

	void loadTitleView() {
		int mapId=-1;
		mapFile=null;mapString=null;mapCharSet=null;
		if(!render.created)return;
		// TODO Auto-generated method stub
//		if(drawList.contains(bubbleSet))return;
		
		
		if(ct==null)ct = new Curtain();
//		drawList.add(ct);
		
		loadGame(mapId);
		
		if(fireSet==null)
		   fireSet = new FireSet(5,400,0);
		if(bubbleSet==null)bubbleSet = new BubbleSet(10);
			
		if(snowBloodSet==null)
			snowBloodSet=new BloodSet(10,TexId.RAIN);
		drawList.add(snowBloodSet);
		drawList.add(fireSet);
		drawList.add(bubbleSet);
		music.setBGM(R.raw.bocai);
		
		
		
		resume();
	
	}
	public void startGame( final int mapIndex){
		World.curMapIndex=mapIndex;
//		if(render.created)
//			loadGame(mapIndex);
//		else 
		// new thread to start game 
		{
			new Thread() {
				public void run() {
					while (!render.created)// time to load texture  Thread not asame
						timeRush();

					long t1=System.currentTimeMillis();
					try{
						loadGame(mapIndex);
					}catch(NullPointerException e){
						e.printStackTrace();
						MenuActivity.showDialog("", "空指针异常", 0);
					}
					Log.i("loadGameTime"+(System.currentTimeMillis()-t1+"ms"));
				}
			}.start();
		}
	}

	private void initializeGame(int mapIndex) {// ��ʼ���ǿ�ʼ�˵��Ķ���
		 gameTime=timerMax;//chongzhi shi jian
			
		 if(mapCharSet!=null){
				map = new Map(mapCharSet);
				mapName="随机挑战关卡";
				
			}
		 else if(mapString!=null){
				map = new Map(mapString);
				mapName=acti.selectedToSaveOnlineFileName.replace(".txt", "");
				
			}
		 else if(mapFile!=null){
			map = new Map(mapFile, acti);
			mapName=mapFile.getName().replace(".txt", "");
			
		}
		else{
			map = new Map(mapIndex, acti);
			mapName="第"+acti.mapIndex+"关";
		}
		
		lightningSet = new LightningSet(2);// ����
		gra = new GrassSet(64f, map.charData, lightningSet,this);
		goal=gra.getGoal();
		animationList=gra.animationList;
		
		ps = new ParticleSet(gra, 10);
		{
			int id=mapIndex;
			if(mapCharSet!=null)id=(int) (Math.random()*Map.max);//randombackground
			bg = new BackGround(id);
			
			if(!gra.getGoal().showable)bg.setTextureId(TexId.TIANSHAN);
			// bg = new BackGroundRoll(mapIndex);
			// bg.setTextureId(TexId.ICE);
			if (bg.getTextureId() == TexId.TIANSHAN)
				snowSet = new SnowSet(20, gra);
		}

		enemySet = new EnemySet(gra);
		if (RAMPAGE)
			enemySet.setCHASE_MODEL(true);
		player =gra.player;
		
		playerSet = new PlayerSet(gra, player);
		
		enemySet.setPlayer(player);

		playerSet.setFriendSet(playerSet);
		playerSet.setEnemySet(enemySet);
		
		enemySet.setFriendSet(enemySet);
		enemySet.setEnemySet(playerSet);
		
//		playerSet.cList.clear();
//		playerSet.cList.add(player);
		
		
		cs = new CoinSet(player, gra.getCoinList(), gra, this);
		fruitSet = new FruitSet(player, gra.getFruitList(), this, gra);
		
			
			ArrayList<Creature>cList=new ArrayList<Creature>();
			cList.addAll(enemySet.cList);
			cList.addAll(playerSet.cList);
		lightningSet.setEnemy(cList);


//		if (!NOWEPON) {
//			// gun = new MissileGun(enemySet, player, 10);
//			// gun = new KindNessGun(enemySet, player, 10);
//			// gun = new Gun(enemySet, player, 10);
//			Gun gun = new Gun(enemySet, player, 10);
//			player.gun = gun;
//		}

		

		fireWorkSet = new FireworkSet(5, gra);
		hikariSet = new HikariSet(10,gra.isCastle);

		boomSet = new BoomSet(2);
		lightSpotSet = new LightSpotSet(10, TexId.HIKARI);
//		lightSpotSet.tringer(Render.px + 1280 * Math.random(), Render.py + 720				* Math.random());

		

		touchTail = new Tail(15, TexId.CANDLETAIL);
		touchTail.w = 8;

		guidePost = new GuidePost();
		
		 if(editMode)initAnimationShopList();
		 sendLoadedMessage();
	}

	public void sendLoadedMessage() {
		 sendMessage(LOADED);
	}

	public void loadGame(int i) {
		music.initSoundPool();
		drawList.clear();
//		pauseDraw();
		
		if(ct!=null)ct.close();
		initializeGame(i);
		
		
		recycleList.clear();

		drawList.add(bg);
		 if(bg!=null&&(bg.getTextureId()==TexId.EVENING))
		drawList.add(lightSpotSet);
		 
		 drawList.add(gra);
		drawList.add(cs);
		drawList.add(fruitSet);
		drawList.add(ps);
		drawList.add(enemySet);
		drawList.add(touchTail);
		drawList.add(playerSet);
	/*	if (modle != NOWEPON)
			drawList.add(gun);*/

		drawList.add(hikariSet);
		drawList.add(fireWorkSet);
		if (bg.getTextureId() == TexId.TIANSHAN)
			drawList.add(snowSet);
		drawList.add(lightningSet);
		drawList.add(boomSet);

		drawList.add(guidePost);

//		Animation c = (Animation) player.clone();
//		drawList.add(c);
		
		
//		drawList.add(ct);

		ct.open();
		onTouch();

	
		resume();// 提前吃金币 估计会出bug

		showLifeColumn(player);
		increaseCoin(0);
		increaseTime(0);
		increaseChance(0);
		increaseScore(0);
		setGameMusic();
		
		if(true){
			if(i>0){
				tringerGuidePost();
//				MenuActivity.showDialog(null, "欢迎来到"+mapName, R.drawable.cup);
			}
			if(mapString != null || mapFile != null || mapCharSet != null)return;
//			if(i==2)MenuActivity.showDialog("厉害了我的哥", "欢迎来到火星猎人的世界第"+i+"关", R.drawable.zan);
//			if(i==2)MenuActivity.showDialog(acti.userName, "哈哈！坏蛋看到我都得逃跑！", R.drawable.cap);
			else if(i==Map.max+1){
				MenuActivity.showDialog("厉害了我的哥", "经历了各种有趣的难关，终于到达这里，旅程也算告一段落，但是精彩并没有结束！还有各种有意思的地方没有去。更多精彩尽在“在线关卡”，各种新奇关卡，等你来闯关！", R.drawable.zan);
				gameTime=0;
				mapName="这并不是终点";
			}
			
			
		}
		if(editMode)gameTime=0;
	}

//	public boolean onTouchEvent(MotionEvent e) {
//		return isGameRunning;
//		// touch.onTouch(this,e);
//	}

	public int gameTime = 256;
	private boolean isGameRunning;
	private boolean paused;
	TouchMove touchMove;
	private Goal goal;

	public void onTouch() {
		// Touch touch=new Touch(gun,ab,lightBallSet,player);
		touchMove=new TouchMove(touchTail,player,this);
		// use @MyMoveView
		
//		this.setOnTouchListener(touchMove);// move
		
		// this.setOnTouchListener(touch);// 触摸事件监听器

		// this.setOnTouchListener(new Touch(bts,ab,tail,player));
		// this.setOnTouchListener(new Touch( ab, fireWorkSet, player));
		// this.setOnTouchListener(new Touch(ab,bubbleSet,player));
		// this.setOnTouchListener(new Touch(ab,tail,this,player));
		// this.setOnTouchListener(new Touch(ab,ct,player));
		// this.setOnTouchListener(new Touch( ab, lightSpotSet, player));
		// this.setOnTouchListener(null);
	}

	public void run() {
		int frameIndex = 0;
		while (isGameRunning) {
			timeRush(1000 / 60);
			if(paused)continue;////////////
			requestRender();
			if (frameIndex++ % 60 == 0) {
				MenuActivity.FPS = Render.frame;
				Render.frame = 0;
				Log.i("" + Render.frame, "");
			}
		}
		Log.i("frameIndex" + frameIndex, "" + MenuActivity.FPS);
	}

	void timerTask() {
		// if(tail!=null)tail.tringer(player.x,player.y);// 指示位置
		if(paused)return;//////////test
			
		if(playerSet!=null)
			for(Creature c:playerSet.cList){
				c.timerTask();
			}
			if(enemySet!=null)
			for(Creature c:enemySet.cList){
				c.timerTask();
			}
		if (player != null) {
			player.setViewPot();
		}
		if(MenuActivity.titleMode
//				||editMode
				)return;
		
		if(gra!=null&&editMode)gra.toStartPosition();
	
		
		if (player != null) {
			
			
			showSecondaryLifeColumn(player);
			if (!player.isDead && goal.hasFirstBlood && index % 60 == 0) {// 正常游戏计时
																			// 没有达到目的地
																			// 玩家没有死亡
																			// 才计时
				if (gameTime > 0)
					increaseTime(-1);
				else if (gameTime == 0) {
//					if(editMode)return;// edit mode dont dai
					player.die();
				}

			}
		}
		if (index % 5 == 0) {
		}
		if (index % 10 == 0) {
			music.soundPoolTime=4;
			if (snowSet != null)
				snowSet.tringer(1, 16, 20, 1);
			if(snowBloodSet!=null)snowBloodSet.tringer(Render.px+Math.random()*Render.width,Render.height, 16, 20, 1);
		}
		if (index % 45 == 0) {
			// tail.tringer(player.x, player.y);
			if ( ALWAYSGUN &&player!=null&& player.gun != null)
				player.gun.alwaysFire();
			

		}
		if (index % 80 == 0) {
			if(bubbleSet!=null)bubbleSet.tringerScreen(1);
			// bloodSet.tringer(player.x, player.y,
			// 4+player.getxSpeed(), 8+player.getxSpeed(), 1);

			// lightSpotSet.tringer(Render.px + 500+20*Math.random(),
			// Render.py + 300);
			// lightSpotSet.tringer(Render.px + 1280 * Math.random(),
			// Render.py + 720 * Math.random());
		}
		if (index % 120 == 0) {
		}
		if (index % 180 == 0) {
			// sendBC();
			// astarSearch(Render.px + 1280 * (float)Math.random(),
			// (float) (Render.py + 720 * Math.random()));
			// astar.tringer(Render.px + 1280 * Math.random(), Render.py
			// + 720 * Math.random(), 25, 25, lightBallSet.getCount());
			index = 1;
			// double xf = Render.px + 1280 * Math.random();
			// double yf = Render.py + 720 * Math.random();
			// boomSet.tringer(xf, yf);
			// && fireWorkSet != nullfireWorkSet.tringer(xf, yf, 15, 20, 20);
			// gra.newBendTail();

		}
		if (player != null && guidePost != null	&&goal.hasFirstBlood) {
			if(!isMapMoved(5 * 60) ) {
				tringerGuidePost();
			} 
			
		}
		
		
//		final int attackSecond=10 * 60;
//		if (player != null && !isPlayerMoved(attackSecond)) {
//			player.attacked((int) (5*(Math.random()-0.49)));// - speed
//			if(playerMoveIndex==attackSecond)MenuActivity.showDialog("", "你感到身体不舒服，请动起来！", R.drawable.clock);
//		}

		index++;

		// long dTime=System.currentTimeMillis()-drawTime;
		// Log.i("dTime"+dTime,"dTime"+dTime);
		// if(dTime>0)timeRush(flash-dTime);
	}

	private void tringerGuidePost() {
		// TODO Auto-generated method stub
		guidePost.tringer(Render.px + Render.width / 2, Render.py + Render.height *7/8,
				gra.getGoal().x - guidePost.x, gra.getGoal().y - guidePost.y);
	}

	private boolean isPlayerMoved(int index) {
		// TODO Auto-generated method stub
//		Log.i("player.ySpeed" + player.getySpeed(), "player.g" + player.getG());
//		if (player.getxSpeed() != 0 && player.getySpeed() != -player.getG()) {
//			playerMoveIndex = 0;
//		} else
		
			playerMoveIndex++;
		if (playerMoveIndex < index)
			return true;
		return false;
	}

	
	float px,py;
	private boolean isMapMoved(int index) {
		// TODO Auto-generated method stub
		// Log.i("Render.py"+Render.py,"Player.py"+Player.py);
		if (Render.px != px || Render.py != py) {
			mapMoveIndex=0;
		} else {
			mapMoveIndex++;
//			if(mapMoveIndex==index){	MenuActivity.showDialog(null, "请沿着指示方向前进", R.drawable.guidepost);			else 
			if (mapMoveIndex > index){
				return false;
			}
		}
		px=Render.px;
		py=Render.py;
		return true;
	}

	public void drawElements(GL10 gl) {
		try{
			for (int i = 0; i < drawList.size(); i++) {
				drawList.get(i).drawElement(gl);
			}
			if(ct!=null){
				ct.setPosition(Render.px, Render.py);
				ct.drawElement(gl);
			}
			if(gd!=null&&haveDialog){
				gd.drawElement(gl);
			}
			if(editMode&&touchMove!=null)touchMove.drawElement(gl);
			timerTask();
		}catch(Exception e){
			String str=e.getStackTrace().toString();
			MenuActivity.showDialog("", str, R.drawable.close);
			e.printStackTrace();
		}
	}

	public void quitGame() {
		
		 {////////////clear the foece message
				redList.clear();
				blueList.clear();
				force_in_battle=-111111;
			}
		
		ct.close();
		haveDialog=false;
		for (int i = 0; i < drawList.size(); i++) {
			drawList.get(i).quitgame();
		}
		drawList.clear();// ��ֹ���ٵĹؼ�///////////
		System.gc();// ֪ͨ��������
//		music.pause();
		music.releaseSoundPool();
		// player.successful=true;
	}

	public void succeed() {
//		if (player.isDead)return;
		music.setBGM(R.raw.victory);
		music.setLooping(false);
		increaseScore(20);
		culStar();
		sucDraw = new Draw(){
			int sucIndex = 0;
			public void drawElement(GL10 gl){
				if (gameTime > 0) {
					if (fireWorkSet != null && boomSet != null && sucIndex++ % 60 == 0) {
						double xf = Render.px + 1280 * Math.random();
						double yf = Render.py + 360 + 360 * Math.random();
						boomSet.tringer(xf, yf);
						fireWorkSet.tringer(xf, yf, 15, 20, 20);
						// sucIndex = 0;
					}
					final int step=3;
					if (gameTime > step)
						increaseTime(-step);
					else
						increaseTime(-gameTime);
					increaseScore(1);
//					timeRush(1000 / 60);
				}
				
				else{
					handler.sendEmptyMessage(SUCCEED);
					Log.i("succeed gameTime"+gameTime);
					drawList.remove(sucDraw);
				}
			}
		};
		drawList.add(sucDraw);
	}

	public void gameOver() {
		if(editMode)return;
		FruitSet.pickedList.clear();
		music.setBGM(R.raw.death);
		increaseChance(-1);
		handler.sendEmptyMessage(GAMEOVER);

	}
	
	public void pauseGame(){
		pauseDraw();
		music.pause();
	}
	public void pauseDraw() {
//		for (int i = 0; i < drawList.size(); i++) {
//			drawList.get(i).pause();
//		}
		paused=true;
	}

	public void resume() {
//		for (int i = 0; i < drawList.size(); i++) {
//			drawList.get(i).resume();
//		}
		if (!isGameRunning) {
			isGameRunning = true;
			new Thread(this).start();
			Log.i("drawThread线程数", "" + (++drawThreadIndex));
		}
		music.resume();
		paused=false;
		playerMoveIndex=0;
		mapMoveIndex=0;
	}

	static int drawThreadIndex;

	void onDestroy() {
		music.onDestroy();
		render.onDestory();
		// textureId = 0;
		// textures = null;
		drawList.add(new Draw() {
			public void drawElement(GL10 gl) {
				texId.deleteTex(gl);
			}
		});
		this.destroyDrawingCache();//没啥用
		isGameRunning=false;
	}

	public void showLifeColumn(Creature c) {
		mes[LIFE] = c.getLife() * 100 / c.getLifeMax();
		handler.sendEmptyMessage(LIFE);
	}

	private void showSecondaryLifeColumn(Player c) {
		// if(c.bloodSecondaryindex++<10) return;
		if (c.secondaryLife > c.getLife()) {
			c.secondaryLife -=
			// 30f;
			(c.secondaryLife - c.getLife()) / 6f;
		} else if (c.secondaryLife < c.getLife()) {
			c.secondaryLife = c.getLife();
		}
		mes[LIFE2] = c.secondaryLife * 100 / c.getLifeMax();
		handler.sendEmptyMessage(LIFE2);
	}

	public static void timeRush() {
		SystemClock.sleep(flash);
		// try {
		// Thread.sleep(flash);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

	public static void timeRush(long dTime) {
		SystemClock.sleep(dTime);
		// try {
		// Thread.sleep(dTime);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

	int index;
	private int SPEEDSTAR;
	private int COINSTAR;
	private int ITEMSTAR;

	public ArrayList<Draw> getDrawList() {
		return drawList;
	}
	public void sendMessage(int message) {
		handler.sendEmptyMessage(message);
	}
	public void increaseScore(int score) {
//		mes[SCORE] = score;
		acti.score+=score;
		handler.sendEmptyMessage(SCORE);
		// if(this.score>999) {
		// sb.showChance(chance+=1);
		// this.score=0;
		// }
	}

	public void increaseCoin(int coinCount) {
//		mes[COIN] = coinCount;
		acti.coinCount+=coinCount;
		handler.sendEmptyMessage(COIN);
	}

	public void increaseTime(int itime) {
		mes[TIME] = (gameTime += itime);
		handler.sendEmptyMessage(TIME);
	}

	public void increaseChance(int ichance) {
		mes[CHANCE] = ichance;
		acti.chance+=ichance;
		handler.sendEmptyMessage(CHANCE);
	}

	public FruitSet getFruitSet() {
		return fruitSet;
	}

	public void setFruitSet(FruitSet fruitSet) {
		this.fruitSet = fruitSet;
	}

	public void culStar() {
		final int step = 3;
		SPEEDSTAR = step * gameTime / timerMax;
		// gra.goal.x
		COINSTAR = (int) (step * cs.getStar());
		ITEMSTAR = (int) (step * fruitSet.getStar());
		// Log.i("SPEEDSTAR"+SPEEDSTAR, "COINSTAR"+COINSTAR);
	}

	public int getSpeedStar() {
		return SPEEDSTAR;
	}

	public int getCoinStar() {
		return COINSTAR;
	}

	public int getItemStar() {
		return ITEMSTAR;
	}

	public void onPause() {
		super.onPause();
		pauseDraw();
	}

	public void onResume() {
		super.onResume();
		resume();
	}

	
	
	
	private AStar astar;
	private Draw sucDraw;
	public int timerMax=255;
	private FireSet fireSet;
	public ArrayList<Animation> animationList;
	public  ArrayList<Animation> animationshopList;
	public String mapString;
	public char[] mapCharSet;
	
	public ArrayList<Integer> blueList=new ArrayList<Integer>();
	public ArrayList<Integer> redList=new ArrayList<Integer>();
	
	public static boolean editMode;
	public static boolean rpgMode;
//	public static double baseBSpeed=20;
	public static double baseBSpeed=15;//20
	public static boolean bigMode;
	public static boolean openMode;
	public static int baseActionCdMax=180;
	public static boolean Item3Mode;
	public static int baseWudiTime=60;

	public void astarSearch(float x, float y) {
		if (astar == null)
			astar = new AStar(gra.map, gra.getZero());
		int sx = (int) (player.x / gra.getGrid());
		int sy = (int) (player.y / gra.getGrid());
		int tx = (int) (x / gra.getGrid());
		int ty = (int) (y / gra.getGrid());
		astar.search(new Node(sx, sy, null), new Node(tx, ty, null));
		Node goal = astar.goal;
		while (goal != null) {
			touchTail.tringer((goal.x + 0.5f) * gra.getGrid(), (goal.y + 0.5f)
					* gra.getGrid());
			goal = goal.father;
		}
	}
	public int getIntMes(int what) {
		// TODO Auto-generated method stub
		return mes[what];
	}

	public synchronized void  buildAnimation(Animation a) {
		// TODO Auto-generated method stub
//		Animation cloneA=(Animation) a.clone();
		Animation cloneA=(Animation) a.clone();
//			float xx=(float) ((Math.random()-0.4)*Render.width);
//			float yy=(float)(((Math.random()-0.4))*Render.height);
//			xx-=xx%gra.getGrid();
//			yy-=yy%gra.getGrid();
		
		
		cloneA.setStartXY(Render.px+Render.width/2, Render.py+Render.height/2);
		animationList.add(cloneA);
		Log.i("clone A: x  "+cloneA.x+"y    "+cloneA.y);
		drawList.add(cloneA);
	}

	public void relife() {
		// TODO Auto-generated method stub
		setGameMusic();
		
		 int gameTimeMax=timerMax/3;
		if((gameTime+=gameTimeMax)>gameTimeMax)
			gameTime=timerMax;
		
	}

	private void setGameMusic() {
		// TODO Auto-generated method stub
		if(!Music.bgm)music.pauseBGM();
//		music.setBGM(R.raw.paopaotang2);
		music.setBGM(R.raw.maiji);
		music.setLooping(true);
	}

	public void addDrawAnimation(Animation newAnimation) {
		// TODO Auto-generated method stub
		drawList.add(newAnimation);
		
	}

	public void removeDraw(Animation editTarget) {
		// TODO Auto-generated method stub
		drawList.remove(editTarget);
	}

	public boolean isOutMapResource() {
		// TODO Auto-generated method stub
		if(mapString!=null||mapFile!=null||mapCharSet!=null)return true;
		return false;
	}

	public void battleAction(String[] strSet) {
		// TODO Auto-generated method stub
		
		battleActionCheck(strSet, gra.battleManList);
	}


	private void battleActionCheck(String[] strSet, 		ArrayList<BattleMan> list) {
		int cId=Integer.parseInt(strSet[0]);
		for(int i=0;i<list.size();i++){
			BattleMan bm=list.get(i);
			if(bm.userId==cId){
				bm.onlineActionCheck(strSet);
			}
		}
	}

	public void addForce(int force, int userId) {
		// TODO Auto-generated method stub
		if(userId==acti.userId){
			force_in_battle=force;return;
		}
	
		switch(force){
		case BLUE_FORCE:{
			if(!blueList.contains((Integer)userId)){
				blueList.add((Integer)userId);
				addCheck(force, userId);
			}
			}break;
		case RED_FORCE:{
			if(!redList.contains((Integer)userId)){
				redList.add((Integer)userId);
				addCheck(force, userId);
			}
			}break;
		}
		
	}

	private void addCheck(int force, int userId) {
		if(!drawList.isEmpty()){
			BattleMan bm;
			if(force_in_battle==force){
				playerSet.addCreature(bm=new BattleMan(' ', gra, player.startX, player.startY,force, userId));
			}else {
				enemySet.addCreature(bm=new BattleMan(' ', gra, gra.getGrid()*gra.getMapWidth()
						-player.startX, player.startY,force, userId));
			}
			bm.loadTexture();
		}
	}

	public void useItemBattleMan(String strRes) {
		// TODO Auto-generated method stub
		String[] strSet = strRes.split(" ");
		int userId=Integer.parseInt(strSet[0]);
		ArrayList<BattleMan> list = gra.battleManList;
		for(BattleMan bm:list){
			if(bm.userId==userId){
				bm.useItemOnline(strSet[1]);
			}
		}
	}

	public void thisOneDie(String strRes) {
		// TODO Auto-generated method stub
		int userId=Integer.parseInt(strRes);
		if(player.userId==userId){
			player.die();
		}
		
		ArrayList<BattleMan> list = gra.battleManList;
		for(BattleMan bm:list){
			if(bm.userId==userId){
				bm.die();
			}
		}
	}

	GlDialog gd;
	boolean haveDialog;
	public  void showGlDialog(String string,Animation a, float dx,float dy) {
		if(string.length()<1)haveDialog=false;
		else haveDialog=true;
		if(gd==null)gd=new GlDialog(texId);
		gd.setText(string, a, dx, dy);
	}
}
