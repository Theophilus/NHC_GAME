package com.nhc.nhc_game.view;

import com.nhc.nhc_game.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import com.nhc.database.view.DatabaseAdapter;
import android.content.*;


import android.database.*;



public class ProfileView extends Activity {
	
	public TextView helloTextView;
	private DatabaseAdapter dbHelper;
	public static final String MY_PREFS = "SharedPreferences";
	
	
	
	private TextView theFullName;
	private TextView theAge;
	private TextView theSex;
	private TextView theState;
	private Button editprofile;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        SharedPreferences sp = getSharedPreferences(MY_PREFS,  
                0);
        String stringValue = sp.getString("username", "error");
        System.out.println("username is" + stringValue);
        dbHelper = new DatabaseAdapter(this);
        dbHelper.open();
        
        
        // Make the Query
        
        
        
        Cursor theUser = dbHelper.fetchUserByUname(stringValue);
       
      
        String disp_name = theUser.getString(theUser.getColumnIndex(DatabaseAdapter.COL_FULLNAME));
        String age = theUser.getString(theUser.getColumnIndex(DatabaseAdapter.COL_AGE));
        String sex = theUser.getString(theUser.getColumnIndex(DatabaseAdapter.COL_SEX));
        String state = theUser.getString(theUser.getColumnIndex(DatabaseAdapter.COL_STATE));
        System.out.println("display name is" + disp_name);
        theFullName = (TextView) findViewById(R.id.fullname);
        theAge      = (TextView) findViewById(R.id.textView5);
        theSex      = (TextView) findViewById(R.id.textView6);
        theState      = (TextView) findViewById(R.id.textView8);
        theFullName.setText(disp_name);
        theAge.setText(age);
        theSex.setText(sex);
        theState.setText(state);
        
        //System.out.println("Fullname is" + theUser.getString(theUser.getColumnIndex(DatabaseAdapter.COL_FULLNAME)));
       
         

         
        initControls();
    }
	    
	public void initControls(){
		editprofile = (Button) findViewById(R.id.button1);
		
		editprofile.setOnClickListener(new Button.OnClickListener(){
    		public void onClick (View v){
    			edit_profile(v);
    			
    		}
    	});
		
	}
	
	public void edit_profile(View v)
	{
		Intent i = new Intent(v.getContext(), EditView.class);
    	startActivity(i);
	}
}
	

