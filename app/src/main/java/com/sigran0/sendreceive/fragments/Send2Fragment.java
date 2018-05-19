package com.sigran0.sendreceive.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sigran0.sendreceive.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2018-05-20.
 */

public class Send2Fragment extends BaseFragment {

    @BindView(R.id.f_send_2_mv)
    MapView mapView;

    MarkerOptions selectedMarker = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRoot(R.layout.fragment_send_2, inflater, container, savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initializeLayout(){
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng SEOUL = new LatLng(37.56, 126.97);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(SEOUL);
                markerOptions.title("서울");
                markerOptions.snippet("수도");
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

                selectedMarker = markerOptions;
                binderManager.startUpdate("ok_send2", selectedMarker.getPosition());

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(mapView != null)
            mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mapView != null)
            mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mapView != null)
            mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mapView != null)
            mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mapView != null)
            mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mapView != null)
            mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(mapView != null)
            mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mapView != null)
            mapView.onLowMemory();
    }
}
