package Weapon;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.World;

import element2.SceneTail;
import element2.Tail;
import element2.TexId;
import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;

public class TailBullet extends Bullet{
	Tail tail;

	public TailBullet(EnemySet es, float time) {
		super(es);
		setSize(4, 4);
		setTextureId(TexId.BULLET);
		tail=new Tail(6, TexId.CANDLETAIL);
		tail.width=(int) (w);
		this.attack=(int) (time*World.baseAttack);
	}
	
	public TailBullet(EnemySet es) {
		this(es, 4);
	}
	
	protected void gotTarget(Creature enemy) {
		
		if(enemy.getLife()>=attack){
			super.gotTarget(enemy);
		}// bullet will be reset 
		else {
			es.attacked(enemy, enemy.getLife());
			attack-=enemy.getLife();
			push(enemy);// bug严重 消失
		}
	}
	public void drawElement(GL10 gl){
		super.drawElement(gl);
		tail.tringer(x,y);
		tail.drawElement(gl);
	}
	public  void setPosition(float x,float y) {
		tail.startTouch(x+1,y+1);
		super.setPosition(x,y);
	}
	public void tringer(float x, float y, double sx, double sy){
		super.tringer(x, y, sx, sy);
		tail.startTouch(x, y);
	}
}
class ToBigBullet extends Bullet{
	float scaleTime=1,backTime=1;
	private float wReal;
	public ToBigBullet(EnemySet es) {
		super(es);
		setTextureId((TexId.BULLET));
		// TODO Auto-generated constructor stub
		attack=(int) (1f*World.baseAttack);
		setwReal(8);
	}
	public void drawElement(GL10 gl){
		shot();
		move();
		gravity();
		gl.glTranslatef(x, y, 0);
		
		if(isFire()){
			setW(getW() + 0.15f);
			scaleTime=getW()/wReal;
			backTime=1/scaleTime;
		}
		
		
		gl.glScalef(scaleTime, scaleTime, 0);
		super.baseDrawElement(gl);
		gl.glScalef(backTime, backTime, 0);
		gl.glTranslatef(-x, -y, 0);
	}
	void resetBullet(){
		super.resetBullet();
		setW(wReal);
		scaleTime=backTime=1;
	}
	public float getwReal() {
		return wReal;
	}
	public void setwReal(float wReal) {
		w=wReal;
		h=wReal;
		this.wReal = wReal;
	}
}