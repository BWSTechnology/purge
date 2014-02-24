package com.bwstechnology.purge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.content.Context;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.app.Activity;
import android.os.Environment;
import android.text.*;

public class TextAdapter extends BaseAdapter {
    public LayoutInflater layoutInflater;
    public ArrayList<ListItemFragmentViewHolder> arrayListListItems = new ArrayList<ListItemFragmentViewHolder>();
    private Activity mainActivity;
    public ListItemFragmentViewHolder listItemFragment;

    public TextAdapter(Activity mainActivity) {
    	this.mainActivity = mainActivity;
        layoutInflater = (LayoutInflater) this.mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ListItemFragmentViewHolder listItemFragment = new ListItemFragmentViewHolder(mainActivity);
        arrayListListItems.add(listItemFragment);
        arrayListListItems.add(listItemFragment);
        notifyDataSetChanged();
    }
    
    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
        	writeData();
            return true;
        }
        return false;
    }
    
    private void loadData()
    {
    	File directory = Environment.getExternalStorageDirectory();
		try
		{
			File dir = new File(directory.getAbsolutePath() + "/download");
	        dir.mkdirs();
	        File file = new File(dir, "myData.txt");
	        try {
	        	BufferedReader br = new BufferedReader(new FileReader(file));
	            String line;
	            
	            while ((line = br.readLine()) != null) {
	                text.append(line);
	                text.append('\n');
	            }
	            
	            // get listview view
	            // for each edittext, get text
	            
	            ListView listView = (ListView) this.mainActivity.findViewById(R.id.ToDoListView);
	            for (int i = 0; i < listView.getChildCount(); i++)
	            {
	            	ListItemFragmentViewHolder listItemFragment = new ListItemFragmentViewHolder(mainActivity);
	            	arrayListListItems.add(listItemFragment);
	                notifyDataSetChanged();
	                
	                // load item.xml-layout
	                convertView = this.layoutInflater.inflate(R.layout.item, null);
	                // get visual representation from container and put it to the element
	                this.listItemFragment = listItemFragment;
	                this.listItemFragment.editTextField = (EditText) convertView.findViewById(R.id.ContentField);
	            }
	            br.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		catch(Exception exp)
		{
		}
    }

    
    private void writeData()
    {
    		File directory = Environment.getExternalStorageDirectory();
    		try
    		{
    			File dir = new File(directory.getAbsolutePath() + "/download");
    	        dir.mkdirs();
    	        File file = new File(dir, "myData.txt");
    	        try {
    	            FileOutputStream f = new FileOutputStream(file);
    	            PrintWriter pw = new PrintWriter(f);
    	            
    	            // get listview view
    	            // for each edittext, get text
    	            
    	            ListView listView = (ListView) this.mainActivity.findViewById(R.id.ToDoListView);
    	            for (int i = 0; i < listView.getChildCount(); i++)
    	            {
    	            	EditText editText = (EditText) listView.getChildAt(i);
    	            	//editText.getText();
    	            	// write value
    		    		pw.println(editText.getText().toString() + "\n\r");
    	            }
    	            
    	            /*
    		    	for (int i = 0; i < arrayListListItems.size(); i++)
    	            {
    		    		// write value
    		    		pw.println(arrayListListItems.get(i).editTextField.getText().toString());
    		    	}
    		    	*/
    	            pw.flush();
    	            pw.close();
    	            f.close();
    	        } catch (FileNotFoundException e) {
    	            e.printStackTrace();
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    		}
    		catch(Exception exp)
    		{
    		}
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
	            //listItemFragment.editTextField.setText("Text");
	            
	            // Text NOT available - why ?
	            //listItemFragment.editTextField.getText().toString();
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
