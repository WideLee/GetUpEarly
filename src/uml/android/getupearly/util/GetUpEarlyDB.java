package uml.android.getupearly.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GetUpEarlyDB extends SQLiteOpenHelper {

	private static final String DB_NAME = "GetUpEarly.db";
	private static final int DB_VRESION = 1;
	private static final String EVENT_TABLE = "event";
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final String EVENT_SQL_CREATE = "create table "
			+ EVENT_TABLE + " ( eid integer primary key autoincrement,"
			+ " signin integer, " + " time text	, " + " content text);";

	public GetUpEarlyDB(Context context) {
		super(context, DB_NAME, null, DB_VRESION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 数据库没有表时创建一个
		db.execSQL(EVENT_SQL_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 升级数据库
		db.execSQL("DROP TABLE IF EXISTS notes");
		onCreate(db);
	}

	// insert event
	public long insert(Event entity) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("signin", entity.isSignIn());
		values.put("content", entity.getContent());
		values.put("time", dateFormat.format(new Date(entity.getTime())));
		// 必须保证 values 至少一个字段不为null ，否则出错
		long rid = db.insert(EVENT_TABLE, null, values);
		entity.setId(rid);
		db.close();
		return rid;
	}

	// 删除数据操作
	public int deleteEventById(Integer id) {
		SQLiteDatabase db = getWritableDatabase();
		String whereClause = "eid = ?";
		String[] whereArgs = { id.toString() };
		int row = db.delete(EVENT_TABLE, whereClause, whereArgs);
		db.close();
		return row;
	}

	public int deleteEventBySignInfo(String signin) {
		SQLiteDatabase db = getWritableDatabase();
		String whereClause = "signin = ?";
		String[] whereArgs = { signin };
		int row = db.delete(EVENT_TABLE, whereClause, whereArgs);
		db.close();
		return row;
	}

	// 更新数据操作
	public int updateEventById(Event entity) {
		SQLiteDatabase db = getWritableDatabase();
		String whereClause = "eid = ?";
		String[] whereArgs = { Integer.toString((int) entity.getId()) };
		ContentValues values = new ContentValues();
		values.put("signin", entity.isSignIn());
		values.put("content", entity.getContent());
		values.put("time", entity.getTime());

		int rows = db.update(EVENT_TABLE, values, whereClause, whereArgs);
		db.close();
		return rows;
	}

	// 查询数据操作
	public Event getEventById(Integer id) throws ParseException {
		Event event = null;
		SQLiteDatabase db = getReadableDatabase();
		String selection = "eid = ?";
		String[] selectionArgs = { id.toString() };
		Cursor c = db.query(EVENT_TABLE, null, selection, selectionArgs, null,
				null, null);
		if (c.moveToNext()) {
			event = new Event(c.getInt(1), (long) c.getInt(2), c.getString(3));
			event.setId(c.getLong(0));
		}
		c.close();
		db.close();
		return event;
	}

	public List<Event> getEventByDate(Date date) throws ParseException {
		List<Event> list = new ArrayList<Event>();
		SQLiteDatabase db = getReadableDatabase();
		SimpleDateFormat format_begin = new SimpleDateFormat(
				"yyyy-MM-dd 00:00:00");
		SimpleDateFormat format_end = new SimpleDateFormat(
				"yyyy-MM-dd 23:59:59");
		String dateBegin = format_begin.format(date);
		String dateEnd = format_end.format(date);
		String SEARCH_EVENT = "select * from " + EVENT_TABLE
				+ " where  ( datetime(time) < \"" + dateEnd + "\" and "
				+ "datetime(time) > \"" + dateBegin + "\" );";

		Cursor c = db.rawQuery(SEARCH_EVENT, null);

		while (c.moveToNext()) {
			Event event = new Event(c.getInt(1), dateFormat.parse(
					c.getString(2)).getTime(), c.getString(3));
			event.setId(c.getLong(0));
			list.add(event);
		}
		c.close();
		db.close();

		return list;
	}

	public List<Event> getSignInEventByDate(Date date) throws ParseException {
		List<Event> list = new ArrayList<Event>();
		SQLiteDatabase db = getReadableDatabase();
		SimpleDateFormat format_begin = new SimpleDateFormat(
				"yyyy-MM-dd 00:00:00");
		SimpleDateFormat format_end = new SimpleDateFormat(
				"yyyy-MM-dd 23:59:59");
		String dateBegin = format_begin.format(date);
		String dateEnd = format_end.format(date);
		String SEARCH_EVENT = "select * from " + EVENT_TABLE
				+ " where  ( datetime(time) < \"" + dateEnd + "\" and "
				+ "datetime(time) > \"" + dateBegin + "\" );";
		Cursor c = db.rawQuery(SEARCH_EVENT, null);

		while (c.moveToNext()) {
			if (c.getInt(1) == 1) {
				Event event = new Event(c.getInt(1), dateFormat.parse(
						c.getString(2)).getTime(), c.getString(3));
				event.setId(c.getLong(0));
				list.add(event);
			}
		}
		c.close();
		db.close();

		return list;
	}

	public List<Event> getTodoEventByDate(Date date) throws ParseException {
		List<Event> list = new ArrayList<Event>();
		SQLiteDatabase db = getReadableDatabase();
		SimpleDateFormat format_begin = new SimpleDateFormat(
				"yyyy-MM-dd 00:00:00");
		SimpleDateFormat format_end = new SimpleDateFormat(
				"yyyy-MM-dd 23:59:59");
		String dateBegin = format_begin.format(date);
		String dateEnd = format_end.format(date);
		String SEARCH_EVENT = "select * from " + EVENT_TABLE
				+ " where  ( datetime(time) < \"" + dateEnd + "\" and "
				+ "datetime(time) > \"" + dateBegin + "\" );";
		Cursor c = db.rawQuery(SEARCH_EVENT, null);

		while (c.moveToNext()) {
			if (c.getInt(1) == 0) {
				Event event = new Event(c.getInt(1), dateFormat.parse(
						c.getString(2)).getTime(), c.getString(3));
				event.setId(c.getLong(0));
				list.add(event);
			}
		}
		c.close();
		db.close();

		return list;
	}

	public List<Event> getALLEvent() throws ParseException {
		List<Event> list = new ArrayList<Event>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query(EVENT_TABLE, null, null, null, null, null, "time");

		while (c.moveToNext()) {
			Event event = new Event(c.getInt(1), dateFormat.parse(
					c.getString(2)).getTime(), c.getString(3));
			event.setId(c.getLong(0));
			list.add(event);
		}
		c.close();
		db.close();

		return list;
	}

	public List<Event> getALLSignInEvent() throws ParseException {
		List<Event> list = new ArrayList<Event>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query(EVENT_TABLE, null, null, null, null, null, "time");

		while (c.moveToNext()) {
			if (c.getInt(1) == 1) {
				Event event = new Event(c.getInt(1), dateFormat.parse(
						c.getString(2)).getTime(), c.getString(3));
				event.setId(c.getLong(0));
				list.add(event);
			}
		}
		c.close();
		db.close();

		return list;
	}

}
