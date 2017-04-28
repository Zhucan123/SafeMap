package com.example.zhucan.safemap.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.example.zhucan.safemap.R;
import com.example.zhucan.safemap.util.ApplicationContext;
import com.example.zhucan.safemap.util.PreferenceUtil;

/**
 * Created by zhucan on 2017/4/23.
 */

public class NaviGoActivity extends BaseActivity {
    protected NaviLatLng mEndLatlng;
    protected NaviLatLng mStartLatlng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        double[] stardsite = intent.getDoubleArrayExtra("start");
        double[] enddite = intent.getDoubleArrayExtra("end");
        if (stardsite == null) {
            mStartLatlng = new NaviLatLng(PreferenceUtil.getCityCoordX(), PreferenceUtil.getCityCoordY());
        } else {
            mStartLatlng = new NaviLatLng(stardsite[0], stardsite[1]);
        }
        mEndLatlng = new NaviLatLng(enddite[0], enddite[1]);
        sList.add(mStartLatlng);
        eList.add(mEndLatlng);

        setContentView(R.layout.navi_go);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。0
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // mAMapNavi.setCarNumber("京", "DFZ588");
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);

    }

    @Override
    public void onCalculateRouteSuccess() {
        super.onCalculateRouteSuccess();
        mAMapNavi.startNavi(NaviType.GPS);
    }
}
