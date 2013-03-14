package com.marcrazysoftware.voicemessenger;

import java.util.List;

import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class MainMenu extends Activity {

	/*
	 * Class member variables
	 */
	private Button mMicrophoneButton;
	private ListView mMessageList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_layout);
		setWidgets(hasRecognition());
	}

	/**
	 * Attach the widgets to code elements, and set click listeners.
	 */
	private void setWidgets(boolean isRecognitionAvailable) {
		/*
		 * Attach the views to code elements
		 */
		this.mMicrophoneButton = (Button) findViewById(R.id.bMicrophone);
		this.mMessageList = (ListView) findViewById(R.id.lvMessageView);
		
		/*
		 * Only enable the button if voice recognition is available
		 */
		this.mMicrophoneButton.setEnabled(isRecognitionAvailable);

		/*
		 * Click listener for the microphone button
		 */
		this.mMicrophoneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Mic Button Clicked",
						Toast.LENGTH_SHORT).show();
			}
		});

		/*
		 * Item click listener for the list view
		 */
		this.mMessageList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int index, long id) {
				Toast.makeText(getBaseContext(), "ScrollView Clicked",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private boolean hasRecognition() {
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			return false;
		} else
			return true;
	}

}
