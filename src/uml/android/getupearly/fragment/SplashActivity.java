package uml.android.getupearly.fragment;

import uml.android.getupearly.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题
		getWindow().setFlags(WindowManager.LayoutParams.TYPE_STATUS_BAR,
				WindowManager.LayoutParams.TYPE_STATUS_BAR); // 全屏
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = new Intent(SplashActivity.this,
						MainActivity.class);
				SplashActivity.this.startActivity(mainIntent);
				SplashActivity.this.finish();
			}

		}, SPLASH_DISPLAY_LENGHT);
	}

}
