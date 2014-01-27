package com.example.twitterclient.models;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;



@Table(name = "users")
public class User extends Model {
	
	@Column(name = "idStr")
	private String idStr;

	@Column(name = "profileImageUrl")
	private String profileImageUrl;

	@Column(name = "name")
	private String name;

	@Column(name = "screenName")
	private String screenName;

	@Column(name = "tagLine")
	private String tagLine;

	@Column(name = "followersCount")
	private int followersCount;

	@Column(name = "friendsCount")
	private int friendsCount;

	public User() {
		super();
	}

	public User(JSONObject object) {
		super();
		
		try {
			this.idStr = object.getString("id_str");
			this.name = object.getString("name");
			this.screenName = object.getString("screen_name");
			this.profileImageUrl = object.getString("profile_image_url");
			this.tagLine = object.getString("description");
			this.followersCount = object.getInt("followers_count");
			this.friendsCount = object.getInt("friends_count");
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}

	public String getIdStr() {
		return idStr;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;	
	}

	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}
	
	public String getTagline() {
		return tagLine;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFriendsCount() {
		return friendsCount;
	}
	
	public String getFormattedScreenname()
	{
		return "@" + getScreenName().toLowerCase();
	}
	
	public static User fromIDStr(String idStr) {
		List<User> user = new Select().from(User.class).where("idStr = ?", idStr).execute();
		
		if (user == null)
			return null;
		
		return user.get(0);
	}
}
