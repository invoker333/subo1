package Enviroments;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Element.AnimationMove;
import Mankind.Flyer;
import Mankind.Player;

import com.mingli.toms.Log;
import com.mingli.toms.Music;
import com.mingli.toms.MusicId;
import com.mingli.toms.R;
import com.mingli.toms.World;

import element2.Set;
import element2.TexId;

public class FruitSet extends Set {
	public ArrayList<Fruit> fruitList;
	ArrayList<Fruit> drawList = new ArrayList<Fruit>();
	protected ArrayList<Fruit> effectList = new ArrayList<Fruit>();
	Player player;
	// private int fruitId;
	protected Music music = World.music;

	// private float checkW;
	// private float checkH;
	protected GrassSet gs;
	protected float COUNT;
	public static ArrayList<Fruit> shopList;
	public static ArrayList<Fruit> pickedList =new ArrayList<Fruit>();
	private static ChanceFruit chanceFruit;

	public FruitSet(Player player, ArrayList<Fruit> fruitList, GrassSet gs) {// 跳台起始x值，长度，起始
		// y值
		this.player = player;
		this.fruitList = fruitList;
		this.gs = gs;
		COUNT = fruitList.size();

		drawList.addAll(fruitList);
		loadSound();
		// checkW=player.getW()+fruitList.get(0).getW();//设置碰撞宽度
		// checkH=player.getH()+fruitList.get(0).getH();//设置碰撞宽度
		// initItemList();
		initShopList();
	}

	public static  void initShopList() {
		// TODO Auto-generated method stub
		
		if(chanceFruit!=null){
			final int cost = 10;
//			chanceFruit.cost=cost;
			chanceFruit.chancecost=cost;
		}
		
		
		char bi=0;
		if (shopList == null) {
			shopList = new ArrayList<Fruit>();
			shopList.add(chanceFruit=new ChanceFruit(bi,1, 1));// ..
			if(World.rpgMode)shopList.add(new Tomato(bi,1, 1, 500));// ..
			shopList.add(new sizeFruit(bi,1, 1));
			shopList.add(new Toukui(bi,0, 0, 9999));
			shopList.add(new Gao(bi,1, 1, 9999));
			shopList.add(new FruitFly(bi,1, 1, 9999));
			shopList.add(new Wudi(bi,1, 1));
			shopList.add(new FruitGun(bi,1, 1, TexId.SHUFUDAN));
			shopList.add(new FruitGun(bi,1, 1, TexId.ZIDONGDAN));
			shopList.add(new FruitGun(bi,1, 1, TexId.S));
			shopList.add(new FruitGun(bi,1, 1, TexId.B));
			shopList.add(new FruitGun(bi,1, 1, TexId.D));
			shopList.add(new FruitGun(bi,1, 1, TexId.M));
			shopList.add(new FruitGun(bi,1, 1, TexId.O));
			shopList.add(new FruitBlade(bi,1, 1));
		}
	}

	public static void initItemList(char[] item) {
		Log.i("initItemString"+new String(item));
		char bi=0;
		if(pickedList==null){
			pickedList=new ArrayList<Fruit>();
//			pickedList.add(new Tomato(1, 1,1000000000));//..
//			pickedList.add(new sizeFruit(1, 1));
			pickedList.clear();
			for(int i=0;i<item.length;i++){
			switch(item[i]){
			case 'H':pickedList.add(new sizeFruit());break;	
			case 'T':pickedList.add(new Tomato(bi,0,0,500));break;		
			case 'n':pickedList.add(new Toukui(bi,0, 0, 9999));break;	
			case 'G':pickedList.add(new Gao(bi,0, 0, 9999));break;	
			case 'S':pickedList.add(new FruitGun(bi,0,0,TexId.S));break;	
			case 'O':pickedList.add(new FruitGun(bi,0,0,TexId.O));break;	
			case 'M':pickedList.add(new FruitGun(bi,0,0,TexId.M));break;	
			case 'D':pickedList.add(new FruitGun(bi,0,0,TexId.D));break;	
			case 'B':pickedList.add(new FruitGun(bi,0,0,TexId.B));break;	
			case 'K':pickedList.add(new FruitBlade(bi,0,0));break;	
			}
		}		
			
	}
		
			
	}

	public static char[] getItemArray() {
		char[] item = new char[pickedList.size()];
		for (int i = 0; i < pickedList.size(); i++) {
			switch (pickedList.get(i).getIcon()) {
			case R.drawable.height:
				item[i] = 'H';
				break;
			case R.drawable.tomato:
				item[i] = 'T';
				break;
			case R.drawable.toukui:
				item[i] = 'n';
				break;
			case R.drawable.gao:
				item[i] = 'G';
				break;
			case R.drawable.s:
				item[i] = 'S';
				break;
			case R.drawable.putongdan:
				item[i] = 'O';
				break;
			case R.drawable.jujidan:
				item[i] = 'M';
				break;
			case R.drawable.huck:
				item[i] = 'D';
				break;
			case R.drawable.daodan:
				item[i] = 'B';
				break;
			case R.drawable.jian:
				item[i] = 'K';
				break;
			}
		}
		return item;
	}

	void pick(Fruit fruit) {
		AnimationMove goreAni = gs.goreAni;
		if (Math.abs(fruit.x - player.x) < fruit.getW() + player.getwEdge()
				&& Math.abs(fruit.y - player.y) < fruit.getH() + player.getH()
				) {
			picked(fruit);
		}

		if(gs.isGore() && Math.abs(fruit.x-goreAni.x)<fruit.w+goreAni.w
		&&Math.abs(fruit.y-goreAni.y)<fruit.h+goreAni.h){
			gs.setGore(false);
			picked(fruit);
		}
	}

	protected void picked(Fruit fruit) {
		
		fruit.use(player, pickedList);// whatever fruit will be used at once
		
		if (fruit.loadAble(player)) {
			if(World.rpgMode)
				pickedList.add(fruit);
			gunLoseCheck(fruit);
		}
		effectList.add(fruit);
		fruitList.remove(fruit);// 2016.10
		drawList.remove(fruit);
	}

	private void gunLoseCheck(Fruit fruit) {
		// TODO Auto-generated method stub
		if(fruit.kind.equals("fruitgun")){
			for(int i=0;i<pickedList.size();i++){
				if(pickedList.get(i).kind.equals("fruitgun")){
					pickedList.remove(i);
				}
			}
		}
		
	}

	public void loadSound() {
		setSoundId(MusicId.magic);
		for (int i = 0; i < fruitList.size(); i++) {
			Fruit fruit = fruitList.get(i);
			fruit.setSoundId(getSoundId());
		}
	}

	public void playSound() {
		music.playSound(getSoundId(), 0);
	}

	public void loadTexture() {
	}

	public void drawElement(GL10 gl) {
		Fruit fruit;
		for (int i = 0; i < drawList.size(); i++) {
			fruit = drawList.get(i);
			if (fruit.isAnimationFinished()) {
				continue;
			}
			if (fruit.x > Player.gx1 && fruit.x < Player.gx2
					&& fruit.y > Player.gy1 && fruit.y < Player.gy2) {
				fruit.drawElement(gl);
			}
		}
		timerTask();

	}

	protected void timerTask() {
		Fruit fruit;
		for (int i = 0; i < fruitList.size(); i++) {
			fruit = fruitList.get(i);
			pick(fruit);
		}
		for (int i = 0; i < effectList.size(); i++) {
			fruit = effectList.get(i);
			fruit.effectCheck(player, effectList);
		}
	}

	public void resume() {
		if (!fruitList.isEmpty())
			if (!isRunning()) {
				setRunning(true);
				// if(!isLiving())
				// new Thread(this).start();
			}
	}

	public ArrayList<Fruit> getItemList() {
		return pickedList;
	}

	public float getStar() {
		// TODO Auto-generated method stub
		return 1 - fruitList.size() / COUNT;
	}

	public void useItem(int id) {
		Fruit item = pickedList.get(id);
		useItem(item);
	}

	public void useItem(Fruit selectedItem) {
		// TODO Auto-generated method stub
		selectedItem.use(player, pickedList);
		effectList.add(selectedItem);
	}

	public void buyItem(Fruit item) {
		// TODO Auto-generated method stub
		player.increaseCoinBy(-item.cost);
		player.increaseChanceBy(-item.chancecost);
		
		if (!item.loadAble(player))
			return;// not load Able return
		if(World.rpgMode)pickedList.add(item);

	}

	public static void cml() {
		// TODO Auto-generated method stub
		initShopList();
		if(World.rpgMode)shopList.add(new Tomato((char) 0,0,0,99999));
		else shopList.add(new Wudi((char) 0,0,0,99999));
	}

}