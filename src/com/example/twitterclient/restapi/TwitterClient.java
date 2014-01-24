package com.example.twitterclient.restapi;

import java.util.ArrayList;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.example.twitterclient.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "http://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "xvg1mfWQUMU60n4SxHiBg";       // Change this
    public static final String REST_CONSUMER_SECRET = "zIaT9AhwG1gc753fYqx3NX6ehBJ1Lqf2pWNDnrETBto"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://twitterintro"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public static interface ErrorListener {
    	public void onError(String message);
    }
    
    public static interface TweetsListener extends ErrorListener {
    	public void onTweets(ArrayList<Tweet> tweets);
    }
    
    public void getHomeTimeline(final TweetsListener listener) {
    	client.get("statuses/home_timeline.json", new AsyncHttpResponseHandler() {
    		@Override
    		public void onSuccess(String response) {
    			Log.d("Foo", response);
    		}
    		
    		@Override
    		public void onFailure(Throwable arg0) {
    			
    		}
    		
    		@Override
    		protected void handleFailureMessage(Throwable arg0, String arg1) {
    			// TODO Auto-generated method stub
    			super.handleFailureMessage(arg0, arg1);
    		}
    	});
    }
}