package Mankind;

import Enviroments.GrassSet;

import com.mingli.toms.World;

public class JointEnemy extends JointCreature{
	private Player player;
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
	public Player getPlayer() {
		return player;
	}
	public void setPlayerAndToBeBoss(Player player) {
		setLifeMax(5*World.baseAttack);
		this.player = player;
	}
}
