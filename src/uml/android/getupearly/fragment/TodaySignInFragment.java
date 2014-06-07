package uml.android.getupearly.fragment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import uml.android.getupearly.R;
import uml.android.getupearly.template.BannerNoBackTemplate;
import uml.android.getupearly.util.Event;
import uml.android.getupearly.util.GetUpEarlyDB;
import uml.android.getupearly.util.Tool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TodaySignInFragment extends Fragment {

	protected static final int GUI_STOP_NOTIFIER = 0x108;
	protected static final int GUI_THREADING_NOTIFIER = 0x109;
	private ImageView iv;
	private Thread thread;
	private static int i, flag;
	TextView m_textView;
	EditText m_editText;

	private Button mOKButton;
	private long mSignTime;
	private GetUpEarlyDB mDB;

	public TodaySignInFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mDB = new GetUpEarlyDB(getActivity());

		View main = inflater.inflate(R.layout.fragment_today_sign, null);
		BannerNoBackTemplate template = new BannerNoBackTemplate(getActivity(),
				Tool.getString(R.string.today_signin));
		template.setRightBtnIc(R.drawable.ic_banner_more);
		template.setRightBtnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Tool.showToast("Jump to game list");
			}
		});
		template.addView(main);
		View baseView = template.getWholeView();

		Calendar cal = Calendar.getInstance();

		try {
			flag = mDB.getSignInEventByDate(cal.getTime()).size() == 0 ? 0 : 1;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return baseView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		iv = (ImageView) getView().findViewById(R.id.imageview_button);
		m_textView = (TextView) getView().findViewById(R.id.inputtips);
		m_editText = (EditText) getView().findViewById(R.id.inputbox);

		mOKButton = (Button) getView().findViewById(R.id.btn_ok);
		if (flag == 0) {
			iv.setImageResource(R.drawable.signbutton1);
		} else {
			iv.setImageResource(R.drawable.signbutton2);
		}
		iv.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (flag == 0) {
					ImageView tmp = (ImageView) v;
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						// 按住事件发生后执行代码的区域
						new Thread(new Runnable() {
							@Override
							public void run() {
								Message msg;
								msg = mUIHandler.obtainMessage(0);
								msg.sendToTarget();
							}
						}).start();

						break;
					}
					case MotionEvent.ACTION_MOVE: {
						// 移动事件发生后执行代码的区域
						break;
					}
					case MotionEvent.ACTION_UP: {
						// 松开事件发生后执行代码的区域
						new Thread(new Runnable() {
							@Override
							public void run() {
								Message msg;
								if (i != 9)
									i = 100;
								msg = mUIHandler.obtainMessage(9);
								msg.sendToTarget();
							}
						}).start();
						break;
					}

					default:
						break;
					}
					return true;
				}
				return false;
			}

		});

		mOKButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (m_editText.getText().toString().equals("")) {
					Tool.showToast(Tool.getString(R.string.say_something));
				} else {
					mDB.insert(new Event(1, mSignTime, m_editText.getText()
							.toString()));
					Tool.showToast(Tool.getString(R.string.sign_success));
					m_textView.setVisibility(View.INVISIBLE);
					m_editText.setVisibility(View.INVISIBLE);
					mOKButton.setVisibility(View.INVISIBLE);
					Tool.hideKeyBoard(getView());
				}
			}
		});
	}

	/* UIHandler负责更新页面 */
	private Handler mUIHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				thread = new Thread(new Runnable() {
					@Override
					public void run() {
						Message msg;
						for (i = 1; i <= 8; i++) {
							try {
								Thread.sleep(100); // 调速度
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							msg = mUIHandler.obtainMessage(i);
							msg.sendToTarget();
						}
					}
				});
				thread.start();
				break;
			case 1:
				iv.setImageDrawable(getResources().getDrawable(
						R.drawable.progress1));
				break;
			case 2:
				iv.setImageDrawable(getResources().getDrawable(
						R.drawable.progress2));
				break;
			case 3:
				iv.setImageDrawable(getResources().getDrawable(
						R.drawable.progress3));
				break;
			case 4:
				iv.setImageDrawable(getResources().getDrawable(
						R.drawable.progress4));
				break;
			case 5:
				iv.setImageDrawable(getResources().getDrawable(
						R.drawable.progress5));
				break;
			case 6:
				iv.setImageDrawable(getResources().getDrawable(
						R.drawable.progress6));
				break;
			case 7:
				iv.setImageDrawable(getResources().getDrawable(
						R.drawable.progress7));
				break;
			case 8:
				iv.setImageDrawable(getResources().getDrawable(
						R.drawable.progress8));
				break;
			case 9:
				// thread.stop();
				if (i == 100) {
					iv.setImageDrawable(getResources().getDrawable(
							R.drawable.signbutton1));
				} else if (i == 9) {
					flag = 1; // 不可再触摸
					m_textView.setVisibility(View.VISIBLE);
					m_editText.setVisibility(View.VISIBLE);
					mOKButton.setVisibility(View.VISIBLE);
					m_editText.requestFocus();
					mSignTime = System.currentTimeMillis();
				}
				break;
			}
		}
	};
}