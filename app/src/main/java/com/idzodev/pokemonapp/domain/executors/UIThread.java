package com.idzodev.pokemonapp.domain.executors;


import com.idzodev.pokemonapp.core.executors.PostExecutionThread;

import javax.inject.Inject;
import javax.inject.Singleton;


import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by vladimir on 02.06.16.
 */
@Singleton
public class UIThread implements PostExecutionThread {

    @Inject
    public UIThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
