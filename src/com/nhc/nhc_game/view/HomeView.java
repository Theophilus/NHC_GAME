package com.nhc.nhc_game.view;

import com.nhc.nhc_game.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class HomeView extends Activity{

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
       GridView menu_grid=(GridView) findViewById(R.id.home_gridView);
       menu_grid.setAdapter(new MenuAdapter(this));
      
       menu_grid.setOnItemClickListener(new OnItemClickListener(){
    	   
		//@Override
		public void onItemClick(AdapterView<?> adapter, View v, int pos,long id) {
			// TODO Auto-generated method stub
			if(pos== 0){
				Intent i = new Intent(v.getContext(), ProfileView.class);
    		    startActivity(i);
			}
			if(pos == 1){
				Intent i = new Intent(v.getContext(), ForumView.class);
    		    startActivity(i);
			}
			if(pos ==2){
				Intent i = new Intent(v.getContext(), ScoreBoardView.class);
    		    startActivity(i);
			}
			if(pos == 3){
				Intent i = new Intent(v.getContext(), InviteView.class);
    		    startActivity(i);
			}
			if(pos == 4){
				Intent i = new Intent(v.getContext(), BenchmarkView.class);
    		    startActivity(i);
			}
			if(pos == 5){
				Intent i = new Intent(v.getContext(), StateRankView.class);
    		    startActivity(i);
			}
			if(pos ==6){
				Intent i = new Intent(v.getContext(), ImportView.class);
    		    startActivity(i);
			}
			if(pos == 7){
				Intent i = new Intent(v.getContext(), WebsiteView.class);
    		    startActivity(i);
			}
		}
       });
    }
	
	public class MenuAdapter extends BaseAdapter{

		private Integer[] menu_labels ={R.string.profile_menu,R.string.forum_menu, R.string.scoreBoard_menu,
				R.string.invite_menu,R.string.scoreBenchmark_menu,R.string.stateRank_menu,
				R.string.import_menu,R.string.viewMap_menu};
		
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
	            tv.setBackgroundColor(R.style.menu_bgcolor);
	            tv.setTextAppearance(context,R.style.menu_font);
	            tv.setGravity(R.style.menu_text_align);
	        }
	        else {
	            tv = (TextView) convertView;
	        }

	            tv.setText(menu_labels[position]);
	        return tv;
	    
		}
		
	}
	
}

