package com.mzam.starter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class TimlineAdapter extends ParseQueryAdapter<ParseObject> {
	public TimlineAdapter(final Context context) {
		// Use the QueryFactory to construct a PQA that will only show
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
			public ParseQuery create() {
				
				
				ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Follow");
        		innerQuery.whereEqualTo("from", ParseUser.getCurrentUser());
        		
        		// Get the photos from the Users returned in the previous query
				ParseQuery<ParseObject> postFromFollowedUsers = new ParseQuery<ParseObject>("Post");
				//ParseObject obj = ParseObject.createWithoutData("_User",innerQuery.get("objectId"));
				postFromFollowedUsers.whereMatchesKeyInQuery("PostWriter", "to", innerQuery);
				postFromFollowedUsers.orderByDescending("createdAt");
				
				// Get the current user's photos
				ParseQuery<ParseObject> photosFromCurrentUserQuery = new ParseQuery<ParseObject>("Post");
				photosFromCurrentUserQuery.whereEqualTo("PostWriter", ParseUser.getCurrentUser());
				
				ParseQuery<ParseObject> query = ParseQuery.or(Arrays.asList( postFromFollowedUsers, photosFromCurrentUserQuery ));
				query.include("PostWriter");
				query.orderByDescending("createdAt");
				
				return query;
        		
				
			}
		});
	}
	
	public View getItemView(final ParseObject object, View v, ViewGroup parent) {

		if (v == null) {
			v = View.inflate(getContext(), R.layout.list_row, null);
		}

		super.getItemView(object, v, parent);

		final ImageView iv = (ImageView) v.findViewById(R.id.icon);
		final ImageView delete = (ImageView) v.findViewById(R.id.imageView5);
        final TextView tvTitle = (TextView) v.findViewById(R.id.textView1);
        final TextView fullname = (TextView) v.findViewById(R.id.textView2);
        final TextView username = (TextView) v.findViewById(R.id.textView3);
        final TextView tvDesc = (TextView) v.findViewById(R.id.releaseYear);
        ParseImageView picprof = (ParseImageView) v.findViewById(R.id.imageView3);
        
      
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Fonts/Rosemary.ttf");
        
        fullname.setText(object.getString("firstName")+" "+object.getString("LastName")+"");
        fullname.setAllCaps(true);
        fullname.setTypeface(font);
        
		return v;
	}
}