package com.idzodev.pokemonapp.core.mvp;

/**
 * Created by vladimir on 04.06.16.
 */
public interface Presenter<V> {
    void attachView(V view);
    void detachView();
    V getView();
}
