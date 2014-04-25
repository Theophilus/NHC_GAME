package com.nhc.nhc_game.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nhc.nhc_game.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Authorization extends Activity {

	  private WebView webView;
	  
	    private final static String CLIENT_ID = "fd146da592b3470eaa9b120ca716c896";
	    private final static String CLIENT_SECRET = "21eedbc8dccc414d8caf64d7a0554dff";
	    private final static String CALLBACK_URL = "com.example.runkeeperapi://RunKeeperIsCallingBack";
	    
	    private String UserName;
	    String accessToken;
	    String [] info_array;
	    @SuppressLint("SetJavaScriptEnabled")
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.auth);
	        UserName = getIntent().getStringExtra("Name");
	      //Force to login on every launch.
	        CookieManager cookieManager = CookieManager.getInstance();
	        cookieManager.removeAllCookie();
	 
	        webView = (WebView) findViewById(R.id.auth_webview);
	        //This is important. JavaScript is disabled by default. Enable it.
	        webView.getSettings().setJavaScriptEnabled(true);
	        webView.setVisibility(View.VISIBLE);       
	        
	        getAuthorizationCode();
	    }

	
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		
		private void getAuthorizationCode() {
	        String authorizationUrl = "https://runkeeper.com/apps/authorize?response_type=code&client_id=%s&redirect_uri=%s";
	        authorizationUrl = String.format(authorizationUrl, CLIENT_ID, CALLBACK_URL);
	        
	        webView.setWebViewClient(new WebViewClient() {
	            @Override
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                if (url.startsWith(CALLBACK_URL)) {
	                    final String authCode = Uri.parse(url).getQueryParameter("code");
	                    webView.setVisibility(View.GONE);
	                    getAccessToken(authCode);
	                    return true;
	                }
	 
	                return super.shouldOverrideUrlLoading(view, url);
	            }
	        });
	 
	        webView.loadUrl(authorizationUrl);
	    }
		
		private void getAccessToken(String authCode) {
	        String accessTokenUrl = "https://runkeeper.com/apps/token?grant_type=authorization_code&code=%s&client_id=%s&client_secret=%s&redirect_uri=%s";
	        final String finalUrl = String.format(accessTokenUrl, authCode, CLIENT_ID, CLIENT_SECRET, CALLBACK_URL);
	        
	        Thread networkThread = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                try {
	 
	                    HttpClient client = new DefaultHttpClient();
	                    HttpPost post = new HttpPost(finalUrl);
	 
	                    HttpResponse response = client.execute(post);
	 
	                    String jsonString = EntityUtils.toString(response.getEntity());
	                    final JSONObject json = new JSONObject(jsonString);
	                    accessToken = json.getString("access_token");
	                    storeAccessCode();
	                    
	                } catch (Exception e) {
	                	Toast.makeText(getApplicationContext(),"Exception occured: " ,Toast.LENGTH_SHORT).show();
	                    //displayToast("Exception occured:(");
	                    e.printStackTrace();
	                    resetUi();
	                }
	            }
	            });
	        
	        networkThread.start();
	        
	    }
		/*
		private void getTotalDistance() {        
	        try {
	            HttpClient client = new DefaultHttpClient();
	            HttpGet get = new HttpGet("http://api.runkeeper.com/records");
	            
	            get.addHeader("Authorization", "Bearer " + accessToken);
	            get.addHeader("Accept", "*");
	            
	            HttpResponse response = client.execute(get);
	            
	            String jsonString = EntityUtils.toString(response.getEntity());
	            JSONArray jsonArray = new JSONArray(jsonString);
	            findTotalWalkingDistance(jsonArray);
	 
	        } catch (Exception e) {
	            //displayToast("Exception occured:(");
	            e.printStackTrace();
	            resetUi();
	        }
	    }
		
		private void findTotalWalkingDistance(JSONArray arrayOfRecords) {
	        try {
	            //Each record has activity_type and array of statistics. Traverse to  activity_type = Walking
	            for (int ii = 0; ii < arrayOfRecords.length(); ii++) {
	                JSONObject statObject = (JSONObject) arrayOfRecords.get(ii);
	                if ("Walking".equalsIgnoreCase(statObject.getString("activity_type"))) {
	                    //Each activity_type has array of stats, navigate to "Overall" statistic to find the total distance walked.
	                    JSONArray walkingStats = statObject.getJSONArray("stats");
	                    for (int jj = 0; jj < walkingStats.length(); jj++) {
	                        JSONObject iWalkingStat = (JSONObject) walkingStats.get(jj);
	                        if ("Overall".equalsIgnoreCase(iWalkingStat.getString("stat_type"))) {
	                            long totalWalkingDistanceMeters = iWalkingStat.getLong("value");
	                            double totalWalkingDistanceMiles = totalWalkingDistanceMeters * 0.00062137;
	                            storeData(totalWalkingDistanceMiles);
	                            return;
	                        }
	                    }
	                }
	            }
	           
	        } catch (JSONException e) {
	        	displayToast("Exception occured:(");
	            e.printStackTrace();
	            resetUi();
	        }
	        Toast.makeText(getApplicationContext(),"No data to import" ,Toast.LENGTH_SHORT).show();
	        Intent i = new Intent(getApplicationContext(), HomeView.class);
	  		i.putExtra("Uname", info_array[0]);
		    startActivity(i);
	    }
		*/
		
		private void storeAccessCode(){
			try{
	        	String url = "jdbc:mysql://128.6.29.222:3306/nhcgame";
	        	
	        	///Load JDBC driver
	    	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	        	
	        	//Create a connection to your DB
	    	    Connection conn = DriverManager.getConnection( url, "root", "TheoMensah");
	        	
	    	    String insert= "UPDATE Player SET access_token=? WHERE username=? ";
	    	    PreparedStatement ps = conn.prepareStatement(insert);
	    	    ps.setString(1,accessToken);
	    	    ps.setString(2, UserName);

	    	  	//Run the query against the DB
	    	    ps.executeUpdate();
	        	
	    	    conn.close();
	    	    
	    	} catch (Exception e){
	    		System.out.println("Exception: " + e);
	    	}
	        
	        Intent i = new Intent(getApplicationContext(), ImportView.class);
	  		i.putExtra("NameAccess",UserName+","+accessToken );
		    startActivity(i);
	     
		}
		private void resetUi(){
			runOnUiThread(new Runnable() {            
	            @Override
	            public void run() {                
	                webView.setVisibility(View.GONE);
	            }
	        });
	    } 
		
		private void displayToast(final String message) {
	        runOnUiThread(new Runnable() {
	 
	            @Override
	            public void run() {
	                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	            }
	        });
	    }
}
