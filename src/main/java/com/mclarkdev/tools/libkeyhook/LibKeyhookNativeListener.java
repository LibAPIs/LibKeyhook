package com.mclarkdev.tools.libkeyhook;

import com.sun.jna.platform.win32.User32;

/**
 * LibKeyhook // LibKeyhookNativeListener
 * 
 * The NativeListener handles the native key press events from Windows.
 */
public interface LibKeyhookNativeListener {

	/**
	 * Called from native KeyUp listener.
	 * 
	 * @param lParam
	 */
	public void onKeyUp(User32.KBDLLHOOKSTRUCT lParam);

	/**
	 * Called from native KeyDown listener.
	 * 
	 * @param lParam
	 */
	public void onKeyDown(User32.KBDLLHOOKSTRUCT lParam);
}
