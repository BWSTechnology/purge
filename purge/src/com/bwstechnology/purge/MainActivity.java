package com.bwstechnology.purge;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
 
public class MainActivity extends Activity {
    private ListView toDoList;
    private TextAdapter myAdapter;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	//try
    	//{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	 
	        // get list view, set some properties and bind data source to it
	        toDoList = (ListView) findViewById(R.id.ToDoListView);
	        toDoList.setItemsCanFocus(true);
	        myAdapter = new TextAdapter(this);
	        toDoList.setAdapter(myAdapter);
    	//}
    	//catch(Exception exp)
    	//{
    	//	System.out.println("bad");
    	//}
    }
    
    /***
     * Do save the text data of the ToDo-List when Button is hit
     * @param view
     */
    public void saveData(View view)
    {
    	//this.myAdapter.saveData(this);
    }
}