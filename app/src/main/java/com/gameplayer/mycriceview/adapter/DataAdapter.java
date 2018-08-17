package com.gameplayer.mycriceview.adapter;

import android.os.HardwarePropertiesManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gameplayer.mycriceview.R;
import com.gameplayer.mycriceview.data.DataBean;

import java.util.List;

/**
 * 项目:趣租部落
 *
 * @author：location time：2018/7/31 17:04
 * description：
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
	private List<DataBean> list;


	private OnItmeListener listener;

	public void setListener(OnItmeListener listener) {
		this.listener = listener;
	}

	public DataAdapter(List<DataBean> list) {
		this.list = list;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
		return new MyViewHolder(view);

	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position) {
		DataBean dataBean = list.get(position);

		holder.message.setText(dataBean.getMessage());
		holder.state.setText(dataBean.isSelect()?"已选中":"");

		if(listener!=null){
			holder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					listener.onItemClick(holder,holder.itemView,position);
				}
			});
		holder.message.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.startActivity();
			}
		});

		}
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		TextView message;
		TextView state;
View itemView;
		public MyViewHolder(View itemView) {
			super(itemView);
			message = itemView.findViewById(R.id.item_message);
			state = itemView.findViewById(R.id.item_status);
			this.itemView = itemView;
		}
	}

	public interface  OnItmeListener{
		void onItemClick(MyViewHolder viewHolder,View view ,int position);

		void startActivity();
	}
}
