package com.gameplayer.mycriceview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final CircleView circleView = findViewById(R.id.id_cir);


		final ImageView imageView = findViewById(R.id.id_img);
		imageView.postDelayed(new Runnable() {
			@Override
			public void run() {
				ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
				if(layoutParams instanceof ViewGroup.MarginLayoutParams){
					final int imageOffset = circleView.getImageOffset();
					int measuredHeight = imageView.getMeasuredHeight();
					((ViewGroup.MarginLayoutParams) layoutParams).topMargin = imageOffset-measuredHeight/2;
					imageView.setLayoutParams(layoutParams);
					LogUtils.d("offse===>"+imageOffset);
					LogUtils.d("height===>"+measuredHeight);
					LogUtils.d("设置偏移量");
				}
			}
		},100);
		/**
		 * /**
		 * x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )
		 y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )
		 */
	}
}
