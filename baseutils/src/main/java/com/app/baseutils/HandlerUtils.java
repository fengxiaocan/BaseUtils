package com.app.baseutils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * <pre>
 *
 *
 *     time  : 16/11/01
 *     desc  : Handler相关工具类
 * </pre>
 */
public final class HandlerUtils {

	private static Handler sHandler;

	private HandlerUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}


	/**
	 * Send message.
	 *
	 * @param message the message
	 */
	public static void sendMessage(Message message) {
		getHandler().sendMessage(message);
	}

	/**
	 * Send empty message.
	 *
	 * @param what the what
	 */
	public static void sendEmptyMessage(int what) {
		getHandler().sendEmptyMessage(what);
	}

	/**
	 * Send empty message delayed.
	 *
	 * @param what the what
	 * @param delayMillis the delay millis
	 */
	public static void sendEmptyMessageDelayed(int what,long delayMillis) {
		getHandler().sendEmptyMessageDelayed(what,delayMillis);
	}

	/**
	 * Send empty message at time.
	 *
	 * @param what the what
	 * @param delayMillis the delay millis
	 */
	public static void sendEmptyMessageAtTime(int what,long delayMillis) {
		getHandler().sendEmptyMessageAtTime(what,delayMillis);
	}

	/**
	 * Send message delayed.
	 *
	 * @param msg the msg
	 * @param delayMillis the delay millis
	 */
	public static void sendMessageDelayed(Message msg,long delayMillis) {
		getHandler().sendMessageDelayed(msg,delayMillis);
	}

	/**
	 * Send message at time.
	 *
	 * @param msg the msg
	 * @param delayMillis the delay millis
	 */
	public static void sendMessageAtTime(Message msg,long delayMillis) {
		getHandler().sendMessageAtTime(msg,delayMillis);
	}

	/**
	 * Post.
	 *
	 * @param runnable the runnable
	 */
	public static void post(Runnable runnable) {
		getHandler().post(runnable);
	}

	/**
	 * Post delayed.
	 *
	 * @param runnable the runnable
	 * @param delayMillis the delay millis
	 */
	public static void postDelayed(Runnable runnable,long delayMillis) {
		getHandler().postDelayed(runnable,delayMillis);
	}

	/**
	 * Post at time.
	 *
	 * @param runnable the runnable
	 * @param delayMillis the delay millis
	 */
	public static void postAtTime(Runnable runnable,long delayMillis) {
		getHandler().postAtTime(runnable,delayMillis);
	}

	/**
	 * Has messages boolean.
	 *
	 * @param what the what
	 * @return the boolean
	 */
	public static boolean hasMessages(int what) {
		return getHandler().hasMessages(what);
	}

	/**
	 * Obtain message message.
	 *
	 * @return the message
	 */
	public static Message obtainMessage() {
		return getHandler().obtainMessage();
	}

	/**
	 * Obtain message message.
	 *
	 * @param what the what
	 * @return the message
	 */
	public static Message obtainMessage(int what) {
		return getHandler().obtainMessage(what);
	}

	/**
	 * Get handler handler.
	 *
	 * @return the handler
	 */
	public static Handler getHandler() {
	    synchronized (Handler.class) {
            if (sHandler == null) {
				synchronized (Handler.class) {
					sHandler = new Handler(Utils.getContext().getMainLooper());
				}
            }
            return sHandler;
        }
	}

	/**
	 * Remove callbacks.
	 *
	 * @param runnable the runnable
	 */
	public static void removeCallbacks(Runnable runnable) {
		getHandler().removeCallbacks(runnable);
	}

	/**
	 * Remove callbacks.
	 *
	 * @param r the r
	 * @param token the token
	 */
	public static void removeCallbacks(Runnable r,Object token) {
        getHandler().removeCallbacks(r,token);
	}

	/**
	 * Remove messages.
	 *
	 * @param what the what
	 */
	public static void removeMessages(int what) {
        getHandler().removeMessages(what);
	}

	/**
	 * Remove messages.
	 *
	 * @param what the what
	 * @param object the object
	 */
	public static void removeMessages(int what,Object object) {
        getHandler().removeMessages(what,object);
	}

}
