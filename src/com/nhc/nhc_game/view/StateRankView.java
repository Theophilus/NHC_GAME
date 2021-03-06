package com.nhc.nhc_game.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.nhc.nhc_game.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class StateRankView extends Activity {

	TableRow tableRow;
    TextView textView;
    TableLayout tableLayout;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.state);
        tableLayout = (TableLayout) findViewById(R.id.stateLayout);
        
        try{
	    	String url = "jdbc:mysql://128.6.29.222:3306/nhcgame";
	    	
	    	///Load JDBC driver
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    	
	    	//Create a connection to your DB
		    Connection conn = DriverManager.getConnection( url, "root", "TheoMensah");
	    	
		    String getRanks= "SELECT s_name, s_points,s_rank FROM Distr GROUP BY s_rank";
		    PreparedStatement ps = conn.prepareStatement(getRanks);
		    
		  	//Run the query against the DB
		    ResultSet result = ps.executeQuery();
		    
		    //create header
		    tableRow = new TableRow(getApplicationContext());
		    
		    textView = new TextView(getApplicationContext());
		    textView.setText("Rank");
		    textView.setPadding(20, 20, 20, 20);
            tableRow.addView(textView);
            
		    textView = new TextView(getApplicationContext());
		    textView.setText("State");
		    textView.setPadding(20, 20, 20, 20);
            tableRow.addView(textView);
            
            textView = new TextView(getApplicationContext());
		    textView.setText("Points");
		    textView.setPadding(20, 20, 20, 20);
            tableRow.addView(textView);
            
            tableLayout.addView(tableRow);
            
		  	while(result.next()){
		  		
		         tableRow = new TableRow(getApplicationContext());
		         for (int j = 0; j < 3; j++) {
		        	 textView = new TextView(getApplicationContext());
		        	 if(j == 0){
		        		// System.out.println("In rank");
		        		 textView.setText(""+result.getInt("s_rank"));
		        		 
		        	 }
		        	 if(j == 1){
		        		// System.out.println("In state_name");
		        		 textView.setText(""+result.getString("s_name"));
		        		 
		        	 }
		        	 if(j == 2){
		        		 textView.setText(""+ result.getDouble("s_points"));
		        		 
			         }
		             textView.setPadding(20, 20, 20, 20);
		             tableRow.addView(textView);
		             
		         }
		         tableLayout.addView(tableRow);
		         
		  	}
		  	
		    conn.close();
		    
		} catch (Exception e){
			System.out.println("Exception: " + e);
		}
		
        //createList();
        
    }
	
}

