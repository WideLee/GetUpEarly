package uml.android.getupearly.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import uml.android.getupearly.Event;
import uml.android.getupearly.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ToDoListAdapter extends BaseAdapter {

	private ArrayList<Event> data;
	private Context mContext;

	public ToDoListAdapter(Context context) {
		mContext = context;
		data = new ArrayList<Event>();
	}

	public void setData(ArrayList<Event> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Event getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return data.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.lvi_todo_list, null);
			vh = new ViewHolder();
			vh.mContentTextView = (TextView) convertView
					.findViewById(R.id.tv_content);
			vh.mTimeTextView = (TextView) convertView
					.findViewById(R.id.tv_time);

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		Event item = getItem(position);

		vh.mContentTextView.setText(item.getContent());
		vh.mTimeTextView.setText(getHourMinute(item.getTime()));

		return convertView;
	}
	
	private String getHourMinute(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		String ho = hour < 10 ? "0" + Integer.toString(hour) : Integer
				.toString(hour);
		String min = minute < 10 ? "0" + Integer.toString(minute) : Integer
				.toString(minute);
		return ho + ":" + min;
	}

	private class ViewHolder {
		TextView mTimeTextView;
		TextView mContentTextView;
	}

}
