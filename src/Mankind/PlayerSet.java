package Mankind;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;

import com.mingli.toms.Map;
import com.mingli.toms.World;

public class PlayerSet extends EnemySet{
//	  Creature wheel;
//   	Creature wheel2;
//   	Creature wheel3;
//	private Tail body;
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
		this.cList=gra.friendList;
		cList.add(player);
		this.emplacementList=new ArrayList<Emplacement>();
		//////////////////////////new list
//		addCreature(greenWalker=new Walker(bi,gra,player.x,player.y));
//		greenWalker.setTextureId(TexId.GREENWALKER);
//		addCreature(new Baller(gra,player.x,player.y));
//		addCreature(new Flyer(gra,player.x,player.y));
//		addCreature(new JointCreature(gra,player.x,player.y));
//		addCreature(new JointCreature(gra,player.x,player.y));
//		addCreature(cp=new Creeper(bi, gra,player.x,player.y));
//		cp.changeSize(1.5f);
		
//		FireBall fb;
//		addCreature(fb=new FireBall(bi, gra,player.x,player.y));
//		fb.changeSize(1.5f);
		
//		addEmplacement(new EpAuto(bi,gra, 500, 500));
		
		if(World.curMapIndex==Map.max+1)
			for(int i=0;i<4;i++){
			JointCreature bfg;
			addCreature(bfg=new FlagMan(bi, gra, player.x+(i+1)*100, player.y));
			bfg.changeSize((float) (0.5f+Math.random()*0.5f));
			bfg.loadTexture();
		}
//		
		
//		   addCreature(  wheel=new ParticleBallRandom(gra));
//		   addCreature( wheel2=new ParticleBallRandom(gra));
//		   addCreature(wheel3=new ParticleBallRandom(gra));
//		   wheel3.setPosition(gra.getSx(), gra.getSy());
//	       wheel.setPosition(gra.getSx(), gra.getSy());
//	       wheel2.setPosition(gra.getSx(), gra.getSy());
//	       body = new Tail(4);
//	       body.width=4;
		
		
	}
	public void timerTask() {};
	public void drawElement(GL10 gl){
		
//		gl.glColor4f(1f, 1f, 1f, 0.5f);
		super.drawElement(gl);
//		gl.glColor4f(1, 1, 1, 1);
		
		
		

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
//       body.drawElement(gl);
       
		
	}
}
