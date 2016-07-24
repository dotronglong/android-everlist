package com.globalmediasoft.everlist.dialogs;

import com.globalmediasoft.everlist.R;
import com.globalmediasoft.everlist.activities.EntitiesActivity;
import com.globalmediasoft.everlist.entities.entity.EntityProperties;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EntityPropertyAddDialog extends DialogFragment {
	public static String ADD  = "add";
	public static String EDIT = "edit";
		
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {		
		final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_entity_property_add, null);
		final String entityName = getArguments().containsKey("entity_name") ? getArguments().getString("entity_name") : "";
		final EditText edtEntityPropertyName = (EditText) view.findViewById(R.id.edtEntityPropertyName);
		final Spinner spinner = (Spinner) view.findViewById(R.id.spnEntityPropertType);
		spinner.setAdapter(EntityProperties.getAdapter(getActivity()));
		final boolean isEdit = getArguments() != null && getArguments().containsKey("is_edit") ? getArguments().getBoolean("is_edit") : false;
		
        if (isEdit) {
        	edtEntityPropertyName.setText(getArguments().getString("name"));
        	spinner.setSelection(EntityProperties.getListPosition(getActivity(), getArguments().getInt("type")));
		}
        
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getActivity().getResources().getString(R.string.activity_entities_entity_property_add_title) + " (" + entityName + ")")
               .setPositiveButton(R.string.btn_submit, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   String name  = edtEntityPropertyName.getText().toString();
                	   if (name.isEmpty()) {
                		   Toast.makeText(getActivity(), R.string.activity_entities_entity_lacking_information, Toast.LENGTH_LONG).show();
                		   return;
                	   }
                	   ContentValues data = new ContentValues();
                	   data.put("name", edtEntityPropertyName.getText().toString());
                	   data.put("type", EntityProperties.getType(getActivity(), spinner.getSelectedItemPosition()));
                	   long result = -1;
                	   if (isEdit) {
                		   int property_id = getArguments().getInt("id");
                		   result = new EntityProperties(getActivity()).update(data, EntityProperties.bind("id = ?", property_id));
                	   } else {
                    	   data.put("entity", entityName);
                		   result = new EntityProperties(getActivity()).insert(data);
                	   }
                	   
                	   if (result > 0) {
                		   ((EntitiesActivity) getActivity()).reloadEntityProperties();
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
