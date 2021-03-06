package Enviroments;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Element.AnimationMove;
import Mankind.BattleMan;
import Mankind.Player;
import aid.Client;
import aid.ConsWhenConnecting;
import aid.Log;

import com.mingli.toms.MenuActivity;
import com.mingli.toms.Music;
import com.mingli.toms.R;
import com.mingli.toms.World;

import element2.Set;
import element2.TexId;

public class FruitSet extends Set {
	public ArrayList<Fruit> fruitList;
	ArrayList<Fruit> drawList = new ArrayList<Fruit>();
	protected static ArrayList<Fruit> effectList = new ArrayList<Fruit>();
	Player player;
	// private int fruitId;
	protected Music music = World.music;

	// private float checkW;
	// private float checkH;
	protected GrassSet gs;
	protected float COUNT;
	public static ArrayList<Fruit> shopList;
	public static ArrayList<Fruit> pickedList = new ArrayList<Fruit>();
	private static Fruit relifeFruit;
//	private  ChanceFruit chanceFruit;
	protected World world;

	public FruitSet(Player player, ArrayList<Fruit> fruitList, World world,GrassSet gs) {// 跳台起始x值，长度，起始
		// y值
		this.player = player;
		this.fruitList = fruitList;
		this.world = world;
		this.gs = gs;
		COUNT = fruitList.size();

		drawList.addAll(fruitList);
		// checkW=player.getW()+fruitList.get(0).getW();//设置碰撞宽度
		// checkH=player.getH()+fruitList.get(0).getH();//设置碰撞宽度
		// initItemList();
		initShopList();
		if (MenuActivity.maxMapId < 10&&world.mapString==null){
			checkShopList();
		}
		if(world.mapString!=null){
			for(int i=0;i<shopList.size();i++){
				if(shopList.get(i).name.equals("分身果")){
					shopList.remove(i);
					break;
				}
			}
		}
	}

	private void checkShopList() {
		// TODO Auto-generated method stub
		boolean had = false;
		for (int i = shopList.size() - 1; i > -1; i--) {
			Fruit f = shopList.get(i);

			if (f.getTextureId() == TexId.EGG || f.getTextureId() == TexId.ZAN)
				continue;

			had = false;
			for (int j = 0; j < fruitList.size(); j++) {
				Fruit ff = fruitList.get(j);
				if (ff.getTextureId() == f.getTextureId()) {
					had = true;
					break;
				}
			}
			if (!had)
				shopList.remove(i);
		}
	}

	public static void initShopList() {
		// TODO Auto-generated method stub

		char c = 'a';
		// if (shopList == null)
		{
			shopList = new ArrayList<Fruit>();
			
			shopList.add( new ChanceFruit(c++, 1, 1,World.baseWudiTime));// ..
					relifeFruit=new ChanceFruit(c, 1, 1,3*World.baseWudiTime);
			shopList.add(new Wudi(c++, 1, 1));
			shopList.add(new sizeFruit(c++, 1, 1));
			shopList.add(new Toukui(c++, 0, 0, 9999));
			shopList.add(new Gao(c++, 1, 1, 9999));
			shopList.add(new FruitFly(c++, 1, 1, 9999));
			shopList.add(new Fenshen(c++, 1, 1));
			shopList.add(new FruitGun(c++, 1, 1, TexId.BOOMGUN));
			shopList.add(new FruitGun(c++, 1, 1, TexId.HOOKGUN));
			shopList.add(new FruitBlade(c++, 1, 1));
			if (World.rpgMode) {
				shopList.add(new Tomato(c++, 1, 1, 500));// ..
			}
				shopList.add(new FruitGun(c++, 1, 1, TexId.PUTONGQIANG));
				shopList.add(new FruitGun(c++, 1, 1, TexId.ZIDONGDAN));
				shopList.add(new FruitGun(c++, 1, 1, TexId.GUANGDANQIANG));
				shopList.add(new FruitGun(c++, 1, 1, TexId.SHOTGUN));
				shopList.add(new FruitGun(c++, 1, 1, TexId.MISSILE));
				shopList.add(new FruitGun(c++, 1, 1, TexId.JUJI));
//				shopList.add(new FruitGun(bi, 1, 1, TexId.SHUFUDAN));
		}
	}

	public static void initItemList(char[] item) {
		Log.i("initItemString" + new String(item));
		char bi = 0;
		if (pickedList == null) {
			pickedList = new ArrayList<Fruit>();
			// pickedList.add(new Tomato(1, 1,1000000000));//..
			// pickedList.add(new sizeFruit(1, 1));
			pickedList.clear();
			for (int i = 0; i < item.length; i++) {
				switch (item[i]) {
				case 'H':
					pickedList.add(new sizeFruit());
					break;
				case 'T':
					pickedList.add(new Tomato(bi, 0, 0, 500));
					break;
				case 'n':
					pickedList.add(new Toukui(bi, 0, 0, 9999));
					break;
				case 'G':
					pickedList.add(new Gao(bi, 0, 0, 9999));
					break;
				case 'S':
					pickedList.add(new FruitGun(bi, 0, 0, TexId.SHOTGUN));
					break;
				case 'O':
					pickedList.add(new FruitGun(bi, 0, 0, TexId.GUANGDANQIANG));
					break;
				case 'M':
					pickedList.add(new FruitGun(bi, 0, 0, TexId.JUJI));
					break;
				case 'D':
					pickedList.add(new FruitGun(bi, 0, 0, TexId.HOOKGUN));
					break;
				case 'B':
					pickedList.add(new FruitGun(bi, 0, 0, TexId.MISSILE));
					break;
				case 'P':
					pickedList.add(new FruitGun(bi, 0, 0, TexId.BOOMGUN));
					break;
				case 'x':
					pickedList.add(new FruitGun(bi, 0, 0, TexId.SHUFUDAN));
					break;
				case 'L':
					pickedList.add(new FruitGun(bi, 0, 0, TexId.ZIDONGDAN));
					break;
				case 'K':
					pickedList.add(new FruitBlade(bi, 0, 0));
					break;
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
			case R.drawable.guangdan:
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
			case R.drawable.boomgun:
				item[i] = 'P';
				break;
			case R.drawable.shufudan:
				item[i] = 'x';
				break;
			case R.drawable.autobulletgun:
				item[i] = 'L';
				break;
			}
		}
		return item;
	}

	void pick(Fruit fruit) {
		AnimationMove goreAni = gs.goreAni;
		if (Math.abs(fruit.x - player.x) < fruit.getW() + player.getwEdge()
				&& Math.abs(fruit.y - player.y) < fruit.getH() + player.getH()) {
			picked(fruit);
		}

		if (gs.isGore() && Math.abs(fruit.x - goreAni.x) < fruit.w + goreAni.w
				&& Math.abs(fruit.y - goreAni.y) < fruit.h + goreAni.h) {
			gs.setGore(false);
			picked(fruit);
		}
	}

	protected void picked(Fruit fruit) {

		fruit.use(player, pickedList);// whatever fruit will be used at once

		if (fruit.loadAble(player)) {
			if (World.rpgMode)
				pickedList.add(fruit);
//			gunLoseCheck(fruit);
		}
		effectList.add(fruit);
		fruitList.remove(fruit);// 2016.10
		drawList.remove(fruit);
	}

	private void gunLoseCheck(Fruit fruit) {
		// TODO Auto-generated method stub
		if (fruit.kind.equals("fruitgun")) {
			for (int i = 0; i < pickedList.size(); i++) {
				if (pickedList.get(i).kind.equals("fruitgun")) {
					pickedList.remove(i);
				}
			}
		}

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
		useItem(player,item);
	}

	public void useItem(BattleMan player,Fruit selectedItem) {
		// TODO Auto-generated method stub
		selectedItem.use(player, pickedList);
		effectList.add(selectedItem);
		player.sendUseitemMessage(selectedItem);
	}

	public void buyItem(Fruit item) {
		// TODO Auto-generated method stub
		player.increaseCoinBy(-item.cost);
		player.increaseChanceBy(-item.chancecost);

		if (!item.loadAble(player))
			return;// not load Able return
		if (World.rpgMode)
			pickedList.add(item);

	}

	public static void cml() {
		// TODO Auto-generated method stub
		initShopList();
		if (World.rpgMode)
			shopList.add(new Tomato((char) 0, 0, 0, 99999));
		else
			shopList.add(new Wudi((char) 0, 0, 0, 99999));
	}

	public static void useItem(BattleMan player, char itemMapSign) {
		// TODO Auto-generated method stub
		for(Fruit f:shopList){
			if(f.mapSign==itemMapSign){
				f.use(player, pickedList);
				effectList.add(f);
			}
		}
	}

	public static void sendRelifeFreeMes(int i) {
		// TODO Auto-generated method stub
		Player.sendUseitemMessage(relifeFruit);
	}

}