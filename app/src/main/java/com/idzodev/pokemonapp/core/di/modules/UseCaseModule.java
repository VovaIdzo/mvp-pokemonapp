package com.idzodev.pokemonapp.core.di.modules;

import com.idzodev.pokemonapp.core.android.App;
import com.idzodev.pokemonapp.core.executors.PostExecutionThread;
import com.idzodev.pokemonapp.core.executors.ThreadExecutor;
import com.idzodev.pokemonapp.domain.repository.EntitiesRepository;
import com.idzodev.pokemonapp.domain.use_case.UserUseCase;
import com.idzodev.pokemonapp.domain.use_case.impl.UserUseCaseImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
/**
 * Created by vladimir on 02.06.16.
 */
@Module(includes = {
        RepositoriesModule.class,
        ThreadExecutorsModule.class,
//        ApiModule.class
})
public class UseCaseModule {

    @Provides
    @Singleton
    UserUseCase provideUserUserCase(EntitiesRepository entitiesRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread){
        return new UserUseCaseImpl(entitiesRepository, postExecutionThread, threadExecutor);
    }
}
