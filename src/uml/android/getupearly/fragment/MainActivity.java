package uml.android.getupearly.fragment;

import uml.android.getupearly.R;
import uml.android.getupearly.R.drawable;
import uml.android.getupearly.R.id;
import uml.android.getupearly.R.layout;
import uml.android.getupearly.R.menu;
import uml.android.getupearly.R.string;
import uml.android.getupearly.util.Tool;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private TabHost mTabHost;
	private TabWidget mTabWidget;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		mTabHost.setup();
		addTab(Tool.getString(R.string.today_signin), R.drawable.ic_sign,
				R.id.tab1);
		addTab(Tool.getString(R.string.today_todo), R.drawable.ic_today_todo,
				R.id.tab2);
		addTab(Tool.getString(R.string.todo_list), R.drawable.ic_todo_list,
				R.id.tab3);
		addTab(Tool.getString(R.string.sign_history),
				R.drawable.ic_sign_history, R.id.tab4);
		for (int i = 0; i < 4; i++) {
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.selector_tab_background);
		}
	}

	private void addTab(String label, int drawableId, int content) {
		TabHost.TabSpec spec = mTabHost.newTabSpec(label);

		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.indicator, mTabWidget, false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(label);
		View icon = (View) tabIndicator.findViewById(R.id.icon);
		icon.setBackgroundResource(drawableId);

		spec.setIndicator(tabIndicator);
		spec.setContent(content);
		mTabHost.addTab(spec);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
