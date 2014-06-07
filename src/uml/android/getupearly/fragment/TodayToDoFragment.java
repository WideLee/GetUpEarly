package uml.android.getupearly.fragment;

import java.util.ArrayList;

import uml.android.getupearly.Event;
import uml.android.getupearly.R;
import uml.android.getupearly.R.string;
import uml.android.getupearly.adapter.ToDoListAdapter;
import uml.android.getupearly.template.BannerNoBackTemplate;
import uml.android.getupearly.util.HiThread;
import uml.android.getupearly.util.Tool;
import uml.android.getupearly.util.Weather;
import uml.android.getupearly.util.WeatherUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TodayToDoFragment extends Fragment {

	private static final int GET_WEATHER_DONE = 0;
	private static final int GET_WEATHER_FAIL = 1;

	private ListView mTodoListView;
	private TextView mCityNameTextView;
	private TextView mDateTextView;
	private TextView mOverviewTextView;
	private TextView mUVATextView;
	private TextView mTemperaTextView;
	private ImageView mWeatherIcon;
	private Handler mHandler;

	private HiThread mThread = new HiThread() {

		@Override
		public void run() {
			WeatherUtil weatherUtil = new WeatherUtil();
			Weather weather = weatherUtil.getWeather();
			if (weather == null) {
				Message msg = new Message();
				msg.what = GET_WEATHER_FAIL;
				msg.obj = Tool.getString(R.string.get_fail);
				mHandler.sendMessage(msg);
			} else {
				Message msg = new Message();
				msg.what = GET_WEATHER_DONE;
				msg.obj = weather;
				mHandler.sendMessage(msg);
			}
		}
	};

	public TodayToDoFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case GET_WEATHER_DONE:
					Weather weather = (Weather) msg.obj;
					mCityNameTextView.setText(weather.getCityName());
					mDateTextView.setText(weather.getDate());
					mOverviewTextView.setText(weather.getOverView());
					mUVATextView.setText(weather.getUva());
					mTemperaTextView.setText(weather.getTemperature());
					int res = Integer.parseInt(weather.getWeatherDrawable()
							.split("\\.")[0]);
					mWeatherIcon
							.setImageResource(R.drawable.weather_a_00 + res);
					break;
				case GET_WEATHER_FAIL:
					Tool.showToast((String) msg.obj);
					break;
				default:
					break;
				}
			}
		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View main = inflater.inflate(R.layout.fragment_today_todo, null);
		BannerNoBackTemplate template = new BannerNoBackTemplate(getActivity(),
				Tool.getString(R.string.today_todo));
		template.addView(main);
		View baseView = template.getWholeView();
		mTodoListView = (ListView) baseView.findViewById(R.id.lv_today_todo);
		mCityNameTextView = (TextView) baseView.findViewById(R.id.tv_city_name);
		mDateTextView = (TextView) baseView.findViewById(R.id.tv_date);
		mOverviewTextView = (TextView) baseView.findViewById(R.id.tv_overview);
		mUVATextView = (TextView) baseView.findViewById(R.id.tv_uva);
		mTemperaTextView = (TextView) baseView
				.findViewById(R.id.tv_temperature);
		mWeatherIcon = (ImageView) baseView.findViewById(R.id.tv_weather_icon);

		initView();
		return baseView;
	}

	private void initView() {
		Weather weather = new Weather();
		mCityNameTextView.setText(weather.getCityName());
		mDateTextView.setText(weather.getDate());
		mOverviewTextView.setText(weather.getOverView());
		mUVATextView.setText(weather.getUva());
		mTemperaTextView.setText(weather.getTemperature());
		int res = Integer.parseInt(weather.getWeatherDrawable().split("\\.")[0]);
		mWeatherIcon.setImageResource(R.drawable.weather_a_00 + res);

		ToDoListAdapter adapter = new ToDoListAdapter(getActivity());
		ArrayList<Event> data = new ArrayList<Event>();
		for (int i = 0; i < 20; i++) {
			data.add(new Event(0, System.currentTimeMillis(),
					"最最最最最最最最最込込込込込込込最最最最最最最最最込込込込込込込最最最最最最最最最"
							+ "込込込込込込込最最最最最最最最最込込込込込込込最最最最最最最最最込込込込込込込最"
							+ "最最最最最最最最込込込込込込込最最最最最最最最最込込込込込込込最最最最最最最最最"
							+ "込込込込込込込最最最最最最最最最込込込込込込込最最最最最最最最最込込込込込込込最"
							+ "最最最最最最最最込込込込込込込最最最最最最最最最込込込込込込込" + i));
		}
		adapter.setData(data);
		mTodoListView.setAdapter(adapter);
		
		mThread.start();
	}
}