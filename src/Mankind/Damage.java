package Mankind;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.World;

import Element.Animation;

public class Damage extends Animation{
	int attack = World.baseAttack;

	public Damage(float x,float y) {
		setPosition(x, y);
		
	}
	
	void targetCheck(EnemySet es) {// ����Ŀ��
		Creature enemy;
		ArrayList<Creature> eList = es.cList;
		for (int i = 0; i < eList.size(); i++) {
			enemy = eList.get(i);
			if (!enemy.isDead)
				targetEnemyCheck(enemy, es);
		}
	}
	void targetEnemyCheck(Creature enemy, EnemySet es) {
		if (Math.abs(x - enemy.x) < enemy.getwEdge() + getW()
				&&Math.abs(y - enemy.y) < enemy.gethEdge() + getH()) {
				tooClose(enemy,es);
				
			}
	}
	  protected void tooClose(Creature another,EnemySet es) {
			es.attacked(another, attack);// 伤人判定
		}
	  public void drawElement(GL10 gl){
		  gl.glTranslatef(x, y, 0);
			super.drawElement(gl);
			gl.glTranslatef(-x, -y, 0);
		}
}
