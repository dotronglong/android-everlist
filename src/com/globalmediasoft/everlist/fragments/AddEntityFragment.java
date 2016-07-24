package com.globalmediasoft.everlist.fragments;

import java.util.Vector;

import com.globalmediasoft.everlist.R;
import com.globalmediasoft.everlist.entities.entity.EntityProperties;
import com.globalmediasoft.everlist.entities.entity.EntityValues;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AddEntityFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_entity, container, false);
        
        final Activity activity 	= getActivity();
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.linContainer);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        
        // Entity Title
        EditText edtEntityTitle = new EditText(activity);
        edtEntityTitle.setLayoutParams(params);
        edtEntityTitle.setHint(R.string.fragment_add_entity_label_title);
        edtEntityTitle.requestFocusFromTouch();
        layout.addView(edtEntityTitle);
        
        Bundle args = getArguments();
        String DefinitionName = args.getString("name");
        final Vector<EntityProperties.Model> properties = new EntityProperties(getActivity()).getAllProperties(DefinitionName);
        EditText edtProperty;
        for (EntityProperties.Model property : properties) {
        	switch (property.type) {
        	case EntityProperties.TYPE_STRING:
        	default:
        		edtProperty = new EditText(activity);
        		edtProperty.setId(property.id);
        		edtProperty.setLayoutParams(params);
        		edtProperty.setHint(property.name);
        		layout.addView(edtProperty);
        		break;
        	}
        }
        
        Button btnSubmit = new Button(activity);
        btnSubmit.setText(R.string.fragment_add_entity_button_submit_text);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EntityValues values = new EntityValues(activity);
				for (EntityProperties.Model property : properties) {
					EntityValues.Model model = new EntityValues.Model();
		        	switch (property.type) {
		        	case EntityProperties.TYPE_STRING:
		        	default:
		        		EditText edtProperty = (EditText) activity.findViewById(property.id);
		        		model.property_id = property.id;
		        		model.value = edtProperty.getText().toString();
		        		if (values.insert(model) > 0) {
		        			Toast.makeText(activity, R.string.fragment_add_entity_adding_completed, Toast.LENGTH_SHORT).show();
		        		}
		        		break;
		        	}
		        }
			}
		});
        layout.addView(btnSubmit);
        
        return view;
    }
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().setTitle("Create entity of " + getArguments().getString("title"));
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.clear();
		super.onCreateOptionsMenu(menu, inflater);
	}
}