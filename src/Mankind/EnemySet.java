package Mankind;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Element.Animation;
import Element.BloodSet;
import Enviroments.GrassSet;
import Menu.State;

import com.mingli.toms.Log;
import com.mingli.toms.R;
import com.mingli.toms.World;

import element2.Set;
import element2.TexId;

public class EnemySet extends Set {
	private static final int _256 = 256;
	// no zero because it's began value is 0
	public static final int BALLER = 1001;
	public static final int WALKER = 1002;
	public static final int EMPLACEMENT = 1003;
	public static final int CREEPER = 1004;
	public static final int FLYER = 1005;
	public static final int HEDGEHOG = 1006;
	public static final int FIREBALL = 1007;
	public static final int SPIDE = 1008;
	public   int METAL;
	
	public ArrayList<Creature> cList=new ArrayList<Creature>();
	// 炮台和人是分开的
	public ArrayList<Emplacement> emplacementList;

	BloodSet bloodSet;
	private boolean CHASE_MODEL;
	protected Creature player;// 缓存
	private EnemySet enemySet;
	State lifecolumn;
	State backLife;
	float bloodW = 100;
	float bloodH = 16;
	private Animation guideCircle;
	private EnemySet friendSet;

	public EnemySet(GrassSet gra) {
		this.cList = gra.getEnemyList();
		this.emplacementList = gra.getEmplacementList();
		loadSound();
		bloodSet = new BloodSet(15);

		lifecolumn = new State(0, 0, bloodW, bloodH);
		lifecolumn.loadTexture();
		final int edge = 3;
		backLife = new State(-edge, -edge, bloodW + edge, bloodH + edge);
		backLife.loadTexture(TexId.BLACK);
		initGuideCircle();
	}

	public void setPlayer(Creature creature) {
		this.player = creature;
	}

	
	private void initGuideCircle() {
		// TODO Auto-generated method stub
		guideCircle = new Animation();
		guideCircle.setW(_256);
		guideCircle.setH(_256);
		guideCircle.loadTexture(TexId.GUIDECIRCLE);
	}
	public void resume() {
		super.resume();
		for (int i = 0; i < cList.size(); i++)
			cList.get(i).resume();
	}

	public void pause() {
		super.pause();
		for (int i = 0; i < cList.size(); i++)
			cList.get(i).pause();
	}

	public void loadSound() {
//		setSoundId(music.loadSound(R.raw.fresh));
//		METAL=music.loadSound(R.raw.kill);
//		int soundId;
		int FIREBALL1 = music.loadSound(R.raw.firecolumn);
		int BALLER1 = music.loadSound(R.raw.baller);
		int WALKER1 = music.loadSound(R.raw.walker);
		int EMPLACEMENT1 = music.loadSound(R.raw.gun2);
		int FLYER1 = music.loadSound(R.raw.flyer);
		int HEDGEHOG1 = music.loadSound(R.raw.hedgehog);
		int CREEPER1 = music.loadSound(R.raw.creeper4);
		int SPIDE1 = music.loadSound(R.raw.zhizhu);

		for (Creature e : cList) {
			switch (e.getSoundId()) {
			case FIREBALL:
				e.setSoundId(FIREBALL1);
				break;
			case BALLER:
				e.setSoundId(BALLER1);
				break;
			case WALKER:
				e.setSoundId(WALKER1);
				break;
			case EMPLACEMENT:
				e.setSoundId(HEDGEHOG1);
				((Emplacement)e).setSoundIdAttack(EMPLACEMENT1);
				break;
			case FLYER:
				e.setSoundId(FLYER1);
				((Flyer)e).walkerSoundId=WALKER1;
				break;
			case HEDGEHOG:
				e.setSoundId(HEDGEHOG1);
				((Hedgehog)e).creeperSoundId=CREEPER1;
				break;
			case CREEPER:
				e.setSoundId(CREEPER1);
				break;
			case SPIDE:
				e.setSoundId(SPIDE1);
				break;
			}
		}

	}


	private void randomAction(Creature c) {
		if (CHASE_MODEL)
			chasePlayer(c);
		c.randomAction();
		c.attackAnotherOne(enemySet);
	}

	private void chasePlayer(Creature c) {
		if (c.x < player.x)
			c.turnRight();
		else
			c.turnLeft();
	}

	public void drawElement(GL10 gl) {
		
		if(!World.rpgMode)timerTask();
		
		// gl.glColor4f(1f, 0.5f, 0.5f, 1f);
		if(((Player)player).touched)drawGuideCircle(gl);
		for (int i = 0; i < cList.size(); i++) {
			Creature c = cList.get(i);
			if (c.isDead) {
				c.drawDeath(gl);
				continue;
			}
			

			if (c.x > Player.gx1 && c.x < Player.gx2 && c.y > Player.gy1
					&& c.y < Player.gy2) {
				// player.wheelCheck(c);

				drawLifeColumn(c, gl);

				{
					randomAction(c);
					c.drawElement(gl);
					
				}
			} else
//				if (c.x > Player.hx1 && c.x < Player.hx2 && c.y > Player.hy1					&& c.y < Player.hy2) 
			{
				c.move();
				c.gravity();
			}
			// int j=i+1;
			// if(j>=eList.size()-1)j=0;
			// Creature whe=eList.get(j);
			// c.wheelCheck(whe);
			// whe.wheelCheck(c);
		}

		bloodSet.drawElement(gl);
		// gl.glColor4f(1,1, 1,1f);
	}

	public void timerTask() {
		// TODO Auto-generated method stub
		Creature self;
		Creature another;
		
		for (int j = 0; j < cList.size(); j++) {
			self = cList.get(j);
			if(!self.isDead)
			for (int i = 0; i < enemySet.cList.size(); i++) {
				another = enemySet.cList.get(i);
				if (Math.abs(self.x - another.x) < another.getW() + self.getW()
						&& another.y - another.gethEdge() < self.y
						&& another.y + another.gethEdge() > self.y - self.gethEdge()) {
					tooClose(self,another);
				}
			}
		}
	
	}

	private void tooClose(Creature self, Creature another) {
		// TODO Auto-generated method stub
		attacked(another,self.attack);
	}

	private void drawGuideCircle(GL10 gl) {
		for(Emplacement e:emplacementList){
			gl.glTranslatef(e.x, e.y, 0);
			float rate=(float)e.getRange()/(float)_256;
			gl.glScalef(rate, rate, 0);
			guideCircle.drawElement(gl);
			rate=1/rate;
			gl.glScalef(rate, rate, 0);
			gl.glTranslatef(-e.x, -e.y, 0);
		}
	}

	private void drawLifeColumn(Creature c, GL10 gl) {
		// TODO Auto-generated method stub

		float rate = (float) c.getLife() / (float) c.getLifeMax();
		if (rate <= 0)
			return;
		if (rate == 1)
			return;

		float w = c.x - bloodW / 2;
		float h = c.y + c.getH() + bloodH + 20;
		gl.glTranslatef(w, h, 0);

		backLife.drawElement(gl);

		gl.glScalef(rate, 1, 1);
		lifecolumn.drawElement(gl);
		gl.glScalef(1 / rate, 1, 1);

		gl.glTranslatef(-w, -h, 0);
	}

	public void resetSpirit() {
		for (int i = 0; i < cList.size(); i++)
			cList.get(i).resetSpirit();
	}

	public boolean attacked(Creature spi, int attack) {
		if (!spi.isDead) {
			spi.attacked(attack);
			bloodSet.tringerExplode(
					(float) (spi.x + spi.getwEdge() * (2 * Math.random() - 1)),
					(float) (spi.y + spi.getwEdge() * (2 * Math.random() - 1)),
					1.5f * spi.getxSpeed(), 1.5f * spi.getySpeed(),
					(int)(Math.random()*bloodSet.getCount()*Math.min(attack,spi.getLifeMax())/spi.getLifeMax()));
			if (spi.isDead) {// 被攻击致死
			// World.recycleDraw(spi);

				player.increaseScoreBy(spi.getScore());
				player.increaseChanceBy(spi.chance);

				// eList.remove(spi);
				return true;

			}
		}
		return false;

	}

	public void treaded(Player player, Creature spi, int attack) {
		spi.treaded(spi, attack);
		spi.playSound();
	}

	public void removeEnemy() {
		// TODO Auto-generated method stub

	}

	public boolean isCHASE_MODEL() {
		return CHASE_MODEL;
	}

	public void setCHASE_MODEL(boolean cHASE_MODEL) {
		CHASE_MODEL = cHASE_MODEL;
	}

	/*
	 * public Creature getPlayer() { // TODO Auto-generated method stub return
	 * player; }
	 */
	public void addEmplacement(Emplacement e) {
		emplacementList.add(e);
		cList.add(e);
	}

	public void addCreature(Creature e) {
		cList.add(e);
	}

	public void setFriendSet(EnemySet friendSet) {
		// TODO Auto-generated method stub
		this.friendSet=friendSet;
		for(Creature c:cList){
			c.friendList=friendSet.cList;
		}
	}
	public void setEnemySet(EnemySet es) { // di dui shi li
		this.enemySet = es;
		for(Creature c:cList){
			c.setEnemySet(es);
			c.enemyList=es.cList;
		}
		for (int i = 0; i < emplacementList.size(); i++) {
			emplacementList.get(i).initbullet(es);
		}
	}
}
