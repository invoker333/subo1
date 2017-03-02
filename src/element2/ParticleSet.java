package element2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import Element.Animation;
import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.Player;

public class ParticleSet extends Set {
	static int topIndex;
	ArrayList<ParticleBroken> parList = new ArrayList<ParticleBroken>();
	private GrassSet gra;

	public ParticleSet(GrassSet gra, int count) {
		this.gra = gra;
		for (int i = 0; i < count; i++) {
			parList.add(new ParticleBroken(gra));
		}
	}

	public void tringerCheck(Animation a,int tringerCount, Creature cre,int textureId) {
		if (tringerCount > parList.size())
			tringerCount = parList.size();// 防止 out of index

		int doneCount = 0;// 已经触发的统计
		while (doneCount < tringerCount) {
			if (topIndex == parList.size()) 
				topIndex = 0;
				
			parList.get(topIndex).setPosition(
					(float) (a.x + 2*a.getW() * Math.random()),
					(float) (a.y + 2*a.getH() * Math.random()));
			parList.get(topIndex).tringer(cre.getxSpeed(), cre.getySpeed(),
					0.2f,textureId
					);
			parList.get(topIndex).setTringerId(0);
			doneCount++;
			topIndex++;
		}
//		Log.i("x "+parList.get(0).x, "y "+parList.get(0).y);
		
	}
	public void tringerCheck(int tringerId, int tringerCount, Creature cre) {
		if (tringerCount > parList.size())
			tringerCount = parList.size();// 防止 out of index

		float data[] = gra.getgList().get(tringerId).data;
		int doneCount = 0;// 已经触发的统计
		while (doneCount < tringerCount) {
			if (topIndex == parList.size())
				topIndex = 0;
			parList.get(topIndex).setPosition(
					(float) (data[0] + gra.getGrid() * Math.random()),
					(float) (data[1] + gra.getGrid() * Math.random()));
			parList.get(topIndex).tringer(cre.getxSpeed(), cre.getySpeed(),
					gra.getRate(tringerId),
					gra.getgList().get(tringerId).getTextureId());
			parList.get(topIndex).setTringerId(tringerId);
			doneCount++;
			topIndex++;
		}
//		Log.i("x "+parList.get(0).x, "y "+parList.get(0).y);
	}

	public void drawElement(GL10 gl) {
		for (ParticleBallRandom particle : parList)
			if (particle.x > Player.gx1 && particle.x < Player.gx2 && particle.y > Player.gy1
					&& particle.y < Player.gy2) {
				particle.drawElement(gl);
			}else if(particle.y>0){
				particle.move();particle.gravity();
			}
	}
}

class ParticleBroken extends ParticleBallRandom{

	public ParticleBroken(GrassSet gra) {
		super(gra);
	}
	public void setTexture(){
		bbSpi = ByteBuffer.allocateDirect(6 * spiSize);// 为储存顶点坐标开辟缓存
		bbSpi.order(ByteOrder.nativeOrder());
		fbSpi = bbSpi.asFloatBuffer();
		bbtex = new ByteBuffer[getxCount()][getyCount()];// *2
		fbtex = new FloatBuffer[getxCount()][getyCount()];

		float superW=64;
		float superH=64;//调用父类真实方块宽度
		float xCenter = (float) ((superW-getW())*Math.random()+getW()/2)/superW;
		float xW = getW() / superW;
		float yCenter = (float) ((superH-getH())*Math.random()+getH()/2)/superH;
		float yH = getH() /superH;
//			for (int i = 0; i < getxCount(); i++) {
//				for (int j = 0; j < getyCount(); j++) {// xy均等的人物图

		bbtex[0][0] = ByteBuffer.allocateDirect(texSize * 6);// 正反*2
		// 为储存顶点坐标开辟缓存
		bbtex[0][0].order(ByteOrder.nativeOrder());
		fbtex[0][0] = bbtex[0][0].asFloatBuffer();
		fbtex[0][0]
				.put(new float[] {
						xCenter,yCenter,
						xCenter+xW,yCenter+yH,
						xCenter-xW,yCenter+yH,
						xCenter-xW,yCenter-yH,
						xCenter+xW,yCenter-yH,
						xCenter+xW,yCenter+yH,
				});
		fbtex[0][0].flip();
//				}
//			}
/*		changeWidth();
		changeHeight();*/
		syncTextureSize();
	}
}