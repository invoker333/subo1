package Enviroments;

import java.util.ArrayList;

import com.mingli.toms.MusicId;
import com.mingli.toms.R;

import element2.Tail;
import element2.TexId;
import Mankind.Player;

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
		name="飞天鞋";
		instruction="使用后点击任意位置可跳跃，跳跃速度极限为正常的两倍，被攻击后失效";
		setGoodsCost(10,5);
	}
	void init(){
		loadTexture(TexId.FRUITFLY);
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
		player.incWudiTime(time);
		super.use(player, pickedList);
	}

//	public
}
