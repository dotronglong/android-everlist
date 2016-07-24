package com.globalmediasoft.everlist.activities;

import com.globalmediasoft.android.dialog.GMSDialogActions;
import com.globalmediasoft.everlist.R;
import com.globalmediasoft.everlist.dialogs.EntityDefinitionAddDialog;
import com.globalmediasoft.everlist.dialogs.EntityPropertyAddDialog;
import com.globalmediasoft.everlist.entities.entity.EntityDefinitions;
import com.globalmediasoft.everlist.entities.entity.EntityProperties;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class EntitiesActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entities);

		if (savedInstanceState == null) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.container, new ManageEntitiesFragment(), ManageEntitiesFragment.TAG)
			  .addToBackStack(null)
			  .commit();
		}
	}
	
	@Override
	public void onBackPressed() {
		if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
			finish();
		} else {
			super.onBackPressed();
		}
	}
	
	public void reloadEntities() {
		ManageEntitiesFragment fragment = (ManageEntitiesFragment) getSupportFragmentManager().findFragmentByTag(ManageEntitiesFragment.TAG);
		fragment.loadContent();
	}
	
	public void reloadEntityProperties() {
		EntityPropertiesFragment fragment = (EntityPropertiesFragment) getSupportFragmentManager().findFragmentByTag(EntityPropertiesFragment.TAG);
		fragment.loadContent();
	}

	public static class ManageEntitiesFragment extends ListFragment {
		public static String TAG = "manage_entities";

		public ManageEntitiesFragment() {
		}
		
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			inflater.inflate(R.menu.entities, menu);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_add) {
				new EntityDefinitionAddDialog().show(getActivity().getSupportFragmentManager(), EntityDefinitionAddDialog.ADD_ENTITY);
			}
			return super.onOptionsItemSelected(item);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_entities,
					container, false);
			setHasOptionsMenu(true);
			getActivity().setTitle(R.string.action_entity_definitions);
			loadContent();
			
			return rootView;
		}
		
		public void loadContent() {
			String[] from = {"title", "description"};
			int[] to = {android.R.id.text1, android.R.id.text2};
			setListAdapter(new SimpleCursorAdapter(getActivity(), 
					android.R.layout.simple_list_item_2, new EntityDefinitions(getActivity()).getDefinitions(), 
					from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));
		}
		
		public void onListItemClick(ListView l, View v, int position, long id) {
			final SQLiteCursor cursor = (SQLiteCursor) l.getAdapter().getItem(position);
			final EntityDefinitions.Model model = (EntityDefinitions.Model) new EntityDefinitions.Model().parse(cursor);
			
			GMSDialogActions dialog = new GMSDialogActions();
			dialog.setActions(getResources().getStringArray(R.array.entity_actions))
				  .onItemSelected(new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface di, int which) {
						// TODO Auto-generated method stub
						switch (which) {
							case 0:
								Bundle args = new Bundle();
								args.putString("entity_name", model.name);
								EntityPropertiesFragment fragment = new EntityPropertiesFragment();
								fragment.setArguments(args);
								
								FragmentManager fm = getActivity().getSupportFragmentManager();
								FragmentTransaction ft = fm.beginTransaction();
								ft.replace(R.id.container, fragment, EntityPropertiesFragment.TAG)
								  .addToBackStack(null)
								  .commit();
								break;
								
							case 1:
								EntityDefinitionAddDialog dialog = new EntityDefinitionAddDialog();
								dialog.model = model;
								dialog.show(getFragmentManager(), EntityDefinitionAddDialog.EDIT_ENTITY);
								break;
								
							case 2:
								final String name  = model.name;
								final String title = model.title;
								AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
								builder.setMessage(getResources().getString(R.string.activity_entities_confirm_delete_entity)  + " : " + title)
										.setPositiveButton(R.string.btn_submit, new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												// TODO Auto-generated method stub
												long count = new EntityDefinitions(getActivity()).delete("name = ?", name);
												if (count > 0) {
													((EntitiesActivity) getActivity()).reloadEntities();
												}
											}
										})
									   .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												// TODO Auto-generated method stub
											}
									   })
									   .create().show();
								break;
							
							default:
								break;
						}
					}
				})
				.show(getFragmentManager(), "");
		}
	}

	public static class EntityPropertiesFragment extends ListFragment {
		public static String TAG = "entity_properties";
		public EntityPropertiesFragment() {}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_entities,
					container, false);
			
			loadContent();
			
			setHasOptionsMenu(true);
			getActivity().setTitle(R.string.activity_entities_entity_properties_title);
			
			return rootView;
		}
		
		public void loadContent() {
			String[] from = {"name"};
			int[] to = {android.R.id.text1};
			setListAdapter(new SimpleCursorAdapter(getActivity(), 
					android.R.layout.simple_list_item_2, new EntityProperties(getActivity()).getProperties(getArguments().getString("entity_name")), 
					from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));
		}
		
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			inflater.inflate(R.menu.entities, menu);
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// Handle action bar item clicks here. The action bar will
			// automatically handle clicks on the Home/Up button, so long
			// as you specify a parent activity in AndroidManifest.xml.
			int id = item.getItemId();
			if (id == R.id.action_add) {
				EntityPropertyAddDialog dialog = new EntityPropertyAddDialog();
				dialog.setArguments(getArguments());
				dialog.show(getActivity().getSupportFragmentManager(), EntityPropertyAddDialog.ADD);
			}
			return super.onOptionsItemSelected(item);
		}
		
		public void onListItemClick(ListView l, View v, int position, long id) {
			final SQLiteCursor cursor = (SQLiteCursor) l.getAdapter().getItem(position);
			final EntityProperties.Model model = (EntityProperties.Model) new EntityProperties.Model().parse(cursor);
			
			new GMSDialogActions().setActions(getResources().getStringArray(R.array.general_actions))
				  .onItemSelected(new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch (which) {
							case 0:
								EntityPropertyAddDialog dialogPropertyAdd = new EntityPropertyAddDialog();
								Bundle item = model.toBundle();
								item.putBoolean("is_edit", true);
								item.putString("entity_name", getArguments().getString("entity_name"));
								dialogPropertyAdd.setArguments(item);
								dialogPropertyAdd.show(getActivity().getSupportFragmentManager(), EntityPropertyAddDialog.EDIT);
								break;
								
							case 1:
								final String name  = model.name;
								AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
								builder.setMessage(getResources().getString(R.string.activity_entities_confirm_delete_item)  + " : " + name)
										.setPositiveButton(R.string.btn_submit, new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												// TODO Auto-generated method stub
												long count = new EntityProperties(getActivity()).delete("id = ?", model.id);
												if (count > 0) {
													((EntitiesActivity) getActivity()).reloadEntityProperties();
												}
											}
										})
									   .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												// TODO Auto-generated method stub
											}
									   })
									   .create().show();
								break;
								
								default:
									break;
						}
					}
				  
				  })
				  .show(getFragmentManager(), "");
		}
	}
}
