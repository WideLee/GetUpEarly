package uml.android.getupearly.fragment;

import java.util.Calendar;

import uml.android.getupearly.R;
import uml.android.getupearly.template.BannerBackTemplate;
import uml.android.getupearly.util.Event;
import uml.android.getupearly.util.GetUpEarlyDB;
import uml.android.getupearly.util.Tool;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddToDoActivity extends Activity {

	private DatePickerDialog mDatePicker;
	private TimePickerDialog mTimePicker;
	private Button mDatePickButton;
	private Button mTimePickButton;
	private Calendar mCalendar;
	private EditText mEditText;
	private boolean mIsPickDate;
	private GetUpEarlyDB mDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mIsPickDate = false;
		mDB = new GetUpEarlyDB(this);
		mCalendar = Calendar.getInstance();
		BannerBackTemplate template = new BannerBackTemplate(this,
				Tool.getString(R.string.add_todo));
		template.setRightBtnIc(R.drawable.ic_check_tick);
		template.setRightBtnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mIsPickDate) {
					Tool.showToast(Tool.getString(R.string.select_date_hint));
				} else if (mEditText.getText().toString().equals("")) {
					Tool.showToast(Tool.getString(R.string.say_something));
				} else {
					mDB.insert(new Event(0, mCalendar.getTimeInMillis(),
							mEditText.getText().toString()));
					Tool.showToast(Tool.getString(R.string.add_ok));
					finish();
				}
			}
		});
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.activity_add_todo, null);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		template.addView(contentView, params);
		setContentView(template.getWholeView());

		mDatePicker = new DatePickerDialog(this, 0, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				mCalendar.set(Calendar.YEAR, year);
				mCalendar.set(Calendar.MONTH, monthOfYear);
				mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				mIsPickDate = true;
			}
		}, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
				mCalendar.get(Calendar.DAY_OF_MONTH));
		mTimePicker = new TimePickerDialog(this, 0, new OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				mCalendar.set(Calendar.MINUTE, minute);
			}
		}, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE),
				true);

		mDatePickButton = (Button) findViewById(R.id.btn_select_date);
		mTimePickButton = (Button) findViewById(R.id.btn_select_time);

		mDatePickButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDatePicker.show();
			}
		});

		mTimePickButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTimePicker.show();
			}
		});

		mEditText = (EditText) findViewById(R.id.et_todo);
	}
}
