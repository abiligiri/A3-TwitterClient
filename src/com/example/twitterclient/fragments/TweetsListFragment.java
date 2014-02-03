package com.example.twitterclient.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.twitterclient.R;
import com.example.twitterclient.adapters.TweetListAdapter;
import com.example.twitterclient.models.Tweet;

public abstract class TweetsListFragment extends Fragment {
	protected ListView lvTweets;
	protected ArrayAdapter<Tweet> tweetsAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		lvTweets = (ListView) view.findViewById(R.id.lvTweets);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tweetsAdapter = new TweetListAdapter(getActivity(), new ArrayList<Tweet>());
		lvTweets.setAdapter(tweetsAdapter);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
				
				@Override
				public void onLoadMore(int page, int totalItemsCount) {
					if (totalItemsCount > 0) {
						getTweets(tweetsAdapter.getItem(totalItemsCount - 1).getUniqueId());
						return;
					}
				}
			});
		
		getTweets(0);
	}
	
	protected ArrayAdapter<Tweet> getAdapter() {
		return tweetsAdapter;
	}
	
	protected abstract void getTweets(long tweetID);
	
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
