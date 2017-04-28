package com.example.zhucan.safemap.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.zhucan.safemap.R;
import com.example.zhucan.safemap.activity.MainIndexActivity;
import com.example.zhucan.safemap.activity.PoiAroundSearchActivity;
import com.example.zhucan.safemap.adapter.MyRecyclerAdapter;
import com.example.zhucan.safemap.util.ApplicationContext;
import com.example.zhucan.safemap.util.PreferenceUtil;


/**
 * Created by zhucan on 2017/4/15.
 * <p>
 * 搜索附近的页面
 * 点击搜索的热词后跳转到地图页面
 */

public class NearbyFragment extends Fragment {
    private RecyclerView recyclerView;
    private GridLayoutManager manager;
    private LocationManager locationManager;
    private int ICON_NUM_COUNT = 1;
    private int LIST_NUM_COUNT = 4;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup group, Bundle saveInstanceState) {

        View view = inflater.inflate(R.layout.near_fragment, null);
        LinearLayout search = (LinearLayout) view.findViewById(R.id.search_bn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(group.getContext(), PoiAroundSearchActivity.class);
                Bundle data = new Bundle();
                data.putFloatArray("siteXY", new float[]{PreferenceUtil.getCityCoordX(), PreferenceUtil.getCityCoordY()});
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });

        manager = new GridLayoutManager(group.getContext(), 4);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        manager.setAutoMeasureEnabled(true);
        //通过判断position来决定分行的宽度比例
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position < 8) {
                    return ICON_NUM_COUNT;
                } else {
                    return LIST_NUM_COUNT;
                }
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.near_recyclerView);
        recyclerView.setLayoutManager(manager);
        final MyRecyclerAdapter adapter = new MyRecyclerAdapter();
        //为item设置点击事件,把搜索地址放入intent并跳转到搜索页面
        adapter.setOnRecyclerListener(new MyRecyclerAdapter.OnRecyclerListener() {
            @Override
            public void onItemClick(int position) {
                if (position < 8) {
                    Intent intent = new Intent();
                    intent.putExtra("site", MyRecyclerAdapter.sites[position % 8]);

                    Bundle data = new Bundle();
                    data.putFloatArray("siteXY", new float[]{PreferenceUtil.getCityCoordX(), PreferenceUtil.getCityCoordY()});
                    intent.putExtra("data", data);

                    intent.setClass(group.getContext(), PoiAroundSearchActivity.class);
                    startActivity(intent);
                } else if (position > 8) {
                    adapter.notifyItemRemoved(position);
                    if (position != adapter.getItemCount()) {
                        adapter.notifyItemRangeChanged(position, adapter.getItemCount() - position);
                        Log.i("TAG", "onItemClick: " + position);
                    }

                }
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);


        return view;
    }
}
