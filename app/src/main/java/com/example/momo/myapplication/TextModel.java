package com.example.momo.myapplication;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.momo.myapplication.cenment.CementAdapter;
import com.example.momo.myapplication.cenment.CementModel;
import com.example.momo.myapplication.cenment.CementViewHolder;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/09/27
 *   desc: MyApplication
 * </pre>
 */
public class TextModel extends CementModel<TextModel.ViewHolder> {

    private String mItem;

    public TextModel(String item) {
        mItem = item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_model;
    }

    @Override
    public void bindData(@NonNull ViewHolder holder) {
        holder.tv.setText(mItem);
    }

    @Override
    public CementAdapter.IViewHolderCreator<ViewHolder> getViewHolderCreator() {
        return new CementAdapter.IViewHolderCreator<ViewHolder>() {
            @NonNull
            @Override
            public ViewHolder create(@NonNull View view) {
                return new ViewHolder(view);
            }
        };
    }

    class ViewHolder extends CementViewHolder {

        private TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_item);
        }
    }
}
