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
	                }
	        
	    }
		
		
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
		
}
