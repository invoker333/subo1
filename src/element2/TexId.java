package element2;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import aid.Log;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.opengl.GLUtils;

import com.mingli.toms.R;

public class TexId{
	public TexId(Context context){
		this.context = context;
		textures = new int[TexId.textureCount];//
		resIcon = new int[TexId.textureCount];//
	}
	public int[] textures;
//	private int maxId=0;// add auto in start
	private GL10 loadGl;
	public static int[] resIcon;
	
	public static  int BAMBOOPIPLE ;
	public static int PIFENG;
	public static int STONEGRASS;
	public static int PRICKX;
	public static int EGG;
	public static int BULLET;
	public static int BACK;
	public static int MORNING;
//	public static int BUBBLE;
	public static int RED;
	public static int CLOCK;
	public static int COIN;
	public static int WALKER;
	public static int GREENWALKER;
	public static int CREEPER;
	public static int REDCREEPER;
	public static int HEDGEHOG;
	public static int WATERBALL;
	public static int NEXT;
	public static int NUMBER;
	public static int TOMENU;
	public static int FIREBALL;
	public static int ZHUAN;
	public static int BANK;
	public static int RESULT;
	public static int SWORD;
	public static int PAUSE;
	public static int ATTACK;
	public static int BLANK;
	public static int GOAL;
	public static int EVENING;
	public static int SUNSET;
	public static int TIANSHAN;
	public static int STONE;
	public static int WOOD;
	public static int TREE;
	public static int H;
	public static int SOIL;
	public static int SOILGRASS;
	public static int NUMBERSMALL;
	public static int FOG;
	public static int TOMATO;
	public static int JINGUBANG;
	public static int BLUE;
	public static int GREEN;
	public static int DESERT;
	public static int FLYER;
	public static int BALLER;
	public static int BODY;
	public static int HAND;
	public static int FOOT;
	public static int CLOTH;
	public static int CAP;
	public static int PRICK;
	public static int BAMBOO;
	public static int BAMBOOHEART;
	public static int TOUKUI;
	public static int GAO;
	public static int HIKARI;
//	public static int HIKARI2;
	public static int EXPRESSION;
//	public static int ICE;
	public static int RAIN;
	public static int SNOW;
	public static int WIND;
	public static int FIREWORK;
	public static int THUNDER;
	public static int BOOM;
	public static int BLACK;
	public static int RAINBOW;
	public static int LIGHTNING;
	public static int LIGHTSPOT;
	public static int CANDLETAIL;
	public static int GUN;
	public static int BLOOD;
	public static int FIRE;
//	public static int FIRE2;
	public static int CANDLEPOR;
	public static int PAOTA;
	public static int GUIDEPOST;
	public static int GUIDECIRCLE;
	public static int SHOTGUN;
	public static int JUJI;//musket hua tang qiang
	public static int MISSILE;
	public static int HOOKGUN;
	public static int CUP;
	public static int LIGHTTAIL;
	public static int K;//knife
	public static int GUANGDANQIANG;
	public static int WOODROOT;
	
	
	public static int GOALCIRCLE;
	public static int GUIDERECT;
	public static int FRUITFLY;
	public static int ZIDONGDAN;
	public static int SHUFUDAN;
	public static int GOLDENBANK;
	public static int ZAN;
	public static int BOOMGUN;
	public static int QIGAN;
	public static int WIPE;
	public static int GOLDENFOOT;
	public static int STICKER;
	public static int CLOTHENEMY;
	public static int CAPENEMY;
	public static int FLAG;
	public static int EXPRESSIONENEMY;
	public static int PUTONGQIANG;
	public static int FENSHEN;
	public static int DIALOGTRINGLE;
	
	public static int textureCount=200;//纹理数量 行号之差
	
	public  void loadTextureId(GL10 gl) {
		this.loadGl=gl;
//		if(1<2)return;
		DIALOGTRINGLE=loadTexture(gl,R.drawable.dialogtringle);
		FENSHEN=loadTexture(gl,R.drawable.fenshenguo);
		PUTONGQIANG=loadTexture(gl,R.drawable.putongdan);
		EXPRESSIONENEMY=loadTexture(gl,R.drawable.espressionenemy);
		FLAG=loadTexture(gl,R.drawable.flag);
		CAPENEMY=loadTexture(gl,R.drawable.toukuienemy);
		CLOTHENEMY=loadTexture(gl,R.drawable.clothenemy);
		STICKER=loadTexture(gl,R.drawable.sticker);
		GOLDENFOOT=loadTexture(gl,R.drawable.goldenfoot);
		BAMBOOPIPLE=loadTexture(gl,R.drawable.bamboopiple);
		WIPE=loadTexture(gl,R.drawable.wipe);
		QIGAN=loadTexture(gl,R.drawable.qigan);
		BOOMGUN=loadTexture(gl,R.drawable.boomgun);
		GOLDENBANK=loadTexture(gl,R.drawable.goldenbank);
		SHUFUDAN=loadTexture(gl,R.drawable.shufudan);
		ZIDONGDAN=loadTexture(gl,R.drawable.autobulletgun);
		PIFENG=loadTexture(gl,R.drawable.pifeng);
		FRUITFLY=loadTexture(gl,R.drawable.fruitfly);
		STONEGRASS=loadTexture(gl,R.drawable.stonegrass);
		GUIDERECT=loadTexture(gl,R.drawable.guiderect);
		GOALCIRCLE=loadTexture(gl,R.drawable.goalcircle);
		WOODROOT=loadTexture(gl,R.drawable.woodroot);
		GUANGDANQIANG=repeatLoadTexture(gl,R.drawable.guangdan);
		K=repeatLoadTexture(gl,R.drawable.jian);
		GUIDECIRCLE=loadTexture(gl,R.drawable.guidecircle);
		EGG=loadTexture(gl,R.drawable.egg);
		LIGHTTAIL=loadTexture(gl,R.drawable.lighttail);
		CUP=repeatLoadTexture(gl,R.drawable.cup);
		HOOKGUN=repeatLoadTexture(gl,R.drawable.huck);
		JUJI=repeatLoadTexture(gl,R.drawable.jujidan);
		MISSILE=repeatLoadTexture(gl,R.drawable.daodan);
		SHOTGUN=repeatLoadTexture(gl,R.drawable.s);
		BULLET=loadTexture(gl,R.drawable.bullet);
		GUIDEPOST=loadTexture(gl,R.drawable.guidepost2);
		CANDLEPOR=loadTexture(gl,R.drawable.candlepor);
		FIRE=loadTexture(gl,R.drawable.firetail);
		PAOTA=loadTexture(gl,R.drawable.paotai);
		GUN=loadTexture(gl,R.drawable.guntitle);
		BLOOD=loadTexture(gl,R.drawable.blood);
		CANDLETAIL=loadTexture(gl,R.drawable.candle);
		LIGHTSPOT=loadTexture(gl,R.drawable.lightspot);
		LIGHTNING=loadTexture(gl,R.drawable.lightning);
		RAINBOW=loadTexture(gl,R.drawable.tail);
		BLACK=loadTexture(gl,R.drawable.black);
		BOOM=loadTexture(gl,R.drawable.boom);
		THUNDER =loadTexture(gl,R.drawable.lightningset);
		FIREWORK=loadTexture(gl,R.drawable.firework);
		RAIN=loadTexture(gl,R.drawable.rain);
		SNOW=loadTexture(gl,R.drawable.snow);
		WIND=loadTexture(gl,R.drawable.wind);
		EXPRESSION=loadTexture(gl,R.drawable.espression);
		ZAN=loadTexture(gl,R.drawable.zan);
		HIKARI=loadTexture(gl,R.drawable.light);
		TOUKUI=loadTexture(gl,R.drawable.toukui);
		GAO=loadTexture(gl,R.drawable.gao);
		BAMBOO=loadTexture(gl,R.drawable.bamboo);
		
		BAMBOOHEART=loadTexture(gl,R.drawable.bambooheart);
		BODY=loadTexture(gl,R.drawable.body);
		HAND=loadTexture(gl,R.drawable.hand);
		FOOT=loadTexture(gl,R.drawable.foot);
		CLOTH=loadTexture(gl,R.drawable.cloth);
		CAP=loadTexture(gl,R.drawable.cap);
		BACK=loadTexture(gl,R.drawable.back);
		MORNING=loadTexture(gl,R.drawable.background);

		CLOCK=loadTexture(gl,R.drawable.clock);
		WALKER=loadTexture(gl,R.drawable.walker);
		GREENWALKER=loadTexture(gl,R.drawable.greenwalker);
		CREEPER=loadTexture(gl,R.drawable.creeper);
		REDCREEPER=loadTexture(gl,R.drawable.bluecreeper);
		HEDGEHOG=loadTexture(gl,R.drawable.hedgehog);
		FLYER=loadTexture(gl,R.drawable.flyer);
		BALLER=loadTexture(gl,R.drawable.enemy1);
		WATERBALL=loadTexture(gl,R.drawable.fball);
		NEXT=loadTexture(gl,R.drawable.next);
//		NUMBER=loadTexture(gl,R.drawable.number);

		COIN=repeatLoadTexture(gl,R.drawable.coin);
		TOMENU=loadTexture(gl,R.drawable.tomenu);
		FIREBALL=loadTexture(gl,R.drawable.fireball);
		ZHUAN=loadTexture(gl,R.drawable.zhuan);
		BANK=loadTexture(gl,R.drawable.bank);
		BLANK=loadTexture(gl,R.drawable.blank);
//		RESULT=loadTexture(gl, R.drawable.result);
		SWORD=loadTexture(gl, R.drawable.sword);
		PAUSE=loadTexture(gl, R.drawable.pause);
		ATTACK=loadTexture(gl, R.drawable.button_attack);
//		GOAL=loadTexture(gl, R.drawable.goal);
		
		STONE=loadTexture(gl, R.drawable.stone);
		H=loadTexture(gl, R.drawable.height);
		WOOD=loadTexture(gl, R.drawable.wood);
		TREE=loadTexture(gl, R.drawable.tree);
		SOIL=loadTexture(gl, R.drawable.soil);
		SOILGRASS=loadTexture(gl, R.drawable.soilgrass);
		FOG=loadTexture(gl, R.drawable.fog);
		TOMATO=loadTexture(gl, R.drawable.tomato);
		JINGUBANG=loadTexture(gl, R.drawable.jingubang);
		RED=loadTexture(gl,R.drawable.red);
		BLUE=loadTexture(gl, R.drawable.blue);
		GREEN=loadTexture(gl, R.drawable.green);
		PRICK=loadTexture(gl, R.drawable.spine);
		PRICKX=loadTexture(gl, R.drawable.spinex);
		
		
		DESERT=repeatLoadTexture(gl, R.drawable.zhijindong);
		EVENING=repeatLoadTexture(gl, R.drawable.huangshan2);
		SUNSET=repeatLoadTexture(gl, R.drawable.sunset);
		TIANSHAN=repeatLoadTexture(gl, R.drawable.tianshan);
		WORD = loadBmpTexture(gl, initFontBitmap(10, 32, "Hello World"));
	}

	private Context context;
	TexIdAndBitMap WORD;
	private int maxId;
	
	
	
	public static Bitmap initFontBitmap(int pad,int textSize,String str){
		return initFontBitmap(pad, textSize, str.split(" "));
	}
	public static Bitmap initFontBitmap(int pad,int textSize,String []strs){
		int max=1;
		for(String s:strs){
			if(s.length()>max){
				max=s.length();
			}
		}
		 Bitmap bitmap = Bitmap.createBitmap(max*textSize+2*pad, strs.length*textSize+3*pad,
				 Bitmap.Config.ARGB_8888);// strs.length+1 because draw direction is up but tail is down...
		 Canvas canvas = new Canvas(bitmap);
		//背景颜色
//		canvas.drawColor(Color.LTGRAY);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		RectF rect=new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
		canvas.drawRoundRect(rect, pad, pad, paint);
		//字体设置
		String fontType = "宋体";
		Typeface typeface = Typeface.create(fontType, Typeface.BOLD);
		//消除锯齿
		paint.setAntiAlias(true);
		//字体为红色
		paint.setColor(Color.BLACK);
		paint.setTypeface(typeface);
		paint.setTextSize(textSize);
		//绘制字体
//		canvas.drawText(font, 0, 100, paint);
		for(int i=0;i<strs.length;i++){
			canvas.drawText(strs[i], pad, pad+textSize*(i+1), paint);
		}
		return bitmap;
	}
	
	public  void reLoadFront( int texId,String s) {
		reLoadFront(texId, s.split(" "));
	}
	public  void reLoadFront( int texId,String []strs) {// ������Ĭ����������id��bitmap����һ��
		Log.i("TextureId ago "+texId);
		Bitmap bm = initFontBitmap(20, 36, strs);
		loadGl.glDeleteTextures(1, textures, texId);
		maxId--;
		WORD=loadBmpTexture(loadGl, bm);
		Log.i("TextureId after "+WORD.textureId);
	}
	public  TexIdAndBitMap loadBmpTexture(GL10 gl, Bitmap bm) {// ������Ĭ����������id��bitmap����һ��
		maxId++;
		indexAndPara(gl);
		repeatNo(gl);
		TexIdAndBitMap tb
		=new TexIdAndBitMap(bm, texImage2d( bm));
		return tb;
	}
	public int loadTexture(GL10 gl, int resId) {// ������Ĭ����������id��bitmap����һ��
		maxId++;
		Bitmap bm = initBitMap(resId);
		indexAndPara(gl);
		repeatNo(gl);
		return texImage2d( bm);
	}
	public int repeatLoadTexture(GL10 gl, int resId) {// ������Ĭ����������id��bitmap����һ��
		maxId++;
		Bitmap bm = initBitMap(resId);
		indexAndPara(gl);
		repeatYes(gl);
		return texImage2d( bm);// �Զ�����id
	}
	
	private Bitmap initBitMap(int resId) {
		resIcon[maxId] = resId;
		InputStream is = null;
		Bitmap bm;
		try {
			is = context.getResources().openRawResource(resId);
			bm = BitmapFactory.decodeStream(is);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bm;
	}
	private int texImage2d(Bitmap bm) {
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bm, 0);// ͼ--���� ָ������
	
		Log.i("texturesId 【】"+textures[maxId]+" "+maxId);
		return textures[maxId];
	}
	private void repeatNo(GL10 gl) {
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_CLAMP_TO_EDGE);// another is GL_REPEAT
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_CLAMP_TO_EDGE);
	}
	
	private void repeatYes(GL10 gl) {
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_REPEAT);// GL_REPEAT
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_REPEAT);
	}
	private void indexAndPara(GL10 gl) {
		gl.glGenTextures(1, textures, maxId);// ����id���������indexλ��
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[maxId]);// id--�������opengl��Ӧ������ʼ����
		// ����������id��������������ڻ״̬���ڵ�һ�ΰ�һ���������ʱ,
		// �Ὣһϵ�г�ʼֵ����Ӧ���Ӧ�á�
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST); // ���ù�С NEAREST�������
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR); // �<a>��������˲�
	}

	public  void deleteTex(GL10 gl) {
		// TODO Auto-generated method stub
		for (int i = 0; i < textures.length; i++)
			gl.glDeleteTextures(1, textures, i);
	}
}
class TexIdAndBitMap{
	Bitmap bitmap;
	int textureId;
	public TexIdAndBitMap(Bitmap bitmap,int id) {
		this.bitmap = bitmap;
		this.textureId = id;
	}
}

