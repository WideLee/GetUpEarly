package uml.android.getupearly.fragment;

import uml.android.getupearly.R;
import uml.android.getupearly.R.layout;
import uml.android.getupearly.template.BannerNoBackTemplate;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ToDoListFragment extends Fragment {

	public ToDoListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View main = inflater.inflate(R.layout.fragment_todo_list, null);
		BannerNoBackTemplate template = new BannerNoBackTemplate(getActivity(),
				"To Do List");
		template.addView(main);
		return template.getWholeView();
	}
}