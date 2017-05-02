package element2;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.GrassSet;
import Mankind.Creature;

public class FireworkSet extends SnowSet{
	
	public FireworkSet(int count,GrassSet gra) {
		super(count, gra);
	}
	public void resume(){		
	}
	public void loadTexture() {
		for(Creature firework:weatherList)
			firework.loadTexture(TexId.FIREWORK);
	}

	 void init(int count) {
		for (int i = 0; i < count; i++) {
			weatherList.add(new Firework(gra));
		}
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return weatherList.size();
	}
}
class Firework extends Rain{
	private float red=2;
	private float green=2;
	private float blue=2;
	
	private float time=10;
	private float dr;
	private float dg;
	private float db;
	private float alpha;
	private float da;
	public Firework(GrassSet gra) {
		super(gra);
		
		setRgb((float) Math.random(), (float) Math.random(), (float) Math.random(),(float) Math.random());
	}
	static float max=1f;
	static float min=0f;
	public void setRgb(float red,float green,float blue,float alpha){
		this.red = red;
		this.alpha = alpha;
		this.setGreen(green);
		this.blue = blue;
		//		super.setRgb(red, green, blue);
//		if(lightningTime<=1)lightningTime=maxLightningTime;
		dr=red/time;
		dg=green/time;
		db=blue/time;
		da=alpha/time;
	}
	void colorChange(){
		setRed(getRed() + dr);
		setGreen(getGreen() + dg);
		setBlue(getBlue() + db);
		alpha+=da;
		if(getRed()>max||getRed()<min)dr=-dr;
		if(getGreen()>max||getGreen()<min)dg=-dr;
		if(getBlue()>max||getBlue()<min)db=-db;
		if(alpha>max||alpha<min)da=-da;
	}
	public void drawElement(GL10 gl){
		colorChange();
		gl.glColor4f(red, green, blue, alpha);
		super.drawElement(gl);
		gl.glColor4f(1, 1, 1, 1);
	}
	protected void init(){
		super.init();
		setW(16);
		setH(16);
//		sethRate(0.9f);
//		setwRate(0.9f);
		sizeCheck();
	}
	protected void afterInit(){
		setG(0.1f);
		super.afterInit();
	}
	public float getRed() {
		return red;
	}
	public void setRed(float red) {
		this.red = red;
	}
	public float getGreen() {
		return green;
	}
	public void setGreen(float green) {
		this.green = green;
	}
	public float getBlue() {
		return blue;
	}
	public void setBlue(float blue) {
		this.blue = blue;
	}
}