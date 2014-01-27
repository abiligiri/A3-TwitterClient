package com.example.twitterclient.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twitterclient.R;
import com.example.twitterclient.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeListAdapter extends BaseAdapter {
		private User user;
		private String message;
		private Context context;
		 
		public ComposeListAdapter(Context c, User user, String message) {
			this.context = c;
			this.user = user;
			this.message = message;
		}
		
		public String getMessage() {
			return message;
		}
		
		@Override
		public int getCount() {
			return 2;
		}
		
		@Override
		public Object getItem(int position) {
			switch (position) {
			case 0:
				return user;
			
			case 1:
				return message;
				

			default:
				break;
			}
			
			return null;
		}
		
		@Override
		public long getItemId(int position) {
			switch (position) {
			case 0:
				return 0;
			
			case 1:
				return 1;
				

			default:
				break;
			}
		
			return -1;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView != null)
				return convertView;
			
			LayoutInflater inflater = LayoutInflater.from(this.context);
			switch (position) {
			case 0:
				convertView = inflater.inflate(R.layout.list_compose_item_user_info, parent, false);
				TextView tvScreenName = (TextView)convertView.findViewById(R.id.tvScreenName);
				tvScreenName.setText(user.getFormattedScreenname());
				ImageView ivProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);
				ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
				break;
			
			case 1:
				convertView = inflater.inflate(R.layout.list_compose_item_tweet, parent, false);
				EditText etMessage = (EditText)convertView.findViewById(R.id.etMessage);
				etMessage.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						message = s.toString();
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						
					}
				});
				convertView.setFocusable(true);
				convertView.setFocusableInTouchMode(true);
				break;
				

			default:
				break;
			}
			
			return convertView;
		}
}
