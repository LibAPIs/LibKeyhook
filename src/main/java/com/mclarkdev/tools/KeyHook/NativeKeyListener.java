package com.mclarkdev.tools.KeyHook;

import com.sun.jna.platform.win32.User32;

/**
 * The NativeKeyListener handles the native key press events from Windows.
 * 
 * @author Matt Clark
 *
 */
public interface NativeKeyListener {

	public void onKeyUp(User32.KBDLLHOOKSTRUCT lParam);

	public void onKeyDown(User32.KBDLLHOOKSTRUCT lParam);
}
