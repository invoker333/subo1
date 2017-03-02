package com.mingli.toms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class Titlectivity extends Activity  {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
//		if(world==null)world=new World(this);
		setContentView(R.layout.charact);
//		addPreferencesFromResource(R.xml.aaa);		if(true)return;
		new Handler().postDelayed(new Thread(){
			public void run(){
				Intent mainMenu=new Intent(Titlectivity.this,MenuActivity.class);
				startActivity(mainMenu);
				Titlectivity.this.finish();
				overridePendingTransition(R.anim.fadein,R.anim.fadeout);
			}
		},100);
	}

}
