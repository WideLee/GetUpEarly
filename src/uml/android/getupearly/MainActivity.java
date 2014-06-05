package uml.android.getupearly;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.renderscript.ProgramFragmentFixedFunction.Builder.Format;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
		addTab("我的", R.drawable.ic_launcher, R.id.tab1);
		addTab("我的12", R.drawable.ic_launcher, R.id.tab2);
		addTab("我的23", R.drawable.ic_launcher, R.id.tab3);
		addTab("我的42", R.drawable.ic_launcher, R.id.tab4);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
