package com.example.twitterclient.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


public class AccountSettings {
	private static AccountSettings instance = new AccountSettings();
	private static final String PREFERENCE_USER_ID = "userID";
	private Context context;
	private User user;
	
	public static AccountSettings getInstance() {
		return instance;
	}
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	public User getUserInfo() {
		if (user != null)
			return user;
		
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
    	String userID = pref.getString(PREFERENCE_USER_ID, null);
    	
    	if (userID == null)
    		return null;
    	
    	user = User.fromIDStr(userID);
    	
    	return user;
	}
	
	public void saveUserInfo(User user) {
		if (user == null)
			return;
		
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		Editor edit = pref.edit();
		edit.putString(PREFERENCE_USER_ID, user.getIdStr());
		edit.commit();
	}
}
