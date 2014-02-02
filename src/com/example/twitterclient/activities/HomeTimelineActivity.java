package com.example.twitterclient.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.example.twitterclient.R;
import com.example.twitterclient.adapters.TweetListAdapter;
import com.example.twitterclient.application.TwitterClientApp;
import com.example.twitterclient.models.AccountSettings;
import com.example.twitterclient.models.Tweet;
import com.example.twitterclient.restapi.TwitterClient.PostTweetListener;
import com.example.twitterclient.restapi.TwitterClient.TweetsListener;

public class HomeTimelineActivity extends Activity {
	//private static final String TAG = "HomeTimelineActivity";
	private ListView lvTweets;
	private ArrayList<Tweet> tweets;
	private TweetListAdapter tweetsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_timeline);
		tweets = new ArrayList<Tweet>();
		
		setTitle(AccountSettings.getInstance().getUserInfo().getFormattedScreenname());
		setupViews();
		setupAdapters();
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				if (totalItemsCount > 0) {
					getTweets(tweetsAdapter.getItem(totalItemsCount - 1).getUniqueId());
					return;
				}
			}
		});
		
		/* For displaying tweets when offline */
		/* tweetsAdapter.addAll(Tweet.getMostRecentTweets()); */
		getTweets(0);
	}
	
	protected void setupViews() {
		lvTweets = (ListView)findViewById(R.id.lvTweets);
	}
	
	protected void setupAdapters() {
		tweetsAdapter = new TweetListAdapter(this, tweets);
		lvTweets.setAdapter(tweetsAdapter);
	}

	protected void getTweets(long tweetId) {
		TwitterClientApp.getRestClient().getHomeTimeline(tweetId, new TweetsListener() {
			
			@Override
			public void onError(String message) {
				
			}
			
			@Override
			public void onSuccess(ArrayList<Tweet> tweets) {
				tweetsAdapter.addAll(tweets);
			}
		});
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
			tweetsAdapter.clear();
			getTweets(0);
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
				tweetsAdapter.insert(tweet, 0);
			}
			
			@Override
			public void onError(String message) {
				
			}
		});
	}
	
	public abstract class EndlessScrollListener implements OnScrollListener {
	    // The minimum amount of items to have below your current scroll position
	    // before loading more.
	    private int visibleThreshold = 5;
	    // The current offset index of data you have loaded
	    private int currentPage = 0;
	    // The total number of items in the dataset after the last load
	    private int previousTotalItemCount = 0;
	    // True if we are still waiting for the last set of data to load.
	    private boolean loading = true;
	    // Sets the starting page index
	    private int startingPageIndex = 0;

	    public EndlessScrollListener() {
	    }

	    public EndlessScrollListener(int visibleThreshold) {
	        this.visibleThreshold = visibleThreshold;
	    }

	    public EndlessScrollListener(int visibleThreshold, int startPage) {
	        this.visibleThreshold = visibleThreshold;
	        this.startingPageIndex = startPage;
	        this.currentPage = startPage;
	    }

	    // This happens many times a second during a scroll, so be wary of the code you place here.
	    // We are given a few useful parameters to help us work out if we need to load some more data,
	    // but first we check if we are waiting for the previous load to finish.
	    @Override
	    public void onScroll(AbsListView view,int firstVisibleItem,int visibleItemCount,int totalItemCount) 
	        {
	        // If the total item count is zero and the previous isn't, assume the
	        // list is invalidated and should be reset back to initial state
	        // If there are no items in the list, assume that initial items are loading
	        if (!loading && (totalItemCount < previousTotalItemCount)) {
	            this.currentPage = this.startingPageIndex;
	            this.previousTotalItemCount = totalItemCount;
	            if (totalItemCount == 0) { this.loading = true; } 
	        }

	        // If it’s still loading, we check to see if the dataset count has
	        // changed, if so we conclude it has finished loading and update the current page
	        // number and total item count.
	        if (loading) {
	            if (totalItemCount > previousTotalItemCount) {
	                loading = false;
	                previousTotalItemCount = totalItemCount;
	                currentPage++;
	            }
	        }

	        // If it isn’t currently loading, we check to see if we have breached
	        // the visibleThreshold and need to reload more data.
	        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
	        if (!loading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + visibleThreshold)) 
	                {
	            onLoadMore(currentPage + 1, totalItemCount);
	            loading = true;
	        }
	    }

	    // Defines the process for actually loading more data based on page
	    public abstract void onLoadMore(int page, int totalItemsCount);

	    @Override
	    public void onScrollStateChanged(AbsListView view, int scrollState) {
	        // Don't take any action on changed
	    }
	}
}
