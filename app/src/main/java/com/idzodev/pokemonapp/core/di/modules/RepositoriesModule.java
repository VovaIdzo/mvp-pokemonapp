package com.idzodev.pokemonapp.core.di.modules;


import com.idzodev.pokemonapp.core.android.App;
import com.idzodev.pokemonapp.domain.entities.DaoSession;
import com.idzodev.pokemonapp.domain.repository.EntitiesRepository;
import com.idzodev.pokemonapp.domain.repository.PreferenceRepository;
import com.idzodev.pokemonapp.domain.repository.impl.EntitiesRepositoryImpl;
import com.idzodev.pokemonapp.domain.repository.impl.PreferenceRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
/**
 * Created by vladimir on 02.06.16.
 */
@Module(includes = {
        DataModule.class,
        MappersModule.class
})
public class RepositoriesModule {

    @Provides
    @Singleton
    EntitiesRepository provideEntitiesRepository(DaoSession daoSession){
        return new EntitiesRepositoryImpl(daoSession);
    }

    @Provides
    @Singleton
    PreferenceRepository providePreferenceRepository(App app){
        return new PreferenceRepositoryImpl(app);
    }
}
