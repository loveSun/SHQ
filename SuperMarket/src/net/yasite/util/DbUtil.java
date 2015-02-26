package net.yasite.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbUtil extends SQLiteOpenHelper {

	public DbUtil(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// TODO Auto-generated method stub
		db.execSQL("create table goodsname(name varchar(26),tiao integer)");
		db.execSQL("create table car(id integer primary key autoincrement,goods_id varchar(10),image varchar(25),user varchar(20),name varchar(20),price varchar(20),rec_id varchar(5))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
