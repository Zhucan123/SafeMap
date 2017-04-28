package com.example.zhucan.safemap.activity;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhucan.safemap.R;
import com.example.zhucan.safemap.fragment.MineFragment;
import com.example.zhucan.safemap.fragment.NavigationFragment;
import com.example.zhucan.safemap.fragment.NearbyFragment;
import com.example.zhucan.safemap.fragment.RefugeFragment;
import com.example.zhucan.safemap.util.ApplicationContext;
import com.example.zhucan.safemap.util.PreferenceUtil;

/**
 * Created by zhucan on 2017/4/15.
 * <p>
 * 程序的主入口
 * 设置fragmentTabHost
 * 4个fragment分别为附近,导航,避难,我的
 */

public class MainIndexActivity extends FragmentActivity {
    public static double[] coord;
    private LocationManager locationManager;
    private FragmentTabHost tabHost;
    //四个fragment的class数组
    private Class[] fragments = {NearbyFragment.class, NavigationFragment.class,
            RefugeFragment.class, MineFragment.class};
    //导航栏title数组
    private final String title[] = {"附近", "导航", "避难", "我的"};
    //导航栏图标
    private int icons[] = {R.drawable.near_bg, R.drawable.route_bg,
            R.drawable.safe_bg, R.drawable.mine_bg};

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                locationManager = (LocationManager) ApplicationContext.getContext().getSystemService(Context.LOCATION_SERVICE);
                if (ContextCompat.checkSelfPermission(ApplicationContext.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                    Looper.prepare();
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            PreferenceUtil.inCreaseCityCoordX((float) location.getLatitude());
                            PreferenceUtil.inCreaseCityCoordY((float) location.getLongitude());
                            coord = new double[]{location.getLatitude(), location.getLongitude()};
                            Log.i("TAG", "onLocationChanged: " + location.getLatitude());
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                            if (ContextCompat.checkSelfPermission(ApplicationContext.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                                    PackageManager.PERMISSION_GRANTED) {
                                Log.i("TAG", "onLocationChanged: " + "啥子情况1");
                                Location location = locationManager.getLastKnownLocation(provider);
                                PreferenceUtil.inCreaseCityCoordX((float) location.getLatitude());
                                PreferenceUtil.inCreaseCityCoordY((float) location.getLongitude());

                            }
                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });
                    Looper.loop();
                }
            }
        }).start();


        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(MainIndexActivity.this, getSupportFragmentManager(), R.id.myFragment);
        for (int i = 0; i < fragments.length; i++) {
            FragmentTabHost.TabSpec spec = tabHost.newTabSpec(title[i]).setIndicator(getView(i));
            tabHost.addTab(spec, fragments[i], null);
        }
        //设置标题栏的背景
        tabHost.getTabWidget().setBackgroundResource(R.drawable.title_bg);


    }

    //返回标题栏的布局
    private View getView(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.title_item, null);
        TextView titleName = (TextView) view.findViewById(R.id.title_name);
        titleName.setText(title[index]);
        ImageView titleIcon = (ImageView) view.findViewById(R.id.title_icon);
        titleIcon.setImageResource(icons[index]);

        return view;
    }
}
