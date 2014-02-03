package com.example.twitterclient.fragments;

import java.util.ArrayList;

import android.util.Log;
import android.widget.Toast;

import com.example.twitterclient.application.TwitterClientApp;
import com.example.twitterclient.models.Tweet;
import com.example.twitterclient.models.User;
import com.example.twitterclient.restapi.TwitterClient.TweetsListener;


public class HomeTimelineFragment extends TweetsListFragment {
	
	protected void getTweets(long tweetId) {
		Log.d("HomeTimelineFragment", "loading from tweetID " + String.valueOf(tweetId));
		TwitterClientApp.getRestClient().getHomeTimeline(tweetId, new TweetsListener() {
			
			@Override
			public void onError(String message) {
				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onSuccess(ArrayList<Tweet> tweets) {
				getAdapter().addAll(tweets);
			}
		});
	}
	
	public void onTweetPosted (Tweet t) {
		getAdapter().insert(t, 0);
	}
}
