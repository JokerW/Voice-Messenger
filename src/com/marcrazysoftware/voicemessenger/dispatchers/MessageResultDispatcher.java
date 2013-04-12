package com.marcrazysoftware.voicemessenger.dispatchers;

import com.marcrazysoftware.voicemessenger.activities.SendMessageActivity.State;

public class MessageResultDispatcher extends StringMethods {
	
	public String text;

	public MessageResultDispatcher(State state, String result) {
		/*
		 * This does different things depending on the state of
		 * SendMessageActivity.java.
		 */
		switch (state) {
		case LISTENING_RECIPIENT:
			recipientDispatcher(result);
			break;
		case LISTENING_BODY:
			bodyDispatcher(result);
			break;
		case LISTENING_COMMAND:
			commandDispatcher(result);
			break;
		default:
		}
	}

	private void recipientDispatcher(String result) {
		/*
		 * TODO: Grab the soundex codes for the names, and check them against
		 * the list of contacts with phone numbers on the phone.
		 */
	}

	private void bodyDispatcher(String result) {
		/*
		 * Simply set the meber variable to the result string.
		 */
		this.text = result;
	}

	private void commandDispatcher(String result) {
		/*
		 * TODO: This is where we will use Soundex and Levenshtein distance
		 * routines to determine what to do.
		 */
	}

}
