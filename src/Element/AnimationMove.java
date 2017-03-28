package Element;

import javax.microedition.khronos.opengles.GL10;

import Mankind.Creature;

public class AnimationMove extends Animation{
		protected float xSpeed;
		protected float ySpeed;
		protected float aTanxing;
		protected float aSin;
		protected float aCos;
		@Override
		public void drawElement(GL10 gl) {
			move();
			gravity();
			gl.glTranslatef(x, y, 0);
			super.baseDrawElement(gl);
			gl.glTranslatef(-x, -y, 0);
		}
		 public void toStartPosition(){
			x=startX;
			y=startY;
		}
		public void springCheck(AnimationMove wheel,int dsmax) {
			springCheck( wheel,dsmax, 1f / 5f,0.9f);
		}

		public void aCheck( AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			// TODO Auto-generated method stub
			float dx = wheel.x-x;
			float dy = wheel.y-y;
			float s = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));// distance
			if (s == 0)
				return;
			aCos = dx / s;
			aSin = dy / s;
			float ds = dsmax-s;
			aTanxing = ds * tanxingxishu;
		}
		public void springCheck( AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			springACheck(wheel, dsmax, tanxingxishu, zuni);
			{
				wheel.setxSpeed(wheel.getxSpeed()*zuni + aTanxing * aCos);
				wheel.setySpeed(wheel.getySpeed()*zuni + aTanxing * aSin);
			}
		}
		private void springACheck(AnimationMove wheel, float dsmax,
				float tanxingxishu, float zuni) {
			// TODO Auto-generated method stub
			aCheck(wheel, dsmax, tanxingxishu, zuni);
			if(aTanxing>zuni){
//				aTanxing-=zuni;
			}
			else if(aTanxing<-zuni){
//				aTanxing+=zuni;
			}
			else aTanxing=0;
		}
		////Stellar planetary system
		
		public void masterCheck( AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			aCheck(wheel, dsmax, tanxingxishu, zuni);
			 if(aTanxing<-zuni)
			{
				wheel.setxSpeed(getxSpeed() + aTanxing * aCos);
				wheel.setySpeed(getySpeed() + aTanxing * aSin);
			}
		}
		public void earthSunCheck( AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			springACheck(wheel, dsmax, tanxingxishu, zuni);
			{
				wheel.setxSpeed(getxSpeed() + aTanxing * aCos);
				wheel.setySpeed(getySpeed() + aTanxing * aSin);
			}
		}
		public void earthSunCheck2( AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			earthSunCheck(wheel, dsmax, tanxingxishu, zuni);
			wheel.earthSunCheck(this, dsmax, tanxingxishu, zuni);
		}
		public void springCheck2( AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			springCheck(wheel, dsmax, tanxingxishu, zuni);
			wheel.springCheck(this, dsmax, tanxingxishu, zuni);
		}
		public void pushCheck( AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			pushACheck(wheel, dsmax, tanxingxishu, zuni);
			{
				wheel.setxSpeed(getxSpeed() + aTanxing * aCos);
				wheel.setySpeed(getySpeed() + aTanxing * aSin);
			}
		}
		public void stringCheck( AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			if(stringACheck(wheel, dsmax, tanxingxishu, zuni));
			{
				wheel.setxSpeed((wheel.getxSpeed() + aTanxing * aCos)*zuni);
				wheel.setySpeed((wheel.getySpeed() + aTanxing * aSin)*zuni);
			}
		}
		public void stringCheck2( AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			stringCheck(wheel, dsmax, tanxingxishu, zuni);
			wheel.stringCheck(this, dsmax, tanxingxishu, zuni);
		}
		
		protected boolean stringACheck(AnimationMove wheel, float dsmax,
				float tanxingxishu, float zuni) {
			// TODO Auto-generated method stub
			aCheck(wheel, dsmax, tanxingxishu, zuni);
			if(aTanxing>zuni){
				aTanxing=0;return false;
//				aTanxing-=zuni;
			}
			else if(aTanxing<-zuni){
//				aTanxing+=zuni;
			}
			else aTanxing=0;
			return true;
		}
		private void pushACheck(AnimationMove wheel, float dsmax,
				float tanxingxishu, float zuni) {
			// TODO Auto-generated method stub
			aCheck(wheel, dsmax, tanxingxishu, zuni);
			if(aTanxing>zuni){
//				aTanxing-=zuni;
			}
			else if(aTanxing<-zuni){
					aTanxing=0;return;
//				aTanxing+=zuni;
			}
			else aTanxing=0;
		}
		
		public void xPushCheck(AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			xACheck(wheel, dsmax, tanxingxishu, zuni);
			{
				wheel.setxSpeed(getxSpeed() + aTanxing*aCos);
			}
		}
		public void xspringCheck(AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			xACheck(wheel, dsmax, tanxingxishu, zuni);
			{
				wheel.setxSpeed(wheel.getxSpeed() + aTanxing*aCos);
			}
		}
		public void xACheck(AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			// TODO Auto-generated method stub
//			float x = frontwheel.x;
			float dx = wheel.x-x;
			aCos=dx>0?1:-1;
			if (x == 0)
				return;//??
			float ds =  dsmax-Math.abs(dx);

			aTanxing = ds * tanxingxishu;
			
			if(aTanxing>zuni){
//					aTanxing-=zuni;
			}
			else if(aTanxing<-zuni){
//				aTanxing+=zuni;
			}
			else aTanxing=0;
		}
		public void yStandCheck(AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			yACheck(wheel, dsmax, tanxingxishu, zuni);
			{
				wheel.setySpeed(getySpeed() + aTanxing*aSin);
			}
		}
		public void ySpringCheck(AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			yACheck(wheel, dsmax, tanxingxishu, zuni);
			{
				wheel.setySpeed(wheel.getySpeed() + aTanxing*aSin);
			}
		}
		public void yACheck(AnimationMove wheel,float dsmax,float tanxingxishu,float zuni) {
			// TODO Auto-generated method stub
			float dy =  wheel.y-y;
			aSin=dy>0?1:-1;
			if (y == 0)
				return;
			float ds =  dsmax-Math.abs(dy);
			// tan xing jia su du
			aTanxing = ds * tanxingxishu;
			
			if(aTanxing>zuni){

//					aTanxing-=zuni;
			}
			else if(aTanxing<-zuni){
//				aTanxing+=zuni;
			}
			else aTanxing=0;
		}
		public void move(){
			x+=xSpeed;
		}
		public void gravity() {
			y += ySpeed;
		}
		public float getxSpeed() {
			return xSpeed;
		}
		public void setxSpeed(float xSpeed) {
			this.xSpeed = xSpeed;
		}

		public float getySpeed() {
			return ySpeed;
		}

		public void setySpeed(float vt) {
			this.ySpeed = vt;
		}
}
