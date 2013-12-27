package com.bwstechnology.purge;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
 
public class MainActivity extends Activity {
    private ListView myList;
    private MyAdapter myAdapter;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        myList = (ListView) findViewById(R.id.MyListView);
        myList.setItemsCanFocus(true);
        myAdapter = new MyAdapter(this);
        myList.setAdapter(myAdapter);
    }
    
    public void saveData(View view)
    {
    	this.myAdapter.saveData(this);
    }
}