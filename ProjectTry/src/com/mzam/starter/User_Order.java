package com.mzam.starter;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class User_Order extends Activity {
	
	ListView listView ;
	List<String> orders ,shops,products,PaymentType,OrderStatus;
	List<ParseObject> order_objects_list ;
    
	//----------------------Oncreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_order_list);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6adbd9"))); // set your desired color
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getDataForListView1();
		
		listView = (ListView) findViewById(R.id.user_order_list_view);
		
		listView.setAdapter(new UserOrderAdapter(this));
		
		//Toast.makeText(getApplicationContext(), "Hiiiiiiiiiiii", Toast.LENGTH_LONG).show();
        
	}
	//--------------------------------------End Oncreate
	@Override
	public void onResume()
	    {  // After a pause OR at startup
	    super.onResume();
	    
	    listView.setAdapter(new UserOrderAdapter(this));
	    }
	
	  
	  //-----------------------End Activity Methods
	
	//------Data class
		public class UserOrderDetail {
			String productName;
			String productColor;
			int producQuantity;
			double totalCost;
			String shopName;
			String orderStatus;
			String orderDate;
			String productPic;
			String paymentType;
			
		}
	//------------------------------End Data class
		
		 // A method to build the data in a list 
		public void getDataForListView1()
	    {
	    	   // List<UserOrderDetail> userOrderDetail_List = new ArrayList<UserOrderDetail>();
	    	  //return userOrderDetail_List;
	    	
	    	    orders = new ArrayList<String>();
	            shops = new ArrayList<String>();
	            products = new ArrayList<String>();
	            PaymentType = new ArrayList<String>();
	            OrderStatus = new ArrayList<String>();
	            try {    
	       ParseUser currentUser = ParseUser.getCurrentUser();
	       
	       //------------------------Start Query
	       ParseQuery<ParseObject> orderQuery = ParseQuery.getQuery("Order");
       		orderQuery.whereEqualTo("user_id", currentUser);
       		order_objects_list = orderQuery.find();
		
       		for(ParseObject order_object :order_objects_list){
   
       		   //detailObject.orderDate= (String)orderP.getCreatedAt();
       		   PaymentType.add(order_object.getString("payment_type"));
       		   OrderStatus.add(order_object.getString("order_status"));
       		   
       		 
				ParseQuery<ParseObject> ordered_productQuery = ParseQuery.getQuery("Ordered_Product");
				ordered_productQuery.whereEqualTo("order_id", order_object);
				
				products.add(order_object.getParseObject("productId").getObjectId());
				shops.add(order_object.getParseObject("ShopId").getObjectId());
				orders.add(order_object.getObjectId());
										
			}//End for 
	            } catch (ParseException e1) {

    				e1.printStackTrace();
    			}
 	          
	    } 
	
		
	 // ----------------------A method to build the data in a list
		 
	    //----------------------------Adapter Class
	    public class UserOrderAdapter extends BaseAdapter {

	    	//List<UserOrderDetail> userOrderList = getDataForListView2();
	    	
	    	private LayoutInflater layoutInflater;
	   


	        public UserOrderAdapter(User_Order activity) {
	            
	            layoutInflater = (LayoutInflater) activity
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            Toast.makeText(getApplicationContext(), "Adapter Constructor", Toast.LENGTH_LONG).show();
	        	
	        }
			@Override
			public int getCount() {
			
				//return userOrderList.size();
				return orders.size();
			}

			@Override
			//public UserOrderDetail getItem(int arg0) {
			public Object getItem(int arg0) {
			
				//return userOrderList.get(arg0);
				return arg0;
			}

			@Override
			public long getItemId(int arg0) {
			
				return arg0;
			}

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				final int pos = arg0;
				if(arg1==null)
				{
					LayoutInflater inflater = (LayoutInflater) User_Order.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					arg1 = inflater.inflate(R.layout.user_order_row, null);
				}
				//---------------------------------------------------------------------------------
				TextView productQuantity = (TextView)arg1.findViewById(R.id.product_quantity);
				TextView orderStatus = (TextView)arg1.findViewById(R.id.order_status);
				TextView totalCost = (TextView)arg1.findViewById(R.id.order_total_cost);
				TextView shopName = (TextView)arg1.findViewById(R.id.shop_name);
				TextView productName = (TextView)arg1.findViewById(R.id.product_name1);
			    final ParseImageView ShopImg = (ParseImageView) arg1.findViewById(R.id.productIMG);
			    TextView PaymentMethod = (TextView)arg1.findViewById(R.id.textView1);
			    
				//---------------------------------------------------------------------------------
				 try{
					 
					 ParseQuery<ParseObject> shopQuery = ParseQuery.getQuery("shop");
		            	shopQuery.whereEqualTo("objectId", shops.get(pos));
		            	List<ParseObject> shop = shopQuery.find();
		                for(int i = 0; i < shop.size(); i++){
		                	shopName.setText(" "+shop.get(i).get("shop_name").toString()+"");
		                
		                ParseFile imageFile = shop.get(i).getParseFile("shopImage");
		        		if (imageFile != null) {
		        			ShopImg.setParseFile(imageFile);
		        			ShopImg.loadInBackground();
		        		}
		        		else
		        		{
		        				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
		        						R.drawable.ic_launcher);
		        				ShopImg.setImageBitmap(bitmap); // for pevieeeewwww
		        				
		        				
		        		}
		                }
		                   
		                
						 ParseQuery<ParseObject> productQuery = ParseQuery.getQuery("Product");
		                productQuery.whereEqualTo("objectId", products.get(pos));
		            	List<ParseObject> product = productQuery.find();
		    			for(ParseObject prod :product){
		    				productName.setText("Product: "+prod.get("ProductName").toString()+"");
		    				//ParseFile fileObject = (ParseFile) prod.get("product_pic");
		            	
		    			}
		    				ParseQuery<ParseObject> ordered_prodQuery = ParseQuery.getQuery("Ordered_Product");
		    				ParseObject productObject = ParseObject.createWithoutData("Product",products.get(pos));
		    				ordered_prodQuery.whereEqualTo("product_id", productObject);
		    				List<ParseObject> ordered_product = ordered_prodQuery.find();
		    				for(ParseObject ord_prod:ordered_product){
		    					productQuantity.setText("Quantity: "+ord_prod.getInt("product_quantity")+"");
		    		    		totalCost.setText("Cost: "+ord_prod.getInt("product_quantity")*ord_prod.getDouble("unit_cost"));
		    		    		
		    				}
		    				
		    				orderStatus.setText("Status: "+OrderStatus.get(pos));
		    				PaymentMethod.setText("Payment Method : "+PaymentType.get(pos));
				 }catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				return arg1;
			}
			
		

	    }
	    //----------------------------- END Adapter Class
	
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        // Respond to the action bar's Up/Home button
	        case android.R.id.home:
	          //  NavUtils.navigateUpFromSameTask(this);
	            this.finish();
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
		 }
	
	
	

}
