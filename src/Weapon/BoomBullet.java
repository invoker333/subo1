package Weapon;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;
import element2.TexId;

public class BoomBullet extends  Bullet{

	private GrassSet gra;
	BoomAttack boomA;
	public BoomBullet(EnemySet es,GrassSet gra) {
		super( es,gra);
		// TODO Auto-generated constructor stub
		this.gra = gra;
		setSize(20, 20);
		setTextureId(TexId.BULLET);
		boomA=new BoomAttack(this);
	}
	
	public void gravity(){
		final float g=1;
		ySpeed-=g;
		super.gravity();
	}
	
	protected void gotTarget(Creature enemy){
		boomA.gotTarget(enemy);
	}
	public void drawElement(GL10 gl){
		boomA.drawElement(gl);
		super.drawElement(gl);
	}
	
	
}