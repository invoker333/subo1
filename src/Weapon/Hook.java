package Weapon;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;
import Mankind.Player;

import com.mingli.toms.World;

import element2.TexId;

public class Hook extends TailBullet {
	boolean back;
	public final  int speed = 20;
	private Creature player;
	protected Creature huckEnemy;
	private  int range=256;
	private boolean getGrass;
	Creature hookGrass;
	private int hookLength;
	
	public Hook(EnemySet es,GrassSet gra,  Creature player) {
		super(es,   gra, 2);// 必须是2 //以上也行 浪费了
		// super(es, attack);
		// TODO Auto-generated constructor stub
		attack=(int) (0.2f*World.baseAttack);
		this.player = player;
		hookGrass=new Creature(gra);
		setSize(12,12);
		
		setTextureId(TexId.GAO);
		tail.setTextureId(TexId.WIPE);
		tail.w=4;
		
		frameMax=range/speed;
	}

	public void drawElement(GL10 gl) {
		if(hookGrass.isDead) {
			getGrass=false;
		} else {
			if(getGrass){
				hookGrass.stringCheck(player, hookLength, 0.3f, 0.95f);
				
				tail.startTouch(player.x, player.y);
				tail.tringer(hookGrass.x,hookGrass. y);
				tail.drawElement(gl);
				return;
			}
		}
		
		shot();

		move();
		gravity();
		gl.glTranslatef(x, y, 0);
		super.baseDrawElement(gl);
		gl.glTranslatef(-x, -y, 0);

		if (isFire()) {
			tail.startTouch(player.x, player.y);
			tail.tringer(x, y);
			tail.drawElement(gl);
		}
	}

	protected void gotTarget(Creature enemy) {
		if(enemy.equals(enemyGrass)){
			hookGrass.setPosition(enemyGrass.x, enemyGrass.y);
			getGrass=true;
			
			hookLength=(int) Math.sqrt(Math.pow(enemyGrass.x-player.x,2)+Math.pow(enemyGrass.y-player.y,2));
			final float springLength=30;
			hookLength-=springLength;
			// a little distance from grass's centre to player.center

			
//			Log.i("hookLength"+hookLength);
			hookGrass.isDead=false;
			resetBullet();
			return;
		}
		
		enemy.playSound();
//		enemy.setxSpeed(0);enemy.setySpeed(0);//防止乱跑 duociyiju
		huckEnemy = enemy;
		x = enemy.x;
		y = enemy.y;// 附体
		back = true;
		enemy.attacked(attack);
	}

	void rangeCheck() {

		if (isFire() && ++frame >= frameMax) {
			back = true;
		}
	}

	void targetCheck() {// ����Ŀ��
		if (back)
			return;
		super.targetCheck();
	}

	void shot() {
		super.shot();
		if (back) {
			speedCheck(player.x, player.y);
			if (huckEnemy != null) {// 被别人拉走
				huckEnemy.setPosition(x, y);
				if (Math.abs(x - huckEnemy.x) < (huckEnemy.getwEdge() + getW())
						&& Math.abs(y - huckEnemy.y) < (huckEnemy.gethEdge() + getH())) {
				} else {
					letGo();
				}
			}
			if (Math.abs(x - player.x) < (player.getwEdge() + getW())
					&& Math.abs(y - player.y) < (player.gethEdge() + getH())) {

				if (huckEnemy != null)
					letGo();
				resetBullet();
				back = false;
			}
		}
	}

	 protected void letGo() {
		huckEnemy.setxSpeed(xSpeed);
		huckEnemy.setySpeed(ySpeed);
		huckEnemy = null;
	}

	void speedCheck(float x, float y) {
		float dx = x - this.x;
		float dy = y - this.y;
		float s = (float) Math.sqrt((Math.pow(dx, 2) + Math.pow(dy, 2)));
		// s==0 bug 但是问题还没出现
		xSpeed = speed * dx / s;
		ySpeed = speed * dy / s;
	}

	public void tringer(float x, float y, double sx, double sy) {
		if (back)
			return;
		super.tringer(x, y, sx, sy);
		getGrass=false;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
		frameMax=range/speed;
	}
}
