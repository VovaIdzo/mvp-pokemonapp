package com.idzodev.pokemonapp.ui.di;

import com.idzodev.pokemonapp.core.android.App;
import com.idzodev.pokemonapp.core.di.scope.PerActivity;
import com.idzodev.pokemonapp.domain.use_case.UserUseCase;
import com.idzodev.pokemonapp.services.location.FusedLocationProvider;
import com.idzodev.pokemonapp.ui.screens.map.MapActivity;
import com.idzodev.pokemonapp.ui.screens.map.MapPresenter;
import com.idzodev.pokemonapp.ui.screens.map.MapPresenterImpl;

import javax.inject.Singleton;

import dagger.Provides;
import dagger.Subcomponent;

/**
 * Created by vladimir on 12.08.16.
 */
@Subcomponent(modules = MapComponent.Module.class)
@PerActivity
public interface MapComponent {
    void inject(MapActivity mapActivity);

    @dagger.Module
    class Module{
        private final int type;

        public Module(int type) {
            this.type = type;
        }

        @Provides
        @PerActivity
        MapPresenter provideMapPresenter(App app, UserUseCase userUseCase){
            return new MapPresenterImpl(type, app, userUseCase);
        }

        @Singleton
        @PerActivity
        public FusedLocationProvider providerFusedLocationProvider(App app){
            return new FusedLocationProvider(app);
        }
    }
}
