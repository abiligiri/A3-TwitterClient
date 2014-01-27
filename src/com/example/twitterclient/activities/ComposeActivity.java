package com.example.twitterclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import com.example.twitterclient.R;
import com.example.twitterclient.adapters.ComposeListAdapter;
import com.example.twitterclient.models.AccountSettings;

public class ComposeActivity extends Activity {
	private static final String TAG = "ComposeActivity";
	
	public static final int RESULT_CODE_TWEET = 0,
			RESULT_CODE_CANCEL = 1;
	
	public static final String RESULT_KEY_CODE = "code";
	public static final String RESULT_KEY_MESSAGE = "msg";
	
	private ListView lvComposeContainer;
	private ComposeListAdapter listAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
		lvComposeContainer = (ListView)findViewById(R.id.lvComposeContainer);
		lvComposeContainer.addHeaderView(getLayoutInflater().inflate(R.layout.list_compose_header, lvComposeContainer, false));
		listAdapter = new ComposeListAdapter(this, AccountSettings.getInstance().getUserInfo(), null);
		lvComposeContainer.setAdapter(listAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}
	

	public void onCancelCompose(View v) {
		Intent i = new Intent();
		i.putExtra(RESULT_KEY_CODE, RESULT_CODE_CANCEL);
		setResult(RESULT_OK, i);
		finish();
	}
	
	public void onTweet(View v) {
		Intent i = new Intent();
		i.putExtra(RESULT_KEY_CODE, RESULT_CODE_TWEET);
		i.putExtra(RESULT_KEY_MESSAGE, listAdapter.getMessage());
		setResult(RESULT_OK, i);
		finish();
	}
}
