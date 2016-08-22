package com.idzodev.pokemonapp.ui.screens.map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.idzodev.pokemonapp.R;
import com.idzodev.pokemonapp.core.android.App;
import com.idzodev.pokemonapp.core.android.BaseActivity;
import com.idzodev.pokemonapp.core.utils.MapUtils;
import com.idzodev.pokemonapp.core.utils.MarkerUtils;
import com.idzodev.pokemonapp.domain.repository.EntitiesRepository;
import com.idzodev.pokemonapp.domain.repository.impl.EntitiesRepositoryImpl;
import com.idzodev.pokemonapp.services.location.FusedLocationProvider;
import com.idzodev.pokemonapp.ui.di.MapComponent;
import com.idzodev.pokemonapp.ui.dvo.EntityDvo;
import com.idzodev.pokemonapp.ui.dvo.MapDataDvo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by vladimir on 12.08.16.
 */
public class MapActivity extends BaseActivity implements MapView, OnMapReadyCallback{
    private static final String EXTRA_TYPE = "EXTRA_TYPE";

    @BindView(R.id.progress)
    protected View vProgress;

    @Inject
    protected FusedLocationProvider fusedLocationProvider;
    @Inject
    protected MapPresenter mapPresenter;
    @BindView(R.id.map_activity_settings)
    protected CheckBox checkBox;

    private GoogleMap googleMap;
    private Marker myLastLocationMarker;
    private Circle meLastLocationCircle;
    private MarkerAdapter markerAdapter;
    private List<Marker> entities = new ArrayList<>();

    //Android native
    public static void start(Activity activity, int type){
        Intent intent = new Intent(activity, MapActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        setupDagger();
        ButterKnife.bind(this);
        SupportMapFragment fragment = SupportMapFragment.newInstance();
        fragment.getMapAsync(this);
        replaceFragment(fragment, SupportMapFragment.class.getName()).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fusedLocationProvider.unregister();
        mapPresenter.detachView();
        googleMap = null;
    }

    // View
    @Override
    public void showProgress() {
        vProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        vProgress.setVisibility(View.GONE);
    }

    @Override
    public void showMyPosition(double lat, double lan) {
        if (googleMap == null) return;

        if (myLastLocationMarker != null){
            myLastLocationMarker.setPosition(new LatLng(lat, lan));
        } else {
            myLastLocationMarker = googleMap.addMarker(MarkerUtils.createMyMarker(this, 0, "Я", lat, lan));
        }

        if (meLastLocationCircle != null)
            meLastLocationCircle.setCenter(new LatLng(lat, lan));
        else {
            meLastLocationCircle = googleMap.addCircle(new CircleOptions()
                    .center(new LatLng(lat, lan))
                    .radius(EntitiesRepository.USER_RADIUS)
                    .strokeWidth(1)
                    .strokeColor(Color.BLACK)
                    .fillColor(Color.parseColor("#40000000")));
        }
    }

    @Override
    public void showMapData(MapDataDvo mapDataDvo) {
        markerAdapter.setEntities(mapDataDvo.getEntities());
        for (EntityDvo entityDvo : mapDataDvo.getEntities()){
            entities.add(googleMap.addMarker(MarkerUtils.createEntityMarker(this, entityDvo)));
        }

        showMyPosition(myLastLocationMarker.getPosition().latitude, myLastLocationMarker.getPosition().longitude);
    }

    @Override
    public void removeOldMapData() {
        for (Marker marker : entities){
            marker.remove();
        }
        entities.clear();
    }

    @Override
    public void cameraMovementDisable() {
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
    }

    @Override
    public void cameraMovementEnable() {
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
    }

    @Override
    public void moveCamera(double lat, double lon) {
        MapUtils.cameraGoTo(googleMap, new LatLng(lat, lon), 15);
    }

    private void setupDagger(){
        App.getApp(this)
                .getAppComponent()
                .plus(new MapComponent.Module(getIntent().getIntExtra(EXTRA_TYPE, 0)))
                .inject(this);
    }

    // Android listeners
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setOnMarkerClickListener(marker -> {
            marker.showInfoWindow();
//            mapPresenter.onMarkerClicked(marker);
            return true;
        });

        markerAdapter = new MarkerAdapter(this);
        googleMap.setInfoWindowAdapter(markerAdapter);

        mapPresenter.attachView(this);
        mapPresenter.onLocationChange(fusedLocationProvider.getLastLocation(this));
        fusedLocationProvider.register(mapPresenter);
    }

    @OnCheckedChanged(R.id.map_activity_settings)
    public void onCameraCheckListener(){
        mapPresenter.onDisableCameraMovement(checkBox.isChecked());
    }
}
