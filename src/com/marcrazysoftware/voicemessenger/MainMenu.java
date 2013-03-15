package com.marcrazysoftware.voicemessenger;

import java.util.List;
import java.util.Locale;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognitionListener;
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
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class MainMenu extends Activity implements OnInitListener {

	private static final int TTS_CODE = 12345;

	private ListView messageListView;
	private Button micButton;
	private Button sendMessageButton;

	private TextToSpeech TTS;
	private SpeechRecognizer recognizer;

	private boolean hasComponents() {
		/*
		 * Check for voice recognition.
		 */
		PackageManager manager = getPackageManager();
		List<ResolveInfo> ri = manager.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		return ri.size() != 0;
	}

	private boolean isConnected() {
		/*
		 * Check for a data connection.
		 */
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnected();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TTS_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				this.TTS = new TextToSpeech(this, this);
			} else {
				Intent installIntent = new Intent();
				installIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}

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
		} else {
			/*
			 * This device does not support voice recognition, inform the user.
			 */
			Toast.makeText(this,
					"Your device does not support voice recognition =(",
					Toast.LENGTH_LONG).show();
		}
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

			}

			@Override
			public void onResults(Bundle results) {
				List<String> stringList = results
						.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
				resultDispatcher(stringList);

			}

			@Override
			public void onRmsChanged(float rmsdB) {

			}

		});
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

	@Override
	public void onInit(int status) {
		/*
		 * Set the locale to US. Support for other languages will be added
		 * later.
		 */
		if (status == TextToSpeech.SUCCESS) {
			this.TTS.setLanguage(Locale.US);
		} else {
			Toast.makeText(this, "FATAL: TTS HAS FAILED", Toast.LENGTH_LONG)
					.show();
		}
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

	private void runSpeechRecognition() {
		if (isConnected()) {
			Intent recogIntent = new Intent(
					RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			recogIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
					"com.marcrazysoftware.voicemessenger");
			recogIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			this.recognizer.startListening(recogIntent);
		} else {
			Toast.makeText(this, "Voice Messenger needs a data connection",
					Toast.LENGTH_LONG).show();
		}
	}

	private void setWidgets() {
		this.messageListView = (ListView) findViewById(R.id.lvMessageView);
		this.micButton = (Button) findViewById(R.id.bMicrophone);
		this.sendMessageButton = (Button) findViewById(R.id.bSendMessage);

		this.messageListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getBaseContext(), "ListView Clicked",
						Toast.LENGTH_SHORT).show();

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
				Intent intent = new Intent(MainMenu.this,
						SendMessageActivity.class);
				intent.putExtra("hasRecipient", false);
				startActivity(intent);
			}

		});
	}
}
