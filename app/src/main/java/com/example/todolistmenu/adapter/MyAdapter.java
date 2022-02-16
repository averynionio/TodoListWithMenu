package com.example.todolistmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistmenu.R;
import com.example.todolistmenu.bean.RecordBean;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<RecordBean> recordBeanList;


    public MyAdapter(Context context, List<RecordBean> recordBeanList) {
        this.context = context;
        this.recordBeanList = recordBeanList;
    }

    @Override
    public int getCount() {
        return recordBeanList == null ? 0 : recordBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return recordBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.todolist_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        RecordBean recordBean = recordBeanList.get(i);
        viewHolder.tvRecord.setText(recordBean.getTodoRecord());
        viewHolder.tvTime.setText(recordBean.getTime());
        return view;
    }

    class ViewHolder{
        TextView tvRecord;
        TextView tvTime;

        public ViewHolder(View view) {
            tvRecord = view.findViewById(R.id.item_content);
            tvTime = view.findViewById(R.id.item_time);
        }
    }
}