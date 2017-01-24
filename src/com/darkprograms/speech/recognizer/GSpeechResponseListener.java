package com.darkprograms.speech.recognizer;

import com.darkprograms.speech.recognizer.GoogleResponse;

/**
 * Response listeners for URL connections.
 * @author Skylion
 *
 */
public interface GSpeechResponseListener {
	
	public void onResponse(GoogleResponse gr);
	
}
