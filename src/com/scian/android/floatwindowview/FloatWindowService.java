package com.scian.android.floatwindowview;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class FloatWindowService extends Service {

	private Handler handler = new Handler();

	private Timer timer;

	private FloatWindowManager mFlowWindowManager;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (mFlowWindowManager == null) {
			mFlowWindowManager = new FloatWindowManager();
		}
		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new RefreshTask(), 0, 500);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	class RefreshTask extends TimerTask {
		@Override
		public void run() {
			if (isHome() && !mFlowWindowManager.isShowFlowWindow()) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						mFlowWindowManager.showSmallWindow(getBaseContext());
					}
				});
			}
			else if (!isHome() && mFlowWindowManager.isShowFlowWindow()) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						mFlowWindowManager.hideSmallWindow(getBaseContext());
					}
				});

			}
		}
	}

	private boolean isHome() {

		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = activityManager.getRunningTasks(1);
		return getHomes().contains(list.get(0).topActivity.getPackageName());

	}

	private List<String> getHomes() {
		List<String> nameList = new ArrayList<String>();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		if (list != null) {
			for (ResolveInfo resolveInfo : list) {
				nameList.add(resolveInfo.activityInfo.packageName);
			}
		}

		return nameList;
	}

}
