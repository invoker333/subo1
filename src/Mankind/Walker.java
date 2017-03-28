package Mankind;

import com.mingli.toms.MusicId;
import com.mingli.toms.R;

import element2.TexId;
import Enviroments.GrassSet;

public class Walker extends Enemy {

	public Walker(char bi,GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
		setSoundId(MusicId.walker);
		// TODO Auto-generated constructor stub
		setH(getH()*19f/16);
		changeSize(0.7f);
		setxSpeedMax(getxSpeedMax()/4);
		setxSpeedMin(-getxSpeedMax());
	}
	public void sizeCheck(){
		setwEdge((int) (getW() * 55 / 64f));// 左边身体宽度
		sethEdge((int) (getH() * 62 / 64f));// 60/64f=15/16
		aniStepCheck();
	}
	protected void init(){
		super.init();
		setTextureId(TexId.WALKER);
	}
	public void attackAnotherOne(EnemySet es){
		Creature another;
		for (int i = 0; i < es.cList.size(); i++) {
			another = es.cList.get(i);
			if (Math.abs(x - another.x) < another.getwEdge() + getwEdge()
					&& another.y - another.gethEdge() < y
					&& another.y + another.gethEdge() > y - gethEdge()) {
				tooClose(another, es);
				
			}
		}
	}
	
	protected void tooClose(Creature another,EnemySet es) {
		// TODO Auto-generated method stub
		float dsmax=getwEdge()+another.getwEdge();
		final float tanxingxishu= 0.1f;
		final float zuni=1;
//		xWheelCheck(another, dsmax,tanxingxishu,zuni);
//		another.xWheelCheck(this,  dsmax*2,tanxingxishu,zuni);
		
		if(xSpeed/(another.x-x)>0)// faceto
			xPushCheck(another, dsmax, tanxingxishu, zuni);
		
		if(another.getxSpeed()>getxSpeedMax())another.setxSpeed(getxSpeedMax());
		else if(another.getxSpeed()<getxSpeedMin())another.setxSpeed(getxSpeedMin());
	}
}
