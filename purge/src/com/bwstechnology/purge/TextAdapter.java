package com.bwstechnology.purge;

import java.util.ArrayList;

import android.content.Context;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.app.Activity;

public class TextAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    public ArrayList<ListItem> toDoListItems = new ArrayList<ListItem>();
    private Activity mainActivity;

    public TextAdapter(Activity mainActivity) {
    	this.mainActivity = mainActivity;
        mInflater = (LayoutInflater) this.mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ListItem listItem = new ListItem();
        toDoListItems.add(listItem);
        toDoListItems.add(listItem);
        notifyDataSetChanged();
    }

    /***
     * getCount(), getItem(int) and getItemId(int) required by abstract class BaseAdapter
     */
    public int getCount() {
    	int size = 0;
    	
    	try
    	{
    		size = toDoListItems.size();
    	}
    	catch (Exception exp)
    	{
    		System.out.println("Exception in TextAdapter, getCount()");
    	}
        return size;
    }

    /***
     * ToDo: does this return value makes sense?
     */
    public Object getItem(int position) {
    	Object itemPosition = null;
    	
    	try
    	{
    		itemPosition = position;
    	}
    	catch (Exception exp)
    	{
    		System.out.println("Exception in TextAdapter, getItem(int)");
    	}
    	
        return itemPosition;
    }

    /***
     * ToDo: does this return value makes sense?
     */
    public long getItemId(int position) {
    	long itemId = 0;
    	
    	try
    	{
    		itemId = position;
    	}
    	catch (Exception exp)
    	{
    		System.out.println("Exception in TextAdapter, getItemId(int)");
    	}
        return itemId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextFieldContainer textFieldContainer;
        try
        {
            // create new view
	        if (convertView == null) {
	            textFieldContainer = new TextFieldContainer();
	            // load item.xml-layout
	            convertView = mInflater.inflate(R.layout.item, null);
	            // get visual representation from container and put it to the element
	            textFieldContainer.editTextField = (EditText) convertView.findViewById(R.id.ContentField);
	        } else {
	            textFieldContainer = (TextFieldContainer) convertView.getTag();
	        }
	
	        //we need to update adapter (save data) once we finish with editing
	        
	        /*
		    textFieldContainer.editTextField.setOnFocusChangeListener
		    (
		    		new OnFocusChangeListener()
				    {
		    			public void onFocusChange(View v, boolean hasFocus)
				        {
		    				if (!hasFocus)
				            {
		    					int editTextId = v.getId();
				                EditText editTextField = (EditText) v;
				                //String editTextFieldContent = editTextField.
				                //toDoListItems.get(position) = Caption.getText().toString();
				            }
				        }
				    }
		    );*/
        }
        catch (InflateException inflateException)
        {
        	System.out.println("InflateException in TextAdapter, getView()" + "\n" + inflateException.getMessage());
        }
        catch(Exception exp)
        {
        	System.out.println("Exception in TextAdapter, getView()" + "\n" + exp.getMessage());
        }

        return convertView;
    }
}
