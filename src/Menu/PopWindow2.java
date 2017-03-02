package Menu;

import com.mingli.toms.R;
import com.mingli.toms.R.layout;
import com.mingli.toms.R.style;

import android.app.Activity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2016/7/10.
 */


public class PopWindow2{
    private PopupWindow popupWindow;
    private Activity acti;

    PopWindow2(Activity acti){
        this.acti = acti;
    }

    // 点击弹出左侧菜单的显示方式
    public void showCheck(View v){
            getPopupWindow();
            // 这里是位置显示方式,在屏幕的左侧
            popupWindow.showAtLocation(v, Gravity.TOP, 0, 0);
    }


    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = acti.getLayoutInflater().inflate(R.layout.gamemenu, null,
                false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, 200, WindowManager.LayoutParams.MATCH_PARENT, true);
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.AnimationFade);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }
    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }
}