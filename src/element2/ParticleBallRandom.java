package element2;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import Mankind.Creature;

/**
 * Created by Administrator on 2016/7/7.
 */
public class ParticleBallRandom extends ParticleBall {
	public ParticleBallRandom(GrassSet gra) {
		super(gra);
		// TODO Auto-generated constructor stub
	}
	protected void init() {
		float baseRate = getGra().getGrid() / 8;// /2/2
		float length = baseRate + (float) Math.random() * baseRate;
		setW(length);
		setH(length);
		sethRate(0.9f);
		setwRate(0.1f);
		sizeCheck();
		setAngleRate((float) (360 / (gethRate()*getW()* 2 * Math.PI)));// 周长 与角速度
		loadTexture(TexId.CLOCK);
	}

}
