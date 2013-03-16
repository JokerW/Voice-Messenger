package com.marcrazysoftware.voicemessenger;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

@SuppressWarnings("unused")
public class SendMessageActivity extends Activity {

	private Button sendButton;
	private Button cancelButton;
	private Button readBackButton;

	private EditText recipientBox;
	private EditText messageBodyBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_message_layout);
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
		this.sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		this.cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}

		});

		this.readBackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}

		});
	}

}
