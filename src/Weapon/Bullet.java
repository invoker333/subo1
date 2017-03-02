package Weapon;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.World;

import element2.TexId;
import Element.AnimationGrass;
import Element.AnimationMove;
import Enviroments.GrassSet;
import Mankind.Creature;
import Mankind.EnemySet;
import Mankind.Player;

public class Bullet extends AnimationMove{
	private boolean fire;// �ж��Ƿ���Ա�����
	// double xRange,yRange;
	int frame;// ��¼�ӵ������˶�����
	protected ArrayList<Creature> eList;
	// protected Creature p;//����Ʒ�
	int attack = World.baseAttack;
	// protected Creature enemy;//���� spirit
	// protected boolean fire;//������ ������˺�
	int frameMax = 30;
	public EnemySet es;
	public Bullet(EnemySet es) {
		this.es = es;
		this.eList = es.cList;
		setSize(12, 12);
		setTextureId(TexId.HIKARI);
	}

	void setSize(float w, float h) {
		this.setW(w);
		this.setH(h);
		setwEdge(w);
		sethEdge(h);
	}
	public void playSound() {
		music.playSound(getSoundId(), 0);
	}

	public void drawElement(GL10 gl) {
		shot();
		super.drawElement(gl);
	}

	void shot() {
		rangeCheck();
		targetCheck();
	}

	void rangeCheck() {

		// if (fire&&++frame >= frameMax) {
		// resetBullet();
		// }

		if (x < Player.gx1 || x > Player.gx2 || y < Player.gy1
				|| y > Player.gy2)
			resetBullet();

	}

	void targetCheck() {// ����Ŀ��
		if(!fire)return;
		Creature enemy;
		for (int i = 0; i < eList.size(); i++) {
			enemy = eList.get(i);
			if (!enemy.isDead)

				if (Math.abs(x - enemy.x) < (enemy.getwEdge() + getW())
						&& Math.abs(y - enemy.y) < (enemy.gethEdge() + getH())) {
					gotTarget(enemy);
				}
		}
	}

	protected void gotTarget(Creature enemy) {
		
		if (es.attacked(enemy, attack))
			push(enemy, 0.75f);
		else
			push(enemy);// bug严重 消失

		resetBullet();
		
//		if (true)
//			return;// 魂斗罗原理 消失即可用则发射更快 理论上不该有这行代码
//		disappear();
	}

	private void push(Creature enemy, float rate) {
		enemy.setxSpeed(enemy.getxSpeed() + xSpeed * rate);
		// if(!enemy.isJumpAble())
		enemy.setySpeed(enemy.getySpeed() + ySpeed * rate);
		// else enemy.jump(-ySpeed+1);
	}

	protected void push(Creature enemy) {// bug严重
		float rate = 0.25f;
		push(enemy, rate);
	}

	void resetBullet() {// �������·���
		frame = 0;
		fire = false;
		disappear();
	}

	protected void disappear() {// �ӵ���ʧ����Ұ
		setPosition(0, 0);
		setSpeed(0, 0);
	}

	public void setSpeed(double sx, double sy) {
		xSpeed = (float) sx;
		setySpeed((float) sy);
	}

	public void setFire() {// ���������й�����
		fire = true;
	}

	public void tringer(float x, float y, double sx, double sy) {
		setPosition(x, y); // 初始位置重置 start==true
		setSpeed(sx, sy);
		setFire();
	}

	public boolean isFire() {
		return fire;
	}

	public void setFire(boolean fire) {
		this.fire = fire;
	}

}