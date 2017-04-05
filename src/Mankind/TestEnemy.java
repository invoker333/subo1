package Mankind;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.World;

import Enviroments.GrassSet;

public class TestEnemy extends EnemyGrass{
	public TestEnemy(char bi,GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
	}
	public TestEnemy(GrassSet gra, float x, float y) {
		super(' ',gra, x, y);
	}
	protected void init(){
		w=gra.getGrid()/4f;
		h=gra.getGrid()/4f;
		super.init();
	}
}
