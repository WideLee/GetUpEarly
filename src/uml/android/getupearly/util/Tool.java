package uml.android.getupearly.util;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uml.android.getupearly.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
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

	public static long seconds(int y, int m, int d) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(y, m, d);
		return calendar.getTimeInMillis() / 1000;
	}

	public static Calendar calendar(long seconds) {
		seconds *= 1000;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(seconds);
		return calendar;
	}

	public static String minutes2clock(int m) {
		int h = m / 60;
		String hs = h < 10 ? "0" + h : "" + h;
		m = m % 60;
		String ms = m < 10 ? "0" + m : "" + m;

		return hs + ":" + ms;
	}

	public static String timeFmt(long seconds) {
		// Date t = new Date(System.currentTimeMillis());
		// int ty = t.getYear() + 1900;
		// int tm = t.getMonth();
		// int td = t.getDate();
		Calendar t = Calendar.getInstance();
		int ty = t.get(Calendar.YEAR);
		int tm = t.get(Calendar.MONTH);
		int td = t.get(Calendar.DATE);
		Calendar c = calendar(seconds);
		int cy = c.get(Calendar.YEAR);
		int cm = c.get(Calendar.MONTH);
		int cd = c.get(Calendar.DATE);
		int ch = c.get(Calendar.HOUR_OF_DAY);
		int cmin = c.get(Calendar.MINUTE);
		String chs = ch < 10 ? "0" + ch : "" + ch;
		String cmins = cmin < 10 ? "0" + cmin : "" + cmin;
		if (ty != cy) {
			return String.format(getString(R.string.year_fmt), cy, cm + 1);
		} else if (tm != cm || td != cd) {
			return String.format(getString(R.string.date_fmt), cm + 1, cd);
		} else {
			return String.format(getString(R.string.time_fmt), chs, cmins);
		}
	}

	public static String getMonthAndDay(long seconds, boolean same) {
		Calendar t = Calendar.getInstance();
		int ty = t.get(Calendar.YEAR);
		int tm = t.get(Calendar.MONTH);
		int td = t.get(Calendar.DATE);

		Calendar c = calendar(seconds);
		int cy = c.get(Calendar.YEAR);
		int cm = c.get(Calendar.MONTH);
		int cd = c.get(Calendar.DATE);

		if (!same) {
			if (ty == cy && tm == cm) {
				if (td == cd) {
					return getString(R.string.today);
				} else if (td == cd + 1) {
					return getString(R.string.yesterday);
				}
			}
		}

		return String.format(getString(R.string.date_fmt), cm + 1, cd);

	}

	/**
	 * ����ͼƬΪ�����Ĵ�С
	 * 
	 * @param src
	 *            ԭʼ�ߴ��ͼƬ
	 * @param width
	 *            ��Ҫ���ŵ��Ŀ�
	 * @param height
	 *            ��Ҫ���ŵ��ĸ�
	 * @return ���ź�õ���ͼƬ
	 */
	public static Bitmap scaleBitmap(Bitmap src, int width, int height) {
		int mWidth = src.getWidth();
		int mHeight = src.getHeight();

		// ��ȡ���ŵı���
		float scaleWidth = ((float) width) / mWidth;
		float scaleHeight = ((float) height) / mHeight;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(src, 0, 0, mWidth, mHeight, matrix, true);
	}

	/**
	 * �ж����������Ƿ�Ϊͬһ��
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isSameDay(Calendar a, Calendar b) {
		return a.get(Calendar.YEAR) == b.get(Calendar.YEAR)
				&& a.get(Calendar.MONTH) == b.get(Calendar.MONTH)
				&& a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * ����һ�����εļ�����
	 * 
	 * @param canvas
	 *            ����
	 * @param center_X
	 *            Բ��X����
	 * @param center_Y
	 *            Բ��Y����
	 * @param r
	 *            �뾶
	 * @param startAngle
	 *            ��ʼ�Ƕ�
	 * @param sweepAngle
	 *            �յ�Ƕ�
	 */
	public static void getSectorClip(Canvas canvas, float center_X,
			float center_Y, float r, float startAngle, float sweepAngle) {
		Path path = new Path();
		// �����ǻ��һ�������εļ�����
		path.moveTo(center_X, center_Y); // Բ��
		path.lineTo(
				(float) (center_X + r * Math.cos(startAngle * Math.PI / 180)), // ��ʼ��Ƕ���Բ�϶�Ӧ�ĺ�����
				(float) (center_Y + r * Math.sin(startAngle * Math.PI / 180))); // ��ʼ��Ƕ���Բ�϶�Ӧ��������
		path.lineTo(
				(float) (center_X + r * Math.cos(sweepAngle * Math.PI / 180)), // �յ�Ƕ���Բ�϶�Ӧ�ĺ�����
				(float) (center_Y + r * Math.sin(sweepAngle * Math.PI / 180))); // �յ��Ƕ���Բ�϶�Ӧ��������
		path.close();
		// ����һ�������Σ�����Բ
		RectF rectF = new RectF(center_X - r, center_Y - r, center_X + r,
				center_Y + r);
		// �����ǻ�û��μ������ķ���
		path.addArc(rectF, startAngle, sweepAngle - startAngle);
		canvas.clipPath(path);
	}

	/**
	 * ת������Ϊ�ַ�����ת�����Ϊ "xx��xx��"
	 * 
	 * @param c
	 *            ��Ҫת��������
	 * @return ת���Ľ���ַ���
	 */
	public static String getMonthDate(Calendar c) {
		int cm = c.get(Calendar.MONTH);
		int cd = c.get(Calendar.DAY_OF_MONTH);
		return String.format(Tool.getString(R.string.date_fmt), cm + 1, cd);
	}

	/**
	 * ת�����ڸ�ʽΪ�ַ���������� "һ��2014"
	 * 
	 * @param c
	 *            ��Ҫת��������
	 * @return ת��������ַ���
	 */
	public static String getYearMonth(Calendar c) {
		int cy = c.get(Calendar.YEAR);
		int cm = c.get(Calendar.MONTH);
		String month = new String();
		switch (cm) {
		case Calendar.JANUARY:
			month = Tool.getString(R.string.one);
			break;
		case Calendar.FEBRUARY:
			month = Tool.getString(R.string.two);
			break;
		case Calendar.MARCH:
			month = Tool.getString(R.string.three);
			break;
		case Calendar.APRIL:
			month = Tool.getString(R.string.four);
			break;
		case Calendar.MAY:
			month = Tool.getString(R.string.five);
			break;
		case Calendar.JUNE:
			month = Tool.getString(R.string.six);
			break;
		case Calendar.JULY:
			month = Tool.getString(R.string.seven);
			break;
		case Calendar.AUGUST:
			month = Tool.getString(R.string.eight);
			break;
		case Calendar.SEPTEMBER:
			month = Tool.getString(R.string.nine);
			break;
		case Calendar.OCTOBER:
			month = Tool.getString(R.string.ten);
			break;
		case Calendar.NOVEMBER:
			month = Tool.getString(R.string.eleven);
			break;
		case Calendar.DECEMBER:
			month = Tool.getString(R.string.twelve);
			break;
		}
		month += Tool.getString(R.string.month);
		return month + cy;
	}

	/**
	 * ��ȡһ���µĵ�һ�죬ָ��������µĵ�һ�ܵ����գ��������ϸ��µ���ĩ
	 * 
	 * @param calendar
	 *            һ���µ�ĳһ��
	 * @return ����µĵ�һ��
	 */
	public static Calendar getFirstDayOfMonth(Calendar calendar) {
		Calendar firstDay = Calendar.getInstance();
		firstDay.setTimeInMillis(calendar.getTimeInMillis());

		int numWeek = firstDay.get(Calendar.WEEK_OF_MONTH) - 1;
		int offset = numWeek * 7 + firstDay.get(Calendar.DAY_OF_WEEK) - 1;
		firstDay.add(Calendar.DAY_OF_MONTH, -offset);

		return firstDay;
	}

	/**
	 * ��ȡ�������������·�
	 * 
	 * @param start
	 *            ��ʼ������
	 * @param end
	 *            ����������
	 * @return ��ʼʱ�������ʱ�������·�
	 */
	public static int getDiffMonth(Calendar start, Calendar end) {
		return (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12
				+ end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
	}

	public static int getDiffDate(Calendar start, Calendar end) {
		start.set(java.util.Calendar.HOUR_OF_DAY, 0);
		start.set(java.util.Calendar.MINUTE, 0);
		start.set(java.util.Calendar.SECOND, 0);
		end.set(java.util.Calendar.HOUR_OF_DAY, 0);
		end.set(java.util.Calendar.MINUTE, 0);
		end.set(java.util.Calendar.SECOND, 0);

		int days = ((int) (end.getTime().getTime() / 1000) - (int) (start
				.getTime().getTime() / 1000)) / 3600 / 24 + 1;
		return days;
	}

	/**
	 * ��ȡ��ǰ����
	 * 
	 * @return ��ǰ����Calendar��ʵ��
	 */
	public static Calendar getCurCalendar() {
		return Calendar.getInstance();
	}

	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	public static String getWeekDayName(int index) {
		String weekdayName = new String();
		switch (index) {
		case 0:
			weekdayName = getString(R.string._sunday);
			break;
		case 1:
			weekdayName = getString(R.string._monday);
			break;
		case 2:
			weekdayName = getString(R.string._tuesday);
			break;
		case 3:
			weekdayName = getString(R.string._wednesday);
			break;
		case 4:
			weekdayName = getString(R.string._thursday);
			break;
		case 5:
			weekdayName = getString(R.string._friday);
			break;
		case 6:
			weekdayName = getString(R.string._saturday);
			break;
		default:
			break;
		}
		return weekdayName;
	}
}
