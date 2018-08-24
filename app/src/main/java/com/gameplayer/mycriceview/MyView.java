package com.gameplayer.mycriceview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

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

	private int criceOffset;
	private int lineOffset;
	private int textOffset;


	private float extenrlRadios;
	private int allOffset;

	private float vacanyRadios;

	private int vacanyOffset;

	private int contentWith;
	private boolean isToday;
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
	private String FORMAT = "yyyy-MM-dd";

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
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CriceView);

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(typedArray.getColor(R.styleable.CriceView_themeColor, Color.WHITE));
		paint.setStrokeWidth(typedArray.getDimensionPixelSize(R.styleable.CriceView_lineWith, 3));
		lineX = new ArrayList<>();
		lineY = new ArrayList<>();
		radioData = new ArrayList<>();
		pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pointPaint.setColor(typedArray.getColor(R.styleable.CriceView_themeColor, Color.WHITE));
		pointPaint.setStrokeCap(Paint.Cap.ROUND);
		pointPaint.setStrokeWidth(typedArray.getDimensionPixelSize(R.styleable.CriceView_pointSize, 30));
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(typedArray.getColor(R.styleable.CriceView_textColor, Color.WHITE));
		textPaint.setTextSize(typedArray.getDimensionPixelSize(R.styleable.CriceView_textSize, 30));
//		setBackgroundColor(Color.parseColor("#14345E"));
		checkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		checkPaint.setColor(COLOR_CHECK_OUTSIDE);
		checkPaint.setStyle(Paint.Style.FILL);

		arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		arcPaint.setColor(typedArray.getColor(R.styleable.CriceView_arcColor, Color.RED));
		arcPaint.setStyle(Paint.Style.STROKE);
		arcPaint.setStrokeWidth(typedArray.getDimensionPixelSize(R.styleable.CriceView_arcWidth, 10));
		backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		backPaint.setColor(Color.BLUE);
		lineOffset = typedArray.getDimensionPixelSize(R.styleable.CriceView_lineOffset, 20);
		textOffset = typedArray.getDimensionPixelSize(R.styleable.CriceView_textOffset, 30);
		contentWith = typedArray.getDimensionPixelSize(R.styleable.CriceView_contentWidth, 50);
		allOffset = lineOffset + textOffset;
		criceOffset = typedArray.getDimensionPixelSize(R.styleable.CriceView_criceOffset, 50);
		vacanyOffset = typedArray.getDimensionPixelSize(R.styleable.CriceView_vacanyOffset, 50);
		typedArray.recycle();

	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		SimpleDateFormat format = new SimpleDateFormat(FORMAT);
		TestCaleand testCaleand = new TestCaleand();
		//24节气
		TreeMap<Long, String> invs = testCaleand.JQtest(2018);
		String iString = "ceshi";
		long getdays = getdays();
		Set<Long> inks = invs.keySet();
		for (Long ink : inks) {
			Long aLong = ink;
			if (aLong.longValue() == getdays || aLong.longValue() > getdays) {
				if (aLong.longValue() == getdays) {
					isToday = true;
				}
				iString = invs.get(ink);
				break;
			}
		}
		LogUtils.d("节气===>" + iString);

		lineY.clear();
		lineX.clear();
		radioData.clear();
		width = getWidth();
		height = getHeight();

		/**
		 * 获取内边距
		 */
		int top = getPaddingTop();
		int bottom = getPaddingBottom();
		int left = getPaddingLeft();
		int right = getPaddingRight();
//		radios = (width - (left + right)) / 2;
//		criceX = left + radios;
//		criceY = top + radios;
//		radios = radios - allOffset - contentWith;

		/**
		 * 8/24  采用覆盖画法  后续需使用drawArc
		 */
		drawVacany(canvas);
		canvas.drawRect(new RectF(0, 0, width, radios + top + allOffset + contentWith), backPaint);
		canvas.drawCircle(criceX, criceY + 40, extenrlRadios, backPaint);
		canvas.drawCircle(criceX, criceY, radios, paint);

		//平分圆
		angle = 360 / 24;
		//当前节日所需要划过的角度
		float xAngle = angle;
		/**
		 * 外接线的模拟圆
		 *
		 */
		float xradios = radios + lineOffset;

		/**
		 * 半径上最多画13点  每个点的间距
		 * 用于太极
		 */
		float pointMar = radios / 13;
		float m = pointMar * 6;
		boolean add = false;
		//起始点  用于太极计算
		int index = 0;

		//用于太极
		boolean isfoce = true;


		float arcradios = 0;

		//圆弧  划过的角度
		float arcAnge = 0;
		for (int i = 0; i < data.size(); i++) {
			index++;
			String message = data.get(i);
			//粗略计算出字体的角度
			float v3 = textPaint.measureText(message);
			//滑动的角度
			float ange = (float) (180 * v3 / (PI * (radios + lineOffset + textOffset)));
			Rect rect = new Rect();
			//计算文字的宽高
			textPaint.getTextBounds(message, 0, message.length(), rect);
			/**
			 *  内圆半径+偏移量+文字高度+文字偏移量/2
			 *  计算x轴和y轴
			 *  基于文字的外接圆
			 */
			float raidoX = getRaidoX(radios + allOffset + (rect.height()) / 2, xAngle);
			float raidoY = getRaidoY(radios + allOffset + (rect.height()) / 2, xAngle);
			//合适的半径
			float checkRadios = rect.width() / 2;
			/**
			 * 绘制选中效果
			 */
			if (iString.equals(message)) {
				if (xAngle > angle * 9) {
					arcAnge = xAngle - angle * 9;
				} else {
					arcAnge = xAngle + angle * 15;
				}
				/**
				 * 画选中效果
				 */
				if (isToday) {
					checkPaint.setColor(COLOR_CHECK_OUTSIDE);
					canvas.drawCircle(raidoX, raidoY, checkRadios + 20, checkPaint);
					checkPaint.setColor(COLOR_CHECK_INTER);
					canvas.drawCircle(raidoX, raidoY, checkRadios + 10, checkPaint);
				}

			}

			Path path = new Path();
			RectF rectF = new RectF(criceX - radios - allOffset, criceY - radios - allOffset, criceX + radios + allOffset, criceY + radios + allOffset);
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

			/**
			 * 在此数值下的画布旋转180
			 * 为了让文字找到合适的显示角度
			 */
			switch (i) {
				case 23:
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
					//保存当前的画布状态
					canvas.save();
					//以文字的中心旋转180度
					canvas.rotate(180, raidoX, raidoY);
					//画文字
					canvas.drawTextOnPath(data.get(i), path, 0, 0, textPaint);
					//绘制完毕  恢复状态
					canvas.restore();
					break;
				default:
					canvas.drawTextOnPath(data.get(i), path, 0, 0, textPaint);
			}


			/**
			 *   偏移量  内圆半径到文字底部的偏移量
			 */
			aFloat = radios + allOffset + rect.height();
			//最外边的测试圆
//			canvas.drawCircle(criceX, criceY, aFloat, paint);

			/**
			 * 获取连线的结束的x点y点
			 */
			float x = (float) (criceX + xradios * Math.cos(xAngle * PI / 180));
			float y = (float) (criceY + xradios * Math.sin(xAngle * PI / 180));
			/**
			 * 获取 圆点的位置
			 */
			float pointx = (float) (criceX + m * Math.cos(xAngle * PI / 180));
			float pointY = (float) (criceY + m * Math.sin(xAngle * PI / 180));
			lineX.add(x);
			lineY.add(y);
			/**
			 * 绘制线和圆点
			 */
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
		LogUtils.d("arcange===>" + arcAnge);
		/**
		 * 绘制最近的节气 或者当前节气所经过的圆弧
		 */
		canvas.drawArc(new RectF(criceX - radios, criceY - radios, radios + criceX, radios + criceY), 135, arcAnge, false, arcPaint);

	}

	private void drawVacany(Canvas canvas) {
		canvas.drawCircle(criceX, criceY + 40, vacanyRadios, paint);
		//22.5
		float ange = 22.5f;
		float v = extenrlRadios * 2 - width;
		float top = criceY + 40 - extenrlRadios+30;
		float right = width + v / 2;
		float bootom = criceY + 40 + extenrlRadios+30;
		RectF rectF = new RectF(-v / 2, top, right, bootom);
		float v3 = textPaint.measureText("巳时");
		float textange = (float) (180 * v3 / (PI * (extenrlRadios)));
		for (int i = 1; i <= 10; i++) {
			float x = getRaidoX(extenrlRadios, ange);
			float y = getVanRaidoY(extenrlRadios, ange);
			float endX = getRaidoX(vacanyRadios, ange);
			float endY = getVanRaidoY(vacanyRadios, ange);
			if (endX > 0 && endX < width) {
				canvas.drawLine(x, y, endX, endY, paint);
				if (getRaidoX(vacanyRadios, ange + 15f) > 0 && getRaidoX(vacanyRadios, ange + 15f) < width) {
					Rect rect = new Rect();
					//计算文字的宽高
					textPaint.getTextBounds("巳时", 0, "巳时".length(), rect);
					/**
					 *  内圆半径+偏移量+文字高度+文字偏移量/2
					 *  计算x轴和y轴
					 *  基于文字的外接圆
					 */
					float raidoX = getRaidoX( extenrlRadios+vacanyOffset/2, ange+10f);
					float raidoY = getVanRaidoY(extenrlRadios+vacanyOffset/2, ange+10f);
					if(ange>90){
						raidoY+=rect.height();
					}
					Path path = new Path();
					LogUtils.d("endx===>" + endX + "---endY===>" + endY);
					path.addArc(rectF, ange+5, textange);
					canvas.drawText("巳时", raidoX,raidoY, textPaint);
				}

			}


			ange += 15f;
		}


	}

	/**
	 * 获取今天的日子
	 *
	 * @return
	 */
	private long getdays() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long l = System.currentTimeMillis();
		try {
			return format.parse("2018-08-23").getTime();
//			return format.parse(format.format(new Date(l))).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
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
	 * 获取圆内一点的x点
	 *
	 * @param radios
	 * @param ange
	 * @return
	 */
	private float getVanRaidoX(float radios, float ange) {
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

	private float getVanRaidoY(float radios, float ange) {
		float y = (float) (criceY + 40 + radios * Math.sin(ange * PI / 180));
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


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		/**
		 * 这里只考虑高度的测量模式
		 * 宽度 全部为math或者固定宽度
		 */
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightModle = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthModle = MeasureSpec.getMode(widthMeasureSpec);
/**
 * 获取内边距
 */
		int top = getPaddingTop();
		int bottom = getPaddingBottom();
		int left = getPaddingLeft();
		int right = getPaddingRight();
		radios = (widthSize - (left + right)) / 2;
		if (radios > heightSize) {
			radios -= 5;
		}
		extenrlRadios = radios + criceOffset;
		vacanyRadios = extenrlRadios + vacanyOffset;
		criceX = left + radios;
		criceX = left + radios;
		criceY = top + radios;
		radios = radios - allOffset - contentWith;

		float v = vacanyRadios * 2 + 40 + top + bottom;

		while (v > heightSize) {
			vacanyRadios -= 5;
			extenrlRadios -= 5;
			v = vacanyRadios * 2 + 40 + top + bottom;
		}

		if (heightModle == MeasureSpec.AT_MOST) {
			setMeasuredDimension(widthSize, (int) v);
		} else if (heightModle == MeasureSpec.EXACTLY) {
			setMeasuredDimension(widthSize, heightSize);
		}
//		switch (widthModle) {
//			case MeasureSpec.AT_MOST:
//				break;
//			case MeasureSpec.EXACTLY:
//				radios = (width - (left + right)) / 2;
//				break;
//			default:
//		}

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
