package Enviroments;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.R;


import element2.TexId;

import Mankind.Player;

public class Tomato extends ShakeFruit{
	int bloodMax;//吃了增加的血量
	int bloodStep=10;
	int maxback;//to use Second Time
	public Tomato(char bi,float x, float y,int bloodMax) {
		super(bi,x,y);
		if (bloodMax>1000)name="无限活力番茄！！";else 
		name="活力番茄";
		
		setGoodsCost(0, 5);
		// TODO Auto-generated constructor stub
		maxback=bloodMax;
		
	}
	public void loadSound(){
		loadSound(R.raw.fog);
	}
	void init(){
//		setFrameCount(2,1);
//		if(bloodMax<1000)
			loadTexture(TexId.TOMATO);
//		else loadTexture(TexId.TOMATO,1,0);
	}
	
	public boolean loadAble(Player player){
		
		int db=player.getLifeMax()-player.getLife();
		if(db>bloodMax) {
			super.loadAble(player);
			return false;
		}
		return true;
	}
	public void use(Player player, ArrayList<Fruit> pickedList){
		this.bloodMax = maxback;//when use blood max = max so it can use always
		 setAnimationFinished(false);
		super.use(player, pickedList);
	}

	public void effectCheck(Player p,ArrayList<Fruit>effectList) {
		if(p.getLife()<p.getLifeMax())p.attacked(-bloodStep);
		if((bloodMax-=bloodStep)<0){
			bloodMax=maxback;
			effectList.remove(this);
		}
	}
}
