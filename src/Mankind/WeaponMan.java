package Mankind;

import com.mingli.toms.World;

import element2.SceneTail;
import element2.Tail;
import element2.TexId;
import Enviroments.GrassSet;

public class WeaponMan extends Player{

	public WeaponMan(char bi, GrassSet grassSet, World world,float x, float y) {
		// TODO Auto-generated constructor stub
		super(bi, grassSet,world, x, y);
		attack=1;
		haveBlade();
		noGun();
		downData[0]=true;
		
		realBlade.tail=new SceneTail(20,TexId.REDCREEPER);
		realBlade.angstart=60;
		setPosition(x, y);
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

}
