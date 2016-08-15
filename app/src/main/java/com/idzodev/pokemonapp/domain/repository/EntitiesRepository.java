package com.idzodev.pokemonapp.domain.repository;

import com.idzodev.pokemonapp.domain.entities.MainEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by vladimir on 12.08.16.
 */
public interface EntitiesRepository {
    int USER_RADIUS = 1000;
    int MAX_RADIUS = 20*1000;

    Observable<List<MainEntity>> getEntities(int type, double myLat, double myLon);
    Observable<Boolean> generateEntities(double myLat, double myLon);
}
