package com.mclarkdev.tools.KeyHook;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;

/**
 * The NativeKeyHook sets up the Windows keyboard hooks and passes the events
 * through to the KeyboardManager.
 * 
 * @author Matt Clark
 *
 */
public class NativeKeyHook {

	private static NativeKeyHook keyboardHook;

	private NativeKeyListener listener;

	private User32.HHOOK hHook;

	private NativeKeyHook() {

		(new Thread(runThread)).start();
	}

	public void attachListener(NativeKeyListener listener) {

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
			NativeKeyHook.this.hHook = User32.INSTANCE.SetWindowsHookEx(//
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

	protected static NativeKeyHook getHook() {

		if (keyboardHook == null) {
			keyboardHook = new NativeKeyHook();
		}

		return keyboardHook;
	}
}
