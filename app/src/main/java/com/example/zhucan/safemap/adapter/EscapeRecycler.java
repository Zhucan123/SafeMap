package com.example.zhucan.safemap.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhucan.safemap.R;

/**
 * Created by zhucan on 2017/4/28.
 */

public class EscapeRecycler extends RecyclerView.Adapter {
    private String[] titles={"全景观看","逃生路线","用户手册","民防资讯"};
    private String[] contents={"观看全市区的所有逃生点,明确逃生信息,了解逃生的大体情况...",
    "智能选取最近的几个逃生点,并规划最优的逃生路线,有助于市民遇到紧急灾害是的逃生...",
    "制定逃生的基本手册,讲述逃生技巧,加强市民的逃生意识,...",
    "提供实时的民防资讯,提高市民的内心灾害防范意识..."};

    public static interface OnRecyclerListener{
        void onItemClick(int position);
    }

    private OnRecyclerListener listener;

    public void setOnRecyclerListener(OnRecyclerListener listener){
        this.listener=listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup group,int viewType){
        View view= LayoutInflater.from(group.getContext()).inflate(R.layout.escape_recycler_item,null);

        return new EscapeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
        EscapeViewHolder viewHolder=(EscapeViewHolder)holder;
        viewHolder.position=position;
        viewHolder.title.setText(titles[position]);
        viewHolder.content.setText(contents[position]);

    }

    @Override
    public int getItemCount(){
        return 4;
    }

    class EscapeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public TextView content;
        public TextView end;
        public LinearLayout layout;
        public int position;

        public EscapeViewHolder(View view){
            super(view);
            this.title=(TextView)view.findViewById(R.id.escape_title);
            this.content=(TextView)view.findViewById(R.id.escape_content);
            this.end=(TextView)view.findViewById(R.id.escape_end);
            this.layout=(LinearLayout)view.findViewById(R.id.escape_item);
            this.layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            if (listener!=null){
                listener.onItemClick(position);
            }

        }
    }
}
