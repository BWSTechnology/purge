package com.bwstechnology.purge;

import java.io.FileOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.app.Activity;

public class TextAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    public ArrayList toDoListItems = new ArrayList();
    private Activity mainActivity;

    public TextAdapter(Activity mainActivity) {
    	this.mainActivity = mainActivity;
        mInflater = (LayoutInflater) this.mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < 20; i++) {
            ListItem listItem = new ListItem();
            toDoListItems.add(listItem);
        }
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
        // create new view
        if (convertView == null) {
            textFieldContainer = new TextFieldContainer();
            // load item.xml-layout
            convertView = mInflater.inflate(R.layout.item, null);
            textFieldContainer.editTextField = (EditText) convertView.findViewById(R.id.ContentField);
            //convertView.setTag(holder);
        } else {
            textFieldContainer = (TextFieldContainer) convertView.getTag();
        }

        //we need to update adapter once we finish with editing
        try
        {
	        textFieldContainer.editTextField.setOnFocusChangeListener
	        (
	        		new OnFocusChangeListener()
			        {
			            public void onFocusChange(View v, boolean hasFocus)
			            {
			                if (!hasFocus)
			                {
			                    final int position = v.getId();
			                    final EditText Caption = (EditText) v;
			                    //???????
			                    //toDoListItems.get(position) = Caption.getText().toString();
			                }
			            }
			        }
	        );
        }
        catch(Exception exp)
        {
        	System.out.println("Exception in TextAdapter, getView()");
        }

        return convertView;
    }
    
    /***
     * Saves the content of the listview to an internal file
     * @param ctx
     */
    /*
    public void saveData(Context ctx)
    {
    	String filename = "myfile";
    	String string = "Hello world!";
    	FileOutputStream outputStream;

    	try {
    		outputStream = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
    		outputStream.write(string.getBytes());
    		outputStream.close();
    	} catch (Exception e) {
    	  e.printStackTrace();
    	}
    }*/
}
