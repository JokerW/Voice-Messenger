package com.marcrazysoftware.voicemessenger;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
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
	}

	/**
	 * Attachs the widgets to code elements for later use.
	 */
	private void setWidgets() {
		this.sendButton = (Button) findViewById(R.id.bSend);
		this.cancelButton = (Button) findViewById(R.id.bCancel);
		this.readBackButton = (Button) findViewById(R.id.bReadBack);
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

			}
		});

		/*
		 * Click listener for the cancel button
		 */
		this.cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}

		});

		/*
		 * Click listener for the read back button.
		 */
		this.readBackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

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
