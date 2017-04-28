package com.example.zhucan.safemap.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhucan.safemap.R;

/**
 * Created by zhucan on 2017/4/25.
 */

public class NaviRecyclerAdapter extends RecyclerView.Adapter {
    private int TYPE_ONE = 4;
    private int TYPE_TWO = 2;
    private int TYPE_FOUR = 1;
    private String[] items = {"离线地图", "导航语音", "违章查询", "电子狗"};
    private int[] icons = {R.drawable.navi_item1, R.drawable.navi_item2,
            R.drawable.navi_item3, R.drawable.navi_item4};

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup group, int type) {
        View view = LayoutInflater.from(group.getContext()).inflate(R.layout.navi_fourline_item, null);
        return new fourLineHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        fourLineHolder holder = (fourLineHolder) viewHolder;
        holder.iocn.setImageResource(icons[position]);
        holder.textView.setText(items[position]);
    }


    @Override
    public int getItemCount() {
        return 4;
    }

    class oneLineHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public oneLineHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.oneLine_text);
        }
    }

    class twoLineHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView text;

        public twoLineHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.twoLine_name);
            this.text = (TextView) view.findViewById(R.id.twoLine_text);
        }
    }

    class fourLineHolder extends RecyclerView.ViewHolder {
        public ImageView iocn;
        public TextView textView;

        public fourLineHolder(View view) {
            super(view);
            this.iocn = (ImageView) view.findViewById(R.id.fourLine_icon);
            this.textView = (TextView) view.findViewById(R.id.fourLine_text);
        }
    }
}
