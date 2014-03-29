package com.bwstechnology.purge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import android.app.Activity;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.text.Editable;
import android.text.TextWatcher;
 
public class MainActivity extends Activity implements View.OnTouchListener, TextWatcher{
    private LinearLayout toDoList;
    private int position = 0;
    
    static final int MIN_DISTANCE = 10;
    private float downX, downY, upX, upY;
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	 
	    if (!this.loadData())
	    {
	    	toDoList = (LinearLayout) findViewById(R.id.linearLayout);
		    
		    EditText text1 = new EditText(this);
		    text1.addTextChangedListener(this);
		    text1.setOnTouchListener(this);
		    		    
		    toDoList.addView(text1);
	    }
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
    	
    }
    
    public void onTextChanged(CharSequence s, int start, int before, int count)
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
	    	    text1.setOnTouchListener(this);
	    	    	    	    
	    	    linearLayout.addView(text1);
	        }
    	}
    }
    
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
    	if (directory.exists())
    	{
    		try
			{
    			File dir = new File(directory.getAbsolutePath() + "/purge");
		        if (dir.exists())
		        {		        	
		        	File file = new File(dir, "purgeData.txt");
			        if (file.exists())
			        {
			        	try {
				        	BufferedReader br = new BufferedReader(new FileReader(file));
				            String line;
				            
				            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
				            
				            while ((line = br.readLine()) != null) {
				            	EditText text1 = new EditText(this);
				            	text1.setText(line);
				        	    text1.addTextChangedListener(this);
				        	    text1.setOnTouchListener(this);
				        	    
				        	    linearLayout.addView(text1);
				            }
				            linearLayout.getChildAt(linearLayout.getChildCount()-1).requestFocus();
				            br.close();
				            fileLoaded = true;
				        }
			        	catch (FileNotFoundException e)
			        	{
			        		e.printStackTrace();
				        }
			        	catch (IOException e)
			        	{
			        		e.printStackTrace();
				        }
			        }
			        else
			        {
			        	//Toast toast = Toast.makeText(getApplicationContext(), "Data file does not exist", Toast.LENGTH_SHORT);
			        	//toast.show();
			        }
		        }
			}
			catch(NullPointerException nullPointerException)
			{
				nullPointerException.getStackTrace();
			}
			catch(Exception exp)
			{
				exp.getStackTrace();
			}
    	}
		return fileLoaded;
    }
    
    private void writeData()
    {
    	File directory = Environment.getExternalStorageDirectory();
    	if (directory.exists())
    	{
	    	try
	    	{
	    		File dir = new File(directory.getAbsolutePath() + "/purge");
	    	    dir.mkdir();
	    	    
	    	    File file = new File(dir, "purgeData.txt");
	    	    if (dir.exists())
	    	    {
		    	    try
		    	    {
		    	        FileOutputStream f = new FileOutputStream(file);
		    	        PrintWriter pw = new PrintWriter(f);
		    	            
		    	        LinearLayout linerLayout = (LinearLayout) findViewById(R.id.linearLayout);
		    	        for (int i = 0; i < linerLayout.getChildCount(); i++)
		    	        {
		    	        	EditText editText = (EditText) linerLayout.getChildAt(i);
		    	            // write value
		    		    	pw.println(editText.getText().toString());
		    	        }
		    	            
		    	        pw.flush();
		    	        pw.close();
		    	        f.close();
		    	    }
		    	    catch (FileNotFoundException e)
		    	    {
		    	    	e.printStackTrace();
		    	    }
		    	    catch (IOException e)
		    	    {
		    	    	e.printStackTrace();
		    	    }
	    	    }
	    	}
	    	catch(Exception exp)
	    	{
	    		exp.getStackTrace();
	    	}
    	}
    }
    
    /*
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
    */
    
    public void onLeftToRightSwipe(float fingerX, float fingerY){
    	
    	LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        for (int i=0; i < linearLayout.getChildCount()-1; i++){
            EditText editor = (EditText)linearLayout.getChildAt(i);
            
            int[] location = new int[2];
            editor.getLocationOnScreen(location);
            int editorHeight = editor.getMeasuredHeight();
            int editorY = location[1];
            
            if (fingerY > editorY && fingerY < editorY + editorHeight)
            {
            	//touch is within this child
            	editor.getText().toString();
            	if (linearLayout.getChildCount() > 1)
            	{
            		linearLayout.removeView(editor);
            		// save data
            		this.isExternalStorageWritable();
            		return;
            	}
            	else
            	{
            		// do not delete last element but clear text else no way to add new items!
            		editor.setText("");
            		// save data
            		this.isExternalStorageWritable();
            		return;
            	}
            }
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
    	System.out.println(event.getAction());
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                downX = event.getRawX();
            	downY = event.getRawY();
                return false;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getRawX();
                upY = event.getRawY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // swipe horizontal?
                if(Math.abs(deltaX) > MIN_DISTANCE)
                {
                    // left or right
                    if(deltaX < 0)
                    { 
                    	this.onLeftToRightSwipe(upX, upY);
                    	return false;
                    }
                    //if(deltaX > 0) { this.onRightToLeftSwipe(); return true;}
                }
                else {
                        return false; // We don't consume the event
                }

                // swipe vertical?
                if(Math.abs(deltaY) > MIN_DISTANCE){
                    // top or down
                    //if(deltaY < 0) { this.onTopToBottomSwipe(); return true; }
                    //if(deltaY > 0) { this.onBottomToTopSwipe(); return true; }
                }
                else {
                        return false; // We don't consume the event
                }

                return false;
            }
        }
        return false;
    }
}