package uml.android.getupearly.util;

import java.util.ArrayList;
import java.util.Calendar;

import uml.android.getupearly.R;

public class Weather {
	private String mCityName;
	private String mDate;
	private String mTemperature;
	private String mWeatherDrawable;
	private String mUva;
	private String mOverView;
	private String mUpdateTime;

	public Weather() {
		mCityName = Tool.getString(R.string.nothing);
		mTemperature = Tool.getString(R.string.nothing);
		mWeatherDrawable = "32.gif";
		mOverView = Tool.getString(R.string.nothing);
		mUva = Tool.getString(R.string.nothing);
		mUpdateTime = Tool.getString(R.string.nothing);

		Calendar calendar = Calendar.getInstance();
		mDate = Tool.getMonthDate(calendar) + " "
				+ Tool.getString(R.string.weekday_name)
				+ Tool.getWeekDayName(calendar.get(Calendar.DAY_OF_WEEK) - 1);
	}

	public void initWeather(ArrayList<String> data) {
		mCityName = data.get(1);
		mTemperature = data.get(10).split(Tool.getString(R.string.colon))[2]
				.split(Tool.getString(R.string.semi))[0];
		mWeatherDrawable = data.get(8);
		mOverView = data.get(6).split(" ")[1];
		mUva = Tool.getString(R.string.uva)
				+ data.get(10).split(Tool.getString(R.string.colon))[6];
		mUpdateTime = data.get(4);

		Calendar calendar = Calendar.getInstance();
		mDate = Tool.getMonthDate(calendar) + " "
				+ Tool.getString(R.string.weekday_name)
				+ Tool.getWeekDayName(calendar.get(Calendar.DAY_OF_WEEK) - 1);
	}

	public String getCityName() {
		return mCityName;
	}

	public void setCityName(String mCityName) {
		this.mCityName = mCityName;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String mDate) {

		this.mDate = mDate;
	}

	public String getTemperature() {
		return mTemperature;
	}

	public void setTemperature(String mTemperature) {
		this.mTemperature = mTemperature;
	}

	public String getWeatherDrawable() {
		return mWeatherDrawable;
	}

	public void setWeatherDrawable(String mWeatherDrawable) {
		this.mWeatherDrawable = mWeatherDrawable;
	}

	public String getUva() {
		return mUva;
	}

	public void setUva(String mUva) {
		this.mUva = mUva;
	}

	public String getOverView() {
		return mOverView;
	}

	public void setOverView(String mOverView) {
		this.mOverView = mOverView;
	}

	public String getUpdateTime() {
		return mUpdateTime;
	}

	public void setUpdateTime(String mUpdateTime) {
		this.mUpdateTime = mUpdateTime;
	}

	@Override
	public String toString() {
		return mCityName + "\n" + mDate + "\n" + mTemperature + "\n"
				+ mWeatherDrawable + "\n" + mUva + "\n" + mOverView + "\n"
				+ mUpdateTime;
	}
}
