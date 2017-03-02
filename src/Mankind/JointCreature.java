package Mankind;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import Weapon.Blade;
import Weapon.GunDraw;

import com.mingli.toms.Log;

import element2.ColorJoint;
import element2.Joint;
import element2.TexId;

public class JointCreature extends Creature {
	private ArrayList<Joint> jointList;
	private ArrayList<Joint> shakeList;
	protected ArrayList<Joint> holdList;
	float[] speedAgo;
	float[] speedJump;
	private Joint cap;
	protected GunDraw gunDraw;
	
	protected Blade blade;
	protected Joint hand;
	private GunDraw gunHand;
	private Joint normalHand;
	private GunDraw haveGunDraw;
	private GunDraw noGunDraw;
	protected Blade noBlade;
	ColorJoint foot;ColorJoint foot1;
	

	void haveGun(){
		int id;
		if((id=jointList.indexOf(normalHand))!=-1){
			
			jointList.set(id, gunHand);
			holdList.add(gunHand);
			hand=gunHand;
		}
		if((id=jointList.indexOf(noGunDraw))!=-1){
			jointList.set(id, haveGunDraw);
			holdList.add(haveGunDraw);
			gunDraw=haveGunDraw;
		}
		
	}
	void noGun(){
		int id;
		if((id=jointList.indexOf(gunHand))!=-1){
			
			jointList.set(id, normalHand);
			holdList.add(normalHand);
			hand=normalHand;
		}
		if((id=jointList.indexOf(haveGunDraw))!=-1){
			jointList.set(id, noGunDraw);
			holdList.add(noGunDraw);
			gunDraw=noGunDraw;
		}
	}
	public void init() {
		setW(36);
		setH(54);
		setwRate(28 / 36f);
		sethRate(54 / 54f);
		float x0 = getW();
		float y0 = getH();
		jointList = new ArrayList<Joint>();// blade 的空间也算上
		shakeList = new ArrayList<Joint>();// blade 的空间也算上
		
		jointList.add(foot1=new ColorJoint(this, -9, -24, 9, 0, 9, -31, TexId.FOOT));
		realBlade = new Blade(this, null);
		noBlade=new Blade(this,null){
			 public void drawElement(GL10 gl){};
			  public void drawTail(GL10 gl){};
			    public void attack(){}
		};
		jointList.add(noBlade);
		blade=noBlade;
		Joint body;
		jointList
				.add(body=new Joint(this, -x0, -y0, x0, y0, 0, 11, TexId.BODY, 1, 5));
		jointList.add(expression=new Joint(4, this, -20, -24, 20, 24, 16, 18,
				TexId.EXPRESSION, 1, 5));
		
		jointList.add(foot=new ColorJoint(this, -9, -24, 9, 0, -9, -31, TexId.FOOT, -1));
		Joint cloth;
		jointList.add(cloth=new Joint(this, -36, -25, 36, 25, 0, -12, TexId.CLOTH, 1,5));// �㷨����124 84
		jointList.add(cap = new Joint(this, -49, -31, 49, 31, 0, 60, TexId.CAP,
				1, 5));
			noGunDraw=new GunDraw(0, this, -32, -23, 96, 23, -10, -0,TexId.GUN, 1, 5){				public void drawElement(GL10 gl){}};
			haveGunDraw= new GunDraw(0, this, -32, -23, 96, 23, -10, -0,				TexId.GUN, 1, 5);
			normalHand=new Joint( this, -11, -23, 11, 5, -15, -0,				TexId.HAND);
			gunHand=new GunDraw(45, this, -11, -23, 11, 5, -15, -0,				TexId.HAND,1,5);
			
		gunDraw=noGunDraw;
//		hand
		jointList.add(noGunDraw);
		jointList.add(normalHand);// 38
		
		shakeList.add(foot1);
		shakeList.add(body);
		shakeList.add(foot);
		shakeList.add(cloth);
		shakeList.add(normalHand);
		shakeList.add(expression);
		shakeList.add(cap);
		
		holdList = new ArrayList<Joint>();
		


		// Log.i("jointList.size()", ""+jointList.size());
		sizeCheck();

		loadSound();
		speedAgo = new float[jointList.size()];
		speedJump = new float[jointList.size()];
		int i = 0;
		for (Joint joint : shakeList) {
			speedAgo[i] = joint.getSpeed();
			speedJump[i++] = joint.getSpeed() / 5;
		}
		setAnimationFinished(true);// �ܹ�����

	}

	public void jump(float rate) {
		super.jump(rate);
		for (int i = 0; i < shakeList.size(); i++) {
			shakeList.get(i).setSpeed(speedJump[i]);
		}
	}

	public void stopJump() {
		super.stopJump();
		for (int i = 0; i < shakeList.size(); i++) {
			shakeList.get(i).setSpeed(speedAgo[i]);
		}
	}

	public JointCreature(char bi,GrassSet gra,float	 x,float y) {
		super(bi, gra, x, y);
		setTextureId(TexId.CAP);
	}

/*	public void loadTexture() {
		for (Joint joint : jointList)
			joint.setTexture();
	}*/

	public JointCreature(GrassSet gra) {
		// TODO Auto-generated constructor stub
		super(gra);
	}

	public void setGunAngle(double d) {
		setAnimationFinished(false);
		float angle = (float) d;

		if (d < 90 && d > -90) {
			faceRight();
			for (Joint gunDraw : holdList) {
				gunDraw.setAngle(angle);
			}
		} else {
			faceLeft();
			for (Joint gunDraw : holdList) {
				if (d > 90)
					gunDraw.setAngle(180 - angle);
				else if (d < -90)
					gunDraw.setAngle(-180 - angle);
				Log.d("180-angle" + (180 - d), "180+angle" + (180 + angle));
			}

		}

	}

	protected void faceLeft() {
		setDirection(-1);
		for (int i = 0; i < jointList.size(); i++) {
			Joint joint = jointList.get(i);
			if (joint.getDirection() == 0)
				joint.start();
		}
		gunDraw.faceLeft();
		blade.faceLeft();
	}

	protected void faceRight() {
		setDirection(1);
		for (int i = 0; i < jointList.size(); i++) {
			Joint joint = jointList.get(i);
			if (joint.getDirection() == 0)
				joint.start();
		}
		gunDraw.faceRight();
		blade.faceRight();
	}

	float ang;
	protected Blade realBlade;
	private Joint expression;

	 protected void attack() {
		if (blade != null) {
			blade.setDirection(getDirection());
			blade.attack();
		}
	}

	public void drawElement(GL10 gl) {
		
		

	
		if (fdirection == 0 && Math.abs(ySpeed) < 2)
			changeState(0.01f, 0.25f, expression);
		else
			expression.setxState(0);

		gl.glTranslatef(x, y, 0);
		gl.glScalef(getxScaleRate(), getyScaleRate(), 0);

		float yAngle = getDirection() == -1 ? 180f : 0;
		gl.glRotatef(yAngle, 0, 1, 0);
		baseDrawElement(gl);
		gl.glRotatef(-yAngle, 0, 1, 0);

		gl.glScalef(1 / getxScaleRate(), 1 / getyScaleRate(), 0);
		gl.glTranslatef(-x, -y, 0);
		
		gravity();
		move();
		
		blade.drawTail(gl);
	
		
	}

	public void baseDrawElement(GL10 gl) {
		for (Joint joint : jointList){
			
			joint.drawElement(gl);
		}
	}

	private void changeState(float step, float stepy, Joint joint) {
		if ((int) joint.getxState() % 2 == 0)
			joint.setxState(joint.getxState() + step);
		else
			joint.setxState(joint.getxState() + stepy);

		if (joint.getxState() + step >= joint.getxCount()) {
			joint.setxState(0);
			// joint.animationFinished=true;
		}
	}

	public void stopMove() {
		super.stopMove();

		// 剑不能停
		for (int i = 0; i < shakeList.size(); i++) {
			shakeList.get(i).stop();
		}
	}

	void animation() {//

	}

	public Joint getCap() {
		return cap;
	}

	public void setCap(Joint cap) {
		this.cap = cap;
	}


	public void haveBlade() {
		// TODO Auto-generated method stub
//		holdList.add(realBlade);
		int id;
		if((id=jointList.indexOf(noBlade))!=-1)
			jointList.set(id,realBlade);
		blade = realBlade;
	}

	public void noBlade() {
		int id;
		if((id=jointList.indexOf(realBlade))!=-1)
			jointList.set(id,noBlade);
		blade = noBlade;
	}
	
	public void setEnemySet(EnemySet enemySet) {
		super.setEnemySet(enemySet);
		realBlade.setEs(enemySet);
	}
	void setFootColor(float red,float green,float blue,float alpha){
		 foot1.setColor(red, green, blue, alpha);
		 foot.setColor(red, green, blue, alpha);
	}

}
