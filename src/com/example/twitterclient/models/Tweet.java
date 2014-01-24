package com.example.twitterclient.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the ActiveAndroid wiki for more details:
 * https://github.com/pardom/ActiveAndroid/wiki/Creating-your-database-model
 * 
 */
@Table(name = "items")
public class Tweet extends Model {
	// Define table fields
	@Column(name = "name")
	private String name;
	
	public Tweet() {
		super();
	}
	
	// Parse model from JSON
	public Tweet(JSONObject object){
		super();

		try {
			this.name = object.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		for (int i=0; i < jsonArray.length(); i++) {
			try {
				tweets.add(new Tweet(jsonArray.getJSONObject(i)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tweets;
	}
	
	// Getters
	public String getName() {
		return name;
	}
	
	// Record Finders
	public static Tweet byId(long id) {
	   return new Select().from(Tweet.class).where("id = ?", id).executeSingle();
	}
	
	public static List<Tweet> recentItems() {
      return new Select().from(Tweet.class).orderBy("id DESC").limit("300").execute();
	}
}
