package com.marcrazysoftware.voicemessenger;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;

@SuppressWarnings("deprecation")
public class PrefsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * I'm aware that this is deprecated, I still use it to keep backwards
		 * compatibility. When more people move to ICS, this will be done the
		 * recommended way with fragments.
		 */
		addPreferencesFromResource(R.xml.preferences_layout);
	}

}
