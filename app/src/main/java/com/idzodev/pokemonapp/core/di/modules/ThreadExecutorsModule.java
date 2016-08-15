package com.idzodev.pokemonapp.core.di.modules;


import com.idzodev.pokemonapp.core.executors.PostExecutionThread;
import com.idzodev.pokemonapp.core.executors.ThreadExecutor;
import com.idzodev.pokemonapp.domain.executors.JobExecutor;
import com.idzodev.pokemonapp.domain.executors.UIThread;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vladimir on 02.06.16.
 */
@Module
public class ThreadExecutorsModule {

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecuter(){
        return new JobExecutor();
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread (){
        return new UIThread();
    }


}
