package element2;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.view.View;

import com.mingli.toms.Log;
import com.mingli.toms.R;
import com.mingli.toms.World;

public class TexId{
	public TexId(Context context){
		this.context = context;
		textures = new int[TexId.textureCount];//
		resIcon = new int[TexId.textureCount];//
	}
	public int[] textures;
	private int textureId;
	public static int[] resIcon;
	
	public static int STONEGRASS;
	public static int PRICKX;
	public static int EGG;
	public static int BULLET;
	public static int BACK;
	public static int BACKGROUND;
	public static int BUBBLE;
	public static int RED;
	public static int UP;
	public static int BUTTON_LEF;
	public static int BUTTON_RIGHT;
	public static int SOUND;
	public static int MUSIC;
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
	public static int HUANGSHAN;
	public static int SUNSET;
	public static int ZHAOZHOUQIAO;
	public static int TIANSHAN;
	public static int HETANG;
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
	public static int ZHIJINDONG;
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
	public static int HIKARI2;
	public static int EXPRESSION;
	public static int ICE;
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
	public static int FIRE2;
	public static int CANDLEPOR;
	public static int SEA;
	public static int PAOTA;
	public static int GUIDEPOST;
	public static int GUIDECIRCLE;
	public static int S;
	public static int M;//musket hua tang qiang
	public static int B;
	public static int D;
	public static int CUP;
	public static int LIGHTTAIL;
	public static int K;//knife
	public static int O;
	public static int WOODROOT;
	public static int BOOM2;
	
	public static int textureCount=130-28;//纹理数量 行号之差
	public static int GOALCIRCLE;
	public static int GUIDERECT;
	public static int FRUITFLY;
	public  void loadTextureId(GL10 gl) {
//		if(1<2)return;
		FRUITFLY=loadTexture(gl,R.drawable.fruitfly);
		STONEGRASS=loadTexture(gl,R.drawable.stonegrass);
		GUIDERECT=loadTexture(gl,R.drawable.guiderect);
		GOALCIRCLE=loadTexture(gl,R.drawable.goalcircle);
		BOOM2=loadTexture(gl,R.drawable.boom2);
		WOODROOT=loadTexture(gl,R.drawable.woodroot);
		O=repeatLoadTexture(gl,R.drawable.putongdan);
		K=repeatLoadTexture(gl,R.drawable.jian);
		GUIDECIRCLE=loadTexture(gl,R.drawable.guidecircle);
		EGG=loadTexture(gl,R.drawable.egg);
		LIGHTTAIL=loadTexture(gl,R.drawable.lighttail);
		CUP=repeatLoadTexture(gl,R.drawable.cup);
		D=repeatLoadTexture(gl,R.drawable.huck);
		M=repeatLoadTexture(gl,R.drawable.jujidan);
		B=repeatLoadTexture(gl,R.drawable.daodan);
		S=repeatLoadTexture(gl,R.drawable.s);
		BULLET=loadTexture(gl,R.drawable.bullet);
		GUIDEPOST=loadTexture(gl,R.drawable.guidepost);
		CANDLEPOR=loadTexture(gl,R.drawable.candlepor);
		FIRE=loadTexture(gl,R.drawable.firetail);
		FIRE2=loadTexture(gl,R.drawable.fire2);
		PAOTA=loadTexture(gl,R.drawable.paotai);
		GUN=loadTexture(gl,R.drawable.guntitle);
		SEA=loadTexture(gl,R.drawable.sea);
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
		ICE=loadTexture(gl,R.drawable.ice);
		EXPRESSION=loadTexture(gl,R.drawable.espression);
		HIKARI2=loadTexture(gl,R.drawable.light2);
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
		BACKGROUND=loadTexture(gl,R.drawable.background);

		BUTTON_LEF=loadTexture(gl,R.drawable.button_left);
		BUTTON_RIGHT=loadTexture(gl,R.drawable.button_right);
		SOUND=loadTexture(gl,R.drawable.button_sound);
		MUSIC=loadTexture(gl,R.drawable.button_music);
		UP=loadTexture(gl,R.drawable.button_up);
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
//		NUMBERSMALL=loadTexture(gl, R.drawable.numbersmall);
		FOG=loadTexture(gl, R.drawable.fog);
//		MENUDIFALOG=loadTexture(gl, R.drawable.menudialog);
//		ASK=loadTexture(gl, R.drawable.ask);
//		ANSWER=loadTexture(gl, R.drawable.answer);
//		STARTMENU=loadTexture(gl, R.drawable.startmenu);
		TOMATO=loadTexture(gl, R.drawable.tomato);
		JINGUBANG=loadTexture(gl, R.drawable.jingubang);
		BUBBLE=loadTexture(gl,R.drawable.bubble);
		RED=loadTexture(gl,R.drawable.red);
		BLUE=loadTexture(gl, R.drawable.blue);
		GREEN=loadTexture(gl, R.drawable.green);
		PRICK=loadTexture(gl, R.drawable.spine);
		PRICKX=loadTexture(gl, R.drawable.spinex);
		
		
		ZHIJINDONG=repeatLoadTexture(gl, R.drawable.zhijindong);
		HUANGSHAN=repeatLoadTexture(gl, R.drawable.huangshan2);
		SUNSET=repeatLoadTexture(gl, R.drawable.sunset);
		ZHAOZHOUQIAO=repeatLoadTexture(gl, R.drawable.zhaozhouqiao);
		TIANSHAN=repeatLoadTexture(gl, R.drawable.tianshan);
		HETANG=repeatLoadTexture(gl, R.drawable.hetang);
	}

	private Context context;
	
	public int loadTexture(GL10 gl, int resId) {// ������Ĭ����������id��bitmap����һ��
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
		gl.glGenTextures(1, textures, textureId);// ����id���������indexλ��
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[textureId]);// id--�������opengl��Ӧ������ʼ����
		// ����������id��������������ڻ״̬���ڵ�һ�ΰ�һ���������ʱ,
		// �Ὣһϵ�г�ʼֵ����Ӧ���Ӧ�á�
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST); // ���ù�С NEAREST�������
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR); // �<a>��������˲�
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_CLAMP_TO_EDGE);// GL_REPEAT
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_CLAMP_TO_EDGE);

		/*
		 * gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
		 * GL10.GL_REPEAT);//GL_REPEAT // s t ���򲻶��ظ�����
		 * gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
		 * GL10.GL_REPEAT);
		 */
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bm, 0);// ͼ--���� ָ������
		resIcon[textures[textureId]] = resId;
		Log.i("texturesId"+textures[textureId]);
		return textures[textureId++];// �Զ�����id
	}

	public int repeatLoadTexture(GL10 gl, int resId) {// ������Ĭ����������id��bitmap����һ��
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
		gl.glGenTextures(1, textures, textureId);// ����id���������indexλ��
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[textureId]);// id--�������opengl��Ӧ������ʼ����
		// ����������id��������������ڻ״̬���ڵ�һ�ΰ�һ���������ʱ,
		// �Ὣһϵ�г�ʼֵ����Ӧ���Ӧ�á�
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST); // ���ù�С NEAREST�������
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR); // �<a>��������˲�
		/*
		 * gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
		 * GL10.GL_CLAMP_TO_EDGE);//GL_REPEAT
		 * gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
		 * GL10.GL_CLAMP_TO_EDGE);
		 */

		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_REPEAT);// GL_REPEAT
		// s t ���򲻶��ظ�����
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_REPEAT);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bm, 0);// ͼ--���� ָ������
		resIcon[textures[textureId]] = resId;
		Log.i("texturesId"+textures[textureId]);
		return textures[textureId++];// �Զ�����id
	}

	public  void deleteTex(GL10 gl) {
		// TODO Auto-generated method stub
		for (int i = 0; i < textures.length; i++)
			gl.glDeleteTextures(1, textures, i);
	}
}
