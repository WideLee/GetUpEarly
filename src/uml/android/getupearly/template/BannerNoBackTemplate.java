package uml.android.getupearly.template;

import android.content.Context;

public class BannerNoBackTemplate extends BannerTemplate {

	public BannerNoBackTemplate(Context context, String title) {
		super(context, title);
		hideIcBannerBack();
	}

}
