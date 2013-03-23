package com.marcrazysoftware.voicemessenger;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressWarnings("unused")
public class SendMessageActivity extends Activity implements OnInitListener {

	private Button sendButton;
	private Button cancelButton;
	private Button readBackButton;
	private Button speakButton;

	private EditText recipientBox;
	private EditText messageBodyBox;

	private TextToSpeech TTS;
	private SpeechRecognizer recognizer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_message_layout);

		/*
		 * Set up the text-to-speech service.
		 */
		this.TTS = new TextToSpeech(this, this);

		/*
		 * Set up the widgets.
		 */
		setWidgets();

		/*
		 * Set the components.
		 */
		setComponents();

	}

	private void setComponents() {
		this.recognizer = SpeechRecognizer.createSpeechRecognizer(this);
		this.recognizer.setRecognitionListener(new RecognitionListener() {

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
		});
	}

	/**
	 * Attachs the widgets to code elements for later use.
	 */
	private void setWidgets() {
		this.sendButton = (Button) findViewById(R.id.bSend);
		this.cancelButton = (Button) findViewById(R.id.bCancel);
		this.readBackButton = (Button) findViewById(R.id.bReadBack);
		this.speakButton = (Button) findViewById(R.id.bMessageSpeak);

		this.recipientBox = (EditText) findViewById(R.id.etRecipient);
		this.messageBodyBox = (EditText) findViewById(R.id.etMessageBody);

		setListeners();
	}

	/**
	 * Sets the OnClickListeners for the widgets in {@code this}.
	 */
	private void setListeners() {
		/*
		 * Click listener for the send button.
		 */
		this.sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Send Button Clicked", Toast.LENGTH_SHORT).show();
			}
		});

		/*
		 * Click listener for the cancel button
		 */
		this.cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * Return to the main menu.
				 */
				startActivity(new Intent(SendMessageActivity.this, MainMenu.class));
			}

		});

		/*
		 * Click listener for the read back button.
		 */
		this.readBackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Read Back Button Clicked", Toast.LENGTH_SHORT).show();
			}

		});

		/*
		 * Click listener for the read back button.
		 */
		this.speakButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * TODO: Set speech recognition.
				 */
				Toast.makeText(getBaseContext(), "Speak Button Clicked", Toast.LENGTH_SHORT).show();
			}

		});
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			this.TTS.setLanguage(Locale.US);
		} else {
			Toast.makeText(this, "FATAL: TTS Has Failed", Toast.LENGTH_LONG).show();
		}

	}

}
