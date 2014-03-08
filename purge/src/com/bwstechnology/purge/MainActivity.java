package com.bwstechnology.purge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.text.Editable;
import android.text.TextWatcher;
 
public class MainActivity extends Activity implements OnGestureListener, TextWatcher{
    private LinearLayout toDoList;
    //private TextAdapter myAdapter;
    private GestureDetector mGestureDetector;
    private int position = 0;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	 
	    if (!this.loadData())
	    {
		    // get list view, set some properties and bind data source to it
		    toDoList = (LinearLayout) findViewById(R.id.linearLayout);
		    
		    EditText text1 = new EditText(this);
		    text1.addTextChangedListener(this);
		    
		    toDoList.addView(text1);
	    }
	    
	    mGestureDetector = new GestureDetector(this, this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    // start TextWatcher
    
    public void afterTextChanged(Editable s)
    {
    	
    }
    
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
    	if (this.isExternalStorageWritable())
    	{
	    	LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
	    	EditText edit = (EditText)linearLayout.getFocusedChild();
	    	position = linearLayout.indexOfChild(edit);
	        
	        if (position == (linearLayout.getChildCount()-1))
	        {   
	        	EditText text1 = new EditText(this);
	    	    text1.addTextChangedListener(this);
	    	    
	    	    //toDoList.addView(text1);
	    	    linearLayout.addView(text1);
	        }
    	}
    }
    
    public void onTextChanged(CharSequence s, int start, int before, int count) { }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	this.position = position;
    	return convertView;
    }
    
    // end TextWatcher
    
    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
        	writeData();
            return true;
        }
        return false;
    }
    
    private boolean loadData()
    {
    	boolean fileLoaded = false;
    	File directory = Environment.getExternalStorageDirectory();
		try
		{
			File dir = new File(directory.getAbsolutePath() + "/download");
	        dir.mkdirs();
	        File file = new File(dir, "myData.txt");
	        try {
	        	BufferedReader br = new BufferedReader(new FileReader(file));
	            String line;
	            
	            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
	            
	            while ((line = br.readLine()) != null) {
	            	EditText text1 = new EditText(this);
	            	text1.setText(line);
	        	    text1.addTextChangedListener(this);
	        	    
	        	    linearLayout.addView(text1);
	            }
	            
	            /*for (int i = 0; i < linearLayout.getChildCount(); i++)
	            {
	            	
	                //this.listItemFragment.editTextField = (EditText) convertView.findViewById(R.id.ContentField);
	            }*/
	            br.close();
	            fileLoaded = true;
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		catch(Exception exp)
		{
		}
		return fileLoaded;
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
    	            
    	            LinearLayout linerLayout = (LinearLayout) findViewById(R.id.linearLayout);
    	            for (int i = 0; i < linerLayout.getChildCount(); i++)
    	            {
    	            	EditText editText = (EditText) linerLayout.getChildAt(i);
    	            	//editText.getText();
    	            	// write value
    		    		pw.println(editText.getText().toString());
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
    
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId()) {
        	case R.id.action_settings:
        		//Intent intent = new Intent(this, LoadDataActivity.class);
            	//this.startActivity(intent);
            	break;
        	default:
        		return super.onOptionsItemSelected(item);
        }

        return true;
    }

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		//Defining Sensitivity
		float sensitivity = 50;
		//Swipe Right Check
		if(e2.getX() - e1.getX() > sensitivity)
		{
			//remove edittext from view
			//ListView listView = (ListView) findViewById(R.id.ToDoListView);
			
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		mGestureDetector.onTouchEvent(event);
		return false;
	}
}