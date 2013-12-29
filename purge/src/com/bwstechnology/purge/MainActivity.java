package com.bwstechnology.purge;
 
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
 
public class MainActivity extends Activity {
    private ListView toDoList;
    private TextAdapter myAdapter;
 
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
    }
}