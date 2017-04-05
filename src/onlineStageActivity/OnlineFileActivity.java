package onlineStageActivity;

import com.mingli.toms.Info4;
import com.mingli.toms.R;
import com.mingli.toms.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OnlineFileActivity extends Activity {
	public static final String ONLINE_STAGE_STRING_FROM_NET = "onlineStageStringExtra";
	public static final String ONLINE_STAGE_ITEM_SELECTED = "onlineStageItem";
	
	private String[] onlineStageFiles;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_online_stage);
		ListView lv=(ListView) findViewById(R.id.onlineList);
		
		String ss = getIntent().getExtras().getString(ONLINE_STAGE_STRING_FROM_NET);
		lv.setAdapter(new OnlineListAdapter(ss));
		lv.setOnItemClickListener(clickOnline);
	}
	private OnItemClickListener clickOnline= new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			returnResault(position);
		}
	};
	
	void returnResault(int position){
		Intent intent = getIntent();
		String onlineStageNameString = onlineStageFiles[position];
		intent.putExtra(ONLINE_STAGE_ITEM_SELECTED, onlineStageNameString);
		setResult(RESULT_OK, intent);
		finish();
	}
	class OnlineListAdapter extends BaseAdapter{
		public OnlineListAdapter(String onlineStage) {
			onlineStageFiles = onlineStage.split(" ");
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return onlineStageFiles.length;
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return onlineStageFiles[position];
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView stageName=null;
			View v=null;
			if(convertView==null){
				 v=OnlineFileActivity.this.getLayoutInflater().inflate(R.layout.onlinestageitem, null);
			}else v=convertView;
			
			stageName=(TextView) v.findViewById(R.id.onlineStageName);
			stageName.setText(onlineStageFiles[position].replace(".txt", ""));
			return v;
		}
		
	}
	
}
