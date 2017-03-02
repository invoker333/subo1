package fileRW;

import java.io.File;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingli.toms.R;

public class FileListAdapter extends BaseAdapter {
	private File[] currentFiles;
	private LayoutInflater inflater;
	public FileListAdapter(Context context,File[] currentFiles) {
		// TODO Auto-generated constructor stub
		this.currentFiles=currentFiles;
		this.inflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		Log.v("lidong", ""+currentFiles.length);
		return currentFiles.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.file_list, null);
			viewHolder=new ViewHolder();
			viewHolder.icon=(ImageView) convertView.findViewById(R.id.icon);
			viewHolder.file_name=(TextView) convertView.findViewById(R.id.file_name);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		if(currentFiles[position].isDirectory()){
			viewHolder.icon.setImageResource(R.drawable.icon);
		}else{
			viewHolder.icon.setImageResource(R.drawable.file_icon);
		}
		viewHolder.file_name.setText( currentFiles[position].getName());
				
		return convertView;
	}
	
	class ViewHolder{
		public ImageView icon;
		public TextView file_name;
	}
	
	
}
