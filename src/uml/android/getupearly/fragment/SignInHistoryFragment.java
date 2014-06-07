package uml.android.getupearly.fragment;

import java.util.ArrayList;

import uml.android.getupearly.Event;
import uml.android.getupearly.R;
import uml.android.getupearly.adapter.SignHistoryAdapter;
import uml.android.getupearly.template.BannerNoBackTemplate;
import uml.android.getupearly.util.Tool;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class SignInHistoryFragment extends Fragment {

	private TextView mSignTextView;
	private ListView mHistoryListView;

	public SignInHistoryFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View main = inflater.inflate(R.layout.fragment_sign_history, null);
		BannerNoBackTemplate template = new BannerNoBackTemplate(getActivity(),
				Tool.getString(R.string.sign_history));
		template.addView(main);
		View baseView = template.getWholeView();

		mSignTextView = (TextView) baseView.findViewById(R.id.tv_significant);
		mHistoryListView = (ListView) baseView.findViewById(R.id.lv_history);
		initView();
		return baseView;
	}

	private void initView() {
		ArrayList<Event> data = new ArrayList<Event>();
		for (int i = 0; i < 20; i++) {
			data.add(new Event(0, System.currentTimeMillis(),
					"最最最最最最最最最込込込込込込込最最最最最最最最最込込込込込込込最最最最最最最最最"
							+ "込込込込込込込最最最最最最最最最込込込込込込込最最最最最最最最最込込込込込込込最"
							+ "最最最最最最最最込込込込込込込最最最最最最最最最込込込込込込込最最最最最最最最最"
							+ "込込込込込込込最最最最最最最最最込込込込込込込最最最最最最最最最込込込込込込込最"
							+ "最最最最最最最最込込込込込込込最最最最最最最最最込込込込込込込" + i));
		}
		SignHistoryAdapter adapter = new SignHistoryAdapter(getActivity());
		adapter.setData(data);
		mHistoryListView.setAdapter(adapter);
		mHistoryListView.setDivider(null);
	}
}