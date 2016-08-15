package com.idzodev.pokemonapp.core.rx;

import android.util.Log;

/**
 * Created by vladimir on 02.06.16.
 */
public class SimpleSubscriber<T> extends rx.Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Log.e("error", "", e);
        e.printStackTrace();
    }


    @Override
    public void onNext(T item) {

    }
}
