package com.mclarkdev.tools.libkeyhook;

import java.util.ArrayList;

/**
 * LibKeyhook // LibKeyhookListener
 * 
 * Implement this listener to handle global key press events.
 */
public interface LibKeyhookListener {

	/**
	 * Called when new Keyboard events are received.
	 * 
	 * Returns an array containing a list of all key codes detected.
	 * 
	 * @param keySet keys pressed
	 */
	public void onKeysChanged(ArrayList<Integer> keySet);
}
