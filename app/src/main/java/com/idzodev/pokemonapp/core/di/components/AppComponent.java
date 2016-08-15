package com.idzodev.pokemonapp.core.di.components;

import com.idzodev.pokemonapp.core.android.App;
import com.idzodev.pokemonapp.core.bus.Bus;
import com.idzodev.pokemonapp.core.di.modules.AppModule;
import com.idzodev.pokemonapp.core.di.modules.DataModule;
import com.idzodev.pokemonapp.core.di.modules.MappersModule;
import com.idzodev.pokemonapp.core.di.modules.RepositoriesModule;
import com.idzodev.pokemonapp.core.di.modules.ThreadExecutorsModule;
import com.idzodev.pokemonapp.core.di.modules.UseCaseModule;
import com.idzodev.pokemonapp.core.executors.PostExecutionThread;
import com.idzodev.pokemonapp.core.executors.ThreadExecutor;
import com.idzodev.pokemonapp.domain.repository.PreferenceRepository;
import com.idzodev.pokemonapp.services.location.FusedLocationProvider;
import com.idzodev.pokemonapp.ui.di.HomeComponent;
import com.idzodev.pokemonapp.ui.di.MapComponent;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vladimir on 25.07.16.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        DataModule.class,
        ThreadExecutorsModule.class,
//        ApiModule.class,
        UseCaseModule.class,
        RepositoriesModule.class,
        MappersModule.class
})
public interface AppComponent {
    ServicesComponent plus(ServicesComponent.ServicesModule module);
    MapComponent plus(MapComponent.Module module);
    HomeComponent plus(HomeComponent.Module module);


    App getApp();
    Bus getBus();
    PreferenceRepository getPreferenceRepository();
    FusedLocationProvider getFusedLocationProvider();
    ThreadExecutor getJobExcecutor();
    PostExecutionThread getUIThread();
}
