package com.globalmediasoft.everlist.entities.entity;

import android.content.Context;

import com.globalmediasoft.android.database.GMSDatabase;
import com.globalmediasoft.everlist.entities.AbstractEntities;

public class Entity extends AbstractEntities {

	public Entity(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setupTable() {
		// TODO Auto-generated method stub
		setTableName("entities")
		.addColumn(new TableColumn("id", "INTEGER", true, true))
		.addColumn(new TableColumn("definition_name", "VARCHAR(255)"))
		.addColumn(new TableColumn("title", "VARCHAR(255)"));
	}

	public static class Model extends GMSDatabase.Model {
		public int user_id;
		public String title;
	}
}
