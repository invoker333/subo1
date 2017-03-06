package Enviroments;

import java.util.ArrayList;

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
	}
	void init(){
		loadTexture(TexId.TOUKUI);
	}
	public void use(Player player,ArrayList<Fruit> pickedList){
		if(player.getToukuiTime()>0) {
		} else {
			player.setToukuiTime(player.getToukuiTime() + time);
			player.getCap().setTextureId(TexId.TOUKUI);
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
//		footTail=new Tail(15,x,y,8);
	}
	void init(){
		loadTexture(TexId.GAO);
	}
	public void use(Player player,ArrayList<Fruit> pickedList){
		player.setGaoTime(player.getGaoTime() + time);
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
class FruitAuto extends ShakeFruit{

	private int time;
	public FruitAuto(char bi,float x, float y, int time) {
		super(bi,x, y);
		// TODO Auto-generated constructor stub
		this.time = time;
		name="束缚泡泡";
	}
	void init(){
		loadTexture(TexId.RED);
	}
	public void use(Player player,ArrayList<Fruit> pickedList){
		player.autoBulletTime+=time;
	}
	public void effectCheck(Player p, ArrayList<Fruit> pickedList){
//		super.effectCheck(p,pickedList);
	}
}
