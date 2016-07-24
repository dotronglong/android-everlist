package com.globalmediasoft.everlist.entities.entity;
import java.util.Vector;

import com.globalmediasoft.android.database.GMSDatabase;
import com.globalmediasoft.everlist.entities.AbstractEntities;

import android.content.Context;
import android.database.Cursor;

public class EntityDefinitions extends AbstractEntities {

	public EntityDefinitions(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setupTable() {
		// TODO Auto-generated method stub
		setTableName("entity_definitions")
		.addColumn(new TableColumn("title", "VARCHAR(255)"))
		.addColumn(new TableColumn("name", "VARCHAR(255)"))
		.addColumn(new TableColumn("description", "TEXT"));
	}
	
	public static class Model extends GMSDatabase.Model {
		public String name;
		public String title;
		public String description;
	}

	public Cursor getDefinitions() {
		return this.db(true).query(true, TBL_NAME, getColumns(), null, null, null, null, null, null);
	}
	
	public Vector<Model> getDefinitions(boolean toModel) {
		return (Vector<Model>) new Model().parseAll(getDefinitions());
	}
	
	public Cursor getDefinitionByName(String name) {
		return this.db(true).query(TBL_NAME, getColumns(), "name = ?", new String[]{name}, null, null, null, "1");
	}
}