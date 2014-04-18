package com.nhc.nhc_game.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nhc.nhc_game.R;
/**
 * Handles the user registration activity.
 * 
 */
public class RegisterView extends Activity {
	private EditText username;
	private EditText pNumber;
	private EditText password;
	private EditText confirmPass;
	private EditText email;
	private DatePicker DOB;
	private Spinner sex;
	private Spinner state;
	private Button registerButton;
	private Button cancelButton;
	private TextView error;
	
	final Calendar c = Calendar.getInstance();;
	private int year;
	private int month;
	private int day;
	
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	        
	        
        SharedPreferences settings = getSharedPreferences(LoginView.MY_PREFS, 0);
        Editor editor = settings.edit();
        editor.putLong("uid", 0);
        editor.commit();
        setContentView(R.layout.register);
        
        username= (EditText) findViewById(R.id.reg_username);
        pNumber= (EditText) findViewById(R.id.reg_pnumber);
        password= (EditText) findViewById(R.id.reg_passwd1);
        confirmPass= (EditText) findViewById(R.id.reg_passwd2);
        email= (EditText) findViewById(R.id.reg_email);
        DOB= (DatePicker) findViewById(R.id.reg_DOB);
        sex = (Spinner) findViewById(R.id.reg_sex);
        state= (Spinner) findViewById(R.id.reg_state);
        registerButton = (Button) findViewById(R.id.reg_register);
        cancelButton= (Button) findViewById(R.id.reg_cancel);
        error =(TextView) findViewById(R.id.reg_error);
        error.setText("All fields are required!");
        setDefaultDate();
        
      //Create touch listeners for all buttons.
    	cancelButton.setOnClickListener(new Button.OnClickListener(){
    		public void onClick (View v){
    			returnToLogin(v);
    		}
    	});
    	
    	registerButton.setOnClickListener(new Button.OnClickListener(){
    		public void onClick (View v){
    			register(v);
    		}
    	});
	    }
	 
	 
	 public void setDefaultDate(){
		 
			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DAY_OF_MONTH);
			
			DOB.init(year, month, day, null);
	 }

	protected void register(View v) {
		//get imputs
		String pnum= pNumber.getEditableText().toString();
    	String uname= username.getEditableText().toString();
    	String pass= password.getEditableText().toString();
    	String cpass= confirmPass.getEditableText().toString();
    	String mail= email.getEditableText().toString();
    	String sx= sex.getSelectedItem().toString();
    	String st= state.getSelectedItem().toString();
    	
    	
    	if(uname.equalsIgnoreCase("")){
    		Toast.makeText(getApplicationContext(), "username is empty",
	  				Toast.LENGTH_SHORT).show();
    		
    	}
    	else if(pass.equalsIgnoreCase("")){
    		Toast.makeText(getApplicationContext(), "password is empty",
	  				Toast.LENGTH_SHORT).show();
    		
    	}
    	else if(cpass.equalsIgnoreCase("")){
    		Toast.makeText(getApplicationContext(), "password is empty!!",
	  				Toast.LENGTH_SHORT).show();
    	}
    	else if(!(pass.equalsIgnoreCase( cpass))){
    		Toast.makeText(getApplicationContext(), "password does not match",
	  				Toast.LENGTH_SHORT).show();
    	}
    	else if(pnum.equalsIgnoreCase("")){
    		Toast.makeText(getApplicationContext(), "phone# is empty",
	  				Toast.LENGTH_SHORT).show();
    	}
    	else if(mail.equalsIgnoreCase("")){
    		Toast.makeText(getApplicationContext(), "email is empty",
	  				Toast.LENGTH_SHORT).show();
    	}
    	else if(sx.equalsIgnoreCase("Select one")){
    		Toast.makeText(getApplicationContext(), "select sex",
	  				Toast.LENGTH_SHORT).show();
    	}
    	else if(st.equalsIgnoreCase("Select one")){
    		Toast.makeText(getApplicationContext(), "select state",
	  				Toast.LENGTH_SHORT).show();
    	}
    	else if(DOB.getDayOfMonth() == day && DOB.getMonth() == month && DOB.getYear() == year){
    		Toast.makeText(getApplicationContext(), "Check date of birth",
	  				Toast.LENGTH_SHORT).show();
    	}
    	else 
    		try{
    			String url = "jdbc:mysql://128.6.29.222:3306/nhcgame";
    	    	
    	    	///Load JDBC driver
    		    Class.forName("com.mysql.jdbc.Driver").newInstance();
    	    	
    	    	//Create a connection to your DB
    		    Connection conn = DriverManager.getConnection( url, "root", "TheoMensah");
    	    	
    	    	//check if usernsme exists
    		    String userCheck= "SELECT username FROM Player WHERE username = ? ";
    		    PreparedStatement ps = conn.prepareStatement(userCheck);
    		    ps.setString(1,uname);
    		
    		  	//Run the query against the DB
    		    ResultSet result = ps.executeQuery();
    		    
    		  	if(result.next() != false){
    		  		conn.close();
    		  		error.setText("Username already exists! ");
    		  	}
    		  	else {
    		  		try{
    		  		String insertPlayer = "INSERT INTO Player(phone_number, username, password, access_token,img, "
    		  				+ "email, sex,DOB, nat_rank, state_rank, e_points) VALUES (?, ?, ?, ?,?,?,?,?,?,?,?)";
    		  		PreparedStatement ps1 = conn.prepareStatement(insertPlayer);
    		  		ps1.setString(1, pnum);
    		  		ps1.setString(2, uname);
    		  		ps1.setString(3, pass);
    		  		ps1.setString(4, null);
    		  		ps1.setString(5, null);
    		  		ps1.setString(6, mail);
    		  		ps1.setString(7, sx);
    		  		ps1.setString(8, null);
    		  		//ps1.setString(8, DOB.getMonth() +"-"+ DOB.getDayOfMonth()+"-" + DOB.getYear());
    		  		ps1.setString(9, null);
    		  		ps1.setString(10, null);
    		  		ps1.setString(11, null);
    		  		
    		  		String insertRegDate = "INSERT INTO Registers(p_username,reg_date) VALUES (?, NOW())";
    		  		
    		  		PreparedStatement ps2 = conn.prepareStatement(insertRegDate);
    		  		ps2.setString(1, uname);
    		  		
    		  		String insertLivesIn = "INSERT INTO Lives_in(s_name, p_username) VALUES (?, ?)";
    		  		PreparedStatement ps3 = conn.prepareStatement(insertLivesIn);
    		  		ps3.setString(1, st);
    		  		ps3.setString(2, uname);
    		  		
    		  		ps1.executeUpdate();
    		  		ps2.executeUpdate();
    		  		ps3.executeUpdate();
    		  		
    		  		Toast.makeText(getApplicationContext(), "Registration was successful !!",
    		  				Toast.LENGTH_SHORT).show();
    		  		
    		  		Intent i = new Intent(v.getContext(), LoginView.class);
    			    startActivity(i);
    		  		} catch (Exception e){
    		  			System.out.println("Exception inner: " + e);
    		  		}
    		  	}
    			
    		}catch (Exception e){
    			System.out.println("Exception: " + e);
    		}
		
	}

	protected void returnToLogin(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent(v.getContext(), LoginView.class);
	    startActivity(i);
	}
	    
	 
}

