package Enviroments;

import java.util.ArrayList;

import Element.Animation;
import Mankind.BattleMan;

import com.mingli.toms.MusicId;

public class Fruit extends Animation{
	private float alpha=1;
	private float blue=1.2f;
	private float green=1.2f;
	private float red=1.2f;
	
	
	String kind="fruit";
	public String instruction="这个物品的作用作用有待您去发现";
	
	public Fruit(char bi,float x,float y){
		this();
//		start
		mapSign=bi;
		this.setPosition(x, y);
		setStartXY(x,y);
	}
	Fruit(){
		setW(30);
		setH(30);
		
		wEdge=w;
		hEdge=h;
		
		setAnimationFinished(false);
		init();
	}
	void doubleCost(int max){
		if(cost<max&&chancecost<max){
			cost+=cost;
			chancecost+=chancecost;
		}
	}
	void init() {
		loadTexture();
		loadSound(MusicId.magic);
	}
	public boolean loadAble(BattleMan player){
		player.increaseScoreBy(getScore());
		playSound();
//		use(player, null);
		return false;
	}
	public void use(BattleMan player, ArrayList<Fruit> pickedList) {
		// TODO Auto-generated method stub
		if(pickedList!=null)pickedList.remove(this);
	}
	public void playSound() {
		music.playSound(getSoundId(), 0);
	}
	void animation(){
		if(getxState()==getxCount()-1)changeState(1000);
		else changeState(100);
	}

	public void effectCheck(BattleMan p,ArrayList<Fruit>effectedList) {
		effectedList.remove(this);
	}
	public float getBlue() {
		return blue;
	}
	public void setBlue(float blue) {
		this.blue = blue;
	}
	public float getGreen() {
		return green;
	}
	public void setGreen(float green) {
		this.green = green;
	}
	public float getRed() {
		return red;
	}
	public void setRed(float red) {
		this.red = red;
	}
	public float getAlpha() {
		return alpha;
	}
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	



}
