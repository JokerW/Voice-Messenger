package com.marcrazysoftware.voicemessenger;

import java.util.List;

import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
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

	private SpeechRecognizer recognizer;

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
				runCommandListener();
			}
		});

		/*
		 * Item click listener for the list view
		 */
		this.mMessageList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int index, long id) {
				/*
				 * For now, simply notify the user that they have clicked the
				 * listview
				 */
				Toast.makeText(getBaseContext(), "ListView Clicked",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * Checks whether or not the current device supports voice recognition.
	 * 
	 * @return Whether or not the phone supports voice recognition.
	 */
	private boolean hasRecognition() {
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			return false;
		} else
			return true;
	}

	/**
	 * Runs the voice recognition for this activity.
	 */
	private void runCommandListener() {
		/*
		 * Set up the command listener.
		 */
		CommandListener listener = new CommandListener(this.mMicrophoneButton);

		/*
		 * Associate our listener object with the speech recognizer.
		 */
		this.recognizer = SpeechRecognizer.createSpeechRecognizer(this);
		this.recognizer.setRecognitionListener(listener);

		/*
		 * Set up the recognizer intent for the type of speech recognition we
		 * will be needing.
		 */
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
				"com.marcrazysoftware.voicemessenger");

		/*
		 * Run the speech recognition, send the result to the dispatcher.
		 */
		recognizer.startListening(intent);
		resultDispatcher(listener.getResult());
	}

	private void resultDispatcher(List<String> results) {
		/*
		 * For now, we Toast the result for testing purposes.
		 */
		Toast.makeText(this, results.get(0), Toast.LENGTH_LONG).show();
	}

}
