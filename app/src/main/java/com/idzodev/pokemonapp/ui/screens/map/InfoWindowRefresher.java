package com.idzodev.pokemonapp.ui.screens.map;

import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;

/**
 * Created by vova on 19.09.15.
 */
public class InfoWindowRefresher implements Callback {

    private Marker markerToRefresh;

    public InfoWindowRefresher(Marker markerToRefresh) {
        this.markerToRefresh = markerToRefresh;
    }


    @Override
    public void onSuccess() {
        markerToRefresh.showInfoWindow();
    }

    @Override
    public void onError() {

    }
}
