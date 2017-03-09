package com.mingli.toms;

import Enviroments.FruitSet;
import Enviroments.Tomato;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/14.
 */
public class StartMenu{
    private MenuActivity acti;
	private RadioButton noweapon;

	private RadioButton alwaysgun;

	private CheckBox rampage;

	private View startView;
	private TextView et;
    StartMenu(MenuActivity acti){
        this.acti = acti;
//        this.click = click;
//        this.check = check;
    }
    public void loadStartMenu() {
       
        startView = acti.getLayoutInflater().inflate(
				R.layout.startmenu,null);
        
//        acti.addView(startView);
      
//        acti.setContentView(R.layout.startmenu);
        acti.addView(startView);
        
//        Log.i("DecView"+pareView, "风格的萨广泛的萨广泛的撒");
        
//        Animation animation=AnimationUtils.loadAnimation(acti, R.anim.self);
//		pareView.startAnimation(animation);
//        
        
        Button start = (Button) startView.findViewById(R.id.startgame);
        Button more = (Button) startView.findViewById(R.id.more);
        fileChoose = (Button) startView.findViewById(R.id.fileChoose);
        if(!World.editMode)fileChoose.setVisibility(View.INVISIBLE);
        Button stage = (Button) startView.findViewById(R.id.stageChoose);
        
        rampage = (CheckBox) startView.findViewById(R.id.rampage);
        noweapon = (RadioButton) startView.findViewById(R.id.nowepon);
        alwaysgun = (RadioButton) startView.findViewById(R.id.alwaysgun);
        user = startView.findViewById(R.id.user);
        user.setVisibility(View.INVISIBLE);
        
        CheckBox cb1 = (CheckBox) startView.findViewById(R.id.checkBox1);
        CheckBox cb2 = (CheckBox) startView.findViewById(R.id.checkBox2);

        View gameModel=startView.findViewById(R.id.gameModel);
        gameModel.setVisibility(View.INVISIBLE);
        
        start.setOnClickListener(click);
        more.setOnClickListener(click);
        fileChoose.setOnClickListener(click);
        stage.setOnClickListener(click);
        
//        mario.setOnClickListener(click);
//        adventure.setOnClickListener(click);
//        rampage.setOnClickListener(click);
//        randomrampage.setOnClickListener(click);
//        contra.setOnClickListener(click);
//        normalshot.setOnClickListener(click);
        noweapon.setOnClickListener(click);
        alwaysgun.setOnClickListener(click);
        
        cb1.setOnCheckedChangeListener(check);
        cb2.setOnCheckedChangeListener(check);
        
        et = (EditText) startView.findViewById(R.id.geqian);
//        et.setVisibility(View.INVISIBLE);
//        et.setOnClickListener(click);
        et.setText(acti.geqian);
        TextWatcher watcher= new TextWatcher() {
			
        	String before;
        	String after;
//			@Override
			public void onTextChanged(CharSequence c, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				after=""+ c;
				
			}
			@Override
			public void beforeTextChanged(CharSequence c, int arg1, int arg2,
					int arg3) {
				before=""+c;
				Log.i("beforechanged "+c);
			}
			@Override
			public void afterTextChanged(Editable c) {
				Log.i("afterchanged "+c);
				if(after!=null&&after!="")
				acti.saveGeqian(after);
			}
		};
		et.addTextChangedListener(watcher);
    }
    OnClickListener click = new OnClickListener() {
    	int t=0;
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.startgame:
				acti.startGame();
				break;
			case R.id.fileChoose:
				Intent intent = new Intent();
				intent.setClass(acti, fileRW.FileActivity.class);
				acti.startActivityForResult(intent, 0);
				break;
			case R.id.stageChoose:
				acti.initStageChooser();
				return;//bujiazaiyouxi//
			case R.id.more:
				acti.showAppWall();
				return;//bujiazaiyouxi//
		
			case R.id.rampage:
				World.RAMPAGE=rampage.isChecked();
				break;
			case R.id.nowepon:
				World.NOWEPON=noweapon.isChecked();
				break;
			case R.id.alwaysgun:
				World.ALWAYSGUN=alwaysgun.isChecked();
				break;
			case R.id.geqian:
				break;

			}
			
			
		}
	};

	OnCheckedChangeListener check = new OnCheckedChangeListener() {

		
		String str="";
		String moneyStr="0101";
		String editStr="000111";
		String rpgStr="111000";
		String youtietu = "111111";
		String wutietu = "000000";
		int maxLength=10;// max length in the up number
		
		@Override
		public void onCheckedChanged(CompoundButton v, boolean isChecked) {
//			t-=3;
			
			if (v.getId() == R.id.checkBox2) {
				Music.ex = isChecked;
				str+=0;
			}
			else if (v.getId() == R.id.checkBox1) {
				Music.bgm = isChecked;
				str+=1;
			}
			
		if(str.length()>maxLength){
			str="";
			Toast.makeText(acti, "少侠好手速！佩服佩服！", Toast.LENGTH_SHORT).show();
		}
			
			Log.i("str:"+str+""+"targetStr"+moneyStr+str.equals(moneyStr));
		if(str.equals(youtietu)){
			acti.tietu(1);
			Toast.makeText(acti, "有贴图", Toast.LENGTH_SHORT).show();
		} else if(str.equals(wutietu)) {
			acti.tietu(0);
			Toast.makeText(acti, "无贴图", Toast.LENGTH_SHORT).show();
		}
		
		else	if(str.equals(moneyStr)){
				user.setVisibility(View.VISIBLE);
//				et.setHint("豪礼相送！");
				acti.chance+=10000;
				acti.coinCount+=10000;
				FruitSet.cml();
				Toast.makeText(acti, "豪礼相送", Toast.LENGTH_SHORT).show();
			
			}else if(str.equals(editStr)){
				fileChoose.setVisibility(View.VISIBLE);
				if(World.editMode==false){
					World.editMode=true;
					Toast.makeText(acti, "编辑模式开启", Toast.LENGTH_SHORT).show();
				}else {
					World.editMode=false;
					Toast.makeText(acti, "编辑模式关", Toast.LENGTH_SHORT).show();
				}
			}else if(str.equals(rpgStr)){
				if(World.rpgMode==false){
					World.baseLifeMax=1000;
					World.rpgMode=true;
					Toast.makeText(acti, "RPG模式开启", Toast.LENGTH_SHORT).show();
				}else {
					World.rpgMode=false;
					Toast.makeText(acti, "RPG模式关闭", Toast.LENGTH_SHORT).show();
				}
			}
		
			else {
				return;// to avoid str to be  not empty
			}str="";
			
		}

	};
//	private EditText et;
	private Button fileChoose;
	private View user;
	public void hide() {
		// TODO Auto-generated method stub
		acti.removeView(startView);
	}


}
