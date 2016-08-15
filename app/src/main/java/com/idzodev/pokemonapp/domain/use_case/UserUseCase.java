package com.idzodev.pokemonapp.domain.use_case;

import com.idzodev.pokemonapp.ui.dvo.MapDataDvo;
import com.idzodev.pokemonapp.ui.dvo.ZombiesDvo;

import rx.Observable;

/**
 * Created by vladimir on 12.08.16.
 */
public interface UserUseCase {
    Observable<MapDataDvo> getMapData(int mode, double lat, double lon);
    Observable<Boolean> generateEntities( double lat, double lon);
}
