package com.gameplayer.mycriceview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/7/31 17:41
 * description：
 */

public class SelectActivity extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);
		final EditText editText = findViewById(R.id.id_ed);

		findViewById(R.id.id_btn)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if(TextUtils.isEmpty(editText.getText().toString())){
							Toast.makeText(SelectActivity.this, "请输入id 0-49", Toast.LENGTH_SHORT).show();
							return;
						}
						Intent intent = new Intent();
						intent.putExtra("id", Integer.parseInt(editText.getText().toString()));
						setResult(RESULT_OK, intent);
						finish();
					}
				});
	}
}
