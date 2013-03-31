package com.marcrazysoftware.voicemessenger;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Daniel Marchese
 */
@SuppressWarnings("unused")
public class SendMessageActivity extends Activity implements OnInitListener {

	private Button sendButton;
	private Button cancelButton;
	private Button readBackButton;
	private Button speakButton;

	private EditText recipientBox;
	private EditText messageBodyBox;

	private TextView stateText;

	private TextToSpeech TTS;
	private SpeechRecognizer recognizer;

	private State state;

	/*
	 * Message details.
	 */
	private String recipient;
	private String messageBody;

	/*
	 * Holds the shared preferences.
	 */
	private SharedPreferences prefs;

	/**
	 * Provides a method for representing the current state of the
	 * SendMessageActivity.
	 * 
	 * @author Daniel Marchese
	 * 
	 */
	private enum State {
		IDLE, LISTENING_RECIPIENT, LISTENING_BODY, READING_BACK, SENDING
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_message_layout);

		/*
		 * Get the shared preferences.
		 */
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);

		/*
		 * Set up the values based on the gathered intent.
		 */
		Intent intent = getIntent();
		this.recipient = intent.getStringExtra("recipient");

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

	/**
	 * Checks for the devices data connection.
	 * 
	 * @return network connectivity status.
	 */
	private boolean isConnected() {
		/*
		 * Check for a network connection, return whether or not there is one.
		 */
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnected();
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
				/*
				 * Re-enable speech recognition.
				 */
				speakButton.setEnabled(true);
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
				/*
				 * Disable the speech button so we avoid overlapping speech
				 * recognition.
				 */
				speakButton.setEnabled(false);
			}

			@Override
			public void onResults(Bundle results) {
				/*
				 * For now, toast and speak the result for testing purposes.
				 */
				List<String> text = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
				Toast.makeText(getBaseContext(), text.get(0), Toast.LENGTH_LONG).show();
				SendMessageActivity.this.TTS.speak(text.get(0), TextToSpeech.QUEUE_FLUSH, null);
			}

			@Override
			public void onRmsChanged(float rmsdB) {

			}
		});
	}

	/**
	 * Attaches the widgets to code elements for later use.
	 */
	private void setWidgets() {
		this.sendButton = (Button) findViewById(R.id.bSend);
		this.cancelButton = (Button) findViewById(R.id.bCancel);
		this.readBackButton = (Button) findViewById(R.id.bReadBack);
		this.speakButton = (Button) findViewById(R.id.bMessageSpeak);

		this.recipientBox = (EditText) findViewById(R.id.etRecipient);
		this.messageBodyBox = (EditText) findViewById(R.id.etMessageBody);

		this.stateText = (TextView) findViewById(R.id.tvCurrentState);

		/*
		 * We set the current state to idle, and edit from here as necessary.
		 */
		this.stateText.setText("Listening for Recipient");
		this.state = State.LISTENING_RECIPIENT;

		/*
		 * Set the text of the recipient box if there was a recipient string
		 * passed with the intent.
		 */
		if (this.recipient != null) {
			this.recipientBox.setText(this.recipient);
			this.stateText.setText("Listening for Message Body");
			this.state = State.LISTENING_BODY;
		}

		/*
		 * Set whether or not the textboxes are editable based on the
		 * "keyboardEnabled" preference.
		 */
		this.recipientBox.setEnabled(this.prefs.getBoolean("keyboardEnabled", true));
		this.messageBodyBox.setEnabled(this.prefs.getBoolean("keyboardEnabled", true));

		setListeners();
	}

	private void startVoiceRecognition() {
		/*
		 * Check for a network connection, if one is present, start voice
		 * recognition.
		 */
		if (isConnected()) {
			Intent recogIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			recogIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.marcrazysoftware.voicemessenger");
			recogIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			this.recognizer.startListening(recogIntent);
		} else {
			Toast.makeText(this, "Voice Messenger needs a data connection", Toast.LENGTH_LONG).show();
		}
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
				startVoiceRecognition();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		 * Inflate the options menu using the menu_layout.xml file.
		 */
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_layout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.settings) {
			/*
			 * If the settings item has been selected, pull up the settings
			 * screen.
			 */
			Intent intent = new Intent(this, PrefsActivity.class);
			startActivity(intent);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		/*
		 * Close out TTS to avoid memory leaks.
		 */
		if (this.TTS != null) {
			this.TTS.stop();
			this.TTS.shutdown();
		}

		super.onDestroy();
	}

}
