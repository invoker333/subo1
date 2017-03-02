package element2;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.World;

import Element.Animation;
import Enviroments.BackGround;
import Mankind.Creature;

public class LightningSet extends Set{
	ArrayList<Lightninig> weatherList = new ArrayList<Lightninig>(10);
	public LightningSet(int count) {
		
		init(count);
		
		loadTexture();
	}
	public void resume(){
	}
	public void loadTexture() {
		for(Animation lightning:weatherList)
			lightning.loadTexture(TexId.THUNDER);
	}

	void init(int count) {
		BackGround bg = new BackGround();
		for (int i = 0; i < count; i++) {
			weatherList.add(new Lightninig(bg));
		}
	}

	int tringerIndex;

	public void tringer(float x, float y,int count) {
		for (int i = 0; i < count; i++) {
			if(tringerIndex>=weatherList.size())tringerIndex=0;
			Lightninig lightning = weatherList.get(tringerIndex++);
			lightning.tringer(x, y);
		}
	}

	public void drawElement(GL10 gl) {
		if (!weatherList.isEmpty())
			for (Lightninig lightning : weatherList)
				if(lightning.alpha>-1)
					lightning.drawElement(gl);
	}
	public void tringer(double d, double e,int count) {
		tringer((float)d,  (float)e, count) ;
	}

//	public Creature getPlayer() {
//		return player;
//	}

	public void setEnemy(ArrayList<Creature>cList) {
		for(Lightninig lightNing:weatherList)
			lightNing.setEnemyList(cList);
//		this.player = player;
	}
}
class Lightninig extends Animation{
//	private Creature player;
	ArrayList<Creature>cList;
	private int attack;
//	private BackGround bg;

	Lightninig(float attackRate){
		attack = (int) (attackRate*World.baseAttack);
	}
	public void setEnemyList(ArrayList<Creature> cList) {
		this.cList = cList;
		// TODO Auto-generated method stub
	}
	Lightninig(BackGround bg){
//		this.bg = bg;
		attack = (int) (2.99f*World.baseAttack);
		
		final float range=2*getW();
		setW(range);
		setH(range);
	}

	private float blue;



	private float green;
	private float red;
	float alpha;
	static float max=1f;
	float aMax=0.02f;
	private float aSmall=aMax/1f;
	private float alphaA=aMax;
	float speedMax= (float) Math.sqrt(2*alphaA*max);
	float alphaSpeed;
	private int angle;

	public void setRgb(float red,float green,float blue){
		this.red=red;
		this.green=green;
		this.blue=blue;
	}
	void colorInc(){
		red+=alphaSpeed;
		green+=alphaSpeed;
		blue+=alphaSpeed;
		alpha+=alphaSpeed;
		if(alphaSpeed>0)alphaSpeed-=alphaA;
		else alphaSpeed-=aSmall;
	}
	public void drawElement(GL10 gl){
		colorInc();
		
		float alpha=this.alpha;
		final float time=max/1 *2;//1/2 倍
		gl.glColor4f(getRed()/time, getGreen()/time, getBlue()/time, alpha/time);
//		bg.drawElement(gl);
		gl.glColor4f(getRed(), getGreen(), getBlue(), alpha);
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(angle, 0, 0, 1);
		super.baseDrawElement(gl);
		gl.glRotatef(-angle, 0, 0, 1);
		gl.glTranslatef(-x, -y, 0);
		gl.glColor4f(1, 1, 1, 1);
	}
	void tringer(float x,float y){
		angle = (int) (360*Math.random());
		this.x=x;
		this.y=y;
		setRgb((float) Math.random(), (float) Math.random(), (float) Math.random());
		alphaA=aMax;
		alpha=speedMax;
		alphaSpeed= speedMax;
		for(Creature c:cList){
			if(Math.abs(c.x-x)<getW()+c.getwEdge()&&
					Math.abs(y-c.y)<c.gethEdge()+getH())
				c.attacked(attack);
			}
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
}
