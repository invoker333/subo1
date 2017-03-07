package Weapon;

import java.net.URL;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Mankind.Creature;
import Mankind.EnemySet;
import android.net.Uri;

import com.mingli.toms.Log;
import com.mingli.toms.MusicId;
import com.mingli.toms.R;

import element2.Joint;
import element2.Tail;
import element2.TexId;

public class Blade extends Joint {

	private static final float bloodGetRate = 0.5f;
	private static final float angleAMax = 3;
	float angleA;
//	private int attack = 100;
	private boolean fire;
	private Creature player;

	float attackHandCentre = 15;

	private EnemySet es;
	private float w1;
	private float w2; //range should be
	public Tail tail;
	private final int length = 192;
	private float attack=255;
	private final float checkWidth=64;

	public Blade(Creature p, EnemySet es) {
		this.es = es;
		this.player = p;

		setSpeed((angend - angstart) / (3f / p.getAniStep2()[1]));
		loadTexture(TexId.SWORD);
		loadSound();
		// holdy = HandCentre;// meishayong

		tail = new Tail(5, TexId.WIND);
		// tail.width=length/2;
		angstart = 130;
		angend = -20;
	}

	 public void syncTextureSize() {
		distanceToHeart = 32f;
		length2 = distanceToHeart + length;
		final float h = 24f;
		w1 = distanceToHeart * distanceToHeart;
		w2 = (float) Math.pow(length2 + checkWidth, 2);// big shangixng let check width 2 times bigger is  every one's body width
		fbSpi.clear();
		fbSpi.put(new float[] { distanceToHeart, -h, getDepth(), length2, -h, getDepth(), length2, h,
				getDepth(), distanceToHeart, h, getDepth(), distanceToHeart, -h, getDepth() });
		fbSpi.flip();
	}

	public void drawElement(GL10 gl) {
		super.drawElement(gl);
	}

	public void drawTail(GL10 gl) {
		// if(!fire)return;
		tailTringer();

		tail.drawElement(gl);
		// }
	}

	private void tailTringer() {
		// TODO Auto-generated method stub
		setDagree(getDagree() + angleA);
		double anglePi = getAngle() / 180 * 3.14;

		float length = player.getxScaleRate() * this.length / 1.4f;// 触发点

		float cos = (float) Math.cos(anglePi) * getDirection();
		float sin = (float) Math.sin(anglePi);

		float xx = player.x + (cos * (xp + length));// ��
		float yy = player.y + (sin * (yp + length));// +r

		float width = player.getxScaleRate() * this.length / 2f;
		tail.width = (int) width;// change size AutoMa..cly
		tail.tringer(xx, yy, -cos, sin);
	}

	void targetCheck() {// ����Ŀ��

		double s2, dx, dy;
		ArrayList<Creature> sList = es.cList;
		Creature spi;
		
		int size=sList.size();//to avoid if the size is changed in attacking
		for (int i = 0; i <size ; i++) {
			spi = sList.get(i);
			if(spi.isDead)continue;
			

			
			
			dx = spi.x - player.x;// a bigger shanxing
			dx=getDirection()==-1?-dx:dx;
			dy = spi.y - player.y;//if you want to know why  ) please look draw picture let check more right
			s2 = Math.pow(dx, 2) + Math.pow(dy, 2);
			s2=s2/(player.getyScaleRate()); //mormal it is equals i do not know why these two xiangcheng now but i think i know but i am a little mengbi
			
			if (
//					w1 < s2 && 
					s2 < w2)
			{
				
				double eAngle = Math.atan2(dy, dx);
				float realAngle=(float) (eAngle*180.0/Math.PI);
				if(realAngle>angend&&realAngle<angstart
						||
						dy<0&&dy>-(spi.gethEdge()+gethEdge())
						&&dx>0&&dx>length2+spi.getwEdge())
					gotTarget(spi);
			}
		}

	}

	public void stop() {

		setDagree(0);
		angleA = 0;
		fire=false;
	}

	public void positionCheck() {
		setAngle(getAngle() + getDagree());// round
		// setAngle(getAngle()%360);//无限转防止
//		if (!fire) {
//			super.positionCheck();
//			return;
//		}

		if (getAngle() > angstart) {
			
			
			stop();
		} else if (getAngle() < angend) {
			// setDagree(- getDagree()/2);
			player.setAnimationFinished(true);// holder can translate body
			setDagree(0);
//			angleA = angleAMax / 2;
			angleA = angleAMax / 5 ;
			
//			angleA = angleAMax / 25 ;
			
			setAngle(angend);

			
			targetCheck();
		}
	}

	public void attack() {
		if (fire)
			return;
		player.setAnimationFinished(false);
		fire = true;
		
		playSound();
		yp = attackHandCentre;
		setAngle(angstart);
		angleA = -angleAMax;

//		tail.startTouch(p.x, p.y);
	}

	protected void gotTarget(Creature c) {
		// if (!soundPlayed) {
		c.playSound();
		// soundPlayed = true;
		// }
		
		final float speedToAttackRate = 15;
		int demage=(int) (speedToAttackRate*(Math.abs(getDagree())+Math.abs(player.getxSpeed())+Math.abs(player.getySpeed()))+attack);
		// c's speed has not calculate
		es.attacked(player,c, demage);
			player.attacked((int) (-demage*bloodGetRate));
			if(player.getLife()>player.getLifeMax())
				player.setLife(player.getLifeMax());
		
	}

	public void loadSound() {
		setSoundId(MusicId.sword);// �Ӵ�Ŀ�������
		
	}

	public void playSound() {
		music.playSound(getSoundId(), 0);
	}

	public void resume() {
		if (!isRunning()) {
			setRunning(true);
			// new Thread(this).start();
		}
	}

	// public void run() {
	// while (isRunning()) {
	// if (fire) {
	// positionCheck();
	// targetCheck();
	// } else {
	// // if(yp!=holdy)
	// // yp+=16f;
	// }
	// World.timeRush();
	// }
	// }

	float anglface = -75;
	float angrface = -105;

	// float holdx = -16;
	float holdx = 0;
	public static float holdy = 120;
	private float distanceToHeart;
	private float length2;

	/*
	 * public void faceLeft() { setAngle(anglface); yp = holdy; xp = holdx;
	 * setDirection(-1); // super.faceLeft(); }
	 * 
	 * public void faceRight() { setAngle(angrface); yp = holdy; xp = -holdx;
	 * setDirection(1); }
	 */

	public boolean isFire() {
		return fire;
	}

	public void setFire(boolean fire) {
		this.fire = fire;
	}

	public void addEnemySet(EnemySet es) {
		this.es = es;
		// TODO Auto-generated method stub
	}

	public EnemySet getEs() {
		return es;
	}

	public void setEs(EnemySet es) {
		this.es = es;
	}
}
