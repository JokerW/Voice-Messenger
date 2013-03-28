package com.marcrazysoftware.voicemessenger;

import java.util.List;
import java.util.Locale;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class MainMenu extends Activity implements OnInitListener {

	private static final int TTS_CODE = 1234;

	private ListView messageListView;
	private Button micButton;
	private Button sendMessageButton;

	private TextToSpeech TTS;
	private SpeechRecognizer recognizer;

	/*
	 * For accessing the Application Preferences.
	 */
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_layout);

		/*
		 * Check for TTS data.
		 */
		Intent checkTTSIntent = new Intent();
		checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkTTSIntent, TTS_CODE);

		/*
		 * Check for voice recognition.
		 */
		if (hasComponents()) {
			/*
			 * The device has voice recognition, set up the widgets.
			 */
			setWidgets();

			/*
			 * Instantiate the voice recognizer.
			 */
			setRecognizer();

			/*
			 * Access the preference manager.
			 */
			this.prefs = PreferenceManager.getDefaultSharedPreferences(this);

			/*
			 * Start voice recognition if the alwaysListening pref is set to
			 * true.
			 */
			if (this.prefs.getBoolean("alwaysListening", false)) {
				runSpeechRecognition();
			}
			
		} else {
			/*
			 * This device does not support voice recognition, inform the user.
			 */
			Toast.makeText(this, "Your device does not support voice recognition =(", Toast.LENGTH_LONG).show();
		}

		/*
		 * If the preferences have the alwaysListening pref set to true, we run
		 * voice recognition from the start.
		 */
	}

	private boolean hasComponents() {
		/*
		 * Check for voice recognition.
		 */
		PackageManager manager = getPackageManager();
		List<ResolveInfo> rin = manager.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		return rin.size() != 0;
	}

	private void setWidgets() {
		this.messageListView = (ListView) findViewById(R.id.lvMessageView);
		this.micButton = (Button) findViewById(R.id.bMicrophone);
		this.sendMessageButton = (Button) findViewById(R.id.bSendMessage);

		this.messageListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Toast.makeText(getBaseContext(), "ListView Clicked", Toast.LENGTH_SHORT).show();

			}
		});

		this.micButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				runSpeechRecognition();
			}

		});

		this.sendMessageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * Start the sendMessageActivity.
				 */
				Intent intent = new Intent(MainMenu.this, SendMessageActivity.class);
				intent.putExtra("hasRecipient", false);
				startActivity(intent);
			}

		});
	}

	private void runSpeechRecognition() {
		if (isConnected()) {
			Intent recogIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			recogIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.marcrazysoftware.voicemessenger");
			recogIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			this.recognizer.startListening(recogIntent);
		} else {
			Toast.makeText(this, "Voice Messenger needs a data connection", Toast.LENGTH_LONG).show();
		}
	}

	private boolean isConnected() {
		/*
		 * Check for a data connection.
		 */
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnected();
	}

	private void setRecognizer() {
		this.recognizer = SpeechRecognizer.createSpeechRecognizer(this);
		this.recognizer.setRecognitionListener(new RecognitionListener() {

			@Override
			public void onBeginningOfSpeech() {
				micButton.setEnabled(false);

			}

			@Override
			public void onBufferReceived(byte[] buffer) {

			}

			@Override
			public void onEndOfSpeech() {
				micButton.setEnabled(true);

			}

			@Override
			public void onError(int error) {

			}

			@Override
			public void onEvent(int eventType, Bundle params) {

			}

			@Override
			public void onPartialResults(Bundle partialResults) {

			}

			@Override
			public void onReadyForSpeech(Bundle params) {
				micButton.setEnabled(false);
			}

			@Override
			public void onResults(Bundle results) {
				List<String> stringList = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
				resultDispatcher(stringList);

			}

			@Override
			public void onRmsChanged(float rmsdB) {

			}

		});
	}

	private void resultDispatcher(List<String> results) {
		/*
		 * For now, toast and speek the result for testing purposes.
		 */
		if (results.size() > 0) {
			this.TTS.speak(results.get(0), TextToSpeech.QUEUE_FLUSH, null);
			Toast.makeText(this, results.get(0), Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "Please Try Again", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onInit(int status) {
		/*
		 * Set the locale to US. Support for other languages will be added
		 * later.
		 */
		if (status == TextToSpeech.SUCCESS) {
			this.TTS.setLanguage(Locale.US);
		} else {
			Toast.makeText(this, "FATAL: TTS HAS FAILED", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TTS_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				this.TTS = new TextToSpeech(this, this);
			} else {
				Intent installIntent = new Intent();
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		 * Inflate the menu.
		 */
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_layout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.settings) {
			Intent intent = new Intent(this, PrefsActivity.class);
			startActivity(intent);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		if (this.TTS != null) {
			this.TTS.stop();
			this.TTS.shutdown();
		}
		if (this.recognizer != null) {
			this.recognizer.destroy();
		}
		super.onDestroy();
	}
}
