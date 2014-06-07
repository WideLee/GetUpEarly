package uml.android.getupearly.fragment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uml.android.getupearly.R;
import uml.android.getupearly.adapter.CalendarViewPager;
import uml.android.getupearly.adapter.ToDoListAdapter;
import uml.android.getupearly.adapter.ViewPager4SameItem.OnPageSelectedListener;
import uml.android.getupearly.template.BannerNoBackTemplate;
import uml.android.getupearly.util.BaseApplication;
import uml.android.getupearly.util.Event;
import uml.android.getupearly.util.GetUpEarlyDB;
import uml.android.getupearly.util.Tool;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ToDoListFragment extends Fragment {

	private CalendarViewPager mCalendarViewPager;
	private Button mPreButton;
	private Button mNextButton;
	private TextView mMonthNameTextView;
	private OnPageSelectedListener mOnPageSelectedListener;
	private LinearLayout monthTitleLayout;
	private GetUpEarlyDB mDB;
	private ListView mTodoListView;

	public ToDoListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mDB = new GetUpEarlyDB(getActivity());

		View main = inflater.inflate(R.layout.fragment_todo_list, null);
		BannerNoBackTemplate template = new BannerNoBackTemplate(getActivity(),
				Tool.getString(R.string.todo_list));
		template.setRightBtnIc(R.drawable.ic_banner_add);
		template.setRightBtnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), AddToDoActivity.class);
				startActivity(intent);
			}
		});
		template.addView(main);
		View baseView = template.getWholeView();
		mCalendarViewPager = (CalendarViewPager) baseView
				.findViewById(R.id.calendar);
		mPreButton = (Button) baseView.findViewById(R.id.btn_pre_month);
		mNextButton = (Button) baseView.findViewById(R.id.btn_next_month);
		mMonthNameTextView = (TextView) baseView
				.findViewById(R.id.tv_month_name);
		monthTitleLayout = (LinearLayout) baseView
				.findViewById(R.id.ll_month_title);
		mTodoListView = (ListView) baseView.findViewById(R.id.lv_todo);

		initView();
		return baseView;
	}

	private void initView() {
		mPreButton.setVisibility(View.VISIBLE);
		mNextButton.setVisibility(View.VISIBLE);
		mMonthNameTextView.setText(Tool.getYearMonth(mCalendarViewPager
				.getCurDate()));

		for (int j = 0; j < CalendarViewPager.DAY_NUM_OF_WEEK; j++) {
			TextView tv = new TextView(getActivity());
			tv.setTextSize(20);
			tv.setGravity(Gravity.CENTER);
			tv.setTextColor(getActivity().getResources().getColor(
					R.color.divider_of_dialog));
			tv.setTextSize(16);
			tv.setText(Tool.getWeekDayName(j));
			LinearLayout.LayoutParams inner_params = new LinearLayout.LayoutParams(
					Tool.getScreenW() / 8,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			monthTitleLayout.addView(tv, inner_params);
		}

		mPreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCalendarViewPager.setCurrentItem(mCalendarViewPager
						.getCurrentItem() - 1);
			}
		});

		mNextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCalendarViewPager.setCurrentItem(mCalendarViewPager
						.getCurrentItem() + 1);
			}
		});

		mOnPageSelectedListener = new OnPageSelectedListener() {

			@Override
			public void onPageSelected(int position) {
				mMonthNameTextView.setText(Tool.getYearMonth(mCalendarViewPager
						.getCurDate()));
				if (mCalendarViewPager.isFirstMonth()) {
					mPreButton.setVisibility(View.INVISIBLE);
				} else {
					mPreButton.setVisibility(View.VISIBLE);
				}

				if (mCalendarViewPager.isLastMonth()) {
					mNextButton.setVisibility(View.INVISIBLE);
				} else {
					mNextButton.setVisibility(View.VISIBLE);
				}

			}
		};
		mCalendarViewPager.setOnPageSelectedListener(mOnPageSelectedListener);
		mCalendarViewPager.setOnDateClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Calendar cal = (Calendar) v.getTag();
				Toast.makeText(BaseApplication.getContext(), "Click",
						Toast.LENGTH_SHORT).show();
				updateToDoList(cal);
			}
		});

		ToDoListAdapter adapter = new ToDoListAdapter(getActivity());
		List<Event> data = new ArrayList<Event>();
		try {
			data = mDB.getTodoEventByDate(Calendar.getInstance().getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		adapter.setData(data);
		mTodoListView.setAdapter(adapter);
	}

	private void updateToDoList(Calendar cal) {
		List<Event> data = new ArrayList<Event>();
		try {
			data = mDB.getTodoEventByDate(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ToDoListAdapter adapter = (ToDoListAdapter) mTodoListView.getAdapter();
		adapter.setData(data);
	}

	@Override
	public void onResume() {
		initView();
		super.onResume();
	}
}