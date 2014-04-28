package com.nhc.nhc_game.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    String username;
    String accessToken;
    HttpClient client = new DefaultHttpClient();
    
   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_data);
        UserInfo = getIntent().getStringExtra("NameAccess");
        String[] temp = UserInfo.split(",");
        accessToken=temp[1];
        username=temp[0];
        String[] values = new String[] { "Walk","Run","Weight Lifting", "CrossFit","Cycling","Skiing","Yoga"};
        
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
            setListAdapter(adapter);
    }

	@Override
	protected void onListItemClick(ListView l, View v, int pos, long id) {

	    if(pos== 0){
	    	
	    	getData("walking");
			
		}
	    if(pos== 1){
	    	getData("running");
			
		}
	    if(pos== 2){
	    	getData("Strength Training");
			
		}
	    if(pos== 3){
	    	getData("CrossFit");
			
		}
	    if(pos== 4){
	    	getData("Cycling");
			
		}
	    if(pos== 5){
	    	getData("Downhill Skiing");
			
		}
	    if(pos== 6){
	    	getData("Yoga");
			
		}
	  } 

	private void getData(String dtype) {  
		
        try {
        	HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("http://api.runkeeper.com/records");
            get.addHeader("Authorization", "Bearer " + accessToken);
            get.addHeader("Accept", "*/*");
            HttpResponse response = client.execute(get);
           // System.out.println("Test response: "+ response);
            String jsonString = EntityUtils.toString(response.getEntity());
            
            //System.out.println("Test entityString: "+ jsonString);
       
            try {
            	JSONArray arrayOfRecords = new JSONArray(jsonString);
            	for (int ii = 0; ii < arrayOfRecords.length(); ii++) {
            	//JSONObject statObject = new JSONObject (jsonString);
                    JSONObject statObject = (JSONObject) arrayOfRecords.get(ii);
                    if (dtype.equalsIgnoreCase(statObject.getString("activity_type"))) {
                        //Each activity_type has array of stats, navigate to "Overall" statistic to find the total distance walked.
                        JSONArray dataStats = statObject.getJSONArray("stats");
                        
                        for (int jj = 0; jj < dataStats.length(); jj++) {
                            JSONObject dStat = (JSONObject) dataStats.get(jj);
       
                            if ("OVERALL".equalsIgnoreCase(dStat.getString("stat_type"))) {
                            
                                long distanceMeters = dStat.getLong("value");
                                
                                double distanceMiles = distanceMeters * 0.00062137;
                                if(distanceMeters <= 0){
                                	//System.out.println("in zero");
                                	Toast.makeText(getApplicationContext(),"No data to import!!" ,Toast.LENGTH_SHORT).show();
                                	return;
                                }
                                System.out.println("Distance: "+distanceMiles);
                                storeData(distanceMiles,dtype);
                                return;
                                
                                
                            }
                        }
                    }
            	}
               
            } catch (JSONException e) {
            	Toast.makeText(getApplicationContext(),"Import error try again later!!" ,Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                
            }
            Toast.makeText(getApplicationContext(),"No data to import" ,Toast.LENGTH_SHORT).show();
            
 
        } catch (Exception e) {
        	Toast.makeText(getApplicationContext(),"Import error try again later!!" ,Toast.LENGTH_SHORT).show();
            e.printStackTrace();
          
        }
    }
	
	private void storeData(double distance, String dtype) {
		
		try{
	    	String url = "jdbc:mysql://128.6.29.222:3306/nhcgame";
	    	
	    	///Load JDBC driver
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    	
	    	//Create a connection to your DB
		    Connection conn = DriverManager.getConnection( url, "root", "TheoMensah");
		    String latestImport= "SELECT max(distance) AS latest FROM Upload WHERE p_username =? AND eType =?";
		    PreparedStatement ps = conn.prepareStatement(latestImport);
		    ps.setString(1, username);
		    ps.setString(2, dtype);
		    ResultSet result = ps.executeQuery();
		    if(result.next() != false){
		    	double maxDistance = result.getDouble("latest");
		    	if( maxDistance >= distance){
		    		Toast.makeText(getApplicationContext(),"Data already imported!" ,Toast.LENGTH_SHORT).show();
		    		return;
		    	}
		    }
	    	
		  	String uploadData ="INSERT INTO Upload(distance, p_username, eType,upload_date,upload_time) "
		  			+ "VALUES (?,?,?, NOW(),CURTIME())";
		  	PreparedStatement ps2 = conn.prepareStatement(uploadData);
		  	ps2.setDouble(1,distance);
		  	ps2.setString(2,username);
		  	ps2.setString(3,dtype);
		  	Toast.makeText(getApplicationContext(),dtype+" import Successful!" ,Toast.LENGTH_SHORT).show();
		  	//System.out.println("stored data");
		  	ps2.executeUpdate();
		  	conn.close();
			    
		} catch (Exception e){
			Toast.makeText(getApplicationContext(),"Database Error try again later!!" ,Toast.LENGTH_SHORT).show();
			System.out.println("Exception: " + e);
		}
        
    }
	
}
