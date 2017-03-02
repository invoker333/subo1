package Enviroments;


import Mankind.Player;

import com.mingli.toms.Log;
import com.mingli.toms.R;

import element2.TexId;

public class Coin extends SixFruit {
	private int coinCount = 1;
	
	Coin(float x, float y) {
		super('1',x, y);
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
	public boolean loadAble(Player player){
		player.increaseScoreBy(score);
		playSound();
		return false;
	}
	
}
