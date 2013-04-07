package com.marcrazysoftware.voicemessenger;

import java.util.List;

public class MainMenuResultDispatcher extends StringMethods {
	
	private static final String sendSoundex = "S530";
	private static final String messageSoundex = "M220";
	private static final String textSoundex = "T230";

	/**
	 * Constructor for this class, serves as a dispatcher for the results of the
	 * voice recognition from the main menu.
	 * 
	 * We leave this separate simply for cleaner code, as well as a way around
	 * multiple inheritance with StringMethods.
	 * 
	 * @param result
	 *            String representing the result of voice recognition.
	 */
	public MainMenuResultDispatcher(String result) {
		/*
		 * Tokenize the result string
		 */
		List<String> resultList = tokenize(result);
		/*
		 * Replace each entry with its corresponding soundex code.
		 */
		for (int i = 0; i < resultList.size(); i++) {
			String value = resultList.remove(i);
			resultList.add(i, soundex(value));
		}
	}
}
