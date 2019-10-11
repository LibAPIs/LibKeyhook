package com.mclarkdev.tools.KeyHook;

import java.util.ArrayList;
import java.util.HashSet;

import com.mclarkdev.tools.KeyHook.KeyboardHook.KeyListener;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;

public class KeyboardManager {

	public interface EventListener {

		public void onEvent(ArrayList<Integer> keyMap);
	}

	private static KeyboardManager keyboardManager;

	private final HashSet<Integer> keysDown;

	private EventListener listener;

	private KeyboardManager() {

		keysDown = new HashSet<>();
		KeyboardHook.getInstance().attachListener(keyListener);
	}

	public void setListener(EventListener listener) {

		this.listener = listener;
	}

	private KeyListener keyListener = new KeyListener() {

		public void onKeyUp(KBDLLHOOKSTRUCT event) {

			ArrayList<Integer> keys;
			synchronized (keysDown) {

				keysDown.remove(Integer.valueOf(event.vkCode));
				keys = new ArrayList<>(keysDown);
			}

			listener.onEvent(keys);
		}

		public void onKeyDown(KBDLLHOOKSTRUCT event) {

			ArrayList<Integer> keys;
			synchronized (keysDown) {

				keysDown.add(Integer.valueOf(event.vkCode));
				keys = new ArrayList<>(keysDown);
			}

			listener.onEvent(keys);
		}
	};

	public static KeyboardManager getInstance() {

		if (keyboardManager == null) {
			keyboardManager = new KeyboardManager();
		}

		return keyboardManager;
	}
}
