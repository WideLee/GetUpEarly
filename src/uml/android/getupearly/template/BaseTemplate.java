package uml.android.getupearly.template;

import uml.android.getupearly.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class BaseTemplate {

	private View mWholeView;
	private ViewGroup mContainer;

	public BaseTemplate(Context context) {
		this(context, R.layout.template_base);
	}

	public BaseTemplate(Context context, int templateLayout) {
		mWholeView = LayoutInflater.from(context).inflate(templateLayout, null);
		mContainer = (ViewGroup) mWholeView.findViewById(R.id.container);
	}

	public void addView(View child) {
		mContainer.addView(child);
	}

	public void addView(View child, int index) {
		mContainer.addView(child, index);
	}

	public void addView(View child, LayoutParams params) {
		mContainer.addView(child, params);
	}

	public void addView(View child, int index, LayoutParams params) {
		mContainer.addView(child, index, params);
	}

	public View getWholeView() {
		return mWholeView;
	}

	public View getContainerView() {
		return mContainer;
	}
}
