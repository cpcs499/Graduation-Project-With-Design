package com.mzam.starter;

import java.io.ByteArrayOutputStream;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Posts extends Activity {
	/** Called when the activity is first created. */
	private static final int SELECT_PHOTO = 1;
	
	final Context context = this;
	ImageView postbut;
	String picturePath,fileName;
	ImageView PhotoinPost;
	Button TP , PP,EP;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.post_tab);	
		final Typeface ft = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
		TP = (Button) findViewById(R.id.textPost);
		PP = (Button) findViewById(R.id.photoPost);
		EP = (Button) findViewById(R.id.button1);
		
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.pull_in_left);
		TP.startAnimation(shake);
		Animation shake2 = AnimationUtils.loadAnimation(this, R.anim.pull_in_right);
		PP.startAnimation(shake2);
		
		TP.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				LayoutInflater li = LayoutInflater.from(context);
				View textPPost = li.inflate(R.layout.textpost, null);
				AlertDialog.Builder Builder = new AlertDialog.Builder(context);
				Builder.setView(textPPost);
				
				//final EditText userInput = (EditText)textPPost.findViewById(R.id.editText1);
				final EditText postdetail = (EditText) textPPost.findViewById(R.id.editText1);
				//final String post = postdetail.getText().toString();
				final ParseImageView profilepic = (ParseImageView) textPPost.findViewById(R.id.imageView1);
				
				ParseUser curr = ParseUser.getCurrentUser();
				TextView firstlastname = (TextView) textPPost.findViewById(R.id.textView1);
				firstlastname.setText(curr.getString("firstName")+" "+curr.getString("LastName"));
				firstlastname.setAllCaps(true);
				firstlastname.setTypeface(ft);
				TextView username = (TextView) textPPost.findViewById(R.id.textView2);
				username.setText("@"+curr.getUsername());
				username.setTypeface(ft);
				
				ParseFile imageFile = curr.getParseFile("ProfilePic");
				if (imageFile != null) {
					profilepic.setParseFile(imageFile);
					profilepic.loadInBackground();
				}
				
				else
				{
						Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
								R.drawable.inspiration_6);
						profilepic.setImageBitmap(bitmap); // for pevieeeewwww
				}
				
				/*
				ParseFile fileObject = (ParseFile) curr.get("ProfilePic");
				fileObject.getDataInBackground(new GetDataCallback() {
					public void done(byte[] data,ParseException e) {
						if (e == null) {
							Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
							profilepic.setImageBitmap(bmp);
								} else {
									
								}
							}
						});
				*/
				 final TextView LetterCounter = (TextView) textPPost.findViewById(R.id.textView3);
				 LetterCounter.setTypeface(ft);
				postdetail.addTextChangedListener(new TextWatcher() {
					 
					   public void afterTextChanged(Editable s) {
						// LetterCounter.setText(post.length());
							  int c = 200 - s.length();  // counter
							  String counter = String.valueOf(c);
							  LetterCounter.setText(counter);
							  LetterCounter.setTextColor(Color.RED);
					          
					   }
					 
					   public void beforeTextChanged(CharSequence s, int start, 
					     int count, int after) {
					   }
					 
					   public void onTextChanged(CharSequence s, int start, 
					     int before, int count) {
					   //TextView myOutputBox = (TextView) findViewById(R.id.myOutputBox);
					   //myOutputBox.setText(s);
					
					   }
				});
				
				Builder.setCancelable(false)
						.setPositiveButton("Post",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										
										 ParseObject MyPost = new ParseObject("Post");
											//Create the Shop
											MyPost.put("PostDetail", postdetail.getText().toString());
											MyPost.put("PostWriter",ParseUser.getCurrentUser());
											//MyPost.put("PostImage", file);
											
											//Add a relation between the Post and Comment
											//myshop.put("parent", myPost);
											MyPost.saveInBackground(); 
										
										 }
							
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// create
				AlertDialog alertDialog = Builder.create();
				alertDialog.show();
				
			}
		});
		
		PP.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				LayoutInflater li = LayoutInflater.from(context);
				View photoPPost = li.inflate(R.layout.photopost, null);
				AlertDialog.Builder Builder = new AlertDialog.Builder(context);
				Builder.setView(photoPPost);

				//final EditText userInput = (EditText)textPPost.findViewById(R.id.editText1);
				final EditText postdetail = (EditText) photoPPost.findViewById(R.id.editText1);
				//final String post = postdetail.getText().toString();
				final ParseImageView profilepic = (ParseImageView) photoPPost.findViewById(R.id.imageView1);
				
				ParseUser curr = ParseUser.getCurrentUser();
				TextView firstlastname = (TextView) photoPPost.findViewById(R.id.textView1);
				firstlastname.setText(curr.getString("firstName")+" "+curr.getString("LastName"));
				firstlastname.setTypeface(ft);
				firstlastname.setAllCaps(true);
				TextView username = (TextView) photoPPost.findViewById(R.id.textView2);
				username.setText("@"+curr.getUsername());
				username.setTypeface(ft);
				
				ParseFile imageFile = curr.getParseFile("ProfilePic");
				if (imageFile != null) {
					profilepic.setParseFile(imageFile);
					profilepic.loadInBackground();
				}
				else
				{
						Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
								R.drawable.inspiration_6);
						profilepic.setImageBitmap(bitmap); // for pevieeeewwww
	
				}
				
				 PhotoinPost = (ImageView) photoPPost.findViewById(R.id.imageView3);
				
				PhotoinPost.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
						intent.setType("image/*");
						startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PHOTO);
					}
				});
				
				final TextView LetterCounter = (TextView) photoPPost.findViewById(R.id.textView3);
				LetterCounter.setTypeface(ft);
				postdetail.addTextChangedListener(new TextWatcher() {
					 
					   public void afterTextChanged(Editable s) {
						// LetterCounter.setText(post.length());
							  int c = 200 - s.length();  // counter
							  String counter = String.valueOf(c);
							  LetterCounter.setText(counter);
							  LetterCounter.setTextColor(Color.RED);
					          
					   }
					 
					   public void beforeTextChanged(CharSequence s, int start, 
					     int count, int after) {
					   }
					 
					   public void onTextChanged(CharSequence s, int start, 
					     int before, int count) {
					   //TextView myOutputBox = (TextView) findViewById(R.id.myOutputBox);
					   //myOutputBox.setText(s);
					
					   }
				});
				
				
				
				Builder.setCancelable(false)
						.setPositiveButton("Post",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										
										 ParseObject MyPost = new ParseObject("Post");
											//Create the Shop
											MyPost.put("PostDetail", postdetail.getText().toString());
											MyPost.put("PostWriter",ParseUser.getCurrentUser());
											//MyPost.put("PostImage", file);
											//ParseFile fileObject = (ParseFile) objects.get(pos).get("PostPic");
											
											
											
											Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
											
											  String fileNameSegments[] = picturePath.split("/");
									          fileName = fileNameSegments[fileNameSegments.length - 1];
									          
									         // Toast.makeText(getApplicationContext(), fileName,
													//	Toast.LENGTH_SHORT).show
									          
									          //like instgram profile pic 110*110
									          Bitmap prsImgScaled = Bitmap.createScaledBitmap(bitmap, 110, 110* bitmap.getHeight() / bitmap.getWidth(), false);
									       Matrix matrix = new Matrix();
									       Bitmap prsImgScaledRotated = Bitmap.createBitmap(prsImgScaled, 0,
														0, prsImgScaled.getWidth(), prsImgScaled.getHeight(),
														matrix, true);
									       ByteArrayOutputStream bos = new ByteArrayOutputStream();
												prsImgScaledRotated.compress(Bitmap.CompressFormat.JPEG, 100, bos);
												byte[] scaledData = bos.toByteArray();
												ParseFile prsFile = new ParseFile("PostPic.jpg", scaledData);
												MyPost.put("PostPic", prsFile);
												
												//query.saveInBackground();
									          
									       /*
											query.saveInBackground(new SaveCallback() {
											  public void done(ParseException e) {
											    if (e == null) {
											      // Now let's update it with some new data. In this case, only cheatMode and score
											      // will get sent to the Parse Cloud. playerName hasn't changed.
											    	//object.put("Phonenumber",phone.getText().toString());
											    	Toast.makeText(getApplicationContext(),
											    			"Succesfully Updated!" , Toast.LENGTH_LONG)
										                     .show();
											//Add a relation between the Post and Comment
											//myshop.put("parent", myPost);
											 * 
											 */
											MyPost.saveInBackground(); 
										
										 }
							
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// create
				AlertDialog alertDialog = Builder.create();
				alertDialog.show();
				
			}
		});
		
		
		EP.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent in = new Intent(Posts.this,searchtry.class);
				startActivity(in);

			}
		});
		
	/*	
	    postbut = (ImageView) findViewById(R.id.imageView2);
		postbut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ParseObject MyPost = new ParseObject("Post");
				//Create the Shop
				MyPost.put("PostDetail", postdetail.getText().toString());
				MyPost.put("PostWriter",ParseUser.getCurrentUser());
				//MyPost.put("PostImage", file);
				
				//Add a relation between the Post and Comment
				//myshop.put("parent", myPost);
				MyPost.saveInBackground();
			}
		});
		*/		
		
		
		

	}
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	      
	     if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && null != data) {
	         Uri selectedImage = data.getData();
	         String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	         Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
	         cursor.moveToFirst();
	 
	         int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	         picturePath = cursor.getString(columnIndex);
	         cursor.close();
	         
	        // ImageView imageView = (ImageView) findViewById(R.id.imgView);
	        // final Bitmap bitmap = BitmapFactory.decodeByteArray(picturePath.getBytes(),0,picturePath.getBytes().length);
	         Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
	         //picturePath.getBytes();
	        //if(bitmap.getByteCount()<=5242880)
	         PhotoinPost.setImageBitmap(bitmap); // for pevieeeewwww
	        // else
	        // Toast.makeText(getApplicationContext(), "Big Size, Choose Another Pic", Toast.LENGTH_SHORT).show();
	        //Toast.makeText(getApplicationContext(), picturePath, Toast.LENGTH_SHORT).show();
	        
	        
	     }
	}
	
	public void onResume(){
		super.onResume();
		{
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.pull_in_left);
			TP.startAnimation(shake);
			Animation shake2 = AnimationUtils.loadAnimation(this, R.anim.pull_in_right);
			PP.startAnimation(shake2);
		}
	}
}
