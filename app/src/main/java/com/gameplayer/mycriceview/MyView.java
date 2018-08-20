package com.gameplayer.mycriceview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.round;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/7/27 14:30
 * description：
 * <p>
 * <p>
 * 关于文字选择半径
 * 应该是  文字宽度&高的一半选取偏移量
 * 同时  给定 绘制方向      文字绘制靠后
 */

public class MyView extends View implements Checkable {


	private final int COLOR_CHECK_INTER = Color.parseColor("#02FD60");
	private final int COLOR_CHECK_OUTSIDE = Color.parseColor("#5EAEFF");
	private static final int PART_ONE = 1;

	private static final int PART_TWO = 2;

	private static final int PART_THREE = 3;

	private static final int PART_FOUR = 4;

	/**
	 * 宽高
	 */
	private int width;
	private int height;

	/**
	 * 圆心点
	 */
	private float criceX;
	private float criceY;

	private Paint backPaint;


	/**
	 * 半径
	 */
	private float radios;
	private Paint paint;

	private Paint pointPaint;

	private Paint textPaint;


	private Paint checkPaint;

	private Paint arcPaint;
	private float angle;

	private List<Float> lineX;
	private List<Float> lineY;
	private MotionEvent mMotionEvent;


	private List<String> data;


	/**
	 * 存储24节气文字的坐标
	 */

	private List<RadioData> radioData;

	private float aFloat;
	private final float PI = 3.1415f;

	public MyView(Context context) {
		this(context, null);
	}

	public MyView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		data = new ArrayList<>();
		String[] stringArray = context.getResources().getStringArray(R.array.data);
		data.addAll(Arrays.asList(stringArray));
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(3);
		lineX = new ArrayList<>();
		lineY = new ArrayList<>();
		radioData = new ArrayList<>();
		pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pointPaint.setColor(Color.WHITE);
		pointPaint.setStrokeCap(Paint.Cap.ROUND);
		pointPaint.setStrokeWidth(30);
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(40);
		setBackgroundColor(Color.parseColor("#14345E"));
		checkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		checkPaint.setColor(COLOR_CHECK_OUTSIDE);
		checkPaint.setStyle(Paint.Style.FILL);

		arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		arcPaint.setColor(Color.RED);
		arcPaint.setStyle(Paint.Style.STROKE);
		arcPaint.setStrokeWidth(10);
		backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		backPaint.setColor(Color.BLUE);


	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		lineY.clear();
		lineX.clear();
		radioData.clear();
		width = getWidth();
		height = getHeight();

		int top = getPaddingTop();
		int bottom = getPaddingBottom();
		int left = getPaddingLeft();
		int right = getPaddingRight();
		radios = (width - (left + right)) / 2;
		criceX = left + radios;
		criceY = top + radios;
		radios = radios - 100;
		canvas.drawRect(new RectF(0, 0, height, radios + top + 100), backPaint);
		canvas.drawCircle(criceX, criceY, radios + 100 + left, backPaint);
		canvas.drawCircle(criceX, criceY, radios, paint);

		angle = 360 / 24;
		float xAngle = angle;
		float xradios = radios + 20;
		float pointMar = radios / 13;
		float m = pointMar * 6;
		boolean add = false;
		int index = 0;
		boolean isfoce = true;
		String text = "夏至";
		float v3 = textPaint.measureText(text);
		//滑动的角度
		float ange = (float) (180 * v3 / (PI * (radios + 50)));
		float arcradios = 0;


		for (int i = 0; i < data.size(); i++) {
			index++;
			String message = data.get(i);
			Rect rect = new Rect();
			textPaint.getTextBounds(message, 0, message.length(), rect);
			/**
			 *  内圆半径+20偏移量+文字高度+文字偏移量/2
			 *  计算x轴和y轴
			 */
			float raidoX = getRaidoX(radios + 50 + (rect.height()) / 2, xAngle);
			float raidoY = getRaidoY(radios + 50 + (rect.height()) / 2, xAngle);
			float checkRadios = rect.width() / 2;
			checkPaint.setColor(COLOR_CHECK_OUTSIDE);
			canvas.drawCircle(raidoX, raidoY, checkRadios + 20, checkPaint);
			checkPaint.setColor(COLOR_CHECK_INTER);
			canvas.drawCircle(raidoX, raidoY, checkRadios + 10, checkPaint);
			float x = (float) (criceX + xradios * Math.cos(xAngle * PI / 180));
			float y = (float) (criceY + xradios * Math.sin(xAngle * PI / 180));
			float pointx = (float) (criceX + m * Math.cos(xAngle * PI / 180));
			float pointY = (float) (criceY + m * Math.sin(xAngle * PI / 180));
			lineX.add(x);
			lineY.add(y);
			Path path = new Path();
			RectF rectF = new RectF(criceX - radios - 50, criceY - radios - 50, criceX + radios + 50, criceY + radios + 50);
			float textStartAnge = xAngle - ange / 2;
			path.addArc(rectF, textStartAnge, ange);
			path.close();
			LogUtils.d("startAnge===>" + textStartAnge + "\n ange===>" + ange);

			/**
			 * 第二个参数  如果大于360  则开始角度重新开始
			 */
			RadioData radioData = new RadioData(textStartAnge, (textStartAnge + ange) > 360 ? (textStartAnge + ange - 360) : (textStartAnge + ange), data.get(i));
			if ((textStartAnge + ange) > 360) {
				radioData.setFlag(true);
			}
			LogUtils.d("radiodata===>" + radioData.toString());
			this.radioData.add(radioData);
			canvas.drawPath(path, paint);
//			canvas.rotate(90);
			canvas.save();
			canvas.rotate(180, raidoX, raidoY);

			canvas.drawTextOnPath(data.get(i), path, 0, 0, textPaint);
			canvas.restore();
			/**
			 * 50  偏移量  内圆半径到文字底部的偏移量
			 */
			aFloat = radios + 50 + rect.height();
			canvas.drawCircle(criceX, criceY, aFloat, paint);
			canvas.drawLine(criceX, criceY, x, y, paint);
			canvas.drawPoint(pointx, pointY, pointPaint);
			xAngle += angle;
			arcradios += angle;
			if (add) {
				m += pointMar;
			} else {
				m -= pointMar;
			}
			if (index == 5 && isfoce) {
				index = 0;
				m = radios;
				isfoce = false;
			} else {
//				add = true;
			}
			if (index == 12) {
				add = false;
				m = radios;
			}

		}
		canvas.drawArc(new RectF(criceX - radios, criceY - radios, radios + criceX, radios + criceY), 135, 90, false, arcPaint);


//		textPaint.measureText()
//		Path path = new Path();
//		path.addCircle(criceX,criceY,radios+50, Path.Direction.CW);
//		canvas.drawTextOnPath(builder.toString(),path,0,0,textPaint);
//
//		int x = (int) (300 + 150 * cos(90 * 3.14 / 180));
//
//		int y = (int) (300 + 150 * sin(90 * 3.14 / 180));
//		canvas.drawLine(300, 300, x, y, paint);
//		int x1 = (int) (300 + 150 * cos(45 * 3.14 / 180));
//
//		int y1 = (int) (300 + 150 * sin(45 * 3.14 / 180));
//		canvas.drawLine(300, 300, x, y, paint);
//		canvas.drawLine(300, 300, x1, y1, paint);
	}

	/**
	 * 获取圆内一点的x点
	 *
	 * @param radios
	 * @param ange
	 * @return
	 */
	private float getRaidoX(float radios, float ange) {
		float x = (float) (criceX + radios * Math.cos(ange * PI / 180));
		return x;
	}

	/**
	 * 获取圆内一点的y点
	 *
	 * @param radios
	 * @param ange
	 * @return
	 */
	private float getRaidoY(float radios, float ange) {
		float y = (float) (criceY + radios * Math.sin(ange * PI / 180));
		return y;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		double alfa = 0;
		float startArc = 0;

		double distance = Math.pow(x - criceX, 2) + Math.pow(y - criceY, 2);
		if (distance < Math.pow(aFloat, 2) && distance > Math.pow(radios, 2)) {
			float xradios = radios + 20;
			float x1 = (float) (criceX + xradios * Math.cos(360 * PI / 180));
			float y1 = (float) (criceY + xradios * Math.sin(360 * PI / 180));

			alfa = getRotationBetweenLines(criceX, criceY, x, y);
			LogUtils.d("夹角===》" + alfa);

//			ToastUtils.showShort("成功");
//			int i = touchOnWhichPart(event);
//			switch (i) {
//				case PART_ONE:
//					alfa = Math.atan2(x - aFloat, aFloat - y) * 180 / PI;
//					break;
//				case PART_TWO:
//					alfa = Math.atan2(y - aFloat, x - aFloat) * 180 / PI + 90;
//					break;
//				case PART_THREE:
//					alfa = Math.atan2(aFloat - x, y - aFloat) * 180 / PI + 180;
//					break;
//				case PART_FOUR:
//					alfa = Math.atan2(aFloat - y, aFloat - x) * 180 / PI + 270;
//					break;
//
//				default:
//					break;
//			}
//			alfa-=8;
			if (alfa - 90 > 0) {
				alfa -= 90;
			} else {
				alfa += 270;
			}
			LogUtils.d("点击角度===>" + alfa);
			for (int i1 = 0; i1 < radioData.size(); i1++) {
				/**
				 * 夹角匹配
				 * 范围-+3度
				 */
				RadioData radioData = this.radioData.get(i1);
				if (!radioData.isFlag()) {
					if (radioData.getStartAnge() < alfa && alfa < radioData.getEndAnage()) {
						ToastUtils.showShort(radioData.getContent());
						return true;
					}
				} else {
					if (radioData.getStartAnge() < alfa || alfa < radioData.getEndAnage()) {
						ToastUtils.showShort(radioData.getContent());
						return true;
					}
				}

			}

		}

		return super.onTouchEvent(event);
	}

	/**
	 * 4 |  1
	 * -----|-----
	 * 3 |  2
	 * 圆被分成四等份，判断点击在园的哪一部分
	 */
	private int touchOnWhichPart(MotionEvent event) {
		if (event.getX() > criceX) {
			if (event.getY() > criceY) return PART_TWO;
			else return PART_ONE;
		} else {
			if (event.getY() > criceY) return PART_THREE;
			else return PART_FOUR;
		}
	}


	@Override
	public void setChecked(boolean checked) {

	}

	@Override
	public boolean isChecked() {
		return false;
	}

	@Override
	public void toggle() {

	}

	public void toogle(int index) {

	}


	/**
	 * 获取两条线的夹角
	 *
	 * @param centerX
	 * @param centerY
	 * @param xInView
	 * @param yInView
	 * @return
	 */
	public static int getRotationBetweenLines(float centerX, float centerY, float xInView, float yInView) {
		double rotation = 0;

		double k1 = (double) (centerY - centerY) / (centerX * 2 - centerX);
		double k2 = (double) (yInView - centerY) / (xInView - centerX);
		double tmpDegree = Math.atan((Math.abs(k1 - k2)) / (1 + k1 * k2)) / Math.PI * 180;

		if (xInView > centerX && yInView < centerY) {  //第一象限
			rotation = 90 - tmpDegree;
		} else if (xInView > centerX && yInView > centerY) //第二象限
		{
			rotation = 90 + tmpDegree;
		} else if (xInView < centerX && yInView > centerY) { //第三象限
			rotation = 270 - tmpDegree;
		} else if (xInView < centerX && yInView < centerY) { //第四象限
			rotation = 270 + tmpDegree;
		} else if (xInView == centerX && yInView < centerY) {
			rotation = 0;
		} else if (xInView == centerX && yInView > centerY) {
			rotation = 180;
		}

		return (int) rotation;
	}

	class RadioData {
		private float startAnge;
		private float endAnage;
		private String content;

		private boolean flag;

		public boolean isFlag() {
			return flag;
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}

		public float getStartAnge() {
			return startAnge;
		}

		public void setStartAnge(float startAnge) {
			this.startAnge = startAnge;
		}

		public float getEndAnage() {
			return endAnage;
		}

		public void setEndAnage(float endAnage) {
			this.endAnage = endAnage;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public RadioData(float startAnge, float endAnage, String content) {
			this.startAnge = startAnge;
			this.endAnage = endAnage;
			this.content = content;
		}

		@Override
		public String toString() {
			return "RadioData{" +
					"startAnge=" + startAnge +
					", endAnage=" + endAnage +
					", content='" + content + '\'' +
					", flag=" + flag +
					'}';
		}
	}
}
