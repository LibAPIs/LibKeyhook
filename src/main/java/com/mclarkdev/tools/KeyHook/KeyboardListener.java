package com.mclarkdev.tools.KeyHook;

import java.util.ArrayList;

/**
 * Implement this listener to handle global key press events.
 * 
 * @author Matt Clark
 *
 */
public interface KeyboardListener {

	public void onKeysChanged(ArrayList<Integer> keySet);
}
