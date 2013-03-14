package com.marcrazysoftware.voicemessenger;

import java.util.List;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.widget.Button;

public class CommandListener implements RecognitionListener {

	private boolean hasResult;
	private List<String> result;
	private Button micButton;

	public CommandListener(final Button micButton) {
		this.micButton = micButton;
	}

	public boolean hasResult() {
		return this.hasResult;
	}

	public List<String> getResult() {
		return this.result;
	}

	@Override
	public void onBeginningOfSpeech() {
		/*
		 * Disable the button to avoid crashing the app from multiple
		 * listeners.
		 */
		this.micButton.setEnabled(false);
	}

	@Override
	public void onBufferReceived(byte[] buffer) {

	}

	@Override
	public void onEndOfSpeech() {
		/*
		 * The user is done talking, activate the mike button.
		 */
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
		/*
		 * Gather the results, set the member variable accordingly.
		 */
		this.result = results
				.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
	}

	@Override
	public void onRmsChanged(float rmsdB) {

	}

}
