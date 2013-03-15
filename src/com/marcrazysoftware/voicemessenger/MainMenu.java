package com.marcrazysoftware.voicemessenger;

import java.util.List;
import java.util.Locale;

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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

@SuppressWarnings("unused")
public class MainMenu extends Activity implements OnInitListener,
		RecognitionListener {

	private static final int TTS_CODE = 12345;

	private ListView messageListView;
	private Button micButton;
	private Button sendMessageButton;

	private TextToSpeech TTS;

	private boolean hasComponents() {
		/*
		 * Check for voice recognition.
		 */
		PackageManager manager = getPackageManager();
		List<ResolveInfo> ri = manager.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		return ri.size() != 0;
	}

	private boolean hasSpeechRecognition() {
		return false;
	}

	private boolean isConnected() {
		PackageManager pm = this.getPackageManager();
		return false;
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
	public void onBeginningOfSpeech() {
		this.micButton.setEnabled(false);
	}

	@Override
	public void onBufferReceived(byte[] buffer) {

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
		} else {
			/*
			 * This device does not support voice recognition, inform the user.
			 */
			Toast.makeText(this,
					"Your device does not support voice recognition =(",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onDestroy() {
		if (this.TTS != null) {
			this.TTS.stop();
			this.TTS.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onEndOfSpeech() {
		this.micButton.setEnabled(true);
	}

	@Override
	public void onError(int error) {

	}

	@Override
	public void onEvent(int eventType, Bundle params) {

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

	@Override
	public void onPartialResults(Bundle partialResults) {

	}

	@Override
	public void onReadyForSpeech(Bundle params) {

	}

	@Override
	public void onResults(Bundle results) {
		resultDispatcher(results
				.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));
	}

	@Override
	public void onRmsChanged(float rmsdB) {

	}

	private void resultDispatcher(List<String> results) {

	}

	private void runSpeechRecognition() {

	}

	private void setWidgets() {
		this.messageListView = (ListView) findViewById(R.id.lvMessageView);
		this.micButton = (Button) findViewById(R.id.bMicrophone);
		this.sendMessageButton = (Button) findViewById(R.id.bSendMessage);

		this.messageListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});

		this.micButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}

		});

		this.sendMessageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}

		});
	}
}
