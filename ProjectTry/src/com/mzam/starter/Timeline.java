package com.mzam.starter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class Timeline extends Activity {
	
	private TimlineAdapter mHomeViewAdapter;
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    
		setContentView(R.layout.timeline);	
		// TODO Auto-generated method stub

		//ListView listView = (ListView) findViewById(R.id.listview);
			
		//mHomeViewAdapter = new TimlineAdapter(this);

		//listView.setAdapter(mHomeViewAdapter);
	}
		
	
}
