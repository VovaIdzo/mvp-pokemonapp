package com.idzodev.pokemonapp.core.android;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.idzodev.pokemonapp.R;
import com.idzodev.pokemonapp.core.bus.EventBus;
import com.idzodev.pokemonapp.core.bus.IgnoreEvent;
import com.idzodev.pokemonapp.core.mvp.Presenter;

import org.greenrobot.eventbus.Subscribe;



/**
 * Created by vladimir on 29.05.16.
 */
public class BaseActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getApp(this).getAppComponent().getBus().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppForegroundStateManager.getInstance().onActivityVisible(this);
    }

    @Override
    protected void onStop() {
        AppForegroundStateManager.getInstance().onActivityNotVisible(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getApp(this).getAppComponent().getBus().unRegister(this);
    }

    protected <F extends MVPBaseFragment> F replaceFragment(Presenter presenter, F fragment){
        Fragment oldFragment = getSupportFragmentManager().findFragmentByTag(fragment.getClass().getName());
        if (oldFragment == null){
            oldFragment = fragment;
            replaceFragment(fragment, fragment.getClass().getName()).commit();
        }
        ((MVPBaseFragment)oldFragment).attachPresenter(presenter);
        return (F)oldFragment;
    }

    protected <T> Optional<T> findFragment(Class<T> tClass){
        return new Optional<>((T) getSupportFragmentManager().findFragmentByTag(tClass.getName()));
    }

    protected FragmentTransaction replaceFragment(Fragment fragment, String tag){
        return getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, tag);
    }

    @Subscribe
    @EventBus
    public void onEvent(IgnoreEvent ignore){

    }

    public boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
