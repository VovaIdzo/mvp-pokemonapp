package com.idzodev.pokemonapp.ui.screens.map;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.idzodev.pokemonapp.core.mvp.Presenter;
import com.idzodev.pokemonapp.services.location.FusedLocationProvider;

/**
 * Created by vladimir on 12.08.16.
 */
public interface MapPresenter extends Presenter<MapView>, FusedLocationProvider.OnLocationChangeListener{
    void updateData(double lat, double lon);
    void onDisableCameraMovement(boolean disable);
    void onMarkerClicked(Marker markerOptions);
}
