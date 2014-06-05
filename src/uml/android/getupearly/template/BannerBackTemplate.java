package uml.android.getupearly.template;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class BannerBackTemplate extends BannerTemplate {

	public BannerBackTemplate(final Context context, String title) {
		super(context, title);
		setBannerBackListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((Activity) context).finish();
			}
		});
	}
}
