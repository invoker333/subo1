package Mankind;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import element2.Tail;
import element2.TexId;

public class PlayerSet extends EnemySet{
//	  Creature wheel;
//   	Creature wheel2;
//   	Creature wheel3;
	private Tail body;
	private Creature greenWalker;
	public PlayerSet(GrassSet gra, Player player,EnemySet enemySet) {
		this(gra, player);

	}
	
	public void setPlayer(Creature player) {
		this.player = player;
		setSystemAttacker(new Creature(player.getGra()));
	}
	 

	public PlayerSet(GrassSet gra, Player player) {
		super(gra);
		char bi=0;
		this.setPlayer(player);
		
		// TODO Auto-generated constructor stub
		this.cList=new ArrayList<Creature>();
		this.emplacementList=new ArrayList<Emplacement>();
		//////////////////////////new list
		addCreature(greenWalker=new Walker(bi,gra,player.x,player.y));
		greenWalker.setTextureId(TexId.GREENWALKER);
//		addCreature(new Baller(gra,player.x,player.y));
//		addCreature(new Flyer(gra,player.x,player.y));
//		addCreature(new JointCreature(gra,player.x,player.y));
//		addCreature(new JointCreature(gra,player.x,player.y));
//		addCreature(new Creeper(gra,player.x,player.y));
		addEmplacement(new EpAuto(bi,gra, 500, 500));
//		addCreature(new Shader(gra, 0.5f, player));
		
		
		   final float w=16;
//		   addCreature(  wheel=new ParticleBallRandom(gra));
//		   addCreature( wheel2=new ParticleBallRandom(gra));
//		   addCreature(wheel3=new ParticleBallRandom(gra));
//		   wheel3.setPosition(gra.getSx(), gra.getSy());
//	       wheel.setPosition(gra.getSx(), gra.getSy());
//	       wheel2.setPosition(gra.getSx(), gra.getSy());
	       body = new Tail(4);
	       body.width=4;
		
		
		player.setPlayerList(new ArrayList<Creature>(cList));
		// wanjia 能 踩 的 对象 不包括自己 
		cList.add(player);
	}
	public void timerTask() {};
	public void drawElement(GL10 gl){
		
		gl.glColor4f(1f, 1f, 1f, 0.5f);
		super.drawElement(gl);
		gl.glColor4f(1, 1, 1, 1);
		
		

    	final float dsmax= player.getW()*10;
    	final float tanxingxishu= 0.1f;
    	final 	float zuni=1;
		

//    	player.masterCheck(wheel,dsmax,tanxingxishu,zuni);
//    	player.masterCheck(wheel2,dsmax,tanxingxishu,zuni);
//    	player.masterCheck(wheel3,dsmax,tanxingxishu,zuni);
    	
    	
//		wheel. masterCheck2(wheel2,dsmax,tanxingxishu,zuni);
//	    wheel2. masterCheck2(wheel3,dsmax,tanxingxishu,zuni);
		
//		wheel.xPushCheck(wheel2, dsmax, tanxingxishu, zuni);
//		wheel2.xPushCheck(wheel, dsmax, tanxingxishu, zuni);
       
     
//       body.startTouch(player.x, player.y);
//     if(!wheel.isDead)  body.tringer(wheel.x, wheel.y);
//     if(!wheel2.isDead)   body.tringer(wheel2.x, wheel2.y);
//     if(!wheel3.isDead)   body.tringer(wheel3.x, wheel3.y);
//       body.tringer(x, y);
//       
//       wheel.drawElement(gl);
//       wheel2.drawElement(gl);
//       wheel3.drawElement(gl);
       body.drawElement(gl);
       
		
	}
}
