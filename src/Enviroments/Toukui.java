package Enviroments;

import java.util.ArrayList;

import Mankind.Player;

import com.mingli.toms.MusicId;

import element2.TexId;

public class Toukui extends ShakeFruit{

	private int time;

	public Toukui(char bi,float x, float y,int time) {
		super(bi,x, y);
		// TODO Auto-generated constructor stub
		this.time = time;
		name="头盔";
		instruction="使用后您可以顶掉上方一些砖块，被攻击后失效";
		setGoodsCost(5,0);
	}
	void init(){
		loadTexture(TexId.TOUKUI);
		loadSound(MusicId.creeper4);
	}
	public void use(Player player,ArrayList<Fruit> pickedList){
		if(player.getToukuiTime()>0) {
		} else {
			player.changeToukui(time);
		}
		super.use(player, pickedList);
	}

//	public
}
class Gao extends ShakeFruit{

	private int time;
	public Gao(char bi,float x, float y, int time) {
		super(bi,x, y);
		// TODO Auto-generated constructor stub
		this.time = time;
		name="十字镐";
		instruction="手指下滑跳到一定高度下落，可以破坏下面一些砖块，被攻击后失效";
		setGoodsCost(5,0);
//		footTail=new Tail(15,x,y,8);
	}
	void init(){
		loadTexture(TexId.GAO);
		loadSound(MusicId.gore);
	}
	public void use(Player player,ArrayList<Fruit> pickedList){
		player.changeGao(time);
	}
//	Tail footTail;
	public void effectCheck(Player p, ArrayList<Fruit> pickedList){
//		super.effectCheck(p,pickedList);
	}
}
class FruitFly extends ShakeFruit{

	private int time;
	public FruitFly(char bi,float x, float y, int time) {
		super(bi,x, y);
		// TODO Auto-generated constructor stub
		this.time = time;
		name="飞行套装";
		instruction="使用后穿上帅气的披风，并且点击任意位置可跳跃，被攻击后失效";
		setGoodsCost(10,0);
	}
	void init(){
		loadTexture(TexId.FRUITFLY);
		loadSound(MusicId.land);
	}
	public void use(Player player,ArrayList<Fruit> pickedList){
		player.addFlyTime(time);
	}
	public void effectCheck(Player p, ArrayList<Fruit> pickedList){
//		super.effectCheck(p,pickedList);
	}
}
//class FruitAuto extends ShakeFruit{
//
//	private int time;
//	public FruitAuto(char bi,float x, float y, int time) {
//		super(bi,x, y);
//		// TODO Auto-generated constructor stub
//		this.time = time;
//		name="束缚泡泡";
//		setGoodsCost(5,10);
//	}
//	void init(){
//		loadTexture(TexId.RED);
//	}
//	public void use(Player player,ArrayList<Fruit> pickedList){
//		player.autoBulletTime+=time;
//	}
//	public void effectCheck(Player p, ArrayList<Fruit> pickedList){
////		super.effectCheck(p,pickedList);
//	}
//}


 class Wudi extends ShakeFruit{

	private int time;

	public Wudi(char bi,float x, float y,int time) {
		super(bi,x, y);
		// TODO Auto-generated constructor stub
		this.time = time;
		name="无敌果";
		instruction="使用后无敌"+time/60+"秒";
		setGoodsCost(50,0);
	}
	public Wudi(char bi,float x, float y) {
		this(bi,x,y,10*60);
		// TODO Auto-generated constructor stub
	}
	void init(){
		setSoundId(MusicId.light);
		loadTexture(TexId.ZAN);
	}
	public void use(Player player,ArrayList<Fruit> pickedList){
		final int max=200;
		player.incWudiTime(time);
		doubleCost(max);
		super.use(player, pickedList);
	}
}
 
 class Fenshen extends ShakeFruit{

		private int time;
		private int count=10;

		public Fenshen(char bi,float x, float y,int count) {
			super(bi,x, y);
			this.count = count;
			// TODO Auto-generated constructor stub
			name="分身果";
			instruction="使用后分身为"+(count+1)+"个。点击任意个体使本体与之交换位置。";
			setGoodsCost(50,50);
		}
		public Fenshen(char bi,float x, float y) {
			this(bi,x,y,4);
			// TODO Auto-generated constructor stub
		}
		void init(){
			setSoundId(MusicId.light);
			loadTexture(TexId.FENSHEN);
		}
		public void use(Player player,ArrayList<Fruit> pickedList){
			final int max=200;
			player.fenshen(count);
			doubleCost(max);
			super.use(player, pickedList);
		}
	}
