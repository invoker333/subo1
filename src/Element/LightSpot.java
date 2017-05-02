package Element;

import javax.microedition.khronos.opengles.GL10;

import element2.TexId;


public class LightSpot extends Animation{
	protected float red=1;
	protected float green=1;
	protected float blue=1;
	protected float alpha;
	
	private float time=10;
	private float da=1/300f;
	private float dr=da;
	private float dg=da;
	private float db=da;
	
	protected float max=1f;
	protected float min=0f;
	public LightSpot(){
		init();
	}
	 void init() {
		// TODO Auto-generated method stub
		setW(8);
		setH(8);
		loadTexture(TexId.FIREWORK);
	}
	public void setRgb(float red,float green,float blue,float alpha){
		this.red = red;
		this.green = green;
		this.alpha = 0;
		this.blue = blue;
		//		super.setRgb(red, green, blue);
//		if(lightningTime<=1)lightningTime=maxLightningTime;
		dr=red/time;
		dg=green/time;
		db=blue/time;
		da=alpha/time;
	}
	void colorChange(){
		red += dr;
		green += dg;
		blue += db;
		alpha+=da;
		if(red>max||red<min)dr=-dr;
		if(green>max||green<min)dg=-dg;
		if(blue>max||blue<min)db=-db;
		if(alpha>max||alpha<min)da=-da;
	}
	public void drawElement(GL10 gl){
		colorChange();
		gl.glColor4f(red, green, blue, alpha);
		 gl.glTranslatef(x, y, 0);
		 gl.glRotatef(angle, 0, 0, 1);
	        super.baseDrawElement(gl);
	      gl.glRotatef(angle, 0, 0, 1);
	      gl.glTranslatef(-x, -y, 0);
		gl.glColor4f(1, 1, 1, 1);
	}
	
	
    void tringerInColor(float x,float y){
        tringer(x,y);
        setRgb((float)Math.random(),(float)Math.random(),(float)Math.random(),1);
    }
    public void tringer(float x,float y){
    	this.x=x;
        this.y=y;
    }
}