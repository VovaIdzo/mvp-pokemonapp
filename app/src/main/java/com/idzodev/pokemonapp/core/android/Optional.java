package com.idzodev.pokemonapp.core.android;

/**
 * Created by vladimir on 02.06.16.
 */
public class Optional<T> {

    T fragment;

    public Optional(T fragment) {
        this.fragment = fragment;
    }

    public boolean isFragment(){
        return fragment != null;
    }

    public T get() {
        return fragment;
    }
}
