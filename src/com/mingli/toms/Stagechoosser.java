package com.mingli.toms;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/14.
 */
public class Stagechoosser {
	private MenuActivity acti;
	private int itemCount = 4;
	private View stageChooserView;
	public Stagechoosser(){}
	public Stagechoosser(MenuActivity acti) {
		this.acti = acti;
		// this.click = click;
		// this.check = check;
		loadStage();
	}

	public void loadStage() {

		stageChooserView = acti.getLayoutInflater().inflate(R.layout.stagechoosser,
				null);
		
//		acti.setContentView(R.layout.stagechoosser);
		acti.addView(stageChooserView);

		GridView gv = (GridView) stageChooserView.findViewById(R.id.stageGridView);

		StageAdapter adapter;
		gv.setAdapter(adapter = new StageAdapter(acti));

		gv.setNumColumns(itemCount);// ////
		int space = 20;
		gv.setHorizontalSpacing(space);
		gv.setVerticalSpacing(space);
		// gv.setColumnWidth(3);
		// ScaleAnimation ani = new ScaleAnimation(0, 1, 0, 1);
		// ani.setDuration(500);
		Animation an = AnimationUtils.loadAnimation(gv.getContext(),
				R.anim.selfin);
		gv.startAnimation(an);

		View btn = stageChooserView.findViewById(R.id.backStart);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				hide();
				acti.loadTitleView(0);
			}
		});

		OnItemClickListener selectListener = new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (acti.getMaxMapIndex() > position) {
					hide();
					acti.startWithIndex(position + 1);
				}
			}
		};
		gv.setOnItemClickListener(selectListener);

	}

	class StageAdapter extends BaseAdapter {

		private Context context;
		int[] star = acti.star;

		public StageAdapter(Context context) {
			this.context = context;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return Map.max - 1;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			RatingBar r;
			RelativeLayout v;
			TextView st;
			if (convertView == null) {
				v = (RelativeLayout) acti.getLayoutInflater().inflate(
						R.layout.stageitem, null);

			} else {
				v = (RelativeLayout) convertView;
			}

			r = (RatingBar) v.findViewById(R.id.stageRating);
			st = (TextView) v.findViewById(R.id.stageText);
			if (acti.getMaxMapIndex() > position) {

				v.setBackgroundResource(R.drawable.stageitem);

				// st = new TextView(context);
				st.setText("" + (position + 1));
				/*
				 * android.widget.RelativeLayout.LayoutParams params = new
				 * RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
				 * LayoutParams.WRAP_CONTENT);
				 * params.addRule(RelativeLayout.CENTER_IN_PARENT);
				 * st.setLayoutParams(params); st.setTextSize(36);
				 */
				// v.addView(st);
				r.setVisibility(View.VISIBLE);
				r.setRating(star[position]);// from 1
			} else {
				v.setBackgroundResource(R.drawable.lock);
				r.setVisibility(View.INVISIBLE);
//				r.setRating(0);
				st.setText("");
			}

			return v;

		}

	}

	public void hide() {
	acti.removeView(stageChooserView);
	}
}
