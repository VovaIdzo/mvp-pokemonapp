package com.idzodev.pokemonapp.ui.screens.home;

import com.idzodev.pokemonapp.core.mvp.Presenter;

/**
 * Created by vladimir on 15.08.16.
 */
public interface HomePresenter extends Presenter<HomeView> {
    void onMyLocationSelected(double lat, double lon);
    boolean isMyLocationSelected();

    void onPokemonClicked();
    void onZombiClicked();
}
