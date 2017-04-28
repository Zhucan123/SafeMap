package com.example.zhucan.safemap.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.example.zhucan.safemap.R;

/**
 * Created by zhucan on 2017/4/20.
 */

public class SearchActivity extends Activity {
    private String TAG = "走起咯";
    private MapView mapView;
    private AMap aMap;
    private String searchSite;
    private String dataName = "site";
    private LocationManager manager;
    private GeocodeSearch search;
    private RouteSearch route;
    private float x, y;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.near_site_search);
        mapView = (MapView) findViewById(R.id.search_map);
        mapView.onCreate(saveInstanceState);
        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        float xNow = event.getX();
                        float yNow = event.getY();
                        if (Math.abs(xNow - x) > 100 || Math.abs(yNow - y) > 100) {
                            //改变状态
                        }

                }
                return false;
            }
        });
        init();

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        search = new GeocodeSearch(this);
        search.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });

        route = new RouteSearch(this);
        route.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            updatePosition(location);
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    updatePosition(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {
                    if (ContextCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED)
                        updatePosition(manager.getLastKnownLocation(provider));
                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }

        Intent intent = getIntent();
        if (intent.getStringExtra(dataName) != null) {
            if (intent.getStringExtra(dataName).equals("")) {
                Toast.makeText(this, "搜索地址无效", Toast.LENGTH_LONG).show();
            } else {
                searchSite = intent.getStringExtra(dataName);
            }
        }

    }

    public void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            CameraUpdate update = CameraUpdateFactory.zoomBy(16);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    //更新地图中的显示
    private void updatePosition(Location l) {
        LatLng pos = new LatLng(l.getLatitude(), l.getLongitude());
        Log.i(TAG, "updatePosition: " + l.getLatitude() + "  " + l.getLongitude());
        CameraUpdate c = CameraUpdateFactory.changeLatLng(pos);
        aMap.moveCamera(c);
        aMap.clear();
        MarkerOptions m = new MarkerOptions().position(pos).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name)).draggable(true);
        aMap.addMarker(m);
    }
}
