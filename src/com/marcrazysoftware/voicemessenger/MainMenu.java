package com.marcrazysoftware.voicemessenger;

import java.util.List;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.app.Activity;

@SuppressWarnings("unused")
public class MainMenu extends Activity implements OnInitListener,
		RecognitionListener {

	@Override
	public void onBeginningOfSpeech() {

	}

	@Override
	public void onBufferReceived(byte[] buffer) {

	}

	@Override
	public void onEndOfSpeech() {

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

	}

	@Override
	public void onRmsChanged(float rmsdB) {

	}

	@Override
	public void onInit(int arg0) {

	}

	private void resultDispatcher(List<String> results) {

	}

}
