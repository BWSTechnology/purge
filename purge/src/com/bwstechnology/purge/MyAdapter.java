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

public class MyAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    public ArrayList myItems = new ArrayList();
    private Activity myActivity;

    public MyAdapter(Activity mainActivity) {
    	this.myActivity = mainActivity;
        mInflater = (LayoutInflater) this.myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < 20; i++) {
            ListItem listItem = new ListItem();
            listItem.caption = "Caption" + i;
            myItems.add(listItem);
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        return myItems.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item, null);
            holder.caption = (EditText) convertView.findViewById(R.id.TextField);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //Fill EditText with the value you have in data source
        //holder.caption.setText(myItems.get(position).caption);
        holder.caption.setId(position);

        //we need to update adapter once we finish with editing
        holder.caption.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    //myItems.get(position).caption = Caption.getText().toString();
                }
            }
        });

        return convertView;
    }
    
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
    }
}
