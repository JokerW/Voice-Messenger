package com.marcrazysoftware.voicemessenger.dispatchers;

import com.marcrazysoftware.voicemessenger.activities.SendMessageActivity.State;

public class MessageResultDispatcher extends StringMethods {

	public MessageResultDispatcher(State state) {
		/*
		 * This does different things depending on the state of
		 * SendMessageActivity.java.
		 */
		switch (state) {
		case LISTENING_RECIPIENT:
			break;
		case LISTENING_BODY:
			break;
		case LISTENING_COMMAND:
			break;
		default:
		}
	}

}
