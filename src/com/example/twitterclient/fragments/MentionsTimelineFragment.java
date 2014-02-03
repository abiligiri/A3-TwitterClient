package com.example.twitterclient.fragments;

import java.util.ArrayList;

import android.util.Log;
import android.widget.Toast;

import com.example.twitterclient.application.TwitterClientApp;
import com.example.twitterclient.models.Tweet;
import com.example.twitterclient.restapi.TwitterClient.TweetsListener;

public class MentionsTimelineFragment extends TweetsListFragment {

	@Override
	protected void getTweets(long tweetID) {
		Log.d("MentionsTimelineFragment", "loading from tweetID " + String.valueOf(tweetID));
		TwitterClientApp.getRestClient().getMentionsTimeline(tweetID, new TweetsListener() {
			
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

}
