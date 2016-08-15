package com.idzodev.pokemonapp.core.di.modules;


import com.idzodev.pokemonapp.core.android.App;
import com.idzodev.pokemonapp.core.bus.Bus;
import com.idzodev.pokemonapp.core.executors.PostExecutionThread;
import com.idzodev.pokemonapp.services.location.FusedLocationProvider;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimir on 29.05.16.
 */
@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Singleton
    @Provides
    public App provideAppContext(){
        return app;
    }

    @Singleton
    @Provides
    public Bus provideBus(PostExecutionThread postExecutionThread){
        return new Bus(new EventBus(), postExecutionThread);
    }
}
