package Enviroments;


import java.util.ArrayList;

import Mankind.Player;
import aid.Log;

import com.mingli.toms.R;

import element2.TexId;

public class Coin extends SixFruit {
	private int coinCount = 1;
	
	Coin(char bi,float x, float y) {
		super(bi,x, y);
		name="金币";
//		Log.i("coinx"+x,""+y);
	}
	public int getCoinCount() {
//		// TODO Auto-generated method stub
//		Log.i("returncoinCount"+coinCount);
		return coinCount;
	}
	public void setCoinCount(int coinCount) {
		this.coinCount = coinCount;
	}
	public int getIcon() {
		// TODO Auto-generated method stub
		return R.drawable.coinicon;
	}
	void init() {
		loadTexture(TexId.COIN);
	}
	public void use(Player player, ArrayList<Fruit> pickedList) {
		// TODO Auto-generated method stub
		super.use(player, pickedList);
		visible=false;
//		Log.i("Coin has ben used");
	}
	public boolean loadAble(Player player){
		player.increaseScoreBy(score);
		playSound();
		return false;
	}
	
}
