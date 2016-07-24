package com.globalmediasoft.everlist.settings;

import java.util.Vector;

import com.globalmediasoft.android.core.GMSCoreSettings;
import com.globalmediasoft.everlist.entities.entity.EntityDefinitions;

public class GlobalSettings extends GMSCoreSettings {
	public static String dbName = "ever_list";
	public static Vector<EntityDefinitions.Model> definitions;
}
