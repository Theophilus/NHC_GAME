package com.nhc.database.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	// The database name and version
	private static final String DB_NAME = "login1";
	private static final int DB_VERSION = 1;
	// The database user table
	private static final String DB_TABLE = "create table user (id integer primary key autoincrement, " 
											+ "username text not null, password text not null, fullname text not null, age integer not null, " 
											+ "sex text not null, state text not null);";
	/**
	 * Database Helper constructor. 
	 * @param context
	 */
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	/**
	 * Creates the database tables.
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
		System.out.println("Table craete sql is"+ DB_TABLE);
		database.execSQL(DB_TABLE);
		

	}
	/**
	 * Handles the table version and the drop of a table.   
	 */			
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		
		if (newVersion > oldVersion)
		{
			System.out.println("Inside upgrade");
			database.execSQL("ALTER TABLE" + DB_TABLE + " ADD COLUMN" + "fullname text");
		}
		Log.w(DatabaseHelper.class.getName(),
				"Upgrading databse from version" + oldVersion + "to " 
				+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS user");
		onCreate(database);
	}
}
