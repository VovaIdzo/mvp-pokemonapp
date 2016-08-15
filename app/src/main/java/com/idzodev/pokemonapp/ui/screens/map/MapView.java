package com.idzodev.pokemonapp.ui.screens.map;

import com.idzodev.pokemonapp.ui.dvo.MapDataDvo;
import com.idzodev.pokemonapp.ui.dvo.ZombiesDvo;

/**
 * Created by vladimir on 12.08.16.
 */
public interface MapView {
    void showProgress();
    void hideProgress();
    void showMyPosition(double lat, double lan);
    void showMapData(MapDataDvo mapDataDvo);
    void removeOldMapData();
}
