package com.gameplayer.mycriceview;

import android.app.Application;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/8/8 18:00
 * description：
 */

public class App extends Application {

	//https://www.cnblogs.com/linux007/p/5785046.html
	@Override
	public void onCreate() {
		super.onCreate();
		ToastUtils.init(this);
		new LogUtils.LogUtilsBuilder()
				.setPrintClass(false)
				.setPrintLine(false);
	}
}
