package com.idzodev.pokemonapp.core.di.components;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
/**
 * Created by vladimir on 27.07.16.
 */
@Subcomponent(modules = {ServicesComponent.ServicesModule.class})
@Singleton
public interface ServicesComponent {
    @Module
    class ServicesModule {
    }
}
