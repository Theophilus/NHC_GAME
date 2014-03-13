package com.nhc.nhc_game.view;

import com.nhc.nhc_game.view.ProfileView;
import com.nhc.nhc_game.view.RegisterView;
import com.nhc.nhc_game.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import com.nhc.database.view.DatabaseAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginView extends Activity {
	
	public static final String MY_PREFS = "SharedPreferences";
	private DatabaseAdapter dbHelper;
	private EditText theUsername;
	private EditText thePassword;
	private Button loginButton;
	private Button registerButton;
	private	CheckBox rememberDetails;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    SharedPreferences mySharedPreferences = getSharedPreferences(MY_PREFS, 0);
        Editor editor = mySharedPreferences.edit();
        editor.putLong("uid", 0);
        editor.commit();
	    
        dbHelper = new DatabaseAdapter(this);
        dbHelper.open();
        
	    setContentView(R.layout.login);
	    initControls();
	    
	   }
	
	private void initControls() {
    	//Set the activity layout.
    	theUsername = (EditText) findViewById(R.id.loginView_name_input);
    	thePassword = (EditText) findViewById(R.id.loginView_password_input);
    	loginButton = (Button) findViewById(R.id.loginView_login_button);
    	registerButton = (Button) findViewById(R.id.loginView_signup_button);
    	rememberDetails = (CheckBox) findViewById(R.id.RememberMe);
    	
    	//Create touch listeners for all buttons.
    	loginButton.setOnClickListener(new Button.OnClickListener(){
    		public void onClick (View v){
    			LogMeIn(v);
    		}
    	});
    	
    	registerButton.setOnClickListener(new Button.OnClickListener(){
    		public void onClick (View v){
    			Register(v);
    		}
    	});
    	
    	//Create remember password check box listener.
    	rememberDetails.setOnClickListener(new CheckBox.OnClickListener(){
    		public void onClick (View v){
    			RememberMe();
    		}
    	});
    	
    	//Handle remember password preferences.
    	SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
    	String thisUsername = prefs.getString("username", "");
    	String thisPassword = prefs.getString("password", "");
    	boolean thisRemember = prefs.getBoolean("remember", false);
    	if(thisRemember) {
    		theUsername.setText(thisUsername);
    		thePassword.setText(thisPassword);
    		rememberDetails.setChecked(thisRemember);
    	}
    	
    }
    
    
    /**
     * Handles the remember password option.
     */
    private void RememberMe() {
    	boolean thisRemember = rememberDetails.isChecked();
    	SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
    	Editor editor = prefs.edit();
    	editor.putBoolean("remember", thisRemember);
    	editor.commit();
    }
    
    /**
     * This method handles the user login process.  
     * @param v
     */
    private void LogMeIn(View v) {
    	//Get the username and password
    	String thisUsername = theUsername.getText().toString();
    	String thisPassword = thePassword.getText().toString();
    	
    	//Assign the hash to the password
    	thisPassword = md5(thisPassword);
    	
    	// Check the existing user name and password database
    	Cursor theUser = dbHelper.fetchUser(thisUsername, thisPassword);
    	if (theUser != null) {
    		startManagingCursor(theUser);
    		if (theUser.getCount() > 0) {
    			saveLoggedInUId(theUser.getLong(theUser.getColumnIndex(DatabaseAdapter.COL_ID)), thisUsername, thePassword.getText().toString());
    		    stopManagingCursor(theUser);
    		    theUser.close();
    		    Intent i = new Intent(v.getContext(), ProfileView.class);
    		    startActivity(i);
    		}
    		
    		//Returns appropriate message if no match is made
    		else {
    			Toast.makeText(getApplicationContext(), 
    					"You have entered an incorrect username or password.", 
    					Toast.LENGTH_SHORT).show();
    			saveLoggedInUId(0, "", "");
    		}
    		stopManagingCursor(theUser);
    		theUser.close();
    	}
    	
    	else {
    		Toast.makeText(getApplicationContext(), 
    				"Database query error", 
    				Toast.LENGTH_SHORT).show();
    	}
    }
    
    /**
     * Open the Registration activity.
     * @param v
     */
    private void Register(View v)
    {
    	Intent i = new Intent(v.getContext(), RegisterView.class);
    	startActivity(i);
    }
    
    private void saveLoggedInUId(long id, String username, String password) {
    	SharedPreferences settings = getSharedPreferences(MY_PREFS, 0);
    	Editor myEditor = settings.edit();
    	myEditor.putLong("uid", id);
    	myEditor.putString("username", username);
    	myEditor.putString("password", password);
    	boolean rememberThis = rememberDetails.isChecked();
    	myEditor.putBoolean("rememberThis", rememberThis);
    	myEditor.commit();
    }
    
    /**
	 * Deals with the password encryption. 
	 * @param s The password.
	 * @return
	 */
    private String md5(String s) {
    	try {
    		MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
    		digest.update(s.getBytes());
    		byte messageDigest[] = digest.digest();
    		
    		StringBuffer hexString = new StringBuffer();
    		for (int i=0; i<messageDigest.length; i++)
    			hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
    		
    		return hexString.toString();
    	} 
    	
    	catch (NoSuchAlgorithmException e) {
    		return s;
    	}	
    	
    }

	
	@Override
	public void onDestroy() {
	    super.onDestroy();  // Always call the superclass
	    
	    // Stop method tracing that the activity started during onCreate()
	    android.os.Debug.stopMethodTracing();
	}
}
