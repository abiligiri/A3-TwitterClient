package com.example.twitterclient.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.twitterclient.R;
import com.example.twitterclient.application.TwitterClientApp;
import com.example.twitterclient.models.Tweet;
import com.example.twitterclient.restapi.TwitterClient.TweetsListener;

public class HomeTimelineActivity extends Activity {
	private static final String TAG = "HomeTimelineActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_timeline);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		TwitterClientApp.getRestClient().getHomeTimeline(new TweetsListener() {
			
			@Override
			public void onError(String message) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTweets(ArrayList<Tweet> tweets) {
				Log.d(TAG, tweets.toString());
				
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_timeline, menu);
		return true;
	}

}
