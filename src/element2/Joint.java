package element2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;
import Mankind.Creature;
import Menu.State;

public class Joint extends State {
	private float angle;
	private float dagree;
	protected float xp;// 立足点
//	private float xpReal;// 初始立足点
	protected float yp;
	protected int direction;
	int myDirection=1;


	float mAngle = 60;
	public float angstart = -mAngle;
	protected float angend = mAngle;
	private float speed;
//	private float angleDraw;
//	private FloatBuffer fbSpi_1;
//	private ByteBuffer bbSpi1;
//	private FloatBuffer fbSpi1;

	public Joint(Creature p,float x1, float y1, float x2, float y2) {
		super(x1,y1,x2,y2);
		init();
		speed = (angend - angstart) / (3f / p.getAniStep2()[1]);
		loadSound();
	}
	public Joint(Creature p, float x1, float y1, float x2, float y2,
				 int textureId) {
		this(p,x1, y1, x2, y2);
		loadTexture(textureId);
	}
	public Joint(Creature p, float x1, float y1, float x2, float y2, float xp,
				 float yp, int textureId) {
		this(p, x1, y1, x2, y2, textureId);
		this.xp = xp;
//		xpReal = xp;
		this.yp = yp;
	}
	public Joint(Creature p, float x1, float y1, float x2, float y2, float xp,
				 float yp, int textureId, int myDirection) {
		this(p, x1, y1, x2, y2, xp, yp, textureId);
		this.myDirection = myDirection;
	}
	public Joint(Creature p, float x1, float y1, float x2, float y2, float xp,
				 float yp, int textureId, int myDirection,int mAngle) {
		this(p, x1, y1, x2, y2, xp, yp, textureId,myDirection);
		this.mAngle = mAngle;
		angstart = -mAngle;
		angend = mAngle;
		speed =2* (angend - angstart) / (3f / p.getAniStep2()[1]);
	}
	public Joint(int xCount,Creature p, float x1, float y1, float x2, float y2, float xp,
				 float yp, int textureId, int myDirection,int mAngle) {
		this( p,  x1,  y1,  x2,  y2,  xp, yp,  textureId,  myDirection, mAngle);
		setxCount(xCount);
		setTexture();
//		setyCount(yCount);
	}

	public Joint() {
		// TODO Auto-generated constructor stub
	}

	protected void init() {
		// TODO Auto-generated method stub

	}

	public void start() {
		direction = myDirection;
		setDagree(speed * direction);
	}

	public void stop() {
		direction = 0;
	}
	public void faceLeft() {
//		dagree=-dagree;
		direction=-1;
	}

	public void faceRight() {
		direction=1;
	}
	public void drawNotMove(GL10 gl) {
		positionCheck();
		baseJointDrawElement(gl);
	}
	public void drawElement(GL10 gl) {
		incAngle();
		drawNotMove(gl);
	}
	public void incAngle() {
		angle += dagree;
	}

	public void baseJointDrawElement(GL10 gl) {
		gl.glTranslatef(xp, yp, 0);
		gl.glRotatef(angle, 0, 0, 1);
		super.drawElement(gl);
		gl.glRotatef(-angle, 0, 0, 1);
		gl.glTranslatef(-xp, -yp, 0);
	}
	public void positionCheck() {
		if(direction==0) {//逆时针为证
			if (angle > speed)//dagree>0
				setDagree(-speed);
			else if (angle < -speed)
				setDagree(speed);
			else {
				setDagree(0);
				angle=0;
			}
		}else {
			if (angle < angstart) {
				angle = angstart;
				direction = 1;
			} else if (angle > angend) {
				angle = angend;
				direction = -1;
			}
			setDagree(direction * speed);
		}
	}


	public float getDagree() {
		return dagree;
	}

	public void setDagree(float dagree) {
		this.dagree = dagree;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
}

