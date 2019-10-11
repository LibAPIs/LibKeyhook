package com.mclarkdev.tools.KeyHook;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;

public class KeyboardHook {

	public interface KeyListener {

		public void onKeyUp(User32.KBDLLHOOKSTRUCT lParam);

		public void onKeyDown(User32.KBDLLHOOKSTRUCT lParam);
	}

	private static KeyboardHook keyboardHook;

	private KeyListener listener;

	private User32.HHOOK hHook;

	private KeyboardHook() {

		(new Thread(runThread)).start();
	}

	public void attachListener(KeyListener listener) {

		if (this.listener != null) {
			throw new IllegalArgumentException("listener already attached");
		}

		this.listener = listener;
	}

	private User32.LowLevelKeyboardProc callback = new User32.LowLevelKeyboardProc() {

		public LRESULT callback(int nCode, WPARAM wParam, User32.KBDLLHOOKSTRUCT lParam) {

			switch (wParam.intValue()) {
			case User32.WM_KEYUP:
				listener.onKeyUp(lParam);
				break;
			case User32.WM_KEYDOWN:
				listener.onKeyDown(lParam);
				break;
			}

			return User32.INSTANCE.CallNextHookEx(hHook, nCode, wParam, null);
		}
	};

	private Runnable runThread = new Runnable() {

		public void run() {

			// register the hook
			KeyboardHook.this.hHook = User32.INSTANCE.SetWindowsHookEx(//
					User32.WH_KEYBOARD_LL, callback, Kernel32.INSTANCE.GetModuleHandle(null), 0);

			// look for messages forever
			User32.MSG msg = new User32.MSG();
			while (!Thread.currentThread().isInterrupted()) {

				try {

					Thread.sleep(1);
					User32.INSTANCE.PeekMessage(msg, null, 0, 0, 0);
				} catch (Exception e) {
					break;
				}
			}
		}
	};

	public static KeyboardHook getInstance() {

		if (keyboardHook == null) {
			keyboardHook = new KeyboardHook();
		}

		return keyboardHook;
	}
}
