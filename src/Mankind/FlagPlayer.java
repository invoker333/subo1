package Mankind;

import com.mingli.toms.World;

import element2.SceneTail;
import element2.Tail;
import element2.TexId;
import Enviroments.GrassSet;

public class FlagPlayer extends Player{

	public FlagPlayer(char bi, GrassSet grassSet, World world,float x, float y) {
		// TODO Auto-generated constructor stub
		super(bi, grassSet,world, x, y);
		setFlag();
		setGotGoal(true);
		setPosition(x, y);
	}
	private void setFlag() {
		wudiTime=0;
		
		
		haveBlade();
		noGun();
		downData[0]=true;
		realBlade.tail=new Tail(25,TexId.REDCREEPER);
		realBlade.tail.setTextureId(TexId.FLAG);
		realBlade.setTextureId(TexId.QIGAN);
//		realBlade.angstart=60;
		realBlade.angstart=75;
		realBlade.h=4;
		realBlade.loadTexture();
	}
	protected void tooRight() {
		super.tooRight();
//		setAnimationFinished(true);
		downData[0]=true;
		downData[1]=false;
	}
	protected void tooLeft() {
		super.tooLeft();
//		setAnimationFinished(true);
		downData[0]=false;
		downData[1]=true;
	}
	public void die(){setGotGoal(true);}// avoid gameOver
	void sendIcon(int i){world.sendMessage(World.NOTREADICON);}
//	public void quitgame(){
//		for(int i=0;i<downData.length;i++){
//			downData[i]=false;
//		}
//	}

}
