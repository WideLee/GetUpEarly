package uml.android.getupearly;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Tool {
	public static final int NETWORK_NOT_CONNECTED = -1;
	public static final int NETWORK_TYPE_2G = 0;
	public static final int NETWORK_TYPE_3G = 1;
	public static final int NETWORK_TYPE_WIFI = 2;

	protected Tool() {
	}

	public static int getNetworkType() {
		ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication
				.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		int networkType = NETWORK_TYPE_2G;
		if (info == null) {
			networkType = NETWORK_NOT_CONNECTED;
		} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			networkType = NETWORK_TYPE_WIFI;
		} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
			switch (info.getSubtype()) {
			case TelephonyManager.NETWORK_TYPE_CDMA:
			case TelephonyManager.NETWORK_TYPE_GPRS:
			case TelephonyManager.NETWORK_TYPE_EDGE:
			case TelephonyManager.NETWORK_TYPE_1xRTT:
			case TelephonyManager.NETWORK_TYPE_IDEN:
				networkType = NETWORK_TYPE_2G;
				break;
			case TelephonyManager.NETWORK_TYPE_UMTS:
			case TelephonyManager.NETWORK_TYPE_HSDPA:
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
			case TelephonyManager.NETWORK_TYPE_HSPA:
			case TelephonyManager.NETWORK_TYPE_EHRPD:
			case TelephonyManager.NETWORK_TYPE_HSPAP:
			case TelephonyManager.NETWORK_TYPE_LTE:
				networkType = NETWORK_TYPE_3G;
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			default:
				networkType = NETWORK_TYPE_2G;
				break;
			}
		}
		return networkType;
	}

	public static int dip2px(float dip) {
		final float scale = BaseApplication.getContext().getResources()
				.getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	public static float px2dip(float px) {
		final float scale = BaseApplication.getContext().getResources()
				.getDisplayMetrics().density;
		return px / scale + 0.5f;
	}

	private static int screenW = -1, screenH = -1;

	public static int getScreenW() {
		if (screenW < 0) {
			initScreenDisplayParams();
		}
		return screenW;
	}

	public static int getScreenH() {
		if (screenH < 0) {
			initScreenDisplayParams();
		}
		return screenH;
	}

	private static void initScreenDisplayParams() {
		DisplayMetrics dm = BaseApplication.getContext().getResources()
				.getDisplayMetrics();
		screenW = dm.widthPixels;
		screenH = dm.heightPixels;
	}

	public static void showToast(String toast) {
		if (!TextUtils.isEmpty(toast)) {
			Toast.makeText(BaseApplication.getContext(), toast,
					Toast.LENGTH_SHORT).show();
		}
	}

	public static void showLongToast(String toast) {
		if (!TextUtils.isEmpty(toast)) {
			Toast.makeText(BaseApplication.getContext(), toast, Toast.LENGTH_LONG)
					.show();
		}
	}

	public static String md5(String plainText) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			byte[] array = md.digest(plainText.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

	public static boolean validEmail(String email) {
		Pattern pattern = Pattern
				.compile(
						"^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$",
						Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static int bool2int(boolean flag) {
		if (flag) {
			return 1;
		} else {
			return 0;
		}
	}

	public static boolean int2bool(int flag) {
		if (flag == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static String getString(int resId) {
		return BaseApplication.getContext().getString(resId);
	}

	public static void showKeyBoard(View view) {
		InputMethodManager imm = (InputMethodManager) BaseApplication
				.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	public static void hideKeyBoard(View view) {
		InputMethodManager imm = (InputMethodManager) BaseApplication
				.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}
