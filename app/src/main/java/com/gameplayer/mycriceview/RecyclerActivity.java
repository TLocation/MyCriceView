package com.gameplayer.mycriceview;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.gameplayer.mycriceview.adapter.DataAdapter;
import com.gameplayer.mycriceview.data.DataBean;

import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/7/31 16:48
 * description：
 */

public class RecyclerActivity extends AppCompatActivity implements DataAdapter.OnItmeListener {
	private List<DataBean> list = new ArrayList<>();
	private DataAdapter adapter;
private int lastPosition = -1;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_recycler);
          for(int i=0;i<50;i++){
			  list.add(new DataBean(i, "测试" + i));
		  }
		RecyclerView recyclerView = findViewById(R.id.id_recycler);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new DataAdapter(list);
		adapter.setListener(this);
		recyclerView.setAdapter(adapter);


	}

	@Override
	public void onItemClick(DataAdapter.MyViewHolder viewHolder, View view, int position) {
		DataBean dataBean = list.get(position);
		if(dataBean.isSelect()){
			Toast.makeText(this, "已经选中了 不要重复选择", Toast.LENGTH_SHORT).show();
		}else{
			if(lastPosition!=-1){
				list.get(lastPosition).setSelect(false);
			}
			dataBean.setSelect(true);
			lastPosition = position;
			adapter.notifyDataSetChanged();
		}

	}

	@Override
	public void startActivity() {
		startActivityForResult(new Intent(this,SelectActivity.class),100);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==100&&resultCode==RESULT_OK){
			int id = data.getIntExtra("id", -1);
			for (int i = 0; i < list.size(); i++) {
				DataBean dataBean = list.get(i);
				dataBean.setSelect(false);
				if(id==dataBean.getId()){
					dataBean.setSelect(true);
					lastPosition = i;
				}
			}
			for (DataBean dataBean : list) {

			}
           adapter.notifyDataSetChanged();

		}
	}
}
