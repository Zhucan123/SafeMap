package com.example.zhucan.safemap.activity;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.example.zhucan.safemap.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private MapView mapView;
    private AMap amap;
    String TAG = "123";
    private LocationManager lcm;
    private GeocodeSearch search;
    private RouteSearch route;
    private FragmentTabHost tabHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        init();

        //创建GeocodeSearch对象并设置监听器
        search = new GeocodeSearch(this);
        search.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                GeocodeAddress address = geocodeResult.getGeocodeAddressList().get(0);
                //得到查询点
                LatLonPoint point = address.getLatLonPoint();
                Log.i(TAG, "onGeocodeSearched: " + point.toString());
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                    //得到自身所在地
                    Location loc = lcm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    RouteSearch.FromAndTo ft = new RouteSearch.FromAndTo(new LatLonPoint(loc.getLatitude(), loc.getLongitude()), point);
                    //设置驾车路线
                    RouteSearch.DriveRouteQuery d = new RouteSearch.DriveRouteQuery(ft, RouteSearch.DrivingDefault, null, null, null);
                    route.calculateDriveRouteAsyn(d);
                }
            }
        });
        //创建RouteSearch对象并设置监听器
        route = new RouteSearch(this);
        route.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
                DrivePath path = driveRouteResult.getPaths().get(0);
                List<DriveStep> steps = path.getSteps();
                for (DriveStep step : steps) {
                    List<LatLonPoint> points = step.getPolyline();
                    List<LatLng> lat = new ArrayList<>();
                    for (LatLonPoint point : points) {
                        lat.add(new LatLng(point.getLatitude(), point.getLongitude()));
                    }
                    PolylineOptions poly = new PolylineOptions().addAll(lat).color(Color.RED).width(5);
                    amap.addPolyline(poly);
                }
            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });

        //获取locationManager
        lcm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //判断是否打开的gps并进行提醒
        if (!lcm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请在设置中打开您的GPS", Toast.LENGTH_LONG).show();
        }

        //设置request监听器
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)
            lcm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 8, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    updatePosition(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    updatePosition(null);
                }

                @Override
                public void onProviderEnabled(String provider) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED)
                        updatePosition(lcm.getLastKnownLocation(provider));
                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

        //获取输入的地址并进行定位
      /*  final EditText  editText=(EditText)findViewById(R.id.search);
       button=(Button)findViewById(R.id.search_Button);
         button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city=editText.getText().toString().trim();
                GeocodeQuery query=new GeocodeQuery(city,"中国");
                search.getFromLocationNameAsyn(query);
            }
        });

        //卫星地图和实时地图切换开关
       ToggleButton tb=(ToggleButton)findViewById(R.id.choose);
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                amap.setMapType(AMap.MAP_TYPE_SATELLITE);
                else
                 amap.setMapType(AMap.MAP_TYPE_NORMAL);
            }
        });*/
    }

    //初始化地图
    private void init() {
        if (amap == null) {
            amap = mapView.getMap();
            CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(16);
            amap.moveCamera(cameraUpdate);

        }
    }

    //高德地图的回调
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
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
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    //更新地图中的显示
    private void updatePosition(Location l) {
        LatLng pos = new LatLng(l.getLatitude(), l.getLongitude());
        Log.i(TAG, "updatePosition: " + l.getLatitude() + "  " + l.getLongitude());
        CameraUpdate c = CameraUpdateFactory.changeLatLng(pos);
        amap.moveCamera(c);
        amap.clear();
        MarkerOptions m = new MarkerOptions().position(pos).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name)).draggable(true);
        amap.addMarker(m);
    }

}
