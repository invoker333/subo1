package Enviroments;

import javax.microedition.khronos.opengles.GL10;

import Menu.Square;

import com.mingli.toms.Render;
import com.mingli.toms.World;

import element2.TexId;

public class BackGround extends Square{
	public BackGround(){
		loadTexture();
	}
	
	public BackGround(int mapId){
		this();
		final int count=7;
		int id=Math.abs(mapId)/World.dStage%count;
		switch(id){
		default:setTextureId(TexId.SEA);break;		
		case 1:setTextureId(TexId.BACKGROUND);break;
		case 2:setTextureId(TexId.SUNSET);break;
		case 3:setTextureId(TexId.ZHIJINDONG);break;
		case 4:setTextureId(TexId.HUANGSHAN);break;
		case 5:setTextureId(TexId.TIANSHAN);break;			
		case 6:setTextureId(TexId.ZHAOZHOUQIAO);break;
		}
		
	}
//	float dx,dy;
	public void drawElement(GL10 gl){
		
		gl.glTranslatef(Render.px, Render.py, 0);		
		super.drawElement(gl);
		gl.glTranslatef(-Render.px, -Render.py, 0);
	}
	public void loadTexture(){
		
		setTexture();
		
	}
}
