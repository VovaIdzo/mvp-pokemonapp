package com.idzodev.pokemonapp.domain.repository;

/**
 * Created by vladimir on 12.08.16.
 */
public interface PreferenceRepository {
    boolean isFirstStart();
    void setFirstStart(boolean isFirstStart);
}
