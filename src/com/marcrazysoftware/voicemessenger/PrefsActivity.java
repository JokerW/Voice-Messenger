package com.marcrazysoftware.voicemessenger;

import android.os.Bundle;
import android.preference.PreferenceActivity;

@SuppressWarnings("deprecation")
public class PrefsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * Load the preferences, simple as that.
		 */
		this.addPreferencesFromResource(R.xml.preferences_layout);
	}

}
