package com.mclarkdev.tools.libkeyhook;

import java.util.ArrayList;
import java.util.HashSet;

import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;

/**
 * LibKeyhook // LibKeyhookManager
 * 
 * The KeyboardManager handles the key press events processed from the
 * NativeKeyHook and maintains a map of all the keys currently pressed.
 * Attaching a listener to this manager will cause each keyboard event to return
 * a map of all keys currently pressed.
 */
public class LibKeyhookManager {

	private static final LibKeyhookManager keyboardManager = new LibKeyhookManager();

	/**
	 * Returns a static instance of the KeyboardManager.
	 * 
	 * @return the keyboard manager
	 */
	public static LibKeyhookManager getManager() {

		return keyboardManager;
	}

	private final HashSet<Integer> keysDown = new HashSet<>();

	private LibKeyhookListener listener;

	private LibKeyhookManager() {

		LibKeyhookNativeHook.getHook()//
				.attachListener(keyListener);
	}

	/**
	 * Set the listener which will receive keyboard events.
	 * 
	 * @param listener the listener
	 */
	public void setListener(LibKeyhookListener listener) {

		this.listener = listener;
	}

	private LibKeyhookNativeListener keyListener = new LibKeyhookNativeListener() {

		public void onKeyUp(KBDLLHOOKSTRUCT event) {

			ArrayList<Integer> keys;
			synchronized (keysDown) {

				keysDown.remove(Integer.valueOf(event.vkCode));
				keys = new ArrayList<>(keysDown);
			}

			if (listener != null) {
				listener.onKeysChanged(keys);
			}
		}

		public void onKeyDown(KBDLLHOOKSTRUCT event) {

			ArrayList<Integer> keys;
			synchronized (keysDown) {

				keysDown.add(Integer.valueOf(event.vkCode));
				keys = new ArrayList<>(keysDown);
			}

			if (listener != null) {
				listener.onKeysChanged(keys);
			}
		}
	};
}
