package com.idzodev.pokemonapp.core.mvp;

/**
 * Created by vladimir on 04.06.16.
 */
public interface View<Presenter> {
    void attachPresenter(Presenter presenter);
    void detachPresenter();
}
