package com.globalmediasoft.everlist.entities.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.globalmediasoft.android.database.GMSDatabase;
import com.globalmediasoft.everlist.R;
import com.globalmediasoft.everlist.entities.AbstractEntities;

public class EntityProperties extends AbstractEntities {
	public static final int TYPE_STRING = 0;
	public static final int TYPE_INT = 1;
	public static final int TYPE_DOUBLE = 2;
	public static final int TYPE_MONEY = 3;
	public static final int TYPE_PHOTO = 4;
	public static final int TYPE_SELECT = 5;
	
	public static class EntityProperty {
		public int type;
		public String label;
		
		EntityProperty(int type, String label) {
			this.type  = type;
			this.label = label;
		}
	}

	public EntityProperties(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setupTable() {
		// TODO Auto-generated method stub
		setTableName("entity_properties")
		.addColumn(new TableColumn("id", "INTEGER", true, true))
		.addColumn(new TableColumn("entity", "VARCHAR(255)"))
		.addColumn(new TableColumn("name", "VARCHAR(255)"))
		.addColumn(new TableColumn("type", "INTEGER"));
	}

	public static class Model extends GMSDatabase.Model {
		public int id;
		public String entity;
		public String name;
		public int type;
	}
	
	public static int getType(Activity activity, String _type) {
		int type = TYPE_STRING;
		ArrayList<EntityProperty> maps = getMaps(activity);
		Iterator<EntityProperty> iterator = maps.iterator();
		while (iterator.hasNext()) {
			EntityProperty item = iterator.next();
			if (item.label == _type) {
				type = item.type;
				break;
			}
		}
		
		return type;
	}
	
	public static int getType(Activity activity, int position) {		
		return getType(activity, getList(activity).get(position));
	}
	
	public static int getListPosition(Activity activity, int type) {
		ArrayList<EntityProperty> maps = getMaps(activity);
		Iterator<EntityProperty> iterator = maps.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			EntityProperty item = iterator.next();
			if (item.type == type) {
				break;
			}
			i++;
		}
		
		return i;
	}
	
	/**
	 * Get Maps
	 * To change map's item order you should change here
	 * 
	 * @param activity
	 * @return
	 */
	public static ArrayList<EntityProperty> getMaps(Activity activity) {
		ArrayList<EntityProperty> maps = new ArrayList<EntityProperty>();
		maps.add(new EntityProperty(TYPE_STRING, activity.getResources().getString(R.string.entity_property_type_string)));
		maps.add(new EntityProperty(TYPE_INT, activity.getResources().getString(R.string.entity_property_type_int)));
		maps.add(new EntityProperty(TYPE_DOUBLE, activity.getResources().getString(R.string.entity_property_type_double)));
		maps.add(new EntityProperty(TYPE_SELECT, activity.getResources().getString(R.string.entity_property_type_select)));
		maps.add(new EntityProperty(TYPE_PHOTO, activity.getResources().getString(R.string.entity_property_type_photo)));
		maps.add(new EntityProperty(TYPE_MONEY, activity.getResources().getString(R.string.entity_property_type_money)));
		
		return maps;
	}
	
	public static List<String> getList(Activity activity) {
		ArrayList<EntityProperty> maps = getMaps(activity);
		List<String> types = new ArrayList<String>();
		Iterator<EntityProperty> iterator = maps.iterator();
		while (iterator.hasNext()) {
			types.add(iterator.next().label);
		}
		
		return types;
	}
	
	public static ArrayAdapter<String> getAdapter(Activity activity) {		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
				android.R.layout.simple_spinner_item, getList(activity));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		return adapter;
	}
	
	public Cursor getProperties(String entity_name) {
		return query(this.select().where("entity = ?", entity_name));
	}
	
	public Vector<Model> getAllProperties(String entity_name) {
		return (Vector<Model>) new Model().parseAll(getProperties(entity_name));
	}
}
