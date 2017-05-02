package Mankind;

import Enviroments.GrassSet;

import com.mingli.toms.MusicId;

import element2.TexId;

public class Walker extends Enemy {

	public Walker(char bi,GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
		setSoundId(MusicId.walker);
		
		setxSpeedMax(getxSpeedMax()/4);
		setxSpeedMin(-getxSpeedMax());
	}
	public void sizeCheck(){
		setwEdge((int) (getW() * 50 / 64f));// 左边身体宽度
		sethEdge((int) (getH() * 56 / 64f));// 60/64f=15/16
		aniStepCheck();
	}
	protected void init(){
		setH(0.6f*getH());
		w*=0.6f;
		setTextureId(TexId.WALKER);
		super.init();
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
