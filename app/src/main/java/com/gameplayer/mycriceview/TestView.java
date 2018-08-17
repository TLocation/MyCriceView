package com.gameplayer.mycriceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/8/7 13:47
 * description：
 */

public class TestView extends View {
	public TestView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {


	}

	public TestView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Path path = new Path();
		path.addArc(new RectF(100,100,300,300),0,90);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(10);

		canvas.drawPath(path,paint);

		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(30);
		canvas.drawTextOnPath("123",path,0,0,paint);
	}
}
