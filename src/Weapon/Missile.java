package Weapon;

import javax.microedition.khronos.opengles.GL10;

import aid.Log;

import com.mingli.toms.World;

import element2.TexId;
import Element.Boom;
import Element.LightSpot;
import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

public class Missile extends TailBullet{
	
	
	BoomAttack boomA;
	public Missile(EnemySet es,GrassSet gra) {
		super(es,gra);
		attack=4*World.baseAttack;
		// TODO Auto-generated constructor stub
		setSize(12, 12);
		boomA=new BoomAttack(this);
	}
	
	void shot(){
		super.shot();		
		final float a=1.03f;
		xSpeed*=a;
		ySpeed*=a;
	}
	
	protected void gotTarget(Creature enemy){
		boomA.gotTarget(enemy);
	}
	public void drawElement(GL10 gl){
		boomA.drawElement(gl);
		super.drawElement(gl);
	}

}

 