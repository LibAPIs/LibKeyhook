package com.mclarkdev.tools.libkeyhook;

import java.util.ArrayList;

/**
 * Implement this listener to handle global key press events.
 * 
 * @author Matt Clark
 *
 */
public interface LibKeyhookListener {

	public void onKeysChanged(ArrayList<Integer> keySet);
}
