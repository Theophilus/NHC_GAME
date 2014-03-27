package com.nhc.nhc_game.view;

import com.nhc.nhc_game.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileView extends Activity {
	
	public TextView helloTextView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        initControls();
    }
	    
	public void initControls(){
		
	}
}
	

