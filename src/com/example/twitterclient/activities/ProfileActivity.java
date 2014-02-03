package com.example.twitterclient.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twitterclient.R;
import com.example.twitterclient.fragments.UserTimelineFragment;
import com.example.twitterclient.models.AccountSettings;
import com.example.twitterclient.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		String userId = getIntent().getStringExtra("userId");
		
		User user = User.fromIDStr(userId);
		setTitle(user.getFormattedScreenname());
		
		TextView tvProfileName = (TextView)findViewById(R.id.tvProfileName);
		//TextView tvProfileTagline = (TextView)findViewById(R.id.tvProfileTagline);
		ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
		TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
		
		tvProfileName.setText(user.getName());
		//tvProfileTagline.setText(user.getTagline());
		tvFollowers.setText(String.valueOf(user.getFollowersCount()) + " Followers");
		tvFollowing.setText(String.valueOf(user.getFriendsCount()) + " Following");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.frameContainer, UserTimelineFragment.newInstance(user.getIdStr()));
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
