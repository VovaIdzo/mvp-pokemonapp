package com.idzodev.pokemonapp.core.di.modules;

import com.idzodev.pokemonapp.core.android.App;
import com.idzodev.pokemonapp.domain.entities.DaoMaster;
import com.idzodev.pokemonapp.domain.entities.DaoSession;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimir on 12.08.16.
 */
@Module
public class DataModule {

    @Provides
    @Singleton
    DaoSession provideDaoSession(App app){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(app, "pokemon-test", null);
        DaoMaster master = new DaoMaster(helper.getWritableDatabase());
        return master.newSession();
    }
}
