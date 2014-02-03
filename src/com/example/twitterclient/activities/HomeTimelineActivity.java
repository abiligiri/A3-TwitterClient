package com.example.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.example.twitterclient.R;
import com.example.twitterclient.application.TwitterClientApp;
import com.example.twitterclient.fragments.HomeTimelineFragment;
import com.example.twitterclient.models.AccountSettings;
import com.example.twitterclient.models.Tweet;
import com.example.twitterclient.restapi.TwitterClient.PostTweetListener;

public class HomeTimelineActivity extends FragmentActivity {
	//private static final String TAG = "HomeTimelineActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		
		setTitle(AccountSettings.getInstance().getUserInfo().getFormattedScreenname());
		
		FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragmentFrame, new HomeTimelineFragment());
		transaction.commit();
		
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_timeline, menu);
		return true;
	}

	private static final int REQUEST_CODE_TWEET = 0;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_compose:
			Intent i = new Intent(this, ComposeActivity.class);
			startActivityForResult(i, REQUEST_CODE_TWEET);
			break;
			
		case R.id.action_refresh:
//			tweetsAdapter.clear();
//			getTweets(0);
			default:
				break;
		}
		
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_TWEET) {
			if (resultCode == RESULT_OK) {
				int actionCode = data.getIntExtra(ComposeActivity.RESULT_KEY_CODE, ComposeActivity.RESULT_CODE_CANCEL);
				if (actionCode == ComposeActivity.RESULT_CODE_TWEET) {
					String message = data.getStringExtra(ComposeActivity.RESULT_KEY_MESSAGE);
					if (message != null) {
						postTweet(message);
					}
				}
			}
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void postTweet(String message) {
		TwitterClientApp.getRestClient().postTweet(message, new PostTweetListener() {
			
			@Override
			public void onSuccess(Tweet tweet) {
				//tweetsAdapter.insert(tweet, 0);
			}
			
			@Override
			public void onError(String message) {
				
			}
		});
	}
	
	
}
