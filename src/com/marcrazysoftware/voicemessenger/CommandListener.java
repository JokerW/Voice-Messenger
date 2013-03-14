package com.marcrazysoftware.voicemessenger;

import java.util.List;

import android.os.Bundle;
import android.speech.RecognitionListener;

public class CommandListener implements RecognitionListener {
	
	public boolean hasResult;
	public List<String> result;

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
	public void onError(int arg0) {
		
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
