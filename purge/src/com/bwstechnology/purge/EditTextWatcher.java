package com.bwstechnology.purge;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ListView;

import java.text.NumberFormat;

public class EditTextWatcher implements TextWatcher
{
	//boolean mEditing;
	private TextAdapter textAdapter;
	private int position;
	private View convertView;
	private Activity mainActivity;

    public EditTextWatcher(TextAdapter textAdapter, int position, View convertView, Activity mainActivity) {
        //mEditing = false;
    	this.textAdapter = textAdapter;
    	this.position = position;
    	this.convertView = convertView;
    	this.mainActivity = mainActivity;
    }

    public void afterTextChanged(Editable s)
    {
    	this.convertView.requestFocus();
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    { 
    	// if typing text in last textbox (afterTextChanged),
    	// insert a new one below (inflate new edittext fragment - pure editext xml)
    	this.textAdapter.isExternalStorageWritable();
    	
        if (position == this.textAdapter.arrayListListItems.size()-1)
        {   
        	ListItemFragmentViewHolder listItemFragment = new ListItemFragmentViewHolder(mainActivity);
        	this.textAdapter.arrayListListItems.add(listItemFragment);
            this.textAdapter.notifyDataSetChanged();
            
            // load item.xml-layout
            convertView = this.textAdapter.layoutInflater.inflate(R.layout.item, null);
            // get visual representation from container and put it to the element
            this.textAdapter.listItemFragment = listItemFragment;
            this.textAdapter.listItemFragment.editTextField = (EditText) convertView.findViewById(R.id.ContentField);
        }
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) { }
}
