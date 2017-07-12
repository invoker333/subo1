package com.mingli.toms;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import aid.Log;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import element2.TexId;

public class Render implements Renderer{

	public  static float 	px,py;//	视角中心	
	public  static float 	width=1280,height;

	public static int frame;

	World world;

	private FloatBuffer fb;

	private int pointSize=4*4;

	private int bmHeight=50;

	private int bmWidth=50;

	 GL10 gl;
	private TexId texId;

	public  boolean created;
	public static float rate=1;

//	public static float hw;

	Render(World world,TexId texId){
		this.world=world;
		this.texId = texId;
		ByteBuffer bb=ByteBuffer.allocateDirect(bmHeight*bmWidth*pointSize);//为储存顶点坐标开辟缓存
		bb.order(ByteOrder.nativeOrder());
		fb=bb.asFloatBuffer();//顶点坐标
	}
	public void jiepin(){
		gl.glReadPixels (0, 0, bmWidth, bmHeight, GL10.GL_RGBA, GL10.GL_FLOAT, fb); 
//		BufferedWriter bw;
//		bw=new BufferedWriter(new Buffer(fb));
	}
	public void onSurfaceCreated(GL10 gl,
								 javax.microedition.khronos.egl.EGLConfig config) {
		Log.i("onSurfaceCreated");
		this.gl=gl;
		float alpha=0.9f;
//		gl.glClearColor(alpha, alpha, alpha,alpha);//清屏颜色 不设置则是黑色
//		gl.glClearColor(0.5f	, 0.6f, 1f,1);//清屏颜色 不设置则是黑色
		
		
		
//		gl.glEnable(GL10.GL_BLEND);
////		会出现黑边
//		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
//		GLES20.glEnable(GLES20.GL_BLEND);
//		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
//		gl.glBlendFunc(GL10.GL_ONE,GL10.GL_ONE);
		//让png背景显示透明 操你妈逼
//		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用矩阵绘制顶点模式
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnable(GL10.GL_TEXTURE_2D);//开启纹理贴图 至关重要

//		gl.glEnable(GL10.GL_CULL_FACE);//背面裁剪
		gl.glCullFace(GL10.GL_BACK);
		
		gl.glEnable(GL10.GL_LIGHT0);
//		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambient,0);
		
//		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();

		long t1=System.currentTimeMillis();
		if(!created)texId.loadTextureId(gl);
		created=true;
		Log.i("texTime"+-(t1-System.currentTimeMillis())+"ms");
		
		//gl.glFrustumf(-myWidth, myWidth, -myHeight, myHeight, 2*720, -720);
	}

	public void onSurfaceChanged(GL10 gl, int w, int h) {
//		Render.width=w;
		Render.height=h*width/w;
		rate=Render.width/MenuActivity.screenWidth;
		//gl.glMatrixMode(GL10.GL_PROJECTION);//矩阵模式 投影矩阵
		//gl.glLoadIdentity();//加载单位矩阵
	}
	
	public void onDrawFrame(GL10 gl) {
		long startTime = System.currentTimeMillis();//kaishihsijian
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();
//			speedView(Player.dpx,Player.dpy);
		gl.glOrthof(px,px+width,py,py+height,1000,-1000);
		
//		Log.i("render.px "+px,"Render.py"+py);// 跳频不是因为着里没错
//		float t=1280/720f;
//			gl.glFrustumf((px-width)/t,(px+width)/t,py/t,(py+height)/t,1,10);
//		gl.glFrustumf(-t,t,-t,t,1,10);
//			gl.glColor4f(red, green, blue, alpha);
//		gl.glRotatef(60, 1, 0, 0);
		
		world.drawElements(gl);
		
		frame++;
		
		long dTime = System.currentTimeMillis()-startTime;
		if(dTime<1000/60)
			World.flash=1000/60-dTime;
		else World.flash=0;
	}
	private void speedView(float dpx, float dpy) { //速度型视角
		Render.px=Render.px+dpx;
		Render.py=Render.py+dpy;
	}
	public void onDestory() {
		// TODO Auto-generated method stub
		created=false;
	}
}
