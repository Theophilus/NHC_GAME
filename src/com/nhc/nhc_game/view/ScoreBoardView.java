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

public class ScoreBoardView extends Activity{

	TableRow tableRow;
    TextView textView;
    TableLayout tableLayout;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);
        
        tableLayout = (TableLayout) findViewById(R.id.scoreLayout);
        
        try{
	    	String url = "jdbc:mysql://128.6.29.222:3306/nhcgame";
	    	
	    	///Load JDBC driver
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    	
	    	//Create a connection to your DB
		    Connection conn = DriverManager.getConnection( url, "root", "TheoMensah");
	    	
		    String getRanks= "SELECT username, e_points FROM Player GROUP BY e_points DESC LIMIT 0, 101";
		    PreparedStatement ps = conn.prepareStatement(getRanks);
		    
		  	//Run the query against the DB
		    ResultSet result = ps.executeQuery();
		    
		    if(result.next() == false){
		    	System.out.println("In false statement");
            	TextView nodata= (TextView) findViewById(R.id.no_data);
            	nodata.setText("No ranks to display");
            	//nodata.setTextAppearance(getBaseContext(),R.style.menu_font);
	            //nodata.setGravity(Gravity.CENTER);
            }
            else{
		    //create header
		    tableRow = new TableRow(getApplicationContext());
		    
		    textView = new TextView(getApplicationContext());
		    textView.setText("Rank");
		    textView.setPadding(20, 20, 20, 20);
            tableRow.addView(textView);
            
		    textView = new TextView(getApplicationContext());
		    textView.setText("Player");
		    textView.setPadding(20, 20, 20, 20);
            tableRow.addView(textView);
            
            textView = new TextView(getApplicationContext());
		    textView.setText("Points");
		    textView.setPadding(20, 20, 20, 20);
            tableRow.addView(textView);
            
            tableLayout.addView(tableRow);
            //System.out.println("In true statement");
            
            //generate list
            int count = 0;
		  	while(result.next()){
		  		String setRanks= "UPDATE Player SET nat_rank =? WHERE username =? ";
		  		ps = conn.prepareStatement(setRanks);
		  		count++;
		  		//System.out.println("In while loop");
		         tableRow = new TableRow(getApplicationContext());
		         for (int j = 0; j < 3; j++) {
		        	 textView = new TextView(getApplicationContext());
		        	 if(j == 0){
		        		// System.out.println("In rank");
		        		 textView.setText(""+count);
		        	 }
		        	 if(j == 1){
		        		// System.out.println("In state_name");
		        		 textView.setText(""+result.getString("username"));
		        	 }
		        	 if(j == 2){
		        		// System.out.println("In general");
		        		 textView.setText(""+ result.getDouble("e_points"));
			         }
		             textView.setPadding(20, 20, 20, 20);
		             tableRow.addView(textView);
		         }
		         tableLayout.addView(tableRow);
		         ps.setInt(1,count);
        		 ps.setString(2,result.getString("username"));
        		 ps.executeUpdate();
		  	}
		  	
		  	//tableLayout.addView(tableLayout);
		  	//setContentView(tableLayout);
		    conn.close();
            } 
		} catch (Exception e){
			System.out.println("Exception: " + e);
		}
		
       
    }
}
