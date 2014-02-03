package com.example.twitterclient.fragments;

import java.util.ArrayList;

import android.os.Bundle;

import com.example.twitterclient.application.TwitterClientApp;
import com.example.twitterclient.models.Tweet;
import com.example.twitterclient.restapi.TwitterClient.TweetsListener;

public class UserTimelineFragment extends TweetsListFragment {
	private String userId;
	
	public UserTimelineFragment() {
	}

	public static UserTimelineFragment newInstance(String userId) {
		UserTimelineFragment fragment = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putString("userId", userId);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userId = getArguments().getString("userId");
	}
	
	@Override
	protected void getTweets(long tweetID) {
		TwitterClientApp.getRestClient().getUserTimeline(tweetID, userId, new TweetsListener() {
			
			@Override
			public void onSuccess(ArrayList<Tweet> tweets) {
				getAdapter().addAll(tweets);
				
			}
			
			@Override
			public void onError(String message) {
				
			}
		});
	}

}
