package Enviroments;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Mankind.Player;

import com.mingli.toms.Music;
import com.mingli.toms.R;
import com.mingli.toms.World;

import element2.Set;
import element2.Tail;
import element2.TexId;

public class CoinSet extends FruitSet {

	public CoinSet(Player player, ArrayList<Fruit> fruitList, GrassSet gs,
			World world) {
		super(player, fruitList, gs);
		// TODO Auto-generated constructor stub
		this.world = world;
	}

	private World world;

	protected void picked(Fruit coin) {
		world.increaseCoin(((Coin) coin).getCoinCount());
		fruitList.remove(coin);//2016.10
		effectList.add(coin);
		coin.playSound();
	}
	public void loadSound() {
		setSoundId(music.loadSound(R.raw.coin));
		for (int i = 0; i < fruitList.size(); i++) {
			Fruit coin = fruitList.get(i);
			coin.setSoundId(getSoundId());
		}
	}

	public void playSound() {
		music.playSound(getSoundId(), 0);
	}

	public void drawElement(GL10 gl) {
		for (int i = 0; i < fruitList.size(); i++) {
			Fruit coin = fruitList.get(i);
			if (coin.visible)
				if (coin.x > Player.gx1 && coin.x < Player.gx2
						&& coin.y > Player.gy1 && coin.y < Player.gy2) {

					coin.drawElement(gl);
				}
		}
		timerTask();
	}
	protected void timerTask(){
		Fruit fruit;
		topGrassData = gs.getTop().data;// 没有这句就访问不了 权限问题？
		for (int i = 0; i < fruitList.size(); i++) {
			fruit = fruitList.get(i);
			pick(fruit);
		}
	}


	public float getStar() {
		return 1 - fruitList.size() / COUNT;
	}
}
