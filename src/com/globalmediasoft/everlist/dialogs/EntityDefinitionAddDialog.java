package com.globalmediasoft.everlist.dialogs;

import com.globalmediasoft.everlist.R;
import com.globalmediasoft.everlist.activities.EntitiesActivity;
import com.globalmediasoft.everlist.entities.entity.EntityDefinitions;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EntityDefinitionAddDialog extends DialogFragment {
	public static String ADD_ENTITY  = "add";
	public static String EDIT_ENTITY = "edit";
	public EntityDefinitions.Model model;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {		
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_entity_definition_add, null);
		final EditText edtEntityName = (EditText) view.findViewById(R.id.edtEntityName);
		final EditText edtEntityTitle = (EditText) view.findViewById(R.id.edtEntityTitle);
		final EditText edtEntityDescription = (EditText) view.findViewById(R.id.edtEntityDescription);
		final String tag = this.getTag();
        if (tag == EntityDefinitionAddDialog.EDIT_ENTITY) {
        	edtEntityName.setEnabled(false);
        	edtEntityName.clearFocus();
        	edtEntityName.setText(model.name);
			edtEntityTitle.setText(model.title);
			edtEntityTitle.requestFocus();
			edtEntityDescription.setText(model.description);
		}
        
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.title_activity_entity_add)
               .setPositiveButton(R.string.btn_submit, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   String name  = edtEntityName.getText().toString();
                	   String title = edtEntityTitle.getText().toString();
                	   if (name.isEmpty() || title.isEmpty()) {
                		   Toast.makeText(getActivity(), R.string.activity_entities_entity_lacking_information, Toast.LENGTH_LONG).show();
                		   return;
                	   }
                	   ContentValues data = new ContentValues();
                	   data.put("title", title);
                	   data.put("description", edtEntityDescription.getText().toString());
                	   long result = -1;
                	   if (tag == EntityDefinitionAddDialog.EDIT_ENTITY) {
                		   result = new EntityDefinitions(getActivity()).update(data, EntityDefinitions.bind("name = ?", name));
                	   } else {
                		   data.put("name", name);
                		   result = new EntityDefinitions(getActivity()).insert(data);
                	   }
                	   
                	   if (result > 0) {
                		   ((EntitiesActivity) getActivity()).reloadEntities();
                	   }
                   }
               })
               .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               })
               .setView(view);
        return builder.create();
    }
}
