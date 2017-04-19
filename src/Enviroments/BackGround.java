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
		int id=(Math.abs(mapId-1)/World.dStage)%(World.dStage);
		switch(id){
//		default:setTextureId(TexId.SEA);break;		
		default:setTextureId(TexId.MORNING);break;
		case 1:setTextureId(TexId.SUNSET);break;
		case 2:setTextureId(TexId.DESERT);break;
		case 3:setTextureId(TexId.EVENING);break;
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
