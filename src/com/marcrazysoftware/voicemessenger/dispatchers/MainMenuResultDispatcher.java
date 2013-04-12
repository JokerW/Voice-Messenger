package com.marcrazysoftware.voicemessenger.dispatchers;

import java.util.List;

public class MainMenuResultDispatcher extends StringMethods {

	private static final String sendSoundex = "S530";
	private static final String messageSoundex = "M220";
	private static final String textSoundex = "T230";

	private static final int soundexTolerance = 2;
	
	public boolean messageToSend = false;

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
		 * Soundexify the result string.
		 */
		List<String> soundexList = soundexify(result);

		/*
		 * We will keep track of a score to determine what to do with our string
		 * result.
		 */
		int score = 0;

		/*
		 * Calculate the score. We determine if the strings are similar enough
		 * to other keywords using the levenshtein distance algorithm. This will
		 * help determine the action we take next.
		 */
		for (String s : soundexList) {
			if (levenshteinDistance(s, sendSoundex) < soundexTolerance) {
				score++;
			} else if (levenshteinDistance(s, messageSoundex) < soundexTolerance) {
				score++;
			} else if (levenshteinDistance(s, textSoundex) < soundexTolerance) {
				score = 2;
			}

			if (score == 2) {
				messageToSend = true;
			}
		}
		
		/*
		 * TODO: Determine if there is a name to attach to the message we are sending.
		 */
	}
}
