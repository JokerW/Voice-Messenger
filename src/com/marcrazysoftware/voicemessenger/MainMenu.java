package com.marcrazysoftware.voicemessenger;

import java.util.List;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.widget.Button;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;

@SuppressWarnings("unused")
public class MainMenu extends Activity implements OnInitListener,
		RecognitionListener {

	private static final int TTS_CODE = 12345;

	private Button micButton;
	private Button sendMessageButton;
	private ListView messageListView;

	private TextToSpeech TTS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*
		 * TODO: Set the widgets if speech recognition is available.
		 */
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		if (this.TTS != null) {
			this.TTS.stop();
			this.TTS.shutdown();
		}
		super.onDestroy();
	}
	
	private void runSpeechRecognition() {
		
	}

	@Override
	public void onBeginningOfSpeech() {
		this.micButton.setEnabled(false);
	}

	@Override
	public void onBufferReceived(byte[] buffer) {

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

	@Override
	public void onInit(int arg0) {

	}

	private void resultDispatcher(List<String> results) {

	}

	private boolean isConnected() {
		PackageManager pm = this.getPackageManager();
		return false;
	}
	
	private boolean hasSpeechRecognition() {
		return false;
	}
}
