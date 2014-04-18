package com.nhc.nhc_game.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.nhc.nhc_game.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InviteView extends Activity{
	
	private Spinner inviteType;
	private String selection= "";
	private Button sendButton;
	private EditText contact;
	String userInfo;
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite);
        userInfo = getIntent().getStringExtra("Uname");
        createSelectionList();
        addListenerOnSpinnerItemSelection();
        sendButton= (Button) findViewById(R.id.invite_submit_button);
        sendButton.setOnClickListener(new Button.OnClickListener(){
       		public void onClick (View v){
       			sendInvitation(v);
       		}
        });
    }
	
	// create drop down list 
	  public void createSelectionList() {
	 
		inviteType = (Spinner) findViewById(R.id.invite_dropdown);
		List<String> list = new ArrayList<String>();
		list.add("Text");
		//list.add("Email");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inviteType.setAdapter(dataAdapter);
	  }
	 
	  public void addListenerOnSpinnerItemSelection() {
		inviteType = (Spinner) findViewById(R.id.invite_dropdown);
		inviteType.setOnItemSelectedListener( new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
				// TODO Auto-generated method stub
				/*Toast.makeText(parent.getContext(), 
						"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
						Toast.LENGTH_SHORT).show();
				selection =parent.getItemAtPosition(pos).toString();
				*/
				displayInstruction();	
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	  }
	  
	  public void displayInstruction(){
		  TextView tv= (TextView) findViewById(R.id.invite_instr);
		  if(selection == "Text"){
			  tv.setText("Enter phone number:");
		  }
		  if(selection == "Email"){
			  tv.setText("Enter email address:");
		  }
		  
	  }
	  
	  public void sendInvitation (View v){
		  
		  
		  contact=(EditText) findViewById(R.id.invitee_contact);
		
					  try {
						  
						  SmsManager smsManager = SmsManager.getDefault();
							smsManager.sendTextMessage(contact.getText().toString(), null, "Get the \"National Health Challenge Game\","
									+ " hottest fitness game at http://128.6.29.222:8080/nhcgame", null, null);
							Toast.makeText(getApplicationContext(), "Invitation Sent to "+contact.getText().toString(),
										Toast.LENGTH_LONG).show();
							try{
								String url = "jdbc:mysql://128.6.29.222:3306/nhcgame";
				    	    	
				    	    	///Load JDBC driver
				    		    Class.forName("com.mysql.jdbc.Driver").newInstance();
				    	    	
				    	    	//Create a connection to your DB
				    		    Connection conn = DriverManager.getConnection( url, "root", "TheoMensah");
				    	    	
			    		  		String insertPlayer = "INSERT INTO Invite(invitee_number, p_username, invite_date) VALUES (?, ?, NOW())";
			    		  		PreparedStatement ps = conn.prepareStatement(insertPlayer);
			    		  		ps.setString(1, contact.getText().toString());
			    		  		ps.setString(2, userInfo);
			    		  		
			    		  		ps.executeUpdate();
			    		  	
			    		  		} catch (Exception e){
			    		  			System.out.println("Exception inner: " + e);
			    		  		}
		 
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(),
								"SMS faild, please phone number! ",
								Toast.LENGTH_LONG).show();
							e.printStackTrace();
						}
			
	  }
	  

}
