package com.nhc.nhc_game.view;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import com.nhc.nhc_game.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.*;
import android.database.*;



public class ProfileView extends Activity {
	String username;
	private TextView userName;
	private TextView userAge;
	private TextView userSex;
	private TextView userPhone;
	private TextView userEmail;
	private TextView userNRank;
	private TextView userSRank;
	private TextView userPoints;
	private TextView userState;
	private Button editprofile;
	
	final Calendar c = Calendar.getInstance();;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        username = getIntent().getStringExtra("Uname");
        
        //System.out.println("display name is" + disp_name);
        userName 	= (TextView) findViewById(R.id.profile_username);
        userAge     = (TextView) findViewById(R.id.profile_age);
        userSex    	= (TextView) findViewById(R.id.profile_sex);
        userPhone  	= (TextView) findViewById(R.id.profile_phone);
        userEmail	= (TextView) findViewById(R.id.profile_email);
        userNRank 	= (TextView) findViewById(R.id.profile_natRank);
        //userSRank	= (TextView) findViewById(R.id.profile_stateRank);
        userPoints	= (TextView) findViewById(R.id.profile_points);
        userState	= (TextView) findViewById(R.id.profile_state);
        
        

         displayUserInfo();
        //initControls();
    }
	
	public void displayUserInfo(){
		try{
	    	String url = "jdbc:mysql://128.6.29.222:3306/nhcgame";
	    	
	    	///Load JDBC driver
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    	
	    	//Create a connection to your DB
		    Connection conn = DriverManager.getConnection( url, "root", "TheoMensah");
	    	
		    String getData= "SELECT * FROM Player, Lives_in WHERE username = ? AND p_username =?";
		    PreparedStatement ps = conn.prepareStatement(getData);
		    
		    ps.setString(1,username);
		    ps.setString(2,username);
		  	//Run the query against the DB
		    ResultSet result = ps.executeQuery();
		    
		  	if(result.next() != false){
		  		userName.setText("Username:  "+result.getString("username"));
		  		
		  		//national ran
		  		if(result.getString("nat_rank") != null){
		  			userNRank.setText("National Rank:  "+result.getString("nat_rank"));
		  		}
		  		else {
		  			userNRank.setText("National Rank:  Not Applicable");
		  		}
		  		
		  		/*state rank
		  		if(result.getString("state_rank") != null){
		  			userSRank.setText("State Rank:  "+result.getString("state_rank"));
		  		}
		  		else {
		  			userSRank.setText("State Rank:  Not Applicable");
		  		}
		  		*/
		  		//points
		  		if(result.getString("e_points") != null){
		  			userPoints.setText("Points:  "+result.getString("e_points"));
		  		}
		  		else {
		  			userPoints.setText("Points:  None");
		  		}
		  		
		  		//phone number
		  		if(result.getString("phone_number") != null){
		  			userPhone.setText("Phone:  "+result.getString("phone_number"));
		  		}
		  		else {
		  			userPhone.setText("Phone:  Not Provided");
		  		}
		  		
		  		//email
		  		if(result.getString("email") != null){
		  			userEmail.setText("Email:  "+result.getString("email"));
		  		}
		  		else {
		  			userEmail.setText("Email:  Not Provided");
		  		}
		  		//state
		  		if(result.getString("s_name") != null){
		  			userState.setText("State:  "+result.getString("s_name"));
		  		}
		  		else {
		  			userState.setText("State:  Not Provided");
		  		}
		  		
		  		//sex
		  		if(result.getString("sex") != null){
		  			userSex.setText("Sex:  "+result.getString("sex"));
		  		}
		  		else {
		  			userSex.setText("Sex:  Not Provided");
		  		}
		  		
		  		//age
		  		if(result.getString("DOB") != null){
		  			//find age from (year-month-day)
		  			Date out = result.getDate("DOB");
		  			System.out.println("DB year: "+out.getYear()+"month: "+out.getMonth()+"day: "+ out.getDay());
		  			System.out.println("Code year: "+c.get(Calendar.YEAR)+"month: "+c.get(Calendar.MONTH)+"day: "+ c.get(Calendar.DAY_OF_MONTH));
		  			int yr = c.get(Calendar.YEAR) - out.getYear();
		  			System.out.println("Age: "+yr);
					if(out.getMonth() <= c.get(Calendar.MONTH)){
						if(out.getDay() <= c.get(Calendar.DAY_OF_MONTH)){
							
						}
						else{
							yr = yr -1;
						}
					}
					else{
						yr = yr -1;
					}		
					
					if(yr > 1900){
						yr = yr - 1900;
					}
		  			userAge.setText("Age:  "+ yr +" years");
		  		}
		  		else {
		  			userAge.setText("Age:  Not Provided");
		  		}
		  		
		  	}
		    conn.close();
		    
		} catch (Exception e){
			System.out.println("Exception: " + e);
		}
		
	}
	
	 /*   
	public void initControls(){
		editprofile = (Button) findViewById(R.id.button1);
		
		editprofile.setOnClickListener(new Button.OnClickListener(){
    		public void onClick (View v){
    			edit_profile(v);
    			
    		}
    	});
		
	}
	*/
	
	public void edit_profile(View v)
	{
		Intent i = new Intent(v.getContext(), EditView.class);
    	startActivity(i);
	}
}
	

