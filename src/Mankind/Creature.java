package Mankind;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Element.AnimationGrass;
import Enviroments.GrassSet;

import com.mingli.toms.Render;
import com.mingli.toms.World;

public class Creature extends AnimationGrass {
	protected   float DEATHSPEED = 0.032f;
	private int jumpHeight = 256;//
	private float g = 1;//
	private float ySpeedMax;
	private float amLand = 1f;
	float afLand = 1f;
	private float am;// �������ٶ�;
	private float af;// �������ٶ�
	/*
	 * ��Щ��ν�ļ��ٶ� �������ٶ� �������ٶ� �������ٶ� ��ʵ��
	 * ��Ϊ�ȼ����˶�ÿ����ʱ�����ٶȳɵȲ����� Ϊ�˷�������ȡ�� �Ȳ�
	 */
	private float xSpeedMax = 8;
//	private float xSpeedMax = 16;
	private float xSpeedMin = -xSpeedMax;
	private boolean jumpAble = true;
	private int landId;// �����̤���id��
	int fdirection;// ���ķ���
	protected int direction;

	private int lifeMax = World.baseLifeMax;
	private int life = lifeMax;
	private int score = 2;
	
	public int attack = World.baseAttack;// ������
	float speedSizeMax = xSpeedMax;// ����ߴ�ȷ�����ٶȼ���
	private float[] agoMax;// ��ʼ�����ֵ ���ǵ�����

	 ArrayList<Creature> friendList;
	 ArrayList<Creature> enemyList;
	
	public boolean isDead;// 死亡与否

	public Creature(GrassSet gra) {
		super(gra);
		init();
		afterInit();
		setJumpHeight(jumpHeight);// get yspeed max
		// frictionCheck();// ����Ħ�����ٶ�
	}

	protected void afterInit() {// ����ı���������
//		agoData = new float[] { 0, 0, gMax, amLand / 2 };
		agoMax = new float[] { getW(), getH(), jumpHeight, xSpeedMax, amLand,
				afLand, g };
	}

	protected void init() {// ���������ʾ��С�Զ�ȡ����
	
		sizeCheck();
//		setAnimationFinished(true);// �ܹ�����
		setTexture();
	}

	public Creature(char bi,GrassSet gra, float x, float y) {
		this(gra);
		mapSign=bi;
//		setPosition(x, y);
		setStartXY(x,y-gra.getGrid()+gethEdge());
		
		
		setPosition(startX,startY);
	}

	

	protected void autoClimb() {

		// final float a =1*afLand;//玲姐速度之
		// if(Math.abs(xSpeed)<a)return;//大于零戒指

		int my3 = getMy1() + 1;
		if (my3 < gra.getMapHeight() && gra.map[getMx1()][my3] != gra.getZero())
			return;// 上方为空

		float dy = gra.getGrid() - (y - gethEdge()) % gra.getGrid();
		if(dy>jumpHeight)dy=jumpHeight;// can
		
		dy+=16;// jump higher than 10
		int jumpSpeed =  (int) Math.sqrt(2 * g * dy);
		// Log.i("Dy"+dy, "jumpSpeed"+jumpSpeed);
		if (fallen)
			ySpeed += jumpSpeed;
	}

	void turnDown() {
		if (jumpAble && gList.get(landId). turnDown(this)) {
			y -= 1;
			land();
		}
	}

	void changeSpeed(int times) {
		if (times < 0) {
			setxSpeedMin(times * speedSizeMax);
			setxSpeedMax(-times * speedSizeMax);
		} else {
			setxSpeedMin(-times * speedSizeMax);
			setxSpeedMax(times * speedSizeMax);
		}
		aniStepCheck();
	}

	protected float sizeRate=1;

//	protected void culYScaleRate() {
//		setyScaleRate(2*sizeRate  - getxScaleRate());
//	}
	public void changeSize(float rate) {
		sizeRate=rate;
//		setH(agoMax[1] * rate);
//		ySpeed+=g;
		
		float h1 = getH();
		setH(agoMax[1] * rate);
		y = y + getH() - h1 + 0.2f;
		
		

		setW(rate * agoMax[0]);
		maxScaleLengthX=0.8f*w;
		sizeCheck();

		speedSizeMax = rate * agoMax[3];
		amLand = rate * agoMax[4];
		// gra.setAfLand(rate * agoMax[5]);
		jumpHeight = (int) (rate * agoMax[2]);
		ySpeedMax =  (float) Math.sqrt(2 * jumpHeight * g);
		xSpeedMax = agoMax[3] * rate;
		xSpeedMin = -xSpeedMax;
		
			setxScaleRate(rate);
			setyScaleRate(rate);
		
	}

	/*
	 * public void syncTextureSize(){ super.syncTextureSize();
	 * fbSpi_1=fbSpi;//����������� Ҳ��Ĭ������
	 * bbSpi1=ByteBuffer.allocateDirect(5*spiSize);//Ϊ���涥�����꿪�ٻ���
	 * bbSpi1.order(ByteOrder.nativeOrder());
	 * fbSpi1=bbSpi1.asFloatBuffer();//��������
	 * 
	 * fbSpi1.clear(); fbSpi1.put(new float[]{//����xy���� // x,y,0,
	 * -getW(),getH(),0, getW(),getH(),0, getW(),-getH(),0, -getW(),-getH(),0,
	 * -getW(),getH(),0, }); fbSpi1.flip();//����������ת����д��״̬ }
	 */

	public void stopJump() {
		setySpeed(0);
	}

	public void drawElement(GL10 gl) {
		super.drawElement(gl);
		animation();

	}

	float red = 1, green = 1, blue = 1;
	 float alpha = 1;

	void disappear(float rate) {
		red -= rate;
		green -= rate;
		blue -= rate;
		alpha -= rate;
	}

	public void drawDeath(GL10 gl) {
		if (alpha <= 0){
			return;// 死亡不画
		}
		move();
		gravity();
		disappear(DEATHSPEED);
		animation();

		float time = alpha / 1;
		float returnTime = 1 / alpha;

		// gl.glColor4f(red, green, blue, alpha);
		setAngle(getAngle() + rotateSpeed);
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(getAngle(), 0, 0, 1);
		gl.glScalef(time, time, 0);
		baseDrawElement(gl);
		gl.glScalef(returnTime, returnTime, 0);
		gl.glRotatef(-getAngle(), 0, 0, 1);
		gl.glTranslatef(-x, -y, 0);

		// gl.glColor4f(1,1,1,1);
	}

	public void gravity() {
		setyPro(y + getySpeed());
		gravityCheck();// shunxu bu neng huan  made
		setySpeed(getySpeed() - g);

		if (fallen)
			changeToLandData();
		else
			changeToAirData();// 判断是否在空气中

		y = getyPro();
		// setyPro(y + vt);
		// setVt(vt - g);
		// gravityCheck();
		// y = yPro;
		// changeHeight();
		// changePosition();
	}

	protected void changeToLandData() {
		setAf(afLand);
		setAm(amLand);
		jumpAble = true;
	}

	protected void tooDown() {
		super.tooDown();
		stopJump();
	}

	protected void moveCheck() {
		// if (speed > 0)
		// rightMove();
		// else if (speed < 0)
		// leftMove();
		super.moveCheck();
		speedCheck();

	}

	float index;// ����������
	protected boolean attackable=true;
	private boolean moving;
	private float aniStep = xSpeedMax / 80f;//
	float[] aniStep2 = { aniStep, 0.25f };
	int chance=1;
	protected int attackSoundId;
	protected EnemySet enemySet;



	protected void faceRight() {
		// setyState(2);
		// fbSpi=fbSpi1;
		direction = 1;
	}

	public void stopMove() {
		fdirection = 0;
		moving = false;
	}

	
	protected void turnRight() {
		fdirection = 1;// ͨ���������ķ�������ƶ� �����ı����Ĵ�С
		moving = true;
		if (isAnimationFinished()) {
			faceRight();
		}
	}

	protected void turnLeft() {
		fdirection = -1;
		moving = true;
		if (isAnimationFinished()) {
			faceLeft();
		}
	}

	protected void faceLeft() {
		// setyState(0);
		// fbSpi=fbSpi_1;
		direction = -1;
	}

	protected void attack() {
		// aniStep = aniStep2[1];
		index = 0;
		attackable = false;
		// if (direction == -1)
		setState(0, 1);
		// else
		// setState(0, 3);
	}

	void animation() {
		changeState(aniStep);
	}

	protected void speedCheck() {
		if (fdirection == 0) {
			if (xSpeed > 0) {// �����������
				if ((xSpeed += -af) < 0)
					xSpeed = 0;// ���ٲ��ܹ���
			} else if (xSpeed < 0) {// С�������
				if ((xSpeed += af) > 0)
					xSpeed = 0;// ���ٲ��ܳ���0
			}
		} else if (fdirection == -1 && xSpeed > xSpeedMin || fdirection == 1
				&& xSpeed < xSpeedMax) {
			xSpeed += fdirection * am;//
		}
	}

	// protected void dropCheck() {
	// ydata = gList.get(getLandId()).data;// ��i������
	// // float x1=data1[0];//x
	// if (x + wEdge < ydata[0] || x - wEdge > ydata[2])// ��̤������
	// land();// ������������yֵһ��������ʱ ���ǻ����ȥ��
	// }

	public void jump() {
		setySpeed(ySpeedMax);
		// setG(gMax);

		changeToAirData();
	}

	private void changeToAirData() {
		setAf(0f); // ���е������Ͷ���
		setAm(amLand /2);
		jumpAble = false;// ��ֹ������
	}

	public void jump(float rate) {
		if (rate > 1)
			setySpeed(rate);
		else if (rate > 0)// sqrt 为零 就是bug 一直出现的原因
			setySpeed( (float) Math.sqrt(2 * jumpHeight * rate *g));
		
		
		changeToAirData();
	}

	void land() {
		setySpeed(0);
	}

	void setY(float y) {
		this.y = y;
	}

	public int getLandId() {
		return landId;
	}

	public void setLandId(int landId) {
		this.landId = landId;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public void increaseScoreBy(int score) {
		this.setScore(this.getScore() + score);
	}
	public void increaseChanceBy(int ch){
		this.chance+=ch;
	}
	public void attacked(int attack) {
		baseAttacked(attack);
		// return false;
	}

	private void baseAttacked(int attack) {
		// TODO Auto-generated method stub
		if ((life-=attack) > 0) {
		} else {
			die();
			// return true;
		}
	}

	public int getScore() {

		return score;
	}

	public void resetSpirit() {
		setLife(1000);
		setScore(1);
		x = Render.px + Render.width / 2;
		y = Render.py + Render.height;
		stopJump();
		resume();
		isDead = false;
	}

	public float getAm() {
		return am;
	}

	public void setAm(float am) {
		this.am = am;
	}

	public void setAf(float af) {
		this.af = af;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	protected void setScore(int score) {
		this.score = score;
	}

	public int getLifeMax() {
		return lifeMax;
	}

	public void setLifeMax(int lifeMax) {
		life=life*lifeMax/this.lifeMax;
		this.lifeMax = lifeMax;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public boolean isAttackAble() {
		return attackable;
	}

	public void setAttackAble(boolean attacking) {
		this.attackable = attacking;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	// @Override
	// public void run() {
	// setLiving(true);
	// // while (isRunning()) { // ��������һֱ����
	// // move();
	// // gravity();
	// // World.timeRush();
	// // }
	// setLiving(false);
	// }

	public float[] getAniStep2() {
		return aniStep2;
	}

	public void setAniStep2(float[] aniStep2) {
		this.aniStep2 = aniStep2;
	}

	public int getJumpHeight() {
		return jumpHeight;
	}

	public void setJumpHeight(int jumpHeight) {
		this.jumpHeight = jumpHeight;
		ySpeedMax = (float) Math.sqrt(2 * jumpHeight * g);
	}

	public float getxSpeedMax() {
		return xSpeedMax;
	}

	public void setxSpeedMax(float speedMax) {
		this.xSpeedMax = speedMax;
	}

	public float getxSpeedMin() {
		return xSpeedMin;
	}

	public void setxSpeedMin(float speedMin) {
		this.xSpeedMin = speedMin;
	}

	public boolean isJumpAble() {
		return jumpAble;
	}

	public void setJumpAble(boolean jumpAble) {
		this.jumpAble = jumpAble;
	}

	public float getAf() {
		return af;
	}

	public void randomAction() {// ����
	/*
	 * switch ((int) (5 * Math.random())) { case 0: turnRight(); break; case 1:
	 * turnLeft(); break; case 2: stopMove(); break; case 3: if (jumpAble)
	 * jump(); break; case 4: if (isAnimationFinished()) { attack(); break; } if
	 * (fdirection != 0 && getDirection() != fdirection) {
	 * setDirection(fdirection); attack(); break; } break;
	 * 
	 * }
	 */
	}

	public float getAmMax() {
		return amLand;
	}

	public void setAmMax(float amMax) {
		this.amLand = amMax;
	}

	public float[] getAgoMax() {
		return agoMax;
	}

	public void setAgoMax(float[] agoMax) {
		this.agoMax = agoMax;
	}

	public void treaded(Creature player, int attack2) {// ����
//	 playSound();
		scaleTringer(maxScaleLengthX);
		attacked(attack2);
	}

	public float getAmLand() {
		return amLand;
	}

	public void setAmLand(float amLand) {
		this.amLand = amLand;
	}

	public GrassSet getGra() {
		return gra;
	}

	public void setGra(GrassSet gra) {
		this.gra = gra;
	}

	public void attackAnotherOne(EnemySet es) {
	}

	public void die() {
		// TODO Auto-generated method stub
		rotateSpeed = -xSpeed;
		isDead = true;
//		stopMove();
		am = 0;
	}


    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public void setRgb(float red, float green, float blue) {
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

	public void setGunAngle(double d) {
		// TODO Auto-generated method stub
		if (d < 90 && d > -90)
			faceRight();
		else
			faceLeft();
	}

	public void setSpeed(double xSpeed, double ySpeed) {
		// TODO Auto-generated method stub
		this.xSpeed=(float) xSpeed;
		this.ySpeed=(float) ySpeed;
	}

	public float getySpeedMax() {
		return ySpeedMax;
	}

	public void setySpeedMax(float ySpeedMax) {
		this.ySpeedMax = ySpeedMax;
	}
	void changeLifeRate(float rate){
		life*=rate;
		lifeMax*=rate;
	}

	public void setSoundIdAttack(int attackSoundId) {
		this.attackSoundId = attackSoundId;
		// TODO Auto-generated method stub
	}

	public void setEnemySet(EnemySet es) {
		// TODO Auto-generated method stub
		this.enemySet=es;
	}
}
