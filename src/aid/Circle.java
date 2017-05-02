package aid;


import Mankind.Player;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
public class Circle extends View {  
    private Paint paint;  
    //固定摇杆背景圆形的X,Y坐标以及半径  
    static  int LENGTH=128;  
    private int RockerCircleX = LENGTH;  
    private int RockerCircleY = LENGTH;  
   
    //摇杆的X,Y坐标以及摇杆的半径  
    private float SmallRockerCircleX = LENGTH;  
    private float SmallRockerCircleY = LENGTH;  
    
    private int SmallRockerCircleR = 50;
    private int RockerCircleR = LENGTH-SmallRockerCircleR;
	private double rad;
	public Player player;  
    private void cons(Context context) {
        this.setKeepScreenOn(true);  
        paint = new Paint();  
        paint.setAntiAlias(true);  
        setFocusable(true);  
        setFocusableInTouchMode(true);  
        
//        LENGTH=(int) DP.dip2px(context, 128);
//        SmallRockerCircleR=(int) DP.dip2px(context, 50);
	}
   /* protected void onDraw(Canvas c){
    	draw(c);
    }*/
    public Circle(Context context,AttributeSet a) {  
    	super(context, a);
    	cons(context);
    }
    public Circle(Context context,AttributeSet a,int b) {  
    	super(context, a,b);
    	cons(context);
    }
    public Circle(Context context) {  
        super(context);  
        cons(context);
    }  
  
    /*** 
     * 得到两点之间的弧度 
     */  
    public double getAngle(){
    	return rad;
    }
    public double getRad(float px1, float py1, float px2, float py2) {  
    	rad = Math.atan2(py2-py1, px2-px1);
//    	Log.i("rad"+rad);
        return rad;  
    }  
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
        if (event.getAction() == MotionEvent.ACTION_DOWN ||   
                    event.getAction() == MotionEvent.ACTION_MOVE) {  
            // 当触屏区域不在活动范围内  
            if (Math.sqrt(Math.pow((RockerCircleX - (int) event.getX()), 2)   
                    + Math.pow((RockerCircleY - (int) event.getY()), 2)) >= RockerCircleR) {  
                //得到摇杆与触屏点所形成的角度  
                double tempRad = getRad(RockerCircleX, RockerCircleY, event.getX(), event.getY());  
                //保证内部小圆运动的长度限制  
                getXY(RockerCircleX, RockerCircleY, RockerCircleR, tempRad);  
            } else {//如果小球中心点小于活动区域则随着用户触屏点移动即可  
                SmallRockerCircleX = (int) event.getX();  
                SmallRockerCircleY = (int) event.getY();  
            }  
           player.CircleDown((float) -rad);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {  
            //当释放按键时摇杆要恢复摇杆的位置为初始位置  
            SmallRockerCircleX = LENGTH;  
            SmallRockerCircleY = LENGTH; 
           player.CircleUp();
           }  
    	
        invalidate();
        return true;  
    }  
    /** 
     *  
     * @param R 
     *            圆周运动的旋转点 
     * @param centerX 
     *            旋转点X 
     * @param centerY 
     *            旋转点Y 
     * @param rad 
     *            旋转的弧度 
     */  
    public void getXY(float centerX, float centerY, float R, double rad) {  
        //获取圆周运动的X坐标   
        SmallRockerCircleX = (float) (R * Math.cos(rad)) + centerX;  
        //获取圆周运动的Y坐标  
        SmallRockerCircleY = (float) (R * Math.sin(rad)) + centerY;  
    }  
    public void onDraw(Canvas canvas) {  
//        try {  
//            canvas.drawColor(Color.WHITE);  
        	 paint.setColor(0x70000000);  
            canvas.drawCircle(RockerCircleX, RockerCircleY, LENGTH, paint);  
            //设置透明度  
            
//            paint.setColor(0x70000000);  
            //绘制摇杆背景  
//            canvas.drawCircle(RockerCircleX, RockerCircleY, RockerCircleR, paint);  
            
            paint.setColor(0xeedddddd);  
            //绘制摇杆  
            canvas.drawCircle(SmallRockerCircleX, SmallRockerCircleY,   
                    SmallRockerCircleR, paint);  
//        } catch (Exception e) {  
            // TODO: handle exception  
//        } finally {  
//        }  
    }
}