package com.scian.android.floatwindowview;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class FloatWindowManager {

	private SmallFloatWindowView mSmallWindow;
	private BigFloatWindowView mBigSmallWindow;
	private WindowManager mWindowManger;
	private ActivityManager mActivityManager;
	private WindowManager.LayoutParams mSmalLayoutParams;

	public boolean isShowFlowWindow() {
		return mSmallWindow != null && mSmallWindow.isShow();
	}

	public void showSmallWindow(Context context) {

		if (mSmallWindow == null) {
			mSmallWindow = new SmallFloatWindowView(context);
			WindowManager windowManager = getWindowManager(context);
			int screenHeight = windowManager.getDefaultDisplay().getHeight();
			if (mSmalLayoutParams == null) {
				mSmalLayoutParams = new WindowManager.LayoutParams();
				mSmalLayoutParams.type = LayoutParams.TYPE_PHONE;
				mSmalLayoutParams.format = PixelFormat.RGBA_8888;
				mSmalLayoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;
				mSmalLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
				mSmalLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
				mSmalLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
				mSmalLayoutParams.x = 0;
				mSmalLayoutParams.y = screenHeight / 2;

				mSmallWindow.setParams(mSmalLayoutParams);
				windowManager.addView(mSmallWindow.getView(), mSmalLayoutParams);
			}
		}

		mSmallWindow.show();
	}

	public void hideSmallWindow(Context context) {
		if (mSmallWindow != null) {
			// WindowManager windowManager = getWindowManager(context);
			// if (mSmallWindow.getView() != null)
			// windowManager.removeView(mSmallWindow.getView());
			mSmallWindow.hide();
		}
	}

	private WindowManager getWindowManager(Context context) {
		if (mWindowManger == null) {
			mWindowManger = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		}
		return mWindowManger;
	}

	private ActivityManager getActivityManager(Context context) {
		if (mActivityManager == null) {
			mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		}
		return mActivityManager;
	}

}
