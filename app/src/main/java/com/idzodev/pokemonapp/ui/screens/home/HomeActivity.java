package com.idzodev.pokemonapp.ui.screens.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.idzodev.pokemonapp.R;
import com.idzodev.pokemonapp.core.android.App;
import com.idzodev.pokemonapp.core.android.BaseActivity;
import com.idzodev.pokemonapp.ui.di.HomeComponent;
import com.idzodev.pokemonapp.ui.screens.GetMyLocationActivity;
import com.idzodev.pokemonapp.ui.screens.map.MapActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vladimir on 12.08.16.
 */
public class HomeActivity extends BaseActivity implements HomeView{
    private static final int REQUEST_MY_LOCATION = 1;

    @Inject
    protected HomePresenter homePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        App.getApp(this)
                .getAppComponent()
                .plus(new HomeComponent.Module())
                .inject(this);
        ButterKnife.bind(this);
        homePresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MY_LOCATION && resultCode == RESULT_OK){
            final double lat = data.getDoubleExtra(GetMyLocationActivity.EXTRA_LAT, 0);
            final double lon = data.getDoubleExtra(GetMyLocationActivity.EXTRA_LON, 0);
            homePresenter.onMyLocationSelected(lat, lon);
        }
    }

    @Override
    public void showMapScreen(int type) {
        MapActivity.start(this, type);
    }

    @Override
    public void showGetMyLocationScreen() {
        GetMyLocationActivity.start(this, REQUEST_MY_LOCATION);
    }

    @OnClick(R.id.home_activity_btn_pokemon)
    public void onPokemonClick(){
        homePresenter.onPokemonClicked();
    }

    @OnClick(R.id.home_activity_btn_zombi)
    public void onZombiClick(){
        homePresenter.onZombiClicked();
    }
}
