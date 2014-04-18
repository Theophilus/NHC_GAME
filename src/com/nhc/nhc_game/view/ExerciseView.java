package com.nhc.nhc_game.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.nhc.nhc_game.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExerciseView extends Activity {
	
	private String UserName;
	
	private Button import_button;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise);
       import_button= (Button) findViewById(R.id.import_button);
       UserName = getIntent().getStringExtra("Uname");
       import_button.setOnClickListener(new Button.OnClickListener(){
   		public void onClick (View v){
   			startImport(v);
   		}
   	});
    }

	public void startImport(View v){
		
		//check is user has already authorized access.
		try{
	    	String url = "jdbc:mysql://128.6.29.222:3306/nhcgame";
	    	
	    	///Load JDBC driver
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    	
	    	//Create a connection to your DB
		    Connection conn = DriverManager.getConnection( url, "root", "TheoMensah");
		    
	    	//Create a SQL statement
		    Statement stmt = conn.createStatement();
	    	
		    String checkAC= "SELECT access_token FROM Player WHERE username = ?";
		    PreparedStatement ps = conn.prepareStatement(checkAC);
		    
		    ps.setString(1,UserName);
		  	//Run the query against the DB
		    ResultSet result = ps.executeQuery();
		    
		    
		  	if(result.next() != false){
		  		String access = result.getString("access_token");
		  		conn.close();
		  		Intent i = new Intent(v.getContext(), ImportView.class);
		  		i.putExtra("NameAccess", UserName+","+ access+",0");
			    startActivity(i);
			    
		  	}
		  	else {
		  		
		  		conn.close();
		  		Intent i = new Intent(v.getContext(), Authorization.class);
		  		i.putExtra("UserName", UserName);
			    startActivity(i);
		  		
		  	}
	    	
		    conn.close();
		    
		} catch (Exception e){
			System.out.println("Exception: " + e);
		}
		  
	  }
}
