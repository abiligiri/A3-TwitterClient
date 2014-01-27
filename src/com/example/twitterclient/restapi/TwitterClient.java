package com.example.twitterclient.restapi;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.example.twitterclient.models.AccountSettings;
import com.example.twitterclient.models.Tweet;
import com.example.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "xvg1mfWQUMU60n4SxHiBg";       // Change this
    public static final String REST_CONSUMER_SECRET = "zIaT9AhwG1gc753fYqx3NX6ehBJ1Lqf2pWNDnrETBto"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://twitterintro"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    
    public static interface TweetsListener {
    	public void onError(String message);
    	public void onSuccess(ArrayList<Tweet> tweets);
    }
    
    public void getHomeTimeline(long tweetId, final TweetsListener listener) {
    	String url = getApiUrl("statuses/home_timeline.json");
    	RequestParams params = new RequestParams();
    	if (tweetId > 0) {
    		params.put("max_id", String.valueOf(tweetId));
    	}
    	
    	params.put("count", "25");
    	client.get(url, new JsonHttpResponseHandler() {
    		@Override
    		public void onSuccess(JSONArray jsonTweets) {
    			listener.onSuccess(Tweet.fromJsonArray(jsonTweets));
    		}
    		
    		@Override
    		public void onFailure(Throwable t) {
    			listener.onError(t.getMessage());
    		}
    	});
    }
    
    public static interface UserInfoListener {
    	public void onError(String message);
    	public void onSuccess(User user);
    }
    
    public void getMyInfo(final UserInfoListener listener) {
    	String url = getApiUrl("account/verify_credentials.json");
        
    	client.get(url, new JsonHttpResponseHandler() {
    		@Override
    		public void onSuccess(JSONObject json) {
    			User me = new User(json);
    			me.save();
    			listener.onSuccess(me);
    		}
    		
    		@Override
    		public void onFailure(Throwable t) {
    			listener.onError(t.getMessage());
    		}
    	});
    }
    
    public static interface PostTweetListener {
    	public void onError(String message);
    	public void onSuccess(Tweet tweet);
    }
    public void postTweet(String message, final PostTweetListener listener) {
    	String url = getApiUrl("statuses/update.json");
    	RequestParams params = new RequestParams();
    	params.put("status", message);
    	client.post(url, params, new JsonHttpResponseHandler() {
    		@Override
    		public void onSuccess(JSONObject tweet) {
    			// TODO Auto-generated method stub
    			Tweet t = new Tweet(tweet);
    			t.save();
    			listener.onSuccess(t);
    		}
    		
    		@Override
    		public void onFailure(Throwable t) {
    			listener.onError(t.getMessage());
    		}
    	});
    }
}