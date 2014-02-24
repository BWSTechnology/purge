package com.bwstechnology.purge;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ListView;
 
public class MainActivity extends Activity implements OnGestureListener{
    private ListView toDoList;
    private TextAdapter myAdapter;
    private GestureDetector mGestureDetector;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	 
	    // get list view, set some properties and bind data source to it
	    toDoList = (ListView) findViewById(R.id.ToDoListView);
	    toDoList.setItemsCanFocus(true);
	    myAdapter = new TextAdapter(this);
	    toDoList.setAdapter(myAdapter);
	    
	    mGestureDetector = new GestureDetector(this, this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
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
			ListView listView = (ListView) findViewById(R.id.ToDoListView);
			
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