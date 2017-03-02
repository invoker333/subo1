package element2;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import Mankind.Creature;

public class ParticleBall extends Creature{


	public ParticleBall(GrassSet gra) {
		super(gra);
		// TODO Auto-generated constructor stub
	}
	private int tringerId;
	public void tringer(float vx, float vy, float rate, int textureId) {
		 setAngle(getAngle()%360);
		rate = (float) (Math.random() + rate);
		setxSpeed(rate * vx);// vx
		jump(vy * rate);// vy
		if(Math.random()>0.5) {//1/2gailv
			if(getW()<32)rotateSpeed = (float) (10 * (Math.random() - 0.5));//
			else rotateSpeed = (float) (5 * (Math.random() - 0.5));
		}
		setTextureId(textureId);
	}
	protected void autoClimb(){};
  	protected void init(){
		setAnimationFinished(true);
		sethRate(0.9f);
		setwRate(0.9f);
		
		sizeCheck();
		setAngleRate((float) (360 / (gethRate()*getW()* 2 * Math.PI)));// 周长 与角速度
		loadTexture(TexId.CLOCK);
	}

	private float angleRate;

	float getLineSpeed(float angleSpeed){
		return angleSpeed/angleRate;
	}
	float getAngleSpeed(float speed){
		return speed*angleRate;
	}
	public void drawElement(GL10 gl) {
		setAngle(getAngle() + rotateSpeed);
		super.drawElement(gl);
	}

	protected void tooLeft() {
		super.tooLeft();
		ySpeedCheck(-1);
	}

	protected void tooRight() {
		super.tooRight();
		ySpeedCheck(1);
	}

	void autoClimb1(){
		
	}
	protected void speedCheck(){
//		extracted();
//		speedLoss();
	}
	
	private void xSpeedLoss() {
		float fff=getRate(tringerId);// 不能用af
		if (xSpeed > 0) {// �����������
			if ((xSpeed -= fff) < 0)
				xSpeed = 0;// ���ٲ��ܹ���
		} else if (xSpeed < 0) {// С�������
			if ((xSpeed += fff) > 0)
				xSpeed = 0;// ���ٲ��ܳ���0
		}
		if (rotateSpeed > 0) {// �����������
			if ((rotateSpeed -= getAngleSpeed(fff)) < 0)
				rotateSpeed = 0;// ���ٲ��ܹ���
		} else if (rotateSpeed < 0) {// С�������
			if ((rotateSpeed += getAngleSpeed(fff)) > 0)
				rotateSpeed = 0;// ���ٲ��ܳ���0
		}
	}
	private void ySpeedLoss() {
		float fff=getRate(tringerId);// 不能用af
		if (ySpeed > 0) {// �����������
			if ((ySpeed -= fff) < 0)
				ySpeed = 0;// ���ٲ��ܹ���
		} else if (ySpeed < 0) {// С�������
			if ((ySpeed += fff) > 0)
				ySpeed = 0;// ���ٲ��ܳ���0
		}
	}
	private void xSpeedCheck(int yDirection) {
		float lineSpeed=getLineSpeed(rotateSpeed);
		float ff=getRate(tringerId);
		if(Math.abs(Math.abs(xSpeed)-Math.abs(lineSpeed))<=ff){
			xSpeedLoss();// 相差太小就不考虑了
			return;
		}
		if (xSpeed >- lineSpeed) {
			rotateSpeed+=getAngleSpeed(yDirection*ff);//rotateSpeed+
			setxSpeed(getxSpeed() + yDirection * ff);// xSpeed-
		} else if (xSpeed <- lineSpeed) {
			rotateSpeed+=getAngleSpeed(-yDirection*ff);
			setxSpeed(getxSpeed() + -yDirection * ff);
		}//以上只是两个速度互相转换 没有能量损失
		
	}
	private void ySpeedCheck(int xDirection) {
		float lineSpeed=getLineSpeed(rotateSpeed);
		float ff=getRate(tringerId);
		if(Math.abs(Math.abs(ySpeed)-Math.abs(lineSpeed))<=ff){
			ySpeedLoss();// 相差太小就不考虑了
			return;
		}
		if (ySpeed >- lineSpeed) {
			rotateSpeed+=getAngleSpeed(xDirection*ff);//rotateSpeed+
			setySpeed(getySpeed() + xDirection * ff);// xSpeed-
		} else if (ySpeed <- lineSpeed) {
			rotateSpeed+=getAngleSpeed(-xDirection*ff);
			setySpeed(getySpeed() + -xDirection * ff);
		}//以上只是两个速度互相转换 没有能量损失
	}



	float getRate(int grassId) {// 统一磨擦系数
//		return getGra().getRate(grassId);
		return .1f;
	}
	protected void gravityCheck() {
		super.gravityCheck();
		if(fallen){
			xSpeedCheck(-1);
			if(getySpeed()<2)stopJump();//下落时有加速度 所以 判断速度为零的点不好判断
			else jump();
		}

	}
	public void jump(float rate){
		setySpeed(rate);
	}
	public void jump() {
		float rate=getRate(tringerId) * -Math.abs(getySpeed());
		jump(rate);
	}

	protected void tooHigh() {
		super.tooHigh();
		xSpeedCheck(1);
	}


	public int getTringerId() {
		return tringerId;
	}

	public void setTringerId(int tringerId) {
		this.tringerId = tringerId;
	}

	public float getAngleRate() {
		return angleRate;
	}

	public void setAngleRate(float angleRate) {
		this.angleRate = angleRate;
	}
}

