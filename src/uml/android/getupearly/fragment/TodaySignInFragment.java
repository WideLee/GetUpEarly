package uml.android.getupearly.fragment;

import uml.android.getupearly.R;
import uml.android.getupearly.R.layout;
import uml.android.getupearly.template.BannerNoBackTemplate;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TodaySignInFragment extends Fragment {

	public TodaySignInFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View main = inflater.inflate(R.layout.fragment_today_sign, null);
		BannerNoBackTemplate template = new BannerNoBackTemplate(getActivity(),
				"Sign in");
		template.addView(main);
		return template.getWholeView();
	}
}