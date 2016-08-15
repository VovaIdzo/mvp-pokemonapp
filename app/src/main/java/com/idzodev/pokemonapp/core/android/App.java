package com.idzodev.pokemonapp.core.android;

import android.app.Application;
import android.content.Context;

import com.idzodev.pokemonapp.R;
import com.idzodev.pokemonapp.core.di.components.AppComponent;
import com.idzodev.pokemonapp.core.di.components.DaggerAppComponent;
import com.idzodev.pokemonapp.core.di.modules.AppModule;
import com.idzodev.pokemonapp.core.di.modules.DataModule;
import com.idzodev.pokemonapp.core.di.modules.ThreadExecutorsModule;


/**
 * Created by vladimir on 25.07.16.
 */
public class App extends Application{

    private AppComponent appComponent;

    public static App getApp(Context context) {
        return (App) context.getApplicationContext();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupAppComponent();
        setupPicasso();
    }

    private void setupAppComponent(){
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
//                .apiModule(new ApiModule(getString(R.string.baseUrl)))
                .dataModule(new DataModule())
                .threadExecutorsModule(new ThreadExecutorsModule())
                .build();
    }

    private void setupPicasso(){

    }
}
