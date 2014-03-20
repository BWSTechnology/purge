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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.text.Editable;
import android.text.TextWatcher;
 
public class MainActivity extends Activity implements View.OnTouchListener, TextWatcher{
    private LinearLayout toDoList;
    private int position = 0;
    private final String NAMESPACE_NAME = "com.bwstechnology.purge";
    
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
        else
        {
        	Log.e(NAMESPACE_NAME, "isExternalStorageWritable() : " + "state of the current primary external storage: " + state);
        }
        return false;
    }
    
    private boolean loadData()
    {
    	boolean fileLoaded = false;
    	File directory = Environment.getExternalStorageDirectory();
    	if (directory != null)
    	{
    		try
			{
				File dir = new File(directory.getAbsolutePath() + "/Documents");
		        if (dir != null)
		        {
		        	File file = new File(dir, "purgeData.txt");
			        if (file != null)
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
				            
				            br.close();
				            fileLoaded = true;
				        } catch (FileNotFoundException e) {
				            e.printStackTrace();
				        } catch (IOException e) {
				            e.printStackTrace();
				        }
			        }
			        else
			        {
			        	Log.e(NAMESPACE_NAME, "loadData() : " + "failed getting file object for data file");
			        }
		        }
		        else
		        {
		        	Log.e(NAMESPACE_NAME, "loadData() :"  + "failed getting directory absolute path to Documents");
		        }
			}
			catch(NullPointerException nullPointerException)
			{
				Log.e(NAMESPACE_NAME, "loadData() :" + nullPointerException.getMessage());
			}
			catch(Exception exp)
			{
				Log.e(NAMESPACE_NAME, "loadData() : " + exp.getMessage());
			}
    	}
    	else
    	{
    		Log.e(NAMESPACE_NAME, "loadData() : " + "failed getting primary external storage directory");
    	}
		return fileLoaded;
    }

    private void writeLogFile()
    {
    	try
    	{
    	    File file = new File(Environment.getExternalStorageDirectory(), "purgeLog" + String.valueOf(System.currentTimeMillis()));
    	    Runtime.getRuntime().exec("logcat -v time -f " + file.getAbsolutePath() + " com.bwstechnology.purge:I *:F");
    	}
    	catch (IOException e)
    	{
    		System.out.println(e.getMessage());
    	}
    }
    
    private void writeData()
    {
    	File directory = Environment.getExternalStorageDirectory();
    	if (directory != null)
    	{
	    	try
	    	{
	    		File dir = new File(directory.getAbsolutePath() + "/Documents");
	    	    //dir.mkdirs();
	    	    File file = new File(dir, "purgeData.txt");
	    	    if (dir != null)
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
		    	    	Log.e(NAMESPACE_NAME, "writeData() : " + "FileNotFoundException, " + e.getMessage());
		    	    	e.printStackTrace();
		    	    }
		    	    catch (IOException e)
		    	    {
		    	    	Log.e(NAMESPACE_NAME, "writeData() : " + "IOException, " + e.getMessage());
		    	    	e.printStackTrace();
		    	    }
	    	    }
	    	}
	    	catch(Exception exp)
	    	{
	    		Log.e(NAMESPACE_NAME, "writeData() : " + "failed getting Documents directory or creating data file");
	    	}
    	}
    	else
    	{
    		Log.e(NAMESPACE_NAME, "writeData() : " + "failed getting primary external storage directory");
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
    
    public void onStop()
    {
    	writeLogFile();
    }
    
    public void onDestroy()
    {
    	writeLogFile();
    }
}