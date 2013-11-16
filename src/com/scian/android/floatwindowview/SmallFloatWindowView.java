package com.scian.android.floatwindowview;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SmallFloatWindowView implements OnTouchListener {

	private WindowManager mWindowManager;
	private WindowManager.LayoutParams mParams;
	private View mViewBody;
	private ImageView mButton;

	public int mHeight;
	public int mWidth;

	private float mEnterX;
	private float mEnterY;

	private float mViewX;
	private float mViewY;

	private Context mContext;

	private View mParentView;

	public View getView() {
		return mParentView;
	}

	public SmallFloatWindowView(Context context) {
		mContext = context;
		create(context);
	}

	public boolean isShow() {
		return mParentView.getVisibility() == View.VISIBLE;
	}

	public void setParams(WindowManager.LayoutParams para) {
		mParams = para;
	}

	private void create(Context context) {
		mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		mParentView = LayoutInflater.from(context).inflate(R.layout.layout_flow_window_small, null);
		mViewBody = mParentView.findViewById(R.id.flow_window_small_body);
		mButton = (ImageView) mParentView.findViewById(R.id.image_btn_call);
		mHeight = mViewBody.getHeight();
		mWidth = mViewBody.getWidth();
		mParentView.setOnTouchListener(this);
	}

	public void hide() {
		if (mParentView != null) {
			mParentView.setVisibility(View.GONE);
		}
	}

	public void show() {
		if (mParentView != null) {
			mParentView.setVisibility(View.VISIBLE);
		}
	}

	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// switch (event.getAction()) {
	// case MotionEvent.ACTION_DOWN: {
	// mEnterX = event.getX();
	// mEnterY = event.getY();
	// getViewLocation(event.getRawX(), event.getRawY());
	// break;
	// }
	// case MotionEvent.ACTION_MOVE: {
	// getViewLocation(event.getRawX(), event.getRawY());
	// updateViePosition();
	// break;
	// }
	// case MotionEvent.ACTION_UP: {
	// break;
	// }
	// default:
	// break;
	// }
	// return super.onTouchEvent(event);
	// }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				mEnterX = event.getX();
				mEnterY = event.getY();
				getViewLocation(event.getRawX(), event.getRawY());
				break;
			}
			case MotionEvent.ACTION_MOVE: {
				getViewLocation(event.getRawX(), event.getRawY());
				updateViePosition();
				break;
			}
			case MotionEvent.ACTION_UP: {
				break;
			}
			default:
				break;
		}
		return true;
	}

	private void getViewLocation(float x, float y) {
		mViewX = x;
		mViewY = y - getStatusBarHeight(mContext);
	}

	private void updateViePosition() {
		mParams.x = (int) (mViewX - mEnterX);
		mParams.y = (int) (mViewY - mEnterY);
		mWindowManager.updateViewLayout(getView(), mParams);
	}

	private int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object object = null;
		Field field = null;
		int heightInDip = 0;
		int heightInPixel = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			object = c.newInstance();
			field = c.getField("status_bar_height");
			heightInDip = Integer.parseInt(field.get(object).toString());
			if (context != null)
				heightInPixel = context.getResources().getDimensionPixelSize(heightInDip);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return heightInPixel;
	}

}
