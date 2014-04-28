package com.nhc.nhc_game.view;

import android.os.Bundle;

import com.nhc.nhc_game.R;

import android.view.View;
import android.widget.*;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class EditView extends Activity {

	private EditText fullname;
	private Button update;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        initControls();
	}
	
	public void initControls(){
		update = (Button) findViewById(R.id.submit);
		fullname = (EditText) findViewById(R.id.NewName);
		update.setOnClickListener(new Button.OnClickListener(){
    		public void onClick (View v){
    			update(v);
    			
    		}
    	});
		
		
	}
	
	/**
	 * @param args
	 */
	
	public void update(View v)
	{
		/*
	  	String fname = fullname.getText().toString();
	  	boolean res = dbHelper.updateUserTable(long rowId, String username, String password, String fullname, String age, String sex, String state);
		
		Intent i = new Intent(v.getContext(), ProfileView.class);
		startActivity(i);
	   */
	}
}
