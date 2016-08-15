package com.idzodev.pokemonapp.ui.screens.map;

import android.location.Location;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.Marker;
import com.idzodev.pokemonapp.core.android.App;
import com.idzodev.pokemonapp.core.mvp.BasePresenter;
import com.idzodev.pokemonapp.core.rx.SimpleSubscriber;
import com.idzodev.pokemonapp.domain.use_case.UserUseCase;
import com.idzodev.pokemonapp.ui.dvo.MapDataDvo;

import javax.inject.Inject;

/**
 * Created by vladimir on 12.08.16.
 */
public class MapPresenterImpl extends BasePresenter<MapView> implements MapPresenter{

    private UserUseCase userUseCase;
    private final int type;

    @Inject
    public MapPresenterImpl(int type, App app, UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
        this.type = type;
    }

    @Override
    protected void onViewAttached() {
        getView().showProgress();
    }

    @Override
    public void updateData(double lat, double lon) {
        addSubscription(userUseCase.getMapData(type, lat, lon).subscribe(new SimpleSubscriber<MapDataDvo>(){
            @Override
            public void onNext(MapDataDvo item) {
                if (getView() == null) return;

                getView().hideProgress();
                getView().removeOldMapData();
                getView().showMapData(item);
            }
        }));
    }

    @Override
    public void onMarkerClicked(Marker markerOptions) {

    }

    @Override
    public void onLocationChange(Location location) {
        if (location == null)
            return;

        getView().showMyPosition(location.getLatitude(), location.getLongitude());
        updateData(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onShowSetupLocationSettingsPopup() {

    }

    @Override
    public void onResolutionRequired(Status status) {

    }
}
