package com.gameplayer.mycriceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/7/27 18:14
 * description：
 */

public class MyImageView extends android.support.v7.widget.AppCompatImageView {
	public MyImageView(Context context) {
		this(context, null);
	}

	public MyImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		canvas.restore();
		int width = getWidth();
		int height = getHeight();
		double v = width * 0.098;
		double h = height * 0.098;
		float radios = (float) ((width - v*2) / 2);
		float x = (float) (v + radios);
		float y = (float) (h+radios);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);

		paint.setStrokeWidth(5);
		paint.setColor(Color.RED);
		Log.d("radios===>", "radios===>"+radios);

		canvas.drawCircle(x,y,radios,paint);
		canvas.drawRect(200,200,300,300,paint);
	}
}
