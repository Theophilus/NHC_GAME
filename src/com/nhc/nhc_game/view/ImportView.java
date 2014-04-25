package com.nhc.nhc_game.view;

import com.nhc.nhc_game.R;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ImportView extends ListActivity {
	
    String UserInfo;
   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_data);
        UserInfo = getIntent().getStringExtra("NameAccess");
        String[] values = new String[] { "Walk","Run","Weight" };
        
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);
    }

	@Override
	  protected void onListItemClick(ListView l, View v, int pos, long id) {
	    //String item = (String) getListAdapter().getItem(pos);
	    //Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	    if(pos== 0){
			/*
			Intent i = new Intent(v.getContext(), Authorization.class);
			i.putExtra("NameType", UserName+",walk");
		    startActivity(i);
		    */
		}
	  }   
}
