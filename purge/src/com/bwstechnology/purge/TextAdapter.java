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
import android.text.*;

public class TextAdapter extends BaseAdapter {
    public LayoutInflater layoutInflater;
    //public ArrayList<ListItem> arrayListListItems = new ArrayList<ListItem>();
    public ArrayList<ListItemFragmentViewHolder> arrayListListItems = new ArrayList<ListItemFragmentViewHolder>();
    private Activity mainActivity;
    public ListItemFragmentViewHolder listItemFragment;

    public TextAdapter(Activity mainActivity) {
    	this.mainActivity = mainActivity;
        layoutInflater = (LayoutInflater) this.mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //ListItem listItem = new ListItem();
        ListItemFragmentViewHolder listItemFragment = new ListItemFragmentViewHolder(mainActivity);
        arrayListListItems.add(listItemFragment);
        arrayListListItems.add(listItemFragment);
        notifyDataSetChanged();
    }

    /***
     * getCount(), getItem(int) and getItemId(int) required by abstract class BaseAdapter
     */
    public int getCount() {
    	int size = 0;
    	
    	try
    	{
    		size = arrayListListItems.size();
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
        try
        {
            // create new view
	        if (convertView == null) {
	            listItemFragment = new ListItemFragmentViewHolder(mainActivity);
	            // load item.xml-layout
	            convertView = layoutInflater.inflate(R.layout.item, null);
	            // get visual representation from container and put it to the element
	            listItemFragment.editTextField = (EditText) convertView.findViewById(R.id.ContentField);
	            EditTextWatcher textWatcher = new EditTextWatcher(this, position, convertView, mainActivity);
	            listItemFragment.editTextField.addTextChangedListener(textWatcher);
	        } else {
	            listItemFragment = (ListItemFragmentViewHolder) convertView.getTag();
	        }
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
