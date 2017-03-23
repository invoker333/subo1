package aid;

import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mingli.toms.MenuActivity;
import com.mingli.toms.R;

public class Tips {
	private PopupWindow popupWindow;
	private final MenuActivity acti;
	private TextView te;
	public Tips(MenuActivity acti){
		this.acti = acti;
		
	}
	public void showAtTip(View v,String t, int x, int y) {
		initTip(v);
		popupWindow.showAtLocation(v, Gravity.LEFT, x, y);
		te.setText(t);
	}
	
	public void showDownTip(View v,String t) {
		// TODO Auto-generated method stub
		initTip(v);
		popupWindow.showAsDropDown(v);
		te.setText(t);
		
	}
	public void initTip(View v) {
		long tim = System.currentTimeMillis();
		View popupWindow_view;
		if (popupWindow != null && popupWindow.isShowing())
			return;// 防止重新出发
		// 获取自定义布局文件activity_popupwindow_left.xml的视图
		else if (popupWindow == null) {
			popupWindow_view 		
			= acti.getLayoutInflater().inflate(	R.layout.tip, null);
//			 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
			popupWindow = new PopupWindow(popupWindow_view,
					WindowManager.LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.WRAP_CONTENT, true);
			popupWindow.setAnimationStyle(R.style.AnimationFade);
			te=(TextView) popupWindow_view.findViewById(R.id.tip);

		/*	touchEvent.findView(popupWindow_view);// 寻找自定义事件的
			findView(popupWindow_view);
			rateGrid=popupWindow_view.findViewById(R.id.ratinggrid);
			*/
			View.OnTouchListener otl = new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
//					touchEvent.touchAction(v, event);
					isHided();
					return true;
				}
			};
			popupWindow_view.setOnTouchListener(otl);
			/////////////
		}
		// 这里是位置显示方式,在屏幕的左侧
		te.setText("这是一个状态拦提示");
		
		
//		popupWindow.update();
		Log.i("popTime", "" + (System.currentTimeMillis() - tim));
	}
	boolean isHided() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			return false;
		}
		return true;
	}
	public  TextView getTextView() {
		// TODO Auto-generated method stub
		return te;
	}
}
