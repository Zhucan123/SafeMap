package com.example.zhucan.safemap.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.example.zhucan.safemap.R;
import com.example.zhucan.safemap.activity.MainIndexActivity;
import com.example.zhucan.safemap.activity.NaviGoActivity;
import com.example.zhucan.safemap.activity.NaviSearchActivity;
import com.example.zhucan.safemap.adapter.NaviRecyclerAdapter;
import com.example.zhucan.safemap.util.ApplicationContext;
import com.iflytek.cloud.thirdparty.S;

/**
 * Created by zhucan on 2017/4/15.
 */

public class NavigationFragment extends Fragment implements View.OnClickListener {
    private int REQUEST_CODE = 0;
    private int START_RESULT_CODE = 1;
    private int END_RESULT_CODE = 2;
    TextView startSite;
    TextView endSite;
    Button begin;
    private double[] startcoord;
    private double[] endcoord;
    LinearLayout startLayout;
    LinearLayout endLayout;
    private RecyclerView recyclerView;
    private GridLayoutManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.navi_layout, null);
        startSite = (TextView) view.findViewById(R.id.navi_start);
        endSite = (TextView) view.findViewById(R.id.navi_end);
        begin = (Button) view.findViewById(R.id.begin_btn);

        recyclerView = (RecyclerView) view.findViewById(R.id.navi_item);
        manager = new GridLayoutManager(ApplicationContext.getContext(), 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new NaviRecyclerAdapter());

        startLayout = (LinearLayout) view.findViewById(R.id.start_layout);
        endLayout = (LinearLayout) view.findViewById(R.id.end_layout);
        startLayout.setOnClickListener(this);
        endLayout.setOnClickListener(this);
        begin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE)
            switch (resultCode) {
                case 1:
                    startSite.setText(intent.getStringExtra("site"));
                    startcoord = intent.getDoubleArrayExtra("coord");
                    break;

                case 2:
                    endSite.setText(intent.getStringExtra("site"));
                    endcoord = intent.getDoubleArrayExtra("coord");
                    break;
            }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.start_layout:
                Intent intent = new Intent(ApplicationContext.getContext(), NaviSearchActivity.class);
                intent.putExtra("type", "start");
                startActivityForResult(intent, REQUEST_CODE);
                break;

            case R.id.end_layout:
                Intent intent1 = new Intent(ApplicationContext.getContext(), NaviSearchActivity.class);
                intent1.putExtra("type", "end");
                startActivityForResult(intent1, REQUEST_CODE);
                break;

            case R.id.begin_btn:
                //  String start=startSite.getText().toString();
                String end = endSite.getText().toString();
                if (end.equals("")) {
                    Toast.makeText(ApplicationContext.getContext(), "请输入正确终点地址",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent intent2 = new Intent(ApplicationContext.getContext(), NaviGoActivity.class);

                    intent2.putExtra("start", startcoord);
                    intent2.putExtra("end", endcoord);
                    startActivity(intent2);
                }
        }
    }
}
