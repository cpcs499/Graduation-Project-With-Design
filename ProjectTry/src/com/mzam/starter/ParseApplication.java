package com.mzam.starter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Add your initialization code here
    Parse.initialize(this, "CSV5O88MtwgCqFM6ByHKxSn6qP5BufVJqj9rzYhg", "sLGPR5gduT013c3phHTgbzdSOVuUSvaOyQ30w6pT");

    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    
    // Optionally enable public read access.
    defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
  }
}
