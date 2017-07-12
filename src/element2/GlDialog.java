package element2;

import javax.microedition.khronos.opengles.GL10;

import Element.Animation;

public class GlDialog extends Animation {
	Tail tail;
	private TexId ti;
	Animation speaker;
	private float dx;
	private float dy;
	public GlDialog(TexId ti) {
		this.ti = ti;
		// TODO Auto-generated constructor stub
		tail=new Tail(2,TexId.DIALOGTRINGLE);
		tail.w/=2;
		loadTexture(ti.WORD.textureId);
//		tanxingxishu=0.1f;
		setScaleZuni(5f);
	}

	
	public void setText(String str,Animation a, float dx,float dy) {
		this.speaker=a;
		this.dx = dx;
		this.dy = dy;
		 changeTextTextureId(str.split(" "));
	}
	public void setText(float x,float y,float dirx,float diry,String str) {
		setText(x, y, dirx, diry, str.split(" "));
	}
	public void setText(float x,float y,float dirx,float diry,String []strs) {
		// TODO Auto-generated constructor stub
		
		  changeTextTextureId(strs);
		
		this.x=x;
		this.y=y+ti.WORD.bitmap.getHeight();
		
		tail.startTouch(x, y);
		tail.tringer(dirx, diry);
	}


	private void changeTextTextureId(String[] strs) {
		ti.reLoadFront(  getTextureId(), strs);
		  
		  TexIdAndBitMap tb = ti.WORD;
		  setTextureId(tb.textureId);
		  
		  
		w=tb.bitmap.getWidth()/2;
		h=tb.bitmap.getHeight()/2;
		syncTextureSize();
	}
	 public void drawElement(GL10 gl){
		 tail.drawElement(gl);
//		super.drawTranElement(gl);
		super.drawScale(gl);
		if(speaker!=null){
			this.x=speaker.x+dx;
			this.y=dy+speaker.y+ti.WORD.bitmap.getHeight();
			tail.startTouch(x, y);
			tail.tringer(x, speaker.y+speaker.h);
		}
	}
}
