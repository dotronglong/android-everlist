package com.globalmediasoft.everlist.entities.entity;

import android.content.Context;

import com.globalmediasoft.android.database.GMSDatabase;
import com.globalmediasoft.everlist.entities.AbstractEntities;

public class EntityValues extends AbstractEntities {

	public EntityValues(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setupTable() {
		// TODO Auto-generated method stub
		setTableName("entity_values")
		.addColumn(new TableColumn("entity_id", "INTEGER", true, true))
		.addColumn(new TableColumn("property_id", "INTEGER"))
		.addColumn(new TableColumn("value", "TEXT"))
		.addColumn(new TableColumn("int_value", "INTEGER"))
		.addColumn(new TableColumn("double_value", "DOUBLE"))
		.addColumn(new TableColumn("datetime_value", "DATETIME"));
	}
	
	public static class Model extends GMSDatabase.Model {
		public int entity_id;
		public int property_id;
		public String value;
		public int int_value;
		public double double_value;
		public String datetime_value;
	}
}
