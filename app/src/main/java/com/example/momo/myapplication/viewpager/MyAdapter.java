package com.example.momo.myapplication.viewpager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.momo.myapplication.R;

import java.util.List;

public class MyAdapter extends InfinitePagerAdapter {

    private List<String> data;

    public MyAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup container) {
        convertView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_model, container, false);
        TextView textView = convertView.findViewById(R.id.tv_item);
        textView.setText(data.get(position));
        return convertView;
    }
}