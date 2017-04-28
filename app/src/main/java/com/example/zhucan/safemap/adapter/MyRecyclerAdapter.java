package com.example.zhucan.safemap.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhucan.safemap.R;
import com.example.zhucan.safemap.util.RomdomUtil;
import com.example.zhucan.safemap.util.StringConfig;

/**
 * Created by zhucan on 2017/4/15.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter {
    public final static String sites[] = {"美食", "酒店", "景点", "银行", "超市", "公交地铁", "停车场", "加油站",};
    private int icons[] = {R.drawable.site1, R.drawable.site2, R.drawable.site3, R.drawable.site4,
            R.drawable.site5, R.drawable.site6, R.drawable.site7, R.drawable.site8};
    private int TYPE_DIVIDER = 1;
    private int TYPE_ICON = 0;
    private int TYPE_ITEM = 2;


    //定义监听器接口
    public static interface OnRecyclerListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private OnRecyclerListener listener;

    //绑定监听器
    public void setOnRecyclerListener(OnRecyclerListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_site_item, null);
                SiteViewHolder viewHolder = new SiteViewHolder(view);
                return viewHolder;

            case 1:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.divider, null);
                return new DividerHolder(view1);

            case 2:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
                return new ListViewHolder(view2);

        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 8) {
            return TYPE_ICON;
        } else if (position == 8) {
            return TYPE_DIVIDER;
        } else {
            return TYPE_ITEM;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {

            case 0:
                Log.i("TAG", "onBindViewHolder: " + position);
                SiteViewHolder viewHolder = (SiteViewHolder) holder;
                viewHolder.position = position;
                viewHolder.name.setText(sites[position]);
                viewHolder.icon.setImageResource(icons[position]);
                break;
            case 1:
                DividerHolder dividerHolder = (DividerHolder) holder;
                break;
            case 2:
                ListViewHolder listViewHolder = (ListViewHolder) holder;
                listViewHolder.position = position;
                listViewHolder.content.setText(StringConfig.USER_NEWS[RomdomUtil.Ran(StringConfig.USER_NEWS.length)]);
                listViewHolder.author.setText(StringConfig.USER_NAME[RomdomUtil.Ran(StringConfig.USER_NAME.length)]);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    class SiteViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        public ImageView icon;
        public TextView name;
        public int position;
        public View rootView;

        public SiteViewHolder(View view) {
            super(view);
            this.icon = (ImageView) view.findViewById(R.id.site_icon);
            this.name = (TextView) view.findViewById(R.id.site_name);
            rootView = view.findViewById(R.id.site_viewGroup);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (null != listener) {
                listener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (null != listener) {
                listener.onItemLongClick(position);
            }
            return false;
        }
    }

    class DividerHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public DividerHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.my_divider);
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int position;
        private TextView content;
        private TextView author;
        private ImageView close;

        public ListViewHolder(View view) {
            super(view);
            this.content = (TextView) view.findViewById(R.id.list_content);
            this.author = (TextView) view.findViewById(R.id.list_author);
            this.close = (ImageView) view.findViewById(R.id.close_bn);
            this.close.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (null != listener) {
                listener.onItemClick(position);
            }
        }
    }
}

