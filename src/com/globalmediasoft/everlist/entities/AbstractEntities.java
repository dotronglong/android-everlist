package com.globalmediasoft.everlist.entities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.globalmediasoft.android.database.GMSDatabaseSQLite;
import com.globalmediasoft.everlist.settings.GlobalSettings;

public abstract class AbstractEntities extends GMSDatabaseSQLite {
	protected static String DB_NAME = GlobalSettings.dbName;
	
	public AbstractEntities(Context context) {
		super(context, DB_NAME, null, 5); // must use hard code version in child class
		// TODO Auto-generated constructor stub
		
		SQL_Log = true;
	}

	protected void upgradeTable(SQLiteDatabase db) {
		// db.execSQL("DROP TABLE IF EXISTS `" + tbl_name + "`");
		//db.execSQL("DROP TABLE IF EXISTS `" + "entity_definitions" + "`");
		//db.execSQL("DROP TABLE IF EXISTS `" + "entity_properties" + "`");
	}
}
