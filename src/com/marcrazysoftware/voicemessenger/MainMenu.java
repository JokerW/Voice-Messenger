package com.marcrazysoftware.voicemessenger;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;

public class MainMenu extends Activity {

	/*
	 * Class member variables
	 */
	private Button mMicrophoneButton;
	private ListView mMessageList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_layout);
		setWidgets();
	}

	private void setWidgets() {
		this.mMicrophoneButton = (Button) findViewById(R.id.bMicrophone);
		this.mMessageList = (ListView) findViewById(R.id.svMessageView);

		this.mMicrophoneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Mic Button Clicked",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		this.mMessageList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
