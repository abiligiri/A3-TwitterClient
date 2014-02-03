package com.example.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codepath.oauth.OAuthLoginActivity;
import com.example.twitterclient.R;
import com.example.twitterclient.application.TwitterClientApp;
import com.example.twitterclient.models.AccountSettings;
import com.example.twitterclient.models.User;
import com.example.twitterclient.restapi.TwitterClient;
import com.example.twitterclient.restapi.TwitterClient.UserInfoListener;

public class LoginActivity extends OAuthLoginActivity<TwitterClient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		AccountSettings.getInstance().setContext(getBaseContext());
	}
	
	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
    @Override
    public void onLoginSuccess() {
    	User me = AccountSettings.getInstance().getUserInfo();
    	if (me != null) {
    		showHomeTimelineActivity();
    		return;
    	}
    	
    	fetchAccountSettings();
    }
    
    protected void showHomeTimelineActivity() {
    	Intent i = new Intent(LoginActivity.this, TimelineActivity.class);
    	startActivity(i);
    }
    
    protected void fetchAccountSettings() {
    	TwitterClientApp.getRestClient().getMyInfo(new UserInfoListener() {
			
			@Override
			public void onSuccess(User user) {
				AccountSettings.getInstance().saveUserInfo(user);
				showHomeTimelineActivity();
			}
			
			@Override
			public void onError(String message) {
				
			}
		});
    	
    }
    
    // OAuth authentication flow failed, handle the error
    // i.e Display an error dialog or toast
    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }
    
    // Click handler method for the button used to start OAuth flow
    // Uses the client to initiate OAuth authorization
    // This should be tied to a button used to login
    public void loginToRest(View view) {
        getClient().connect();
    }

}
