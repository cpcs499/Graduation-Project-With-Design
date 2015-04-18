package com.mzam.starter;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class searchtry extends Activity {
	/** Called when the activity is first created. */
	
	//ArrayAdapter<String> myAdapter;
	ListView listView;
    String[] dataArray = new String[] {"India","Androidhub4you", "Pakistan", "Srilanka", "Nepal", "Japan"};
    AutoCompleteAdapter myAdapter;
    ArrayList<String> picList,picList2,picList3,picList4;
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trysearch);	
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		//==========================
		
		picList = new ArrayList<String>();
		picList4 = new ArrayList<String>();
		
		//ParseQuery<ParseUser> y = ParseQuery.getUserQuery();
		try {
				ParseQuery<ParseUser> y = ParseQuery.getUserQuery();
	        	//innerQuery.whereEqualTo("username", newText);
	        	
				List<ParseUser> gg = y.find();
				for(ParseObject c:gg){
					//Toast.makeText(getApplicationContext(), c.getObjectId()+"", Toast.LENGTH_LONG).show(); 	
					picList.add(c.getString("username"));
					//picList4.add(c.getObjectId());
				}

			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,androidBooks);
        AutoCompleteTextView acTextView = (AutoCompleteTextView)findViewById(R.id.AndroidBooks);
        
        myAdapter = new AutoCompleteAdapter(this,R.layout.searchresult,picList);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,androidBooks);
        
        acTextView.setThreshold(2);
        acTextView.setAdapter(myAdapter);

        
        acTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				try{
				ParseQuery<ParseUser> y = ParseQuery.getUserQuery();
	        	y.whereEqualTo("username", picList.get(arg2));
	        	List<ParseUser> gg = y.find();
				for(ParseObject c:gg){
	        	   // Toast.makeText(getApplicationContext(), "" +c.getObjectId() , Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(searchtry.this, ProfileOtherUser.class);
					intent.putExtra("USER_ID", c.getObjectId());
					startActivity(intent);
				
			}
				}catch(ParseException ee){
					ee.printStackTrace();
				}
			}
        });
        
        
			    

}

	
	public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable
	{
		private ArrayList<String> items;
		private ArrayList<String> suggestions;
		private ArrayList<String> itemsAll;
		//private ArrayList<String> id;
		private LayoutInflater layoutInflater;
	    private int viewResourceId;
	    
	    public AutoCompleteAdapter(Context context, int viewResourceId, ArrayList<String> items)
	    {
	    	
	    	super(context, viewResourceId, items);
	    	this.viewResourceId = viewResourceId;
	    	this.suggestions = new ArrayList<String>();
	    	this.items = items;
	    	//this.id = id;
	    	this.itemsAll = (ArrayList<String>) items.clone();
	    	
	    	
	    }
	    
	    @
        Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Inflate the item layout and set the views
            View listItem = convertView;
            final int pos = position;
            if (listItem == null) {
            	layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            	listItem = layoutInflater.inflate(viewResourceId, null);
                //listItem = layoutInflater.inflate(R.layout.searchresult, null);   
            }
            
            //listItem.findViewById(R.id.imageView1);
            TextView profileusername = (TextView) listItem.findViewById(R.id.textView1);
            //final TextView firstlast = (TextView)listItem.findViewById(R.id.textView2);
            
           profileusername.setText(items.get(pos)+"");
            //firstlast.setText(id.get(pos)+"");
             
            
            
            
            return listItem;
        }
	    
	    @Override
	    public Filter getFilter() {
	        return nameFilter;
	    }

	    Filter nameFilter = new Filter() {
	    	
	    	public String convertResultToString(Object resultValue) {
	            String str = (String)resultValue; 
	            return str;
	        }
	    	
	        @Override
	        protected FilterResults performFiltering(CharSequence constraint) {
	            if(constraint != null) {
	                suggestions.clear();
	                for (String new_suggest : itemsAll) {
	                    if(new_suggest.toLowerCase().startsWith(constraint.toString().toLowerCase())){
	                        suggestions.add(new_suggest);
	                    }
	                }
	                FilterResults filterResults = new FilterResults();
	                filterResults.values = suggestions;
	                filterResults.count = suggestions.size();
	                return filterResults;
	            } else {
	                return new FilterResults();
	            }
	        }
	        
	        @SuppressWarnings("unchecked")
			@Override
	        protected void publishResults(CharSequence constraint, FilterResults results) {
	            ArrayList<String> filteredList = (ArrayList<String>) results.values;
	            if(results != null && results.count > 0) {
	                clear();
	                for (String c : filteredList) {
	                    add(c);
	                }
	                notifyDataSetChanged();
	            }
	        }
	    };


		

    }
			
}