package com.globalmediasoft.everlist.dialogs;

import java.util.Iterator;

import com.globalmediasoft.everlist.R;
import com.globalmediasoft.everlist.activities.MainActivity;
import com.globalmediasoft.everlist.entities.entity.EntityDefinitions.Model;
import com.globalmediasoft.everlist.fragments.AddEntityFragment;
import com.globalmediasoft.everlist.settings.GlobalSettings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ChooseEntityDefinitionDialog extends DialogFragment {
	 public Dialog onCreateDialog(Bundle savedInstanceState) {		
		 View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_choose_entity_definition, null);
		 final RadioGroup rdgEntityDefinitions = (RadioGroup) view.findViewById(R.id.rdgEntityDefinitions);
		 
		 RadioButton rdbDefinition;
		 if (GlobalSettings.definitions.size() > 0) {
			 Iterator<Model> iterator = GlobalSettings.definitions.iterator();
			 int i = 1000;
			 while (iterator.hasNext()) {
				 Model item = iterator.next();
				 rdbDefinition = new RadioButton(getActivity());
				 rdbDefinition.setText(item.title);
				 rdbDefinition.setEnabled(true);
				 rdbDefinition.setId(i);
				 rdgEntityDefinitions.addView(rdbDefinition);
				 i++;
			 }
		 }
		 
		 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		 builder.setMessage(R.string.fragment_add_choose_entity_definition)
		 		.setPositiveButton(R.string.btn_next, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						int i = rdgEntityDefinitions.getCheckedRadioButtonId() - 1000;
						Model item = GlobalSettings.definitions.get(i);
						AddEntityFragment fragment = new AddEntityFragment();
						fragment.setArguments(item.toBundle());
						((MainActivity) getActivity()).addFragmentToStack(fragment);
					}
		 		})
		 		.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
		 		})
		 		.setView(view);
		 
		 return builder.create();
	 }
}