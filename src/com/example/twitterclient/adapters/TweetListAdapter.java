package com.example.twitterclient.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twitterclient.R;
import com.example.twitterclient.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetListAdapter extends ArrayAdapter<Tweet> {
	public TweetListAdapter(Context c, ArrayList<Tweet> tweets) {
		super(c, R.layout.list_item_tweet, tweets);
	}
	
	public static interface OnImageTapListener {
		public void tweetForTappedImage(Tweet t);
	}
	
	private OnImageTapListener listener;
	
	public void setImageTapListener(OnImageTapListener listener) {
		this.listener = listener;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_tweet, parent, false);
		}
		TextView tvProfileName = (TextView)convertView.findViewById(R.id.tvProfileName);
		TextView tvTweet = (TextView)convertView.findViewById(R.id.tvTweet);
		ImageView ivProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);
		ivProfileImage.setImageBitmap(null);
		final Tweet tweet = (Tweet)getItem(position);
		
		ivProfileImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (listener != null)
					listener.tweetForTappedImage(tweet);
			}
		});

		tvTweet.setText(tweet.getText());
		String userName = "<b>" + tweet.getUser().getName() + "</b> @" + tweet.getUser().getScreenName();
		tvProfileName.setText(Html.fromHtml(userName));
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		return convertView;		
	}
}
