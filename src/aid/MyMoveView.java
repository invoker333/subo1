package aid;

import com.mingli.toms.TouchMove;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyMoveView extends View {

	public MyMoveView(Context context) {
		super(context);
	}

	public MyMoveView(Context context, AttributeSet a) {
		super(context, a);
	}

	public MyMoveView(Context context, AttributeSet a, int b) {
		super(context, a, b);
	}
	public TouchMove tm;
	 public boolean onTouchEvent(MotionEvent e) {
		return tm.onTouch(this, e);  
	 }
}
