package com.idzodev.pokemonapp.ui.screens.home;

import com.idzodev.pokemonapp.core.mvp.BasePresenter;
import com.idzodev.pokemonapp.core.rx.SimpleSubscriber;
import com.idzodev.pokemonapp.domain.entities.MainEntity;
import com.idzodev.pokemonapp.domain.use_case.UserUseCase;

import rx.Observable;
import rx.Subscription;

/**
 * Created by vladimir on 15.08.16.
 */
public class HomePresenterImpl extends BasePresenter<HomeView> implements HomePresenter {
    private UserUseCase userUseCase;

    private boolean isLocationSelected = false;
    private double lat;
    private double lon;
    private int lastSelectedType = -1;

    public HomePresenterImpl(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @Override
    public void onMyLocationSelected(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        isLocationSelected = true;
        if (lastSelectedType != -1){
            generateEntitiesAndShowMap();
        }
    }

    @Override
    public boolean isMyLocationSelected() {
        return isLocationSelected;
    }

    @Override
    public void onPokemonClicked() {
        lastSelectedType = MainEntity.TYPE_POKEMON;
        generateEntitiesAndShowMap();
    }

    @Override
    public void onZombiClicked() {
        lastSelectedType = MainEntity.TYPE_ZOMBI;
        generateEntitiesAndShowMap();
    }

    private void generateEntitiesAndShowMap(){
        if (!isMyLocationSelected()){
            getView().showGetMyLocationScreen();
            return;
        }

        addSubscription(userUseCase.generateEntities(lat, lon).subscribe(new SimpleSubscriber<Boolean>(){
            @Override
            public void onNext(Boolean item) {
                if (getView() == null) return;
                getView().showMapScreen(lastSelectedType);
            }
        }));
    }
}
