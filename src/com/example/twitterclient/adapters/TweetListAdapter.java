package com.example.twitterclient.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
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
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_tweet, parent, false);
		}
		TextView tvProfileName = (TextView)convertView.findViewById(R.id.tvProfileName);
		//TextView tvTweetTime = (TextView)convertView.findViewById(R.id.tvTweetTime);
		TextView tvTweet = (TextView)convertView.findViewById(R.id.tvTweet);
		ImageView ivProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);
		ivProfileImage.setImageBitmap(null);
		Tweet tweet = (Tweet)getItem(position);
		tvTweet.setText(tweet.getText());
		//tvTweetTime.setText(tweet.getTimestamp());
		String userName = "<b>" + tweet.getUser().getName() + "</b> @" + tweet.getUser().getScreenName();
		tvProfileName.setText(Html.fromHtml(userName));
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		return convertView;
		
	}
}
