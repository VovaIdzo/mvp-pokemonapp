package com.idzodev.pokemonapp.domain.use_case.impl;

import com.idzodev.pokemonapp.core.executors.PostExecutionThread;
import com.idzodev.pokemonapp.core.executors.ThreadExecutor;
import com.idzodev.pokemonapp.domain.entities.MainEntity;
import com.idzodev.pokemonapp.domain.repository.EntitiesRepository;
import com.idzodev.pokemonapp.domain.use_case.UserUseCase;
import com.idzodev.pokemonapp.ui.dvo.EntityDvo;
import com.idzodev.pokemonapp.ui.dvo.MapDataDvo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by vladimir on 12.08.16.
 */
public class UserUseCaseImpl implements UserUseCase {

    private EntitiesRepository entitiesRepository;
    private PostExecutionThread postExecutionThread;
    private ThreadExecutor threadExecutor;

    @Inject
    public UserUseCaseImpl(EntitiesRepository entitiesRepository, PostExecutionThread postExecutionThread, ThreadExecutor threadExecutor) {
        this.entitiesRepository = entitiesRepository;
        this.postExecutionThread = postExecutionThread;
        this.threadExecutor = threadExecutor;
    }

    @Override
    public Observable<MapDataDvo> getMapData(int mode, double lat, double lan) {
        return entitiesRepository.getEntities(mode, lat, lan)
                .map(mainEntities -> {
                    List<EntityDvo> entityDvos = new ArrayList<EntityDvo>(mainEntities.size());
                    for (MainEntity mainEntity : mainEntities){
                        entityDvos.add(new EntityDvo(mainEntity));
                    }
                    return new MapDataDvo(entityDvos);
                })
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

    @Override
    public Observable<Boolean> generateEntities( double lat, double lon) {
        return entitiesRepository.generateEntities(lat, lon)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }
}
