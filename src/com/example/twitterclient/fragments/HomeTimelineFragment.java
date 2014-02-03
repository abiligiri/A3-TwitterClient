package com.example.twitterclient.fragments;

import java.util.ArrayList;

import com.example.twitterclient.application.TwitterClientApp;
import com.example.twitterclient.models.Tweet;
import com.example.twitterclient.restapi.TwitterClient.TweetsListener;


public class HomeTimelineFragment extends TweetsListFragment {
	
	protected void getTweets(long tweetId) {
		TwitterClientApp.getRestClient().getHomeTimeline(tweetId, new TweetsListener() {
			
			@Override
			public void onError(String message) {
				
			}
			
			@Override
			public void onSuccess(ArrayList<Tweet> tweets) {
				for (Tweet tweet : tweets) {
					getAdapter().add(tweet);
				}
			}
		});
	}
}
