package Element;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.MusicId;

import element2.TexId;

public class Boom extends LightSpot{
	float alpha;
	private float blue;
	private float green;
	private float red;
	float fruSpeed;
	
//	static float max=2f;
//	static float min=0f;
	float alphaSpeed=-0.03f;
	private float fruLength=1;
	 void init() {
			// TODO Auto-generated method stub
			setW(32);
			setH(32);
			loadTexture(TexId.BOOM);
		}
	void colorInc(){
		setRed(getRed() + alphaSpeed);
		setGreen(getGreen() + alphaSpeed);
		setBlue(getBlue() + alphaSpeed);
//		 alpha+=0.9*alphaSpeed;
		 alpha+=1*alphaSpeed;
	}
	float windRate=1/50f;
	/*F=(1/2)C��SV^2 ����. ʽ�У�
			CΪ��������ϵ����
			��Ϊ�����ܶȣ�S����ӭ�������
			VΪ���������������˶��ٶ�.*/
	//a=x*v^2 v=v-a   v=v-x*v^2=v(1-x*v)
	// rate��ô˵Ҳ�ñȿ��С��
	public void tringer(float x,float y){
		super.tringer(x, y);
		playSound(MusicId.bomb);
		resetBooom();
		fruSpeed=40;//应先嘎爆炸速度
	}
	public void drawElement(GL10 gl){
		if(alpha<-1)return;
		colorInc();//͸������ɫ�仯ת��
			
		if(fruSpeed>0) fruSpeed-=windRate*fruSpeed*fruSpeed;//���ǹ�ʽЧ���á���
		 fruLength += fruSpeed;
		 if(fruLength==0)fruLength=1;// 除数为零
		float fruTime= fruLength/getW();
		float fruTimeBack = getW()/fruLength;
		 float alpha=this.alpha;
		 gl.glColor4f(getRed(), getGreen(), getBlue(), alpha);
		gl.glTranslatef(x, y, 0);
		gl.glScalef(fruTime, fruTime, 0);
		gl.glRotatef(angle, 0, 0, 1);
		super.baseDrawElement(gl);
		gl.glRotatef(-angle, 0, 0, 1);
		gl.glScalef(fruTimeBack, fruTimeBack, 0);
		gl.glTranslatef(-x, -y, 0);
		gl.glColor4f(1, 1, 1, 1);
	}

	private void resetBooom() {
		 alpha=1.5f;
		 red=alpha;
		 green=alpha;
		 blue=alpha;
		 fruLength=1;
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