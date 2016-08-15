package com.idzodev.pokemonapp.ui.di;

import com.idzodev.pokemonapp.core.android.App;
import com.idzodev.pokemonapp.core.di.scope.PerActivity;
import com.idzodev.pokemonapp.domain.use_case.UserUseCase;
import com.idzodev.pokemonapp.services.location.FusedLocationProvider;
import com.idzodev.pokemonapp.ui.screens.home.HomeActivity;
import com.idzodev.pokemonapp.ui.screens.home.HomePresenter;
import com.idzodev.pokemonapp.ui.screens.home.HomePresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

/**
 * Created by vladimir on 15.08.16.
 */
@Subcomponent(modules = {HomeComponent.Module.class})
@PerActivity
public interface HomeComponent {
    void inject(HomeActivity homeActivity);

    @dagger.Module
    class Module{
        @Provides
        @PerActivity
        public HomePresenter provideHomePresenter(UserUseCase userUseCase){
            return new HomePresenterImpl(userUseCase);
        }

        @Singleton
        @PerActivity
        public FusedLocationProvider providerFusedLocationProvider(App app){
            return new FusedLocationProvider(app);
        }
    }
}
