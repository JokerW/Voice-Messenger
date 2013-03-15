package com.marcrazysoftware.voicemessenger;

import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
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

@SuppressWarnings("unused")
public class MainMenu extends Activity implements OnInitListener {

	/*
	 * Class member variables
	 */
	private Button microphoneButton;
	private ListView messageList;

	private SpeechRecognizer recognizer;

	private TextToSpeech TTS;

	private static final int TTS_CODE = 12345;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_layout);
		setWidgets(hasRecognition());

		/*
		 * Check for TTS capabilities.
		 */
		Intent checkTTSIntent = new Intent();
		checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkTTSIntent, TTS_CODE);
	}

	/**
	 * Attach the widgets to code elements, and set click listeners.
	 */
	private void setWidgets(boolean isRecognitionAvailable) {
		/*
		 * Attach the views to code elements
		 */
		this.microphoneButton = (Button) findViewById(R.id.bMicrophone);
		this.messageList = (ListView) findViewById(R.id.lvMessageView);

		/*
		 * Only enable the button if voice recognition is available
		 */
		this.microphoneButton.setEnabled(isRecognitionAvailable);

		/*
		 * Click listener for the microphone button
		 */
		this.microphoneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				runCommandListener();
			}
		});

		/*
		 * Item click listener for the list view
		 */
		this.messageList.setOnItemClickListener(new OnItemClickListener() {

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
		CommandListener listener = new CommandListener(this.microphoneButton);

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

	/**
	 * Takes the necessary action based on the results of the voice recognition.
	 * 
	 * @param results
	 *            List containing the results of the voice recognition.
	 */
	private void resultDispatcher(List<String> results) {
		/*
		 * For now, we Toast the result for testing purposes.
		 */
		Toast.makeText(this, results.get(0), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = this.TTS.setLanguage(Locale.US);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == TTS_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				this.TTS = new TextToSpeech(this, this);
			} else {
				/*
				 * There is no data, install it.
				 */
				Intent installTTSIntent = new Intent();
				installTTSIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installTTSIntent);
			}
		}
	}

	/**
	 * Uses the Android TTS service, to speak the string it has been passed to
	 * the user.
	 * 
	 * @param sayThis
	 *            The string of text to be spoken
	 */
	private void speak(String sayThis) {
		this.TTS.speak(sayThis, TextToSpeech.QUEUE_FLUSH, null);
	}
}
