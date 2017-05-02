package Enviroments;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Mankind.Player;

import com.mingli.toms.MusicId;
import com.mingli.toms.World;

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
		coin.loadAble(player);
		coin.use(player, pickedList);
		fruitList.remove(coin);//2016.10
		effectList.add(coin);
	}
	public void loadSound() {
		setSoundId(MusicId.coin);
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
			if (coin.x > Player.gx1 && coin.x < Player.gx2
					&& coin.y > Player.gy1 && coin.y < Player.gy2) {
				coin.drawElement(gl);
			}
		}
		for (int i = 0; i < effectList.size(); i++) {
			Fruit coin = effectList.get(i);
			if (coin.x > Player.gx1 && coin.x < Player.gx2
					&& coin.y > Player.gy1 && coin.y < Player.gy2) {
				coin.drawElement(gl);
			}
		}
		timerTask();
	}
	protected void timerTask(){
		Fruit fruit;
		for (int i = 0; i < fruitList.size(); i++) {
			fruit = fruitList.get(i);
			pick(fruit);
		}
		for (int i = 0; i < effectList.size(); i++) {
			fruit = effectList.get(i);
			if(!fruit.visible)effectList.remove(i);
		}
	}


	public float getStar() {
		return 1 - fruitList.size() / COUNT;
	}
}
