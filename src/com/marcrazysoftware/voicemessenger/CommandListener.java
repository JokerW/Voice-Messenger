package com.marcrazysoftware.voicemessenger;

import java.util.List;

import android.os.Bundle;
import android.speech.RecognitionListener;
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
		
	}

	@Override
	public void onBufferReceived(byte[] buffer) {
		
	}

	@Override
	public void onEndOfSpeech() {
		
	}

	@Override
	public void onError(int code) {
		
	}

	@Override
	public void onEvent(int arg0, Bundle arg1) {
		
	}

	@Override
	public void onPartialResults(Bundle arg0) {
		
	}

	@Override
	public void onReadyForSpeech(Bundle arg0) {
		
	}

	@Override
	public void onResults(Bundle results) {
		
	}

	@Override
	public void onRmsChanged(float arg0) {
		
	}

}
