package com.mclarkdev.tools.KeyHook;

import java.util.ArrayList;
import java.util.HashSet;

import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;

/**
 * The KeyboardManager handles the key press events processed from the
 * NativeKeyHook and maintains a map of all the keys currently pressed.
 * Attaching a listener to this manager will cause each keyboard event to return
 * a map of all keys currently pressed.
 * 
 * @author Matt Clark
 *
 */
public class KeyboardManager {

	private static final KeyboardManager keyboardManager = new KeyboardManager();

	private final HashSet<Integer> keysDown = new HashSet<>();

	private KeyboardListener listener;

	private KeyboardManager() {

		NativeKeyHook.getHook()//
				.attachListener(keyListener);
	}

	public void setListener(KeyboardListener listener) {

		this.listener = listener;
	}

	private NativeKeyListener keyListener = new NativeKeyListener() {

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

	public static KeyboardManager getManager() {

		return keyboardManager;
	}
}
