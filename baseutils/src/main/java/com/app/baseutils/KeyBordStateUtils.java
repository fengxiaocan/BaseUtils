package com.app.baseutils;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;

import com.app.log.LogUtils;


/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 26/10/18
 * @desc ...
 */
public class KeyBordStateUtils {
	private View mDecorView;
	private onKeyBordStateListener listener;
	private int windowBottom = -1;
	private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
		@Override
		public void onGlobalLayout() {
			calKeyBordState();
		}
	};
	
	public KeyBordStateUtils(Window window) {
		mDecorView = window.getDecorView();
		mDecorView.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
	}
	
	private void calKeyBordState() {
		int[] ints = new int[2];
		mDecorView.getLocationInWindow(ints);
		if (windowBottom <= 0) {
			windowBottom = ints[1];
		}
		WindowInsets rootWindowInsets = mDecorView.getRootWindowInsets();
		if (rootWindowInsets != null) {
			LogUtils.e("noah",rootWindowInsets.getSystemWindowInsetBottom());
		}
		boolean mIsKeyboardShow = windowBottom < ints[1];
		if (mIsKeyboardShow) {
			int keyboard_height = Math.abs(ints[1] - windowBottom);//键盘高度
			if (listener != null) {
				listener.onSoftKeyBoardShow(keyboard_height);
			}
		}
		else {
			if (listener != null) {
				listener.onSoftKeyBoardHide();
			}
		}
	}
	
	public void addOnKeyBordStateListener(onKeyBordStateListener listener) {
		this.listener = listener;
	}
	
	public void removeOnKeyBordStateListener() {
		if (mDecorView != null && mOnGlobalLayoutListener != null) {
			mDecorView.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
		}
		if (listener != null) {
			listener = null;
		}
	}
	
	public interface onKeyBordStateListener {
		void onSoftKeyBoardShow(int keyboardHeight);
		
		void onSoftKeyBoardHide();
	}
}
