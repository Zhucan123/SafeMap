package com.example.zhucan.safemap.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amap.api.maps.SupportMapFragment;
import com.example.zhucan.safemap.R;
import com.example.zhucan.safemap.adapter.EscapeRecycler;
import com.example.zhucan.safemap.util.ApplicationContext;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhucan on 2017/4/15.
 */

public class RefugeFragment extends SupportMapFragment {
    private ViewPager pager;
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private int autoIndex = 0;
    private GridLayoutManager manager;
    private ImageView[] iconResource;
    private int[] showImage = {R.drawable.default_1, R.drawable.default_2, R.drawable.default_3, R.drawable.default_4};
    private ImageView[] oval;
    private Timer timer;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Log.i("TAG", "handleMessage: " + msg.arg1);
                if (msg.arg1 != 0) {
                    pager.setCurrentItem(msg.arg1);
                } else {
                    pager.setCurrentItem(msg.arg1, false);
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        timer = new Timer();
        View rootView = inflater.inflate(R.layout.escape_fragment, null);
        pager = (ViewPager) rootView.findViewById(R.id.escape_pager);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.indicator);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.escape_recycle);

        manager=new GridLayoutManager(group.getContext(),2);
        recyclerView.setLayoutManager(manager);
        EscapeRecycler adapter=new EscapeRecycler();
        adapter.setOnRecyclerListener(new EscapeRecycler.OnRecyclerListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);

        iconResource = new ImageView[4];
        for (int i = 0; i < showImage.length; i++) {
            ImageView view = new ImageView(ApplicationContext.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewPager.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params);
            view.setBackgroundResource(showImage[i]);
            iconResource[i] = view;
        }
        setUpViewPager(iconResource);
        return rootView;
    }

    private void setUpViewPager(final ImageView[] imageViewList) {
        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return iconResource.length;
            }

            @Override
            public Object instantiateItem(ViewGroup group, int position) {
                group.addView(imageViewList[position]);
                return imageViewList[position];
            }

            @Override
            public void destroyItem(ViewGroup group, int position, Object object) {
                group.removeView(imageViewList[position]);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == (View) object;
            }
        });

        oval = new ImageView[4];
        for (int i = 0; i < oval.length; i++) {
            ImageView view = new ImageView(ApplicationContext.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(5, 0, 5, 0);
            view.setLayoutParams(params);
            if (i == 0) {
                view.setImageResource(R.drawable.indicator_image);
            } else {
                view.setImageResource(R.drawable.indicator_bg);
            }
            oval[i] = view;
            linearLayout.addView(oval[i]);
        }

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                autoIndex = position;
            }

            @Override
            public void onPageSelected(int position) {
                int length = oval.length;
                for (int i = 0; i < length; i++) {
                    if (i == position) {
                        oval[i].setImageResource(R.drawable.indicator_image);
                    } else {
                        oval[i].setImageResource(R.drawable.indicator_bg);
                    }
                }
                autoIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                Log.i("TAG", "run: " + autoIndex);
                msg.what = 0x123;
                if (autoIndex == 3) {
                    autoIndex = autoIndex - 4;
                }
                msg.arg1 = autoIndex + 1;
                handler.sendMessage(msg);
            }
        }, 3000, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timer.cancel();
    }
}
