package com.marcrazysoftware.voicemessenger;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
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
    }
    
}
