package uml.android.getupearly.fragment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import uml.android.getupearly.R;
import uml.android.getupearly.adapter.SignHistoryAdapter;
import uml.android.getupearly.template.BannerNoBackTemplate;
import uml.android.getupearly.util.Event;
import uml.android.getupearly.util.GetUpEarlyDB;
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
	private GetUpEarlyDB mDB;

	public SignInHistoryFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mDB = new GetUpEarlyDB(getActivity());

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

	@Override
	public void onResume() {
		List<Event> data = new ArrayList<Event>();
		try {
			data = mDB.getALLSignInEvent();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		((SignHistoryAdapter) mHistoryListView.getAdapter()).setData(data);
		System.out.println("SignOnResume");
		super.onResume();
	}

	private void initView() {
		List<Event> data = new ArrayList<Event>();
		try {
			data = mDB.getALLSignInEvent();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SignHistoryAdapter adapter = new SignHistoryAdapter(getActivity());
		adapter.setData(data);
		mHistoryListView.setAdapter(adapter);
		mHistoryListView.setDivider(null);
	}
}