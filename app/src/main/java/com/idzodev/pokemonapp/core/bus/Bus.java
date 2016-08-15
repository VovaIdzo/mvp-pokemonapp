package com.idzodev.pokemonapp.core.bus;


import com.idzodev.pokemonapp.core.executors.PostExecutionThread;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by vladimir on 19.06.16.
 */
public class Bus {
    private EventBus eventBus;
    private PostExecutionThread postExecutionThread;

    public Bus(EventBus eventBus, PostExecutionThread postExecutionThread) {
        this.eventBus = eventBus;
        this.postExecutionThread = postExecutionThread;
    }

    public void post(Object event){
        postExecutionThread.getScheduler().createWorker().schedule(() -> eventBus.post(event));
    }

    public void register(Object o){
        eventBus.register(o);
    }

    public void unRegister(Object o){
        eventBus.unregister(o);
    }
}
