package com.idzodev.pokemonapp.core.android;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.idzodev.pokemonapp.R;

import butterknife.ButterKnife;

/**
 * Created by vladimir on 07.06.16.
 */
public class BaseFragment extends Fragment {

    public void showProgress(){

    }

    public void hideProgress(){

    }


    protected void setupToolbar(Toolbar toolbar, String text, int icon, android.view.View.OnClickListener onNavigationClickListener){
        ((BaseActivity)getActivity()).setSupportActionBar(toolbar);
        ((BaseActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((BaseActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(text);
        toolbar.setNavigationIcon(icon);
        toolbar.setNavigationOnClickListener(onNavigationClickListener);
    }
}
