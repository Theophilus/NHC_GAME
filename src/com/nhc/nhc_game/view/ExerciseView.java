package com.nhc.nhc_game.view;

import com.nhc.nhc_game.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExerciseView extends Activity {
	private Button import_button;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise);
       import_button= (Button) findViewById(R.id.import_button);
       
       import_button.setOnClickListener(new Button.OnClickListener(){
   		public void onClick (View v){
   			startImport(v);
   		}
   	});
    }

	public void startImport(View v){
		Intent i = new Intent(v.getContext(), ImportView.class);
	    startActivity(i);   
	  }
}
