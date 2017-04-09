package Mankind;

import javax.microedition.khronos.opengles.GL10;

import Element.FireSet;
import Enviroments.GrassSet;

import com.mingli.toms.MusicId;
import com.mingli.toms.R;
import com.mingli.toms.World;

import element2.TexId;

public class FireBall extends Enemy{
	private float angleSpeed;
	private FireSet fireSet;
	public FireBall(char bi, GrassSet gra, float x, float y) {
		super(bi,gra, x, y);
		setJumpHeight((int) y);
		// TODO Auto-generated constructor stub
		changeLifeRate(0.1f);
		setSoundId(MusicId.firecolumn);
		attack=(int) (0.2f*World.baseAttack);
		
		fireSet=new FireSet(5,x,gra.getGrid());
	}
	public void drawElement(GL10 gl){
		super.drawElement(gl);
		fireSet.drawElement(gl);
	}
	protected void afterInit(){
		setG(0.3f);
		y=0;
		super.afterInit();
	}
	protected void init(){
		setTextureId(TexId.FIREBALL);
		loadSound();
		setW(32);setH(32);
		sizeCheck();
		setAnimationFinished(true);// �ܹ�����
		setTexture();
//		setW(-1);setH(-1);
	}
	  public void attackAnotherOne(EnemySet es){
		 Creature another;
			for (int i = 0; i < es.cList.size(); i++) {
				another = es.cList.get(i);
				if (Math.abs(x - another.x) < another.getW() + getW()
					&&Math.abs(y - another.y) < another.getW() + getH()) {
					tooClose(another,es);
				}
				else if (Math.abs(fireSet.x - another.x) < another.getwEdge() + fireSet.w
						&&another.y+another.h>fireSet.h
						&&another.y-another.h<fireSet.y+fireSet.h) {
						tooClose(another,es);
				}
			}
	}
	  protected void tooClose(Creature another,EnemySet es) {
		es.attacked(another, attack);// 伤人判定
	}
	protected void gravityCheck(){
//		if(false)super.gravityCheck();
		if(y<fireSet.h){
//			y=0;
			jump();
			if(x>Player.gx1&&x<Player.gx2&&y>Player.gy1
					&&y<Player.gy2)
				playSound();
		}
	}
	protected void moveCheck(){
		angleSpeed=ySpeed;
		setAngle(getAngle() +angleSpeed);
		xSpeed=0;
//		if(false)super.moveCheck();
	}
}
