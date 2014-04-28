package com.nhc.nhc_game.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.nhc.nhc_game.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class HomeView extends Activity{
	 String username;
	 Button logoutButton;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
       GridView menu_grid=(GridView) findViewById(R.id.home_gridView);
       //menu_grid.setBackgroundColor();
       menu_grid.setAdapter(new MenuAdapter(this));
       username = getIntent().getStringExtra("Uname");
       logoutButton=(Button) findViewById(R.id.HomeView_logoutButton);
     //Create touch listeners for all buttons.
   		logoutButton.setOnClickListener(new Button.OnClickListener(){
   			public void onClick (View v){
   				LogOut(v);
   			}
   		});
      
       menu_grid.setOnItemClickListener(new OnItemClickListener(){
    	   
		//@Override
		public void onItemClick(AdapterView<?> adapter, View v, int pos,long id) {
			// TODO Auto-generated method stub
			if(pos== 0){
				
				Intent i = new Intent(v.getContext(), ProfileView.class);
				i.putExtra("Uname", username);
    		    startActivity(i);
			}
			if(pos == 1){
				Intent i = new Intent(v.getContext(), ScoreBoardView.class);
				i.putExtra("Uname", username);
    		    startActivity(i);
			}
			if(pos == 2){
				Intent i = new Intent(v.getContext(), InviteView.class);
				i.putExtra("Uname", username);
    		    startActivity(i);
			}
			if(pos == 3){
				Intent i = new Intent(v.getContext(), StateRankView.class);
				i.putExtra("Uname", username);
    		    startActivity(i);
			}
			if(pos == 4){
				try{
			    	String url = "jdbc:mysql://128.6.29.222:3306/nhcgame";
			    	
			    	///Load JDBC driver
				    Class.forName("com.mysql.jdbc.Driver").newInstance();
			    	
			    	//Create a connection to your DB
				    Connection conn = DriverManager.getConnection( url, "root", "TheoMensah");
			    	
				    String checkAC= "SELECT access_token FROM Player WHERE username = ?";
				    PreparedStatement ps = conn.prepareStatement(checkAC);
				    
				    ps.setString(1,username);
				  	//Run the query against the DB
				    ResultSet result = ps.executeQuery();
				    
				  	if(result.next() != false){
				  		String access = result.getString("access_token");
				  		conn.close();
				  		if(access != null){
				  		Intent i = new Intent(v.getContext(), ImportView.class);
				  		i.putExtra("NameAccess", username+","+ access);
					    startActivity(i);
				  		}
				  		else {
					  		Intent i = new Intent(v.getContext(), Authorization.class);
					  		i.putExtra("Name", username);
						    startActivity(i);
					  	}
				  	}
				    conn.close();
				    
				} catch (Exception e){
					System.out.println("Exception: " + e);
				}
			}
			if(pos == 5){
				Intent i = new Intent(v.getContext(), WebsiteView.class);
				i.putExtra("Uname", username);
    		    startActivity(i);
			}
		}
       });
    }
	
	public class MenuAdapter extends BaseAdapter{

		private Integer[] menu_labels ={R.string.profile_menu,R.string.scoreBoard_menu,
				R.string.invite_menu,R.string.stateRank_menu,R.string.import_menu,R.string.viewMap_menu};
		
		public Context context;
		
		public MenuAdapter(Context c){
			this.context=c;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return menu_labels.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			WindowManager wm= getWindowManager();
			Display d= wm.getDefaultDisplay();
			TextView tv;
	        if (convertView == null) {
	            tv = new TextView(context);
	            tv.setLayoutParams(new GridView.LayoutParams(d.getWidth()/3,d.getHeight()/5 ));
	            //tv.setBackgroundColor(R.style.menu_bgcolor);
	            tv.setBackgroundColor(Color.rgb(65,105,255));
	            tv.setTextAppearance(context,R.style.menu_font);
	            tv.setGravity(Gravity.CENTER);
	        }
	        else {
	            tv = (TextView) convertView;
	        }

	            tv.setText(menu_labels[position]);
	        return tv;
	    
		}
		
	}
	
	public void LogOut(View v){
		
		try{
	    	String url = "jdbc:mysql://128.6.29.222:3306/nhcgame";
	    	
	    	///Load JDBC driver
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    	
	    	//Create a connection to your DB
		    Connection conn = DriverManager.getConnection( url, "root", "TheoMensah");
	    
		  		String loginData = "INSERT INTO Logout(p_username,logout_date,logout_time) VALUES (?, NOW(),CURTIME())";
		  		PreparedStatement ps = conn.prepareStatement(loginData);
		  		ps.setString(1,username);
		  		ps.executeUpdate();
		  		conn.close();
		  		Intent i = new Intent(v.getContext(), LoginView.class);
			    startActivity(i);
		    conn.close();
		    
		} catch (Exception e){
			System.out.println("Exception: " + e);
		}

	}
	
	//stop back 
	@Override
	public void onBackPressed() {
		//do nothing
	}
	
}

