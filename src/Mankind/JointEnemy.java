package Mankind;

import Enviroments.GrassSet;

import com.mingli.toms.World;

public class JointEnemy extends JointCreature{
	public Player player;
	 int cdMAX=World.baseActionCdMax;
	 int cd=cdMAX;
	public JointEnemy(char bi, GrassSet gra, float x, float y) {
		super(bi, gra, x, y);
		// TODO Auto-generated constructor stub
	}
	public void die(){
		super.die();
		if(player!=null)player.succeed();
	}
}
