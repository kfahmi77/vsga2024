package com.example.vsga_2024;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DataAdapter extends BaseAdapter {

    private Context context;
    private List<Data> dataList;

    public DataAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_data, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tv_name);
        TextView tvAddress = convertView.findViewById(R.id.tv_address);

        Data data = dataList.get(position);
        tvName.setText(data.getName());
        tvAddress.setText(data.getAddress());

        return convertView;
    }
}