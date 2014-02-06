package com.bwstechnology.purge;

import android.content.Context;
import android.widget.EditText;

class ListItemFragmentViewHolder {
    EditText editTextField;
    
    public ListItemFragmentViewHolder(Context context)
    {
    	this.editTextField = new EditText(context);
    }
}