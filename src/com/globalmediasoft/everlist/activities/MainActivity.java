package com.globalmediasoft.everlist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.globalmediasoft.android.activity.GMSActivity;
import com.globalmediasoft.everlist.R;
import com.globalmediasoft.everlist.dialogs.ChooseEntityDefinitionDialog;
import com.globalmediasoft.everlist.entities.entity.Entity;
import com.globalmediasoft.everlist.entities.entity.EntityDefinitions;
import com.globalmediasoft.everlist.fragments.AddEntityFragment;
import com.globalmediasoft.everlist.settings.GlobalSettings;

public class MainActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// cache entity's definitions
		GlobalSettings.definitions = new EntityDefinitions(this).getDefinitions(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		switch (item.getItemId()) {
			case R.id.action_entity_definitions:
				Intent intent = new Intent(this, EntitiesActivity.class);
				startActivity(intent);
				break;
			
			case R.id.action_add_entity:
				new ChooseEntityDefinitionDialog().show(getSupportFragmentManager(), null);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void addFragmentToStack(Fragment fragment) {
		GMSActivity.replaceFragment(this, R.id.container, fragment);
	}
}
