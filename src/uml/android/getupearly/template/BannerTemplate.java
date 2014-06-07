package uml.android.getupearly.template;

import uml.android.getupearly.R;
import uml.android.getupearly.util.BaseApplication;
import uml.android.getupearly.util.Tool;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class BannerTemplate extends BaseTemplate {
	private View mIcBannerBack;
	private TextView mBannerTitle;
	private View mBannerBackView;
	private View mBannerRightBtn;
	private View mBannerRightBtn1;
	private Button mBannerSureBtn;

	public BannerTemplate(Context context, String title) {
		super(context, R.layout.template_banner);
		mIcBannerBack = getWholeView().findViewById(R.id.ic_banner_bakc);
		mBannerTitle = (TextView) getWholeView()
				.findViewById(R.id.banner_title);
		mBannerBackView = getWholeView().findViewById(R.id.banner_back_view);
		mBannerRightBtn = getWholeView().findViewById(R.id.banner_more);
		mBannerRightBtn1 = getWholeView().findViewById(R.id.banner_btn1);
		mBannerSureBtn = (Button) getWholeView().findViewById(
				R.id.btn_banner_sure);

		mBannerTitle.setText(title);
	}

	public void setBannerTitle(String title) {
		mBannerTitle.setText(title);
	}

	protected void hideIcBannerBack() {
		mIcBannerBack.setVisibility(View.GONE);
		mBannerBackView.setPadding(Tool.dip2px(7.0f), 0, 0, 0);
	}

	public void setBannerBackListener(OnClickListener listener) {
		mBannerBackView.setOnClickListener(listener);
	}

	public void setRightBtnIc(int resId) {
		if (mBannerRightBtn != null) {
			((ImageView) mBannerRightBtn).setImageDrawable(BaseApplication
					.getContext().getResources().getDrawable(resId));
		}
	}

	public void setRightBtn1Ic(int resId) {
		if (mBannerRightBtn1 != null) {
			((ImageView) mBannerRightBtn1).setImageDrawable(BaseApplication
					.getContext().getResources().getDrawable(resId));
		}
	}

	public void setRightBtnClickListener(OnClickListener listener) {
		if (mBannerRightBtn != null) {
			mBannerRightBtn.setVisibility(View.VISIBLE);
			mBannerRightBtn.setOnClickListener(listener);
		}
	}

	public void setRightBtn1ClickListener(OnClickListener listener) {
		if (mBannerRightBtn1 != null) {
			mBannerRightBtn1.setVisibility(View.VISIBLE);
			mBannerRightBtn1.setOnClickListener(listener);
		}
	}

	public void setSureBtnText(String text) {
		if (mBannerSureBtn != null) {
			mBannerSureBtn.setText(text);
		}
	}

	public void setSureBtnClickListener(OnClickListener listener) {
		if (mBannerSureBtn != null) {
			mBannerSureBtn.setVisibility(View.VISIBLE);
			mBannerSureBtn.setOnClickListener(listener);
		}
	}

	public void setSureBtnEnabled(boolean enabled) {
		if (mBannerSureBtn != null) {
			mBannerSureBtn.setEnabled(enabled);
		}
	}
}
