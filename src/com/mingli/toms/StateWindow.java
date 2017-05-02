package com.mingli.toms;

import aid.Tips;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/13.
 */
public class StateWindow {
	private TextView coin;
	private TextView score;
	private TextView chance;
	private ProgressBar life;
	private TextView timer;
	private MenuActivity acti;
//	private LayoutParams lp;
	View state;
	private final World world;
	private PopupWindow popupWindow;
	private OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			tips.showDownTip(v,"");
			TextView te = tips.getTextView();
			switch(v.getId()){
			case R.id.timer:te.setText("游戏时间:"+timer.getText()+"/"+world.timerMax+"\n一局游戏的计时器，得在时间为0前过关。");break;
			case R.id.coin:te.setText("当前金币"+coin.getText()+"\n显示广告能获得金币，点击广告获得更多金币。");break;
			case R.id.progressBar1:te.setText("当前体力："+life.getProgress()+"%\n请注意节制，小心身体被掏空……");break;
			case R.id.score:te.setText("当前得分："+score.getText()+" \n 基本上干什么都会得分！");break;
			case R.id.chance:te.setText("获得的生命能量："+chance.getText()+"\n显示广告能获得能量，点击广告得到更多。\n  游戏失败会丢失1生命能量，捕获（消灭）怪物会获得1生命能量。");break;
			
			}
		}
	};
	private final Tips tips;
	StateWindow(MenuActivity acti,World world,Tips tips) {
		this.acti = acti;
		this.world = world;
		this.tips = tips;
		// super(acti, layout, touchEvent);
		// showWindow(acti.findViewById(R.id.pause));
		initView();
	}


	public void initView() {
		state =  acti.getLayoutInflater().inflate(R.layout.state,
				null);
		acti.addView(state);
		
		timer = (TextView) state.findViewById(R.id.timertext);
		coin = (TextView) state.findViewById(R.id.coinText);
		score = (TextView) state.findViewById(R.id.scoreText);
		chance = (TextView) state.findViewById(R.id.chanceText);
		life = (ProgressBar) state.findViewById(R.id.progressBar1);

		// for(int i=0;i<mes.length;i++)
		// mes[i]=""+0;
	/*	
		lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.MATCH_PARENT);*/
		 state.findViewById(R.id.timer).setOnClickListener(listener);
		 state.findViewById(R.id.coin) .setOnClickListener(listener);
		 state.findViewById(R.id.score) .setOnClickListener(listener);
		 state.findViewById(R.id.chance).setOnClickListener(listener);
		 life .setOnClickListener(listener);
		 
		 if(!World.rpgMode)life.setVisibility(View.INVISIBLE);
	}

	void handleCheck(Message msg) {
		switch (msg.what) {
		case World.TIME:
			// if(timer!=null)
			
			timer.setText(world.getStr(msg.what));
			// Log.i("time", World.getStr(msg.what));
			break;
		case World.COIN:
			// if(coin!=null)
			
			coin.setText(""+acti.coinCount);
			
//			 Log.i("coin"," world.getIntMes(msg.what)"+ world.getIntMes(msg.what));
			break;
		case World.CHANCE:
			// if(chance!=null)
			chance.setText(""+acti.chance);
			// Log.i("chance",World.getStr(msg.what));
			break;
		case World.SCORE:
			// if(score!=null)
		
			score.setText(""+acti.score);
//			 Log.i("score"," world.getIntMes(msg.what)"+ world.getIntMes(msg.what));
			// Log.i("score", World.getStr(msg.what));
			break;
		case World.LIFE:
			// if(life!=null)
			int blood =world.getIntMes(msg.what);
				life.setProgress(blood);
			// Log.i("LIFE", World.getStr(msg.what));
			break;
		case World.LIFE2:
			// if(life!=null)
			int blood2 = world.getIntMes(msg.what);
				life.setSecondaryProgress(blood2);
			// Log.i("LIFE", World.getStr(msg.what));
			break;
		}
	}


	public void hide() {
		// TODO Auto-generated method stub
		acti.removeView(state);
	}

	
}
