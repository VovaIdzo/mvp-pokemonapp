package com.idzodev.pokemonapp.domain.repository.impl;

import android.content.Context;

import com.idzodev.pokemonapp.core.android.App;
import com.idzodev.pokemonapp.core.android.BasePreference;
import com.idzodev.pokemonapp.domain.repository.PreferenceRepository;

import javax.inject.Inject;

/**
 * Created by vladimir on 12.08.16.
 */
public class PreferenceRepositoryImpl extends BasePreference implements PreferenceRepository {
    private static final String IS_FIRST_START = "IS_FIRST_START";

    private App app;

    @Inject
    public PreferenceRepositoryImpl(App app) {
        this.app = app;
    }

    @Override
    protected Context getContext() {
        return app;
    }

    @Override
    public boolean isFirstStart() {
        return getLong(IS_FIRST_START) == 1;
    }

    @Override
    public void setFirstStart(boolean isFirstStart) {
        save(IS_FIRST_START, isFirstStart ? 1l : 0l);
    }
}
