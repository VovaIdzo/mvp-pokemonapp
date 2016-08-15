package com.idzodev.pokemonapp.core.android;


import com.idzodev.pokemonapp.core.mvp.Presenter;
import com.idzodev.pokemonapp.core.mvp.View;

/**
 * Created by vladimir on 02.06.16.
 */
public class MVPBaseFragment<F extends Presenter> extends BaseFragment implements View<F> {

    private F presenter;

    @Override
    public void attachPresenter(F f) {
        presenter = f;
    }

    @Override
    public void detachPresenter() {
        if (presenter != null)
            presenter.detachView();
        presenter = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachPresenter();
    }

    public F getPresenter() {
        return presenter;
    }
}
